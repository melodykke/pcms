package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.entity.Feedback;
import com.gzzhsl.pcms.entity.ProjectMonthlyReport;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import com.gzzhsl.pcms.vo.ProjectMonthlyReportVO;

import java.util.List;

public interface ProjectMonthlyReportService {
    ProjectMonthlyReport save(ProjectMonthlyReportVO projectMonthlyReportVO);
    ProjectMonthlyReport save(ProjectMonthlyReport projectMonthlyReport);
    List<ProjectMonthlyReport> getMonthlyReportsByProjectIdAndYear(String baseInfoId, String startDate, String endDate);
    ProjectMonthlyReport getByProjectMonthlyReportId(String projectMonthlyReportId);
    List<ProjectMonthlyReport> findByProjectIdAndState(String projectId, byte state);
    Feedback approveMonthlyReport(UserInfo thisUser, Boolean switchState, String checkinfo, String projectMonthlyReportId, ProjectMonthlyReport projectMonthlyReportRt);
}
