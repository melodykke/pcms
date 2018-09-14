package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.model.HistoryMonthlyReportExcelStatistics;
import com.gzzhsl.pcms.model.ProjectMonthlyReport;
import com.gzzhsl.pcms.vo.MonthlyReportExcelModel;

import java.util.List;

public interface MonthlyReportExcelService {
    MonthlyReportExcelModel getMonthExcelModelWithMonthParams(MonthlyReportExcelModel monthlyReportExcelModel, ProjectMonthlyReport projectMonthlyReport);
    MonthlyReportExcelModel getMonthExcelModelWithYearParams(MonthlyReportExcelModel monthlyReportExcelModel, List<ProjectMonthlyReport> yearProjectMonthlyReports);
    MonthlyReportExcelModel getMonthExcelModelWithSofarParams(MonthlyReportExcelModel monthlyReportExcelModel,
                                                                     List<ProjectMonthlyReport> sofarProjectMonthlyReports,
                                                                     HistoryMonthlyReportExcelStatistics historyMonthlyReportExcelStatistics);
}
