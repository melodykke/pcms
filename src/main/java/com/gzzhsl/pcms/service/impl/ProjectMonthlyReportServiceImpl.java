package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.converter.MonthlyReportVO2MonthlyReport;
import com.gzzhsl.pcms.entity.*;
import com.gzzhsl.pcms.enums.NotificationTypeEnum;
import com.gzzhsl.pcms.enums.SysEnum;
import com.gzzhsl.pcms.exception.SysException;
import com.gzzhsl.pcms.repository.NotificationRepository;
import com.gzzhsl.pcms.repository.ProjectMonthlyReportImgRepository;
import com.gzzhsl.pcms.repository.ProjectMonthlyReportRepository;
import com.gzzhsl.pcms.service.FeedbackService;
import com.gzzhsl.pcms.service.OperationLogService;
import com.gzzhsl.pcms.service.ProjectMonthlyReportService;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import com.gzzhsl.pcms.util.FeedbackUtil;
import com.gzzhsl.pcms.util.OperationUtil;
import com.gzzhsl.pcms.util.PathUtil;
import com.gzzhsl.pcms.vo.ProjectMonthlyReportVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
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

@Service
@Slf4j
public class ProjectMonthlyReportServiceImpl implements ProjectMonthlyReportService{

    @Autowired
    private ProjectMonthlyReportRepository projectMonthlyReportRepository;
    @Autowired
    private ProjectMonthlyReportImgRepository projectMonthlyReportImgRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private OperationLogService operationLogService;
    @Autowired
    private FeedbackService feedbackService;

    @Override
    @Transactional
    public ProjectMonthlyReport save(ProjectMonthlyReportVO projectMonthlyReportVO) {
        UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        Project thisProject = thisUser.getProject();
        // 如果用户对应的Project不存在，则报错
        if (thisProject == null) {
            log.error("【月报错误】 用户对应Project为空，需首先绑定对应的水库工程");
            throw new SysException(SysEnum.MONTHLY_REPORT_ERROR);
        }

        // 在审批状态为“已审批”的状态下，若月报提交月份重复，则不允许提交
        Date monthReportDate = projectMonthlyReportVO.getSubmitDate();
        List<ProjectMonthlyReport> projectMonthlyReports = projectMonthlyReportRepository.findAll();
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
            projectMonthlyReport.setProject(thisProject);
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
            notification.setProjectId(thisUser.getProject().getProjectId());
            notification.setUrl("/monthlyreport/projectmonthlyreportshow");
            notificationRepository.save(notification);
            operationLogService.save(OperationUtil.buildOperationLog(thisUser.getUserId(),
                    projectMonthlyReportRt.getCreateTime(),
                    "提交了没有附件的"+projectMonthlyReportRt.getSubmitDate()+"月报. ID:"+projectMonthlyReportRt.
                            getProjectMonthlyReportId()));
            return projectMonthlyReportRt;
        } else {
            // 上传图片的情况，考虑转存
            String rtFileTempPath = projectMonthlyReportVO.getRtFileTempPath();
            projectMonthlyReport.setProject(thisProject);
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
            notification.setProjectId(thisUser.getProject().getProjectId());
            notification.setUrl("/monthlyreport/projectmonthlyreportshow");
            notificationRepository.save(notification);
            operationLogService.save(OperationUtil.buildOperationLog(thisUser.getUserId(),
                    projectMonthlyReportRt.getCreateTime(),
                    "提交了带附件的"+projectMonthlyReportRt.getSubmitDate()+"月报. ID:"+projectMonthlyReportRt.
                            getProjectMonthlyReportId()));
            return projectMonthlyReportRtWithImg;
        }
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
    public List<ProjectMonthlyReport> getMonthlyReportsByProjectIdAndYear(String projectId, String startDate, String endDate) {
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
                if (StringUtils.isNotBlank(projectId)) {
                    predicates.add(cb.equal(root.join("project").get("projectId").as(String.class), projectId));
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
            Feedback feedback = FeedbackUtil.buildFeedback(thisUser.getUserId(),"月报", projectMonthlyReportId, new Date(),
                    "审批通过", (byte) 1);
            feedbackRt = feedbackService.save(feedback);
            operationLogService.save(OperationUtil.buildOperationLog(thisUser.getUserId(), feedbackRt.getCreateTime(), "审批通过了ID为"+feedbackRt.getTargetId()+"的月报"));
        } else {
            projectMonthlyReportRt.setState((byte) -1); // 审批未通过
            ProjectMonthlyReport projectMonthlyReportRtRt = projectMonthlyReportRepository.save(projectMonthlyReportRt);
            Feedback feedback = FeedbackUtil.buildFeedback(thisUser.getUserId(), "月报",projectMonthlyReportId, new Date(),
                    "审批未通过：" + checkinfo, (byte) -1);
            feedbackRt = feedbackService.save(feedback);
            operationLogService.save(OperationUtil.buildOperationLog(thisUser.getUserId(), feedbackRt.getCreateTime(), "审批未通过ID为"+feedbackRt.getTargetId()+"的月报"));
        }
        return feedbackRt;
    }


}
