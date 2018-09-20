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
    int save(ProjectMonthlyReportVO projectMonthlyReportVO);
    Feedback approveMonthlyReport(UserInfo thisUser, Boolean switchState, String checkinfo, ProjectMonthlyReport projectMonthlyReportRt);



    ProjectMonthlyReport save(ProjectMonthlyReport projectMonthlyReport);


    List<ProjectMonthlyReport> findByProjectIdAndState(String projectId, byte state);

    HistoryMonthlyReportExcelStatistics saveHistoryStatistic(HistoryMonthlyReportStatisticVO historyMonthlyReportStatisticVO);
    HistoryMonthlyReportExcelStatistics getHistoryStatistic();
    Feedback approveHistoryMonthlyStatistic(Boolean switchState, String checkinfo, HistoryMonthlyReportExcelStatistics historyMonthlyReportExcelStatistics);
    List<ProjectMonthlyReportVO> getAllApprovedMonthlyReport();
}
