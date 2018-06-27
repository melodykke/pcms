package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.entity.Project;
import com.gzzhsl.pcms.entity.ProjectMonthlyReport;
import com.gzzhsl.pcms.vo.ProjectMonthlyReportVO;

import java.util.Date;
import java.util.List;

public interface ProjectMonthlyReportService {
    ProjectMonthlyReport save(ProjectMonthlyReportVO projectMonthlyReportVO);
    List<ProjectMonthlyReport> getMonthlyReportsByProjectIdAndYear(String projectId, String startDate, String endDate);
    ProjectMonthlyReport getByProjectMonthlyReportId(String projectMonthlyReportId);
    List<ProjectMonthlyReport> findByProjectIdAndState(String projectId, byte state);
}
