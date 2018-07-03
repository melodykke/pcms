package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.converter.BaseInfo2VO;
import com.gzzhsl.pcms.converter.BaseInfoImg2VO;
import com.gzzhsl.pcms.converter.BaseInfoVO2BaseInfo;
import com.gzzhsl.pcms.converter.MonthlyReportVO2MonthlyReport;
import com.gzzhsl.pcms.entity.*;
import com.gzzhsl.pcms.enums.NotificationTypeEnum;
import com.gzzhsl.pcms.enums.SysEnum;
import com.gzzhsl.pcms.exception.SysException;
import com.gzzhsl.pcms.repository.BaseInfoRepository;
import com.gzzhsl.pcms.repository.NotificationRepository;
import com.gzzhsl.pcms.repository.UserRepository;
import com.gzzhsl.pcms.service.BaseInfoService;
import com.gzzhsl.pcms.service.FeedbackService;
import com.gzzhsl.pcms.service.OperationLogService;
import com.gzzhsl.pcms.service.UserService;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import com.gzzhsl.pcms.util.FeedbackUtil;
import com.gzzhsl.pcms.util.OperationUtil;
import com.gzzhsl.pcms.util.PathUtil;
import com.gzzhsl.pcms.vo.BaseInfoImgVO;
import com.gzzhsl.pcms.vo.BaseInfoVO;
import com.gzzhsl.pcms.vo.ProjectMonthlyReportVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

/*    @Autowired
    private UserRepository userRepository;

    @Override
    public Boolean connectBaseInfoAndUserInfo() {
        List<UserInfo> userInfos = userRepository.findAll();
        List<BaseInfo> baseInfos = baseInfoRepository.findAll();
        for (UserInfo userInfo : userInfos) {
            for (BaseInfo baseInfo : baseInfos) {
                if (userInfo.getName().equals(baseInfo.getPlantName())) {
                    userInfo.setBaseInfo(baseInfo);
                }
            }
        }
        return true;
    }*/
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
        baseInfoVO.setTotalInvestment(baseInfo.getCentralInvestment().add(baseInfo.getProvincialInvestment().add(baseInfo.getLocalInvestment())));
        baseInfoVO.setTotalAccumulativePayment(baseInfo.getCentralAccumulativePayment().add(baseInfo.getProvincialAccumulativePayment().add(baseInfo.getLocalAccumulativePayment())));

        return baseInfoVO;
    }

    @Override
    public BaseInfo save(BaseInfoVO baseInfoVO) {
        UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        BaseInfo thisProject = thisUser.getBaseInfo();
        String origId = null;
        if (thisProject != null && thisProject.getState().equals((byte) 1)) {
            log.error("【基本信息错误】 该账户已经存在配置过的项目基本信息，无需重新配置 ");
            throw new SysException(SysEnum.BASE_INFO_DUPLICATED);
        }
        if (thisProject != null) {
            origId = thisProject.getBaseInfoId();
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
        notification.setTypeId(baseInfoRt.getBaseInfoId()); // 这里是月报ID
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        notification.setYearmonth(formatter.format(baseInfoRt.getUpdateTime()));
        notification.setChecked(false);
        notification.setTypeId(baseInfoRt.getBaseInfoId());
        notification.setUrl("/baseinfo/getbaseinfo");
        notificationRepository.save(notification);
        operationLogService.save(OperationUtil.buildOperationLog(thisUser.getUserId(),
                baseInfoRt.getUpdateTime(),
                "提交了ID:"+ baseInfoRt.getBaseInfoId() +"的水库项目基本信息"));
        thisUser.setBaseInfo(baseInfoRt);
        userService.save(thisUser);
        return baseInfoRt;
    }

    @Override
    public Feedback approveBaseInfo(UserInfo thisUser, Boolean switchState, String checkinfo, String baseInfoId) {
        Feedback feedbackRt = null;
        if (switchState == false) {
            BaseInfo baseInfoRt = baseInfoRepository.findByBaseInfoId(baseInfoId);
            baseInfoRt.setState((byte) 1); // 审批通过
            BaseInfo baseInfoRtRt = baseInfoRepository.save(baseInfoRt);
            Feedback feedback = FeedbackUtil.buildFeedback(thisUser.getUserId(),"项目基本信息", baseInfoId, new Date(),
                    "审批通过", (byte) 1);
            feedbackRt = feedbackService.save(feedback);
            operationLogService.save(OperationUtil.buildOperationLog(thisUser.getUserId(), feedbackRt.getCreateTime(), "审批通过了ID为"+feedbackRt.getTargetId()+"的项目基本信息"));
        } else {
            BaseInfo baseInfoRt = baseInfoRepository.findByBaseInfoId(baseInfoId);
            baseInfoRt.setState((byte) -1); // 审批未通过
            BaseInfo baseInfoRtRt = baseInfoRepository.save(baseInfoRt);
            Feedback feedback = FeedbackUtil.buildFeedback(thisUser.getUserId(), "项目基本信息",baseInfoId, new Date(),
                    "审批未通过：" + checkinfo, (byte) -1);
            feedbackRt = feedbackService.save(feedback);
            operationLogService.save(OperationUtil.buildOperationLog(thisUser.getUserId(), feedbackRt.getCreateTime(), "审批未通过ID为"+feedbackRt.getTargetId()+"的项目基本信息"));
        }
        return feedbackRt;
    }
}
