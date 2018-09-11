package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.converter.MonthlyReportVO2MonthlyReport;
import com.gzzhsl.pcms.converter.ProjectMonthlyReport2VO;
import com.gzzhsl.pcms.entity.*;
import com.gzzhsl.pcms.enums.NotificationTypeEnum;
import com.gzzhsl.pcms.enums.SysEnum;
import com.gzzhsl.pcms.exception.SysException;
import com.gzzhsl.pcms.repository.HistoryMonthlyReportExcelStatisticsRepository;
import com.gzzhsl.pcms.repository.NotificationRepository;
import com.gzzhsl.pcms.repository.ProjectMonthlyReportImgRepository;
import com.gzzhsl.pcms.repository.ProjectMonthlyReportRepository;
import com.gzzhsl.pcms.service.FeedbackService;
import com.gzzhsl.pcms.service.OperationLogService;
import com.gzzhsl.pcms.service.ProjectMonthlyReportService;
import com.gzzhsl.pcms.service.UserService;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import com.gzzhsl.pcms.util.*;
import com.gzzhsl.pcms.vo.HistoryMonthlyReportStatisticVO;
import com.gzzhsl.pcms.vo.ProjectMonthlyReportVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class ProjectMonthlyReportServiceImpl implements ProjectMonthlyReportService{

    @Autowired
    private ProjectMonthlyReportRepository projectMonthlyReportRepository;
    @Autowired
    private HistoryMonthlyReportExcelStatisticsRepository historyMonthlyReportExcelStatisticsRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private OperationLogService operationLogService;
    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private UserService userService;
    @Autowired
    private WebSocket webSocket;

    @Override
    public ProjectMonthlyReport save(ProjectMonthlyReportVO projectMonthlyReportVO) {
       /* UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        BaseInfo thisProject = userService.findByUserId(thisUser.getUserId()).getBaseInfo();
        // 如果用户对应的Project不存在，则报错
        if (thisProject == null) {
            log.error("【月报错误】 用户对应Project为空，需首先绑定对应的水库工程");
            throw new SysException(SysEnum.MONTHLY_REPORT_ERROR);
        }
        if (!thisProject.getState().equals((byte) 1)) {
            log.error("【月报错误】 用户对应Project审批状态不为审批通过");
            throw new SysException(SysEnum.MONTHLY_REPORT_BASE_INFO_STATE_ERROR);
        }
        // 在审批状态为“已审批”的状态下，若月报提交月份重复，则不允许提交
        Date monthReportDate = projectMonthlyReportVO.getSubmitDate();
        List<ProjectMonthlyReport> projectMonthlyReports = projectMonthlyReportRepository.findAllByBaseInfo(thisProject);
        for (ProjectMonthlyReport projectMonthlyReport : projectMonthlyReports) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
            String dateString = simpleDateFormat.format(projectMonthlyReport.getSubmitDate());
            String monthReportDateString = simpleDateFormat.format(monthReportDate);
            if (dateString.equals(monthReportDateString)) {
                if (projectMonthlyReport.getState() == 1 || projectMonthlyReport.getState() == null) {
                    log.error("【月报错误】 插入月报与某已存在月报月份一致，并且该已存在月报状态为空或已审批");
                    throw new SysException(SysEnum.MONTHLY_REPORTS_INSERT_ERROR);
                } else {
                    projectMonthlyReportRepository.delete(projectMonthlyReport);
                }
            }
        }
        ProjectMonthlyReport projectMonthlyReport = MonthlyReportVO2MonthlyReport.convert(projectMonthlyReportVO, thisUser);

        if (projectMonthlyReportVO.getRtFileTempPath() == null || projectMonthlyReportVO.getRtFileTempPath() == "") {
            // 没有上传图片的情况，直接对表格进行存储
            projectMonthlyReport.setBaseInfo(thisProject);
            ProjectMonthlyReport projectMonthlyReportRt = projectMonthlyReportRepository.save(projectMonthlyReport);
            // 把通知提醒也一并存入数据库
            Notification notification = new Notification();
            notification.setCreateTime(new Date());
            notification.setSubmitter(projectMonthlyReportRt.getSubmitter());
            notification.setType(NotificationTypeEnum.MONTHLY_REPORT.getMsg());
            notification.setTypeId(projectMonthlyReportRt.getProjectMonthlyReportId()); // 这里是月报ID
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
            notification.setYearmonth(formatter.format(projectMonthlyReportRt.getSubmitDate()));
            notification.setChecked(false);
            notification.setBaseInfoId(thisUser.getBaseInfo().getBaseInfoId());
            notification.setUrl("/monthlyreport/projectmonthlyreportshow");
            notificationRepository.save(notification);
            operationLogService.save(OperationUtil.buildOperationLog(thisUser.getUserId(),
                    projectMonthlyReportRt.getCreateTime(),
                    "提交了没有附件的"+projectMonthlyReportRt.getSubmitDate()+"月报. ID:"+projectMonthlyReportRt.
                            getProjectMonthlyReportId()));
            // 创建webSocket消息
            WebSocketUtil.sendWSNotificationMsg(thisUser, webSocket, "月报", "新的月报消息");
            return projectMonthlyReportRt;
        } else {
            // 上传图片的情况，考虑转存
            String rtFileTempPath = projectMonthlyReportVO.getRtFileTempPath();
            projectMonthlyReport.setBaseInfo(thisProject);
            ProjectMonthlyReport projectMonthlyReportRt = projectMonthlyReportRepository.save(projectMonthlyReport);
            Calendar cal = Calendar.getInstance();
            cal.setTime(projectMonthlyReportRt.getSubmitDate());
            String date = String.valueOf(cal.get(Calendar.YEAR)) + "/" + String.valueOf(cal.get(Calendar.MONTH) + 1);
            File sourceDestFolder = new File(PathUtil.getFileBasePath(true) + rtFileTempPath);
            String targetDest = PathUtil.getFileBasePath(false) + PathUtil.getMonthlyReportImagePath(thisProject.getPlantName(), date);
            File[] sourceFiles = sourceDestFolder.listFiles();
            List<ProjectMonthlyReportImg> projectMonthlyReportImgs = new ArrayList<>();
            if (sourceFiles.length > 0) {
                for (File sourceFile : sourceFiles) {
                    File targetFile = new File(targetDest + sourceFile.getName());
                    if (!targetFile.exists()) {
                        targetFile.getParentFile().mkdirs();
                    }
                    String imgRelativePath = PathUtil.getMonthlyReportImagePath(thisProject.getPlantName(), date) + sourceFile.getName();
                    sourceFile.renameTo(targetFile);
                   // 把存储到新地址的图片的相对路径拿出来构建月报图片对象。
                    ProjectMonthlyReportImg projectMonthlyReportImg = new ProjectMonthlyReportImg();
                    projectMonthlyReportImg.setCreateTime(new Date());
                    projectMonthlyReportImg.setImgAddr(imgRelativePath);
                    projectMonthlyReportImg.setProjectMonthlyReport(projectMonthlyReport);
                    projectMonthlyReportImgs.add(projectMonthlyReportImg);
                }
            }
            projectMonthlyReport.setProjectMonthlyReportImgList(projectMonthlyReportImgs);
            ProjectMonthlyReport projectMonthlyReportRtWithImg = projectMonthlyReportRepository.save(projectMonthlyReport);
            // 把通知提醒也一并存入数据库
            Notification notification = new Notification();
            notification.setCreateTime(new Date());
            notification.setSubmitter(projectMonthlyReportRt.getSubmitter());
            notification.setType(NotificationTypeEnum.MONTHLY_REPORT.getMsg());
            notification.setTypeId(projectMonthlyReportRt.getProjectMonthlyReportId()); // 这里是月报ID
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
            notification.setYearmonth(formatter.format(projectMonthlyReportRt.getSubmitDate()));
            notification.setChecked(false);
            notification.setBaseInfoId(thisUser.getBaseInfo().getBaseInfoId());
            notification.setUrl("/monthlyreport/projectmonthlyreportshow");
            notificationRepository.save(notification);
            operationLogService.save(OperationUtil.buildOperationLog(thisUser.getUserId(),
                    projectMonthlyReportRt.getCreateTime(),
                    "提交了带附件的"+projectMonthlyReportRt.getSubmitDate()+"月报. ID:"+projectMonthlyReportRt.
                            getProjectMonthlyReportId()));
            // 创建webSocket消息
            WebSocketUtil.sendWSNotificationMsg(thisUser, webSocket, "月报", "新的月报消息");
            return projectMonthlyReportRtWithImg;

        }*/
         return null;
    }

    @Override
    public ProjectMonthlyReport save(ProjectMonthlyReport projectMonthlyReport) {
        return projectMonthlyReportRepository.save(projectMonthlyReport);
    }

    /**
     * 获取某时段内特定工程的月报
     * @param projectId
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public List<ProjectMonthlyReport> getMonthlyReportsByProjectIdAndYear(String baseInfoId, String startDate, String endDate) {
        List<ProjectMonthlyReport> projectMonthlyReports = null;
        Specification<ProjectMonthlyReport> querySpecification = new Specification<ProjectMonthlyReport>() {
            @Override
            public Predicate toPredicate(Root<ProjectMonthlyReport> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(cb.notEqual(root.get("state").as(Byte.class), (byte) -1));
                if (StringUtils.isNotBlank(startDate)) {
                    //大于等于传入开始时间
                    predicates.add(cb.greaterThanOrEqualTo(root.get("submitDate").as(String.class), startDate));
                }
                if (StringUtils.isNotBlank(endDate)) {
                    //小于或等于传入时间
                    predicates.add(cb.lessThanOrEqualTo(root.get("submitDate").as(String.class), endDate));
                }
                if (StringUtils.isNotBlank(baseInfoId)) {
                    predicates.add(cb.equal(root.join("baseInfo").get("baseInfoId").as(String.class), baseInfoId));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Sort sort = new Sort(Sort.Direction.DESC,"submitDate");
        projectMonthlyReports = projectMonthlyReportRepository.findAll(querySpecification, sort);
        return projectMonthlyReports;
    }

    @Override
    @Transactional
    public ProjectMonthlyReport getByProjectMonthlyReportId(String projectMonthlyReportId) {
        return projectMonthlyReportRepository.findByProjectMonthlyReportId(projectMonthlyReportId);
    }

    @Override
    public List<ProjectMonthlyReport> findByProjectIdAndState(String projectId, byte state) {
        List<ProjectMonthlyReport> projectMonthlyReports = null;
        Specification<ProjectMonthlyReport> querySpecification = new Specification<ProjectMonthlyReport>() {
            @Override
            public Predicate toPredicate(Root<ProjectMonthlyReport> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(cb.equal(root.join("project").get("projectId").as(String.class), projectId));
                predicates.add(cb.equal(root.get("state").as(Byte.class), state));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Sort sort = new Sort(Sort.Direction.DESC, "submitDate");
        return projectMonthlyReportRepository.findAll(querySpecification, sort);
    }

    @Override
    public Feedback approveMonthlyReport(UserInfo thisUser, Boolean switchState, String checkinfo, String projectMonthlyReportId, ProjectMonthlyReport projectMonthlyReportRt) {
        Feedback feedbackRt = null;
        if (switchState == false) {
            projectMonthlyReportRt.setState((byte) 1); // 审批通过
            ProjectMonthlyReport projectMonthlyReportRtRt = projectMonthlyReportRepository.save(projectMonthlyReportRt);
            Feedback feedback = FeedbackUtil.buildFeedback(thisUser.getBaseInfo().getBaseInfoId(), thisUser.getUsername(),"月报", projectMonthlyReportId, new Date(),
                    "审批通过", (byte) 1, "/monthlyreport/projectmonthlyreportshow");
            feedbackRt = feedbackService.save(feedback);
            operationLogService.save(OperationUtil.buildOperationLog(thisUser.getUserId(), feedbackRt.getCreateTime(), "审批通过了ID为"+feedbackRt.getTargetId()+"的月报"));
        } else {
            projectMonthlyReportRt.setState((byte) -1); // 审批未通过
            ProjectMonthlyReport projectMonthlyReportRtRt = projectMonthlyReportRepository.save(projectMonthlyReportRt);
            Feedback feedback = FeedbackUtil.buildFeedback(thisUser.getBaseInfo().getBaseInfoId(), thisUser.getUsername(), "月报",projectMonthlyReportId, new Date(),
                    "审批未通过：" + checkinfo, (byte) -1, "/monthlyreport/projectmonthlyreportshow");
            feedbackRt = feedbackService.save(feedback);
            operationLogService.save(OperationUtil.buildOperationLog(thisUser.getUserId(), feedbackRt.getCreateTime(), "审批未通过ID为"+feedbackRt.getTargetId()+"的月报"));
        }
        // 创建webSocket消息
        WebSocketUtil.sendWSFeedbackMsg(thisUser, webSocket, "月报", "新的审批消息");
        return feedbackRt;
    }

    @Override
    public HistoryMonthlyReportExcelStatistics saveHistoryStatistic(HistoryMonthlyReportStatisticVO historyMonthlyReportStatisticVO) {
       /* UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        BaseInfo thisProject = userService.findByUserId(thisUser.getUserId()).getBaseInfo();
        HistoryMonthlyReportExcelStatistics historyMonthlyReportExcelStatistics = thisProject.getHistoryMonthlyReportExcelStatistics();
        if (historyMonthlyReportExcelStatistics != null) { // 之前就报过历史数据或未审批或审批未通过
            if (historyMonthlyReportExcelStatistics.getState().equals((byte) 1)) {
                log.error("【月报错误】 历史数据已设置并且已经被审批通过，无法更改！");
                throw new SysException(SysEnum.HISTORY_UNABLE_MODIFY_ERROR);
            }
            historyMonthlyReportStatisticVO.setHId(historyMonthlyReportExcelStatistics.getHId());
            historyMonthlyReportStatisticVO.setCreateTime(historyMonthlyReportExcelStatistics.getCreateTime());
        } else {
            historyMonthlyReportExcelStatistics = new HistoryMonthlyReportExcelStatistics();
            historyMonthlyReportStatisticVO.setCreateTime(new Date());
            historyMonthlyReportStatisticVO.setUpdateTime(new Date());
        }
        BeanUtils.copyProperties(historyMonthlyReportStatisticVO, historyMonthlyReportExcelStatistics);
        historyMonthlyReportExcelStatistics.setBaseInfo(thisProject);
        historyMonthlyReportExcelStatistics.setState((byte) 0);
        historyMonthlyReportExcelStatistics.setUpdateTime(new Date());
        // 把通知提醒也一并存入数据库
        Notification notification = new Notification();
        notification.setCreateTime(new Date());
        notification.setSubmitter(thisUser.getUsername());
        notification.setType(NotificationTypeEnum.HISTORY_MONTHLY_STATISTIC.getMsg());
        notification.setTypeId(historyMonthlyReportExcelStatistics.getHId()); // 这里是历史数据ID
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        notification.setYearmonth(formatter.format(historyMonthlyReportExcelStatistics.getUpdateTime()));
        notification.setChecked(false);
        notification.setBaseInfoId(thisProject.getBaseInfoId());
        notification.setUrl("/monthlyreport/tomonthshistoryshow");
        notificationRepository.save(notification);
        operationLogService.save(OperationUtil.buildOperationLog(thisUser.getUserId(),
                historyMonthlyReportExcelStatistics.getUpdateTime(),
                "提交了带附件的"+historyMonthlyReportExcelStatistics.getUpdateTime()+"月报历史数据. ID:"+historyMonthlyReportExcelStatistics.
                        getHId()));
        // 创建webSocket消息
        WebSocketUtil.sendWSNotificationMsg(thisUser, webSocket, "月报", "历史数据消息");
        return historyMonthlyReportExcelStatisticsRepository.save(historyMonthlyReportExcelStatistics);*/
       return null;
    }

    @Override
    public HistoryMonthlyReportExcelStatistics getHistoryStatistic() {
       /* UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        BaseInfo thisProject = userService.findByUserId(thisUser.getUserId()).getBaseInfo();
        HistoryMonthlyReportExcelStatistics historyMonthlyReportExcelStatistics = historyMonthlyReportExcelStatisticsRepository.findByBaseInfo(thisProject);
        return historyMonthlyReportExcelStatistics;*/
       return null;
    }

    @Override
    public Feedback approveHistoryMonthlyStatistic(Boolean switchState, String checkinfo, HistoryMonthlyReportExcelStatistics historyMonthlyReportExcelStatistics) {
        /*UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        BaseInfo thisProject = userService.findByUserId(thisUser.getUserId()).getBaseInfo();
        Feedback feedbackRt = null;
        if (switchState == false) {
            historyMonthlyReportExcelStatistics.setState((byte) 1); // 审批通过
            HistoryMonthlyReportExcelStatistics historyMonthlyReportExcelStatisticsRt = historyMonthlyReportExcelStatisticsRepository.save(historyMonthlyReportExcelStatistics);
            Feedback feedback = FeedbackUtil.buildFeedback(thisUser.getBaseInfo().getBaseInfoId(), thisUser.getUsername(),"月报历史数据", historyMonthlyReportExcelStatisticsRt.getHId(), new Date(),
                    "审批通过", (byte) 1, "/monthlyreport/tomonthshistoryshow");
            feedbackRt = feedbackService.save(feedback);
            operationLogService.save(OperationUtil.buildOperationLog(thisUser.getUserId(), feedbackRt.getCreateTime(), "审批通过了ID为"+feedbackRt.getTargetId()+"的月报月报历史数据"));
        } else {
            historyMonthlyReportExcelStatistics.setState((byte) -1); // 审批未通过
            HistoryMonthlyReportExcelStatistics historyMonthlyReportExcelStatisticsRt = historyMonthlyReportExcelStatisticsRepository.save(historyMonthlyReportExcelStatistics);
            Feedback feedback = FeedbackUtil.buildFeedback(thisUser.getBaseInfo().getBaseInfoId(), thisUser.getUsername(), "月报历史数据",historyMonthlyReportExcelStatisticsRt.getHId(), new Date(),
                    "审批未通过：" + checkinfo, (byte) -1, "/monthlyreport/tomonthshistoryshow");
            feedbackRt = feedbackService.save(feedback);
            operationLogService.save(OperationUtil.buildOperationLog(thisUser.getUserId(), feedbackRt.getCreateTime(), "审批未通过ID为"+feedbackRt.getTargetId()+"的月报月报历史数据"));
        }
        // 创建webSocket消息
        WebSocketUtil.sendWSFeedbackMsg(thisUser, webSocket, "月报历史数据", "新的审批消息");
        return feedbackRt;*/
        return  null;
    }

    // 获取所有审批通过的月报
    @Override
    public List<ProjectMonthlyReportVO> getAllApprovedMonthlyReport() {
        Specification querySpecification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                return cb.equal(root.get("state"), (byte) 1);
            }
        };
        List<ProjectMonthlyReport> projectMonthlyReports = projectMonthlyReportRepository.findAll(querySpecification);
        List<ProjectMonthlyReportVO> projectMonthlyReportVOs = projectMonthlyReports.stream().map(e -> ProjectMonthlyReport2VO.convert(e)).collect(Collectors.toList());
        return projectMonthlyReportVOs;
    }


/*    private List<ProjectMonthlyReport> getMyReports(BaseInfo thisProject){
        List<ProjectMonthlyReport> projectMonthlyReports = null;
        Specification querySpecification =  new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {

            }
        }
    }*/
}
