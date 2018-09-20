package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.converter.MonthReport2MonthReportShowVO;
import com.gzzhsl.pcms.converter.MonthlyReportVO2MonthlyReport;
import com.gzzhsl.pcms.converter.ProjectMonthlyReport2VO;
import com.gzzhsl.pcms.converter.ProjectMonthlyReportImg2VO;
import com.gzzhsl.pcms.enums.NotificationTypeEnum;
import com.gzzhsl.pcms.enums.SysEnum;
import com.gzzhsl.pcms.exception.SysException;
import com.gzzhsl.pcms.mapper.HistoryMonthlyReportExcelStatisticsMapper;
import com.gzzhsl.pcms.mapper.NotificationMapper;
import com.gzzhsl.pcms.mapper.ProjectMonthlyReportImgMapper;
import com.gzzhsl.pcms.mapper.ProjectMonthlyReportMapper;
import com.gzzhsl.pcms.model.*;
import com.gzzhsl.pcms.repository.HistoryMonthlyReportExcelStatisticsRepository;
import com.gzzhsl.pcms.repository.NotificationRepository;
import com.gzzhsl.pcms.repository.ProjectMonthlyReportImgRepository;
import com.gzzhsl.pcms.repository.ProjectMonthlyReportRepository;
import com.gzzhsl.pcms.service.*;
import com.gzzhsl.pcms.util.*;
import com.gzzhsl.pcms.vo.HistoryMonthlyReportStatisticVO;
import com.gzzhsl.pcms.vo.ProjectMonthlyReportImgVO;
import com.gzzhsl.pcms.vo.ProjectMonthlyReportShowVO;
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
    private ProjectMonthlyReportMapper projectMonthlyReportMapper;
    @Autowired
    private HistoryMonthlyReportExcelStatisticsMapper historyMonthlyReportExcelStatisticsMapper;
    @Autowired
    private BaseInfoService baseInfoService;
    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private ProjectMonthlyReportImgService projectMonthlyReportImgService;
    @Autowired
    private FeedbackService feedbackService;





    @Autowired
    private ProjectMonthlyReportRepository projectMonthlyReportRepository;
    @Autowired
    private HistoryMonthlyReportExcelStatisticsRepository historyMonthlyReportExcelStatisticsRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private OperationLogService operationLogService;
    @Autowired
    private UserService userService;
    @Autowired
    private WebSocket webSocket;



    @Override
    public ProjectMonthlyReportShowVO buildShowVO(String projectMonthlyReportId) {
        ProjectMonthlyReport projectMonthlyReport = projectMonthlyReportMapper.findWithImgById(projectMonthlyReportId);
        BaseInfo thisProject = baseInfoService.findBaseInfoById(projectMonthlyReport.getBaseInfoId());
        if (thisProject == null) {
            return null;
        }
        ProjectMonthlyReportShowVO projectMonthlyReportShowVO = MonthReport2MonthReportShowVO.convert(projectMonthlyReport, thisProject.getPlantName());
        List<ProjectMonthlyReportImgVO> projectMonthlyReportImgVOList = projectMonthlyReport.getProjectMonthlyReportImgs().stream().map(e -> ProjectMonthlyReportImg2VO.convert(e)).collect(Collectors.toList());
        projectMonthlyReportShowVO.setProjectMonthlyReportImgVOList(projectMonthlyReportImgVOList);
        return projectMonthlyReportShowVO;
    }



    @Override
    public int save(ProjectMonthlyReportVO projectMonthlyReportVO) {
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        UserInfo thisUser = userService.findOneWithRolesAndPrivilegesByUsernameOrId(userInfo.getUsername(), null);
        BaseInfo thisProject = baseInfoService.findBaseInfoById(thisUser.getBaseInfoId());
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
        List<ProjectMonthlyReport> projectMonthlyReports = projectMonthlyReportMapper.findByBaseInfoId(thisProject.getBaseInfoId());
        for (ProjectMonthlyReport projectMonthlyReport : projectMonthlyReports) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
            String dateString = simpleDateFormat.format(projectMonthlyReport.getSubmitDate());
            String monthReportDateString = simpleDateFormat.format(monthReportDate);
            if (dateString.equals(monthReportDateString)) {
                if (projectMonthlyReport.getState() == 1 || projectMonthlyReport.getState() == null) {
                    log.error("【月报错误】 插入月报与某已存在月报月份一致，并且该已存在月报状态为空或已审批");
                    throw new SysException(SysEnum.MONTHLY_REPORTS_INSERT_ERROR);
                } else {
                    projectMonthlyReportMapper.deleteByPrimaryKey(projectMonthlyReport.getProjectMonthlyReportId());
                }
            }
        }
        ProjectMonthlyReport projectMonthlyReport = MonthlyReportVO2MonthlyReport.convert(projectMonthlyReportVO, thisUser);

        if (projectMonthlyReportVO.getRtFileTempPath() == null || projectMonthlyReportVO.getRtFileTempPath() == "") {
            // 没有上传图片的情况，直接对表格进行存储
            projectMonthlyReport.setBaseInfoId(thisProject.getBaseInfoId());
            int intRt = projectMonthlyReportMapper.insert(projectMonthlyReport);
            // 把通知提醒也一并存入数据库
            Notification notification = new Notification();
            notification.setNotificationId(UUIDUtils.getUUIDString());
            notification.setCreateTime(new Date());
            notification.setSubmitter(projectMonthlyReport.getSubmitter());
            notification.setType(NotificationTypeEnum.MONTHLY_REPORT.getMsg());
            notification.setTypeId(projectMonthlyReport.getProjectMonthlyReportId()); // 这里是月报ID
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
            notification.setYearmonth(formatter.format(projectMonthlyReport.getSubmitDate()));
            notification.setChecked(false);
            notification.setBaseInfoId(projectMonthlyReport.getBaseInfoId());
            notification.setUrl("/monthlyreport/projectmonthlyreportshow");
            notificationMapper.insert(notification);
            operationLogService.save(OperationUtil.buildOperationLog(thisUser.getUserId(),
                    projectMonthlyReport.getCreateTime(),
                    "提交了没有附件的"+projectMonthlyReport.getSubmitDate()+"月报. ID:"+projectMonthlyReport.
                            getProjectMonthlyReportId()));
            // 创建webSocket消息
            UserInfo parent = userService.findParent(thisUser);
            if (parent != null) {
                parent = userService.findOneWithRolesAndPrivilegesByUsernameOrId(parent.getUsername(), null);
            }
            WebSocketUtil.sendWSNotificationMsg(thisUser, parent, webSocket, "月报", "新的月报消息");
            return intRt;
        } else {
            // 上传图片的情况，考虑转存
            String rtFileTempPath = projectMonthlyReportVO.getRtFileTempPath();
            projectMonthlyReport.setBaseInfoId(thisProject.getBaseInfoId());
            int intRt = projectMonthlyReportMapper.insert(projectMonthlyReport);
            Calendar cal = Calendar.getInstance();
            cal.setTime(projectMonthlyReport.getSubmitDate());
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
                    projectMonthlyReportImg.setProjectMonthlyReportImgId(UUIDUtils.getUUIDString());
                    projectMonthlyReportImg.setCreateTime(new Date());
                    projectMonthlyReportImg.setImgAddr(imgRelativePath);
                    projectMonthlyReportImg.setProjectMonthlyReportId(projectMonthlyReport.getProjectMonthlyReportId());
                    projectMonthlyReportImgs.add(projectMonthlyReportImg);
                }
            }
            projectMonthlyReportImgService.batchSave(projectMonthlyReportImgs);
            // 把通知提醒也一并存入数据库
            Notification notification = new Notification();
            notification.setNotificationId(UUIDUtils.getUUIDString());
            notification.setCreateTime(new Date());
            notification.setSubmitter(projectMonthlyReport.getSubmitter());
            notification.setType(NotificationTypeEnum.MONTHLY_REPORT.getMsg());
            notification.setTypeId(projectMonthlyReport.getProjectMonthlyReportId()); // 这里是月报ID
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
            notification.setYearmonth(formatter.format(projectMonthlyReport.getSubmitDate()));
            notification.setChecked(false);
            notification.setBaseInfoId(projectMonthlyReport.getBaseInfoId());
            notification.setUrl("/monthlyreport/projectmonthlyreportshow");
            notificationMapper.insert(notification);
            operationLogService.save(OperationUtil.buildOperationLog(thisUser.getUserId(),
                    projectMonthlyReport.getCreateTime(),
                    "提交了带附件的"+projectMonthlyReport.getSubmitDate()+"月报. ID:"+projectMonthlyReport.
                            getProjectMonthlyReportId()));
            // 创建webSocket消息
            UserInfo parent = userService.findParent(thisUser);
            if (parent != null) {
                parent = userService.findOneWithRolesAndPrivilegesByUsernameOrId(parent.getUsername(), null);
            }
            WebSocketUtil.sendWSNotificationMsg(thisUser, parent, webSocket, "月报", "新的月报消息");
            return intRt;
        }
    }

    @Override
    public ProjectMonthlyReport save(ProjectMonthlyReport projectMonthlyReport) {
     /*   return projectMonthlyReportRepository.save(projectMonthlyReport);*/
     return null;
    }

    /**
     * 获取某时段内特定工程的月报
     * @param baseInfoId
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public List<ProjectMonthlyReport> findMonthlyReportsByProjectIdAndPeriod(String baseInfoId, String startDate, String endDate) {
        if (baseInfoId == null || "".equals(baseInfoId) || startDate == null || "".equals(startDate) || endDate == null || "".equals(endDate)) {
            return new ArrayList<ProjectMonthlyReport>();
        }
        List<ProjectMonthlyReport> projectMonthlyReports = projectMonthlyReportMapper
                .findMonthlyReportsByProjectIdAndPeriod(baseInfoId, startDate, endDate);

        return projectMonthlyReports;
    }

    @Override
    public ProjectMonthlyReport findByProjectMonthlyReportId(String projectMonthlyReportId) {
        return projectMonthlyReportMapper.selectByPrimaryKey(projectMonthlyReportId);
    }

    @Override
    public HistoryMonthlyReportExcelStatistics findByBaseInfoId(String baseInfoId) {
        if (baseInfoId == null || "".equals(baseInfoId)) {
            return null;
        }
        return historyMonthlyReportExcelStatisticsMapper.findByBaseInfoId(baseInfoId) ;
    }


    @Override
    public List<ProjectMonthlyReport> findByProjectIdAndState(String projectId, byte state) {
      /*  List<ProjectMonthlyReport> projectMonthlyReports = null;
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
        return projectMonthlyReportRepository.findAll(querySpecification, sort);*/
      return null;
    }

    @Override
    public Feedback approveMonthlyReport(UserInfo thisUser, Boolean switchState, String checkinfo, ProjectMonthlyReport projectMonthlyReportRt) {
        if (switchState == false) {
            int intRt = projectMonthlyReportMapper.approveMonthlyReport(projectMonthlyReportRt.getProjectMonthlyReportId(), (byte)1);
            Feedback feedback = FeedbackUtil.buildFeedback(thisUser.getBaseInfoId(), thisUser.getUsername(),
                    "月报", projectMonthlyReportRt.getProjectMonthlyReportId(), new Date(),
                    "审批通过", (byte) 1, "/monthlyreport/projectmonthlyreportshow");
            feedbackService.save(feedback);
            operationLogService.save(OperationUtil.buildOperationLog(thisUser.getUserId(), feedback.getCreateTime(),
                    "审批通过了ID为"+feedback.getTargetId()+"的月报"));
            // 创建webSocket消息
            List<UserInfo> children = userService.findChildren(thisUser);
            WebSocketUtil.sendWSFeedbackMsg(thisUser, children, webSocket, "月报", "新的审批消息");
            return feedback;
        } else {
            int intRt = projectMonthlyReportMapper.approveMonthlyReport(projectMonthlyReportRt.getProjectMonthlyReportId(), (byte)-1);
            Feedback feedback = FeedbackUtil.buildFeedback(thisUser.getBaseInfoId(), thisUser.getUsername(),
                    "月报", projectMonthlyReportRt.getProjectMonthlyReportId(), new Date(),
                    "审批未通过：" + checkinfo, (byte) -1, "/monthlyreport/projectmonthlyreportshow");
            feedbackService.save(feedback);
            operationLogService.save(OperationUtil.buildOperationLog(thisUser.getUserId(), feedback.getCreateTime(),
                    "审批未通过ID为"+feedback.getTargetId()+"的月报"));
            // 创建webSocket消息
            List<UserInfo> children = userService.findChildren(thisUser);
            WebSocketUtil.sendWSFeedbackMsg(thisUser, children, webSocket, "月报", "新的审批消息");
            return feedback;
        }
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
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        UserInfo thisUser = userService.selectByPrimaryKey(userInfo.getUserId());
        BaseInfo thisProject = baseInfoService.findBaseInfoById(thisUser.getBaseInfoId());
        if (thisProject == null) {
            return null;
        }
        HistoryMonthlyReportExcelStatistics historyMonthlyReportExcelStatistics = historyMonthlyReportExcelStatisticsMapper.findByBaseInfoId(thisProject.getBaseInfoId());
        return historyMonthlyReportExcelStatistics;
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
       /* Specification querySpecification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                return cb.equal(root.get("state"), (byte) 1);
            }
        };
        List<ProjectMonthlyReport> projectMonthlyReports = projectMonthlyReportRepository.findAll(querySpecification);
        List<ProjectMonthlyReportVO> projectMonthlyReportVOs = projectMonthlyReports.stream().map(e -> ProjectMonthlyReport2VO.convert(e)).collect(Collectors.toList());
        return projectMonthlyReportVOs;*/
       return null;
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
