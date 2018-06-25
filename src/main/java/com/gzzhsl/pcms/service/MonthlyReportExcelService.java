package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.entity.HistoryMonthlyReportExcelStatistics;
import com.gzzhsl.pcms.entity.ProjectMonthlyReport;
import com.gzzhsl.pcms.vo.MonthlyReportExcelModel;

import java.util.List;

public interface MonthlyReportExcelService {
    MonthlyReportExcelModel getMonthExcelModelWithMonthParams(MonthlyReportExcelModel monthlyReportExcelModel, ProjectMonthlyReport projectMonthlyReport);
    MonthlyReportExcelModel getMonthExcelModelWithYearParams(MonthlyReportExcelModel monthlyReportExcelModel, List<ProjectMonthlyReport> yearProjectMonthlyReports);
    MonthlyReportExcelModel getMonthExcelModelWithSofarParams(MonthlyReportExcelModel monthlyReportExcelModel,
                                                                     List<ProjectMonthlyReport> sofarProjectMonthlyReports,
                                                                     HistoryMonthlyReportExcelStatistics historyMonthlyReportExcelStatistics);
}
