package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.model.Feedback;
import com.gzzhsl.pcms.model.HistoryMonthlyReportExcelStatistics;
import com.gzzhsl.pcms.model.ProjectMonthlyReport;
import com.gzzhsl.pcms.model.UserInfo;
import com.gzzhsl.pcms.vo.HistoryMonthlyReportStatisticVO;
import com.gzzhsl.pcms.vo.ProjectMonthlyReportShowVO;
import com.gzzhsl.pcms.vo.ProjectMonthlyReportVO;


import java.util.List;

public interface ProjectMonthlyReportService {
    List<ProjectMonthlyReport> findMonthlyReportsByProjectIdAndPeriod(String baseInfoId, String startDate, String endDate);
    ProjectMonthlyReport findByProjectMonthlyReportId(String projectMonthlyReportId);
    HistoryMonthlyReportExcelStatistics findByBaseInfoId(String baseInfoId);
    ProjectMonthlyReportShowVO buildShowVO(String projectMonthlyReportId);




    ProjectMonthlyReport save(ProjectMonthlyReportVO projectMonthlyReportVO);
    ProjectMonthlyReport save(ProjectMonthlyReport projectMonthlyReport);


    List<ProjectMonthlyReport> findByProjectIdAndState(String projectId, byte state);
    Feedback approveMonthlyReport(UserInfo thisUser, Boolean switchState, String checkinfo, String projectMonthlyReportId, ProjectMonthlyReport projectMonthlyReportRt);
    HistoryMonthlyReportExcelStatistics saveHistoryStatistic(HistoryMonthlyReportStatisticVO historyMonthlyReportStatisticVO);
    HistoryMonthlyReportExcelStatistics getHistoryStatistic();
    Feedback approveHistoryMonthlyStatistic(Boolean switchState, String checkinfo, HistoryMonthlyReportExcelStatistics historyMonthlyReportExcelStatistics);
    List<ProjectMonthlyReportVO> getAllApprovedMonthlyReport();
}
