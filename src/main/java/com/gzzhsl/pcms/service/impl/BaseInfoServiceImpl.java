package com.gzzhsl.pcms.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gzzhsl.pcms.converter.*;
import com.gzzhsl.pcms.entity.*;
import com.gzzhsl.pcms.enums.NotificationTypeEnum;
import com.gzzhsl.pcms.enums.SysEnum;
import com.gzzhsl.pcms.exception.SysException;
import com.gzzhsl.pcms.repository.BaseInfoRepository;
import com.gzzhsl.pcms.repository.NotificationRepository;
import com.gzzhsl.pcms.repository.UserRepository;
import com.gzzhsl.pcms.service.*;
import com.gzzhsl.pcms.shiro.bean.SysRole;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import com.gzzhsl.pcms.util.*;
import com.gzzhsl.pcms.vo.BaseInfoImgVO;
import com.gzzhsl.pcms.vo.BaseInfoManagerIndexVO;
import com.gzzhsl.pcms.vo.BaseInfoVO;
import com.gzzhsl.pcms.vo.ProjectMonthlyReportVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class BaseInfoServiceImpl implements BaseInfoService {

    @Autowired
    private BaseInfoRepository baseInfoRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private OperationLogService operationLogService;
    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private BaseInfoImgService baseInfoImgService;
    @Autowired
    private WebSocket webSocket;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public List<BaseInfo> getAllProject() {
        return baseInfoRepository.findAll();
    }

    @Override
    public BaseInfo save(BaseInfo baseInfo) {
        return baseInfoRepository.save(baseInfo);
    }

    @Override
    public BaseInfoVO getBaseInfoById(String baseInfoId) {
        BaseInfo baseInfo = baseInfoRepository.findByBaseInfoId(baseInfoId);
        BaseInfoVO baseInfoVO = BaseInfo2VO.convert(baseInfo);
        if (baseInfo.getBaseInfoImgs() != null && baseInfo.getBaseInfoImgs().size() > 0) {
            List<BaseInfoImgVO> baseInfoImgVOs = baseInfo.getBaseInfoImgs().stream().map(e -> BaseInfoImg2VO.convert(e)).collect(Collectors.toList());
            baseInfoVO.setBaseInfoImgVOs(baseInfoImgVOs);
        } else {
            List<BaseInfoImgVO> baseInfoImgVOs = new ArrayList<>();
            baseInfoVO.setBaseInfoImgVOs(baseInfoImgVOs);
        }
        /*baseInfoVO.setTotalInvestment(baseInfo.getCentralInvestment().add(baseInfo.getProvincialInvestment().add(baseInfo.getLocalInvestment())));
        baseInfoVO.setTotalAccumulativePayment(baseInfo.getCentralAccumulativePayment().add(baseInfo.getProvincialAccumulativePayment().add(baseInfo.getLocalAccumulativePayment())));
*/
        return baseInfoVO;
    }

    @Override
    public BaseInfo save(BaseInfoVO baseInfoVO) {
      /*  UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        UserInfo thisUser = userService.findByUserId(userInfo.getUserId());
        BaseInfo thisProject = userService.findByUserId(thisUser.getUserId()).getBaseInfo();
        String origId = null;
        if (thisProject != null && thisProject.getState().equals((byte) 1)) {
            log.error("【基本信息错误】 该账户已经存在配置过的项目基本信息，无需重新配置 ");
            throw new SysException(SysEnum.BASE_INFO_DUPLICATED);
        }
        if (thisProject != null) {
            origId = thisProject.getBaseInfoId();
            // 需要把之前的baseinfoimg们都删除掉
            baseInfoImgService.deleteByBaseInfo(thisProject);
        }
        thisProject = BaseInfoVO2BaseInfo.convert(baseInfoVO);
        thisProject.setOwner(thisUser.getUsername());
        thisProject.setBaseInfoId(origId);
        BaseInfo baseInfoRt = null;

        if (baseInfoVO.getRtFileTempPath() == null || baseInfoVO.getRtFileTempPath() == "") {
            // 没有上传图片的情况，直接对表格进行存储
            baseInfoRt = baseInfoRepository.save(thisProject);
        } else {
            // 上传图片的情况，考虑转存
            String rtFileTempPath = baseInfoVO.getRtFileTempPath();
            baseInfoRt = baseInfoRepository.save(thisProject);
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
                    baseInfoImg.setBaseInfo(baseInfoRt);
                    baseInfoImgs.add(baseInfoImg);
                }
            }
            baseInfoRt.setBaseInfoImgs(baseInfoImgs);
            baseInfoRt = baseInfoRepository.save(baseInfoRt);
        }

        // 把通知提醒也一并存入数据库
        Notification notification = new Notification();
        notification.setCreateTime(new Date());
        notification.setSubmitter(thisUser.getUsername());
        notification.setType(NotificationTypeEnum.PROJECT_BASIC_INFO.getMsg());
        notification.setBaseInfoId(baseInfoRt.getBaseInfoId());
        notification.setTypeId(baseInfoRt.getBaseInfoId()); // 这里是项目基础信息ID
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        notification.setYearmonth(formatter.format(baseInfoRt.getUpdateTime()));
        notification.setChecked(false);
        notification.setUrl("/baseinfo/getinbaseinfo");
        notificationRepository.save(notification);
        operationLogService.save(OperationUtil.buildOperationLog(thisUser.getUserId(),
                baseInfoRt.getUpdateTime(),
                "提交了ID:"+ baseInfoRt.getBaseInfoId() +"的水库项目基本信息"));
        // 基础信息存库后，将基础信息添加到userinfo
        List<SysRole> roles = thisUser.getSysRoleList();
        if (thisUser.getChildren() != null && thisUser.getChildren().size()==0 && roles.size() == 1 && "reporter".equals(roles.get(0).getRole())) { // 如果是reporter账号...
            if (thisUser.getParent() != null && thisUser.getParent().getUserId() != "" && thisUser.getParent().getUserId() != null ) {
                List<UserInfo> children = thisUser.getParent().getChildren();
                List<UserInfo> parentAndChildren = new ArrayList<UserInfo>();
                for (UserInfo child : children) {
                    parentAndChildren.add(child);
                }
                parentAndChildren.add(thisUser.getParent()); // 儿子们加粑粑
                for (UserInfo eachUser : parentAndChildren) {
                    eachUser.setBaseInfo(baseInfoRt);
                }
                baseInfoRt.setUserInfoList(parentAndChildren);
            }
        } else {
            for (SysRole role : roles) {
                if ("checker".equals(role.getRole())) { // 如果是checker账号...
                    if (thisUser.getChildren() != null) {
                        List<UserInfo> children = thisUser.getChildren();
                        List<UserInfo> childrenAndI = new ArrayList<>();
                        for (UserInfo child : children) {
                            childrenAndI.add(child);
                        }
                        childrenAndI.add(thisUser); // 儿子们加粑粑
                        for (UserInfo person : childrenAndI) {
                            person.setBaseInfo(baseInfoRt);
                        }
                        baseInfoRt.setUserInfoList(childrenAndI);
                    }
                }
            }
        }
        baseInfoRt = baseInfoRepository.save(baseInfoRt);
        // 创建webSocket消息
        WebSocketUtil.sendWSNotificationMsg(thisUser, webSocket, "项目基础信息", "新的项目基础信息消息");
        // 将更新后的所有水库信息重新同步至redis
        List<BaseInfo> baseInfos = this.getAllProject();
        List<BaseInfoManagerIndexVO> baseInfoManagerIndexVOs = baseInfos.stream().map(e -> BaseInfo2ManagerIndexVO.convert(e)).collect(Collectors.toList());
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(baseInfoManagerIndexVOs);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        stringRedisTemplate.opsForValue().set("allBaseInfo", jsonString);*/
        return null;
    }

    @Override
    public Feedback approveBaseInfo(UserInfo thisUser, Boolean switchState, String checkinfo, String baseInfoId) {
        Feedback feedbackRt = null;
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
        WebSocketUtil.sendWSFeedbackMsg(thisUser, webSocket, "基础信息", "新的基础信息审批消息");
        return feedbackRt;
    }

    @Override
    public BaseInfo findBaseInfoById(String baseInfoId) {
        return baseInfoRepository.findByBaseInfoId(baseInfoId);
    }

    @Override
    public List<BaseInfoVO> findByRegionId(Integer regionId) {
        List<BaseInfo> baseInfos = baseInfoRepository.findByRegionId(regionId);
        List<BaseInfoVO> baseInfoVOs = baseInfos.stream().map(e -> BaseInfo2VO.convert(e)).collect(Collectors.toList());
        return baseInfoVOs;
    }
}
