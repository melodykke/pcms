package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.converter.PreProgressEntriesSetPreProgress;
import com.gzzhsl.pcms.entity.*;
import com.gzzhsl.pcms.enums.NotificationTypeEnum;
import com.gzzhsl.pcms.enums.SysEnum;
import com.gzzhsl.pcms.exception.SysException;
import com.gzzhsl.pcms.repository.PreProgressDefaultRepository;
import com.gzzhsl.pcms.repository.PreProgressEntryRepository;
import com.gzzhsl.pcms.repository.PreProgressRepository;
import com.gzzhsl.pcms.service.*;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import com.gzzhsl.pcms.util.FeedbackUtil;
import com.gzzhsl.pcms.util.OperationUtil;
import com.gzzhsl.pcms.util.PathUtil;
import com.gzzhsl.pcms.util.WebSocketUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class PreProgressServiceImpl implements PreProgressService {
    @Autowired
    private PreProgressDefaultRepository preProgressDefaultRepository;
    @Autowired
    private PreProgressRepository preProgressRepository;
    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private OperationLogService operationLogService;
    @Autowired
    private PreProgressImgService preProgressImgService;
    @Autowired
    private PreProgressEntryRepository preProgressEntryRepository;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private WebSocket webSocket;
    @Override
    public PreProgress save(List<PreProgressEntry> preProgressEntries, String rtFileTempPath) {
        UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        BaseInfo thisProject = thisUser.getBaseInfo();
        PreProgress preProgress =  preProgressRepository.findByBaseInfo(thisProject);
        if (preProgress != null && preProgress.getPreProgressId() != null && !preProgress.getState().equals((byte) 1)) {
            // 如果是重新提交，即存在之前的preProgress 则沿用ID，并且将该preProgress下的img和entries删除
            preProgress.setState((byte) 0);
            preProgress.setUpdateTime(new Date());
            preProgress.setRepeatTimes(preProgress.getRepeatTimes()+1);
            if (preProgress.getPreProgressImgs() != null || preProgress.getPreProgressImgs().size() > 0) {
                preProgressImgService.deleteByPreProgress(preProgress);
            }
            if (preProgress.getPreProgressEntries() != null || preProgress.getPreProgressEntries().size() > 0) {
                preProgressEntryRepository.deleteByPreProgress(preProgress);
            }
        } else {
            preProgress = new PreProgress();
            preProgress.setBaseInfo(thisUser.getBaseInfo());
            preProgress.setOwner(thisUser.getUsername());
            preProgress.setState((byte) 0);
            preProgress.setRepeatTimes(0);
            preProgress.setCreateTime(new Date());
            preProgress.setUpdateTime(new Date());
        }
        PreProgress preProgressRt = preProgressRepository.save(preProgress);
        List<PreProgressEntry> preProgressEntriesWithPreProgress = preProgressEntries.stream().map(e -> PreProgressEntriesSetPreProgress.set(preProgressRt, e)).collect(Collectors.toList());
        preProgressRt.setPreProgressEntries(preProgressEntriesWithPreProgress);
        if (rtFileTempPath != null) {
            File sourceDestFolder = new File(PathUtil.getFileBasePath(true) + rtFileTempPath);
            String targetDest = PathUtil.getFileBasePath(false) + PathUtil.getPreProgressImagePath(thisProject.getPlantName());
            File[] sourceFiles = sourceDestFolder.listFiles();
            List<PreProgressImg> preProgressImgs = new ArrayList<>();
            if (sourceFiles.length > 0) {
                for (File sourceFile : sourceFiles) {
                    File targetFile = new File(targetDest + sourceFile.getName());
                    if (!targetFile.exists()) {
                        targetFile.getParentFile().mkdirs();
                    }
                    String imgRelativePath = PathUtil.getPreProgressImagePath(thisProject.getPlantName()) + sourceFile.getName();
                    sourceFile.renameTo(targetFile);
                    // 把存储到新地址的图片的相对路径拿出来构建月报图片对象。
                    PreProgressImg preProgressImg = new PreProgressImg();
                    preProgressImg.setCreateTime(new Date());
                    preProgressImg.setImgAddr(imgRelativePath);
                    preProgressImg.setPreProgress(preProgressRt);
                    preProgressImgs.add(preProgressImg);
                }
            }
            preProgressRt.setPreProgressImgs(preProgressImgs);
        }
        PreProgress preProgressRtRt = preProgressRepository.save(preProgressRt);
        // 把通知提醒也一并存入数据库
        Notification notification = new Notification();
        notification.setCreateTime(preProgressRtRt.getUpdateTime());
        notification.setSubmitter(thisUser.getUsername());
        notification.setType(NotificationTypeEnum.PROJECT_PRE_PROGRESS.getMsg());
        notification.setTypeId(preProgressRtRt.getPreProgressId()); // 这里是项目前期信息ID
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        notification.setYearmonth(formatter.format(preProgressRtRt.getUpdateTime()));
        notification.setChecked(false);
        notification.setBaseInfoId(thisUser.getBaseInfo().getBaseInfoId());
        notification.setUrl("/preprogress/topreprogress");
        notificationService.save(notification);
        operationLogService.save(OperationUtil.buildOperationLog(thisUser.getUserId(),
                preProgressRtRt.getUpdateTime(),
                "提交了ID:"+ preProgressRtRt.getPreProgressId() +"的水库项目前期信息"));
        // 创建webSocket消息
        WebSocketUtil.sendWSMsg(thisUser, webSocket, "项目前期信息", "新的项目前期消息待查收");
        return preProgressRtRt;
    }

    @Override
    public List<PreProgressDefault> getAllPreProgressDefault() {
        return preProgressDefaultRepository.findAll();
    }

    @Override
    public PreProgress findByBaseInfo(BaseInfo baseInfo) {
        return preProgressRepository.findByBaseInfo(baseInfo);
    }

    @Override
    public PreProgress findByPreProgressId(String preProgressId) {
        return preProgressRepository.findByPreProgressId(preProgressId);
    }

    @Override
    public Feedback approvePreProgress(UserInfo thisUser, Boolean switchState, String checkinfo, String preProgressId) {
        Feedback feedbackRt = null;
        PreProgress preProgressRt = preProgressRepository.findByPreProgressId(preProgressId);
        if (preProgressRt.getState().equals((byte) 1)) {
            log.error("【项目前期审批错误】 不能审批已通过项目");
            throw new SysException(SysEnum.PRE_PROGRESS_APPROVAL_PASSED_ERROR);
        }
        if (switchState == false) {
            preProgressRt.setState((byte) 1); // 审批通过
            PreProgress preProgressRtRt = preProgressRepository.save(preProgressRt);
            Feedback feedback = FeedbackUtil.buildFeedback(thisUser.getBaseInfo().getBaseInfoId(), thisUser.getUsername(),"项目前期信息", preProgressId, new Date(),
                    "审批通过", (byte) 1);
            feedbackRt = feedbackService.save(feedback);
            operationLogService.save(OperationUtil.buildOperationLog(thisUser.getUserId(), feedbackRt.getCreateTime(), "审批通过了ID为"+feedbackRt.getTargetId()+"的项目前期信息"));
        } else {
            preProgressRt.setState((byte) -1); // 审批未通过
            PreProgress preProgressRtRt = preProgressRepository.save(preProgressRt);
            Feedback feedback = FeedbackUtil.buildFeedback(thisUser.getBaseInfo().getBaseInfoId(), thisUser.getUsername(), "项目前期信息",preProgressId, new Date(),
                    "审批未通过：" + checkinfo, (byte) -1);
            feedbackRt = feedbackService.save(feedback);
            operationLogService.save(OperationUtil.buildOperationLog(thisUser.getUserId(), feedbackRt.getCreateTime(), "审批未通过ID为"+feedbackRt.getTargetId()+"的项目前期信息"));
        }
        return feedbackRt;
    }
}
