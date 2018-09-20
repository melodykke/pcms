package com.gzzhsl.pcms.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gzzhsl.pcms.converter.*;
import com.gzzhsl.pcms.entity.Feedback;
import com.gzzhsl.pcms.enums.NotificationTypeEnum;
import com.gzzhsl.pcms.enums.SysEnum;
import com.gzzhsl.pcms.exception.SysException;
import com.gzzhsl.pcms.mapper.BaseInfoMapper;
import com.gzzhsl.pcms.model.*;
import com.gzzhsl.pcms.repository.BaseInfoRepository;
import com.gzzhsl.pcms.repository.NotificationRepository;
import com.gzzhsl.pcms.service.*;
import com.gzzhsl.pcms.util.*;
import com.gzzhsl.pcms.vo.BaseInfoImgVO;
import com.gzzhsl.pcms.vo.BaseInfoManagerIndexVO;
import com.gzzhsl.pcms.vo.BaseInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class BaseInfoServiceImpl implements BaseInfoService {

    @Autowired
    private BaseInfoMapper baseInfoMapper;
    @Autowired
    private BaseInfoImgService baseInfoImgService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserService userService;






    @Autowired
    private BaseInfoRepository baseInfoRepository;

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private OperationLogService operationLogService;
    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private WebSocket webSocket;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public List<BaseInfo> findAllProject() {
        return baseInfoMapper.selectAll();
    }

    @Override
    public Integer save(BaseInfoVO baseInfoVO) {
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        UserInfo thisUser = userService.findOneWithRolesAndPrivilegesByUsernameOrId(null, userInfo.getUserId());
        BaseInfo thisProject = baseInfoMapper.selectByPrimaryKey(thisUser.getBaseInfoId());
        String origId = null;
        Date createTime = null;
        if (thisProject != null && thisProject.getState().equals((byte) 1)) {
            log.error("【基本信息错误】 该账户已经存在配置过的项目基本信息，无需重新配置 ");
            throw new SysException(SysEnum.BASE_INFO_DUPLICATED);
        }
        if (thisProject != null) {
            origId = thisProject.getBaseInfoId();
            createTime = thisProject.getCreateTime();
            // 需要把之前的baseinfoimg们都删除掉
            baseInfoImgService.batchDeleteByBaseInfoId(thisProject.getBaseInfoId());
        }
        thisProject = BaseInfoVO2BaseInfo.convert(baseInfoVO);
        thisProject.setOwner(thisUser.getUsername());
        if (origId != null || "".equals(origId)) {
            thisProject.setBaseInfoId(origId);
            thisProject.setCreateTime(createTime);
        }
        if (baseInfoVO.getRtFileTempPath() == null || baseInfoVO.getRtFileTempPath() == "") {
            // 没有上传图片的情况，直接对表格进行存储
            Integer resultInt = baseInfoMapper.insert(thisProject);
        } else {
            // 上传图片的情况，考虑转存
            String rtFileTempPath = baseInfoVO.getRtFileTempPath();
            Integer resultInt = baseInfoMapper.insert(thisProject);
            File sourceDestFolder = new File(PathUtil.getFileBasePath(true) + rtFileTempPath);
            String targetDest = PathUtil.getFileBasePath(false) + PathUtil.getBaseInfoImagePath(thisProject.getPlantName());
            File[] sourceFiles = sourceDestFolder.listFiles();
            List<BaseInfoImg> baseInfoImgs = new ArrayList<>();
            if (sourceFiles.length > 0) {
                for (File sourceFile : sourceFiles) {
                    File targetFile = new File(targetDest + sourceFile.getName());
                    if (!targetFile.exists()) {
                        targetFile.getParentFile().mkdirs();
                    }
                    String imgRelativePath = PathUtil.getBaseInfoImagePath(thisProject.getPlantName()) + sourceFile.getName();
                    sourceFile.renameTo(targetFile);
                    // 把存储到新地址的图片的相对路径拿出来构建月报图片对象。
                    BaseInfoImg baseInfoImg = new BaseInfoImg();
                    baseInfoImg.setCreateTime(new Date());
                    baseInfoImg.setImgAddr(imgRelativePath);
                    baseInfoImg.setBaseInfoId(thisProject.getBaseInfoId());
                    baseInfoImgs.add(baseInfoImg);
                }
            }
            baseInfoImgService.batchSaveBaseInfoImgs(baseInfoImgs);
        }
        // 把通知提醒也一并存入数据库
        Notification notification = NotificationUtil.buildNotification(thisUser, NotificationTypeEnum.PROJECT_BASIC_INFO.getMsg(),
                thisProject.getBaseInfoId(), thisProject.getBaseInfoId(),
                thisProject.getUpdateTime(), "/baseinfo/getinbaseinfo");
        notificationService.save(notification);
        operationLogService.save(OperationUtil.buildOperationLog(thisUser.getUserId(), thisProject.getUpdateTime(),
                "提交了ID:"+ thisProject.getBaseInfoId() +"的水库项目基本信息"));
        // 基础信息存库后，将基础信息添加到userinfo
        List<SysRole> roles = thisUser.getRoles();
        if (roles.size() == 1 && "reporter".equals(roles.get(0).getRole())) { // 如果是reporter账号...
            UserInfo parent = userService.findParent(thisUser);
            if (parent != null && parent.getUserId() != null && "".equals(parent.getUserId())) {
                List<UserInfo> children = userService.findChildren(parent);
                List<UserInfo> parentAndChildren = new ArrayList<UserInfo>();
                for (UserInfo child : children) {
                    parentAndChildren.add(child);
                }
                parentAndChildren.add(parent); // 儿子们加粑粑
                userService.batchUpdateBaseInfoId(parentAndChildren, thisProject.getBaseInfoId());
                // 创建webSocket消息
                WebSocketUtil.sendWSNotificationMsg(thisUser, parent, webSocket, "项目基础信息", "新的项目基础信息消息");
            }
        } else {
            if (roles.size() == 1 && "checker".equals(roles.get(0).getRole())) { // 如果是checker账号...
                List<UserInfo> children = userService.findChildren(thisUser);
                if (children != null) {
                    List<UserInfo> childrenAndI = new ArrayList<>();
                    for (UserInfo child : children) {
                        childrenAndI.add(child);
                    }
                    childrenAndI.add(thisUser); // 儿子们加粑粑
                    userService.batchUpdateBaseInfoId(childrenAndI, thisProject.getBaseInfoId());
                }
            }
        }
        // 将更新后的所有水库信息重新同步至redis
        List<BaseInfo> baseInfos = baseInfoMapper.selectAll();
        List<BaseInfoManagerIndexVO> baseInfoManagerIndexVOs = baseInfos.stream().map(e -> BaseInfo2ManagerIndexVO.convert(e)).collect(Collectors.toList());
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(baseInfoManagerIndexVOs);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        stringRedisTemplate.opsForValue().set("allBaseInfo", jsonString);
        return 1;
    }

    @Override
    public Integer save(BaseInfo baseInfo) {
        return baseInfoMapper.insert(baseInfo);
    }


    @Override
    public BaseInfo findBaseInfoById(String baseInfoId) {
        if (baseInfoId == null || "".equals(baseInfoId)) {
            return null;
        }
        return baseInfoMapper.selectByPrimaryKey(baseInfoId);
    }















    @Override
    public BaseInfoVO findBaseInfoVOById(String baseInfoId) {
        BaseInfo baseInfo = baseInfoMapper.selectByPrimaryKey(baseInfoId);
        BaseInfoVO baseInfoVO = BaseInfo2VO.convert(baseInfo);
        List<BaseInfoImg> baseInfoImgs = baseInfoImgService.findBaseInfoImgsByBaseInfoId(baseInfo.getBaseInfoId());
        if (baseInfoImgs != null && baseInfoImgs.size() > 0) {
            List<BaseInfoImgVO> baseInfoImgVOs = baseInfoImgs.stream().map(e -> BaseInfoImg2VO.convert(e)).collect(Collectors.toList());
            baseInfoVO.setBaseInfoImgVOs(baseInfoImgVOs);
        } else {
            List<BaseInfoImgVO> baseInfoImgVOs = new ArrayList<>();
            baseInfoVO.setBaseInfoImgVOs(baseInfoImgVOs);
        }
        return baseInfoVO;
    }

    @Override
    public Feedback approveBaseInfo(com.gzzhsl.pcms.shiro.bean.UserInfo thisUser, Boolean switchState, String checkinfo, String baseInfoId) {
        return null;
    }


    //@Override
    public Feedback approveBaseInfo(UserInfo thisUser, Boolean switchState, String checkinfo, String baseInfoId) {
      /*  Feedback feedbackRt = null;
        BaseInfo baseInfoRt = baseInfoRepository.findByBaseInfoId(baseInfoId);
        if (baseInfoRt.getState().equals((byte) 1)) {
            log.error("【基本信息审批错误】 不能审批已通过项目");
            throw new SysException(SysEnum.BASE_INFO_APPROVAL_PASSED_ERROR);
        }
        if (switchState == false) {

            baseInfoRt.setState((byte) 1); // 审批通过
            BaseInfo baseInfoRtRt = baseInfoRepository.save(baseInfoRt);
            Feedback feedback = FeedbackUtil.buildFeedback(baseInfoRt.getBaseInfoId(), thisUser.getUsername(),"项目基本信息", baseInfoId, new Date(),
                    "审批通过", (byte) 1, "baseinfo/baseinfoshow");
            feedbackRt = feedbackService.save(feedback);
            operationLogService.save(OperationUtil.buildOperationLog(thisUser.getUserId(), feedbackRt.getCreateTime(), "审批通过了ID为"+feedbackRt.getTargetId()+"的项目基本信息"));
        } else {
            baseInfoRt.setState((byte) -1); // 审批未通过
            BaseInfo baseInfoRtRt = baseInfoRepository.save(baseInfoRt);
            Feedback feedback = FeedbackUtil.buildFeedback(baseInfoRt.getBaseInfoId(), thisUser.getUsername(), "项目基本信息",baseInfoId, new Date(),
                    "审批未通过：" + checkinfo, (byte) -1, "baseinfo/baseinfoshow");
            feedbackRt = feedbackService.save(feedback);
            operationLogService.save(OperationUtil.buildOperationLog(thisUser.getUserId(), feedbackRt.getCreateTime(), "审批未通过ID为"+feedbackRt.getTargetId()+"的项目基本信息"));
        }
        // 创建webSocket消息
        WebSocketUtil.sendWSFeedbackMsg(thisUser, webSocket, "基础信息", "新的基础信息审批消息");*/
        return null;
    }

    @Override
    public List<BaseInfoVO> findByRegionId(Integer regionId) {
        List<BaseInfo> baseInfos = baseInfoMapper.findByRegionId(regionId);
        if (baseInfos == null || baseInfos.size() == 0) {
            return new ArrayList<>();
        }
        List<BaseInfoVO> baseInfoVOs = baseInfos.stream().map(e -> BaseInfo2VO.convert(e)).collect(Collectors.toList());
        return baseInfoVOs;
    }
}
