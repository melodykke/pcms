package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.entity.HistoryMonthlyReportExcelStatistics;
import com.gzzhsl.pcms.entity.ProjectMonthlyReport;
import com.gzzhsl.pcms.service.MonthlyReportExcelService;
import com.gzzhsl.pcms.util.MonthlyReportExcelCalcUtil;
import com.gzzhsl.pcms.vo.MonthlyReportExcelModel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MonthlyReportExcelServiceImpl implements MonthlyReportExcelService {


    @Override
    public MonthlyReportExcelModel getMonthExcelModelWithMonthParams(MonthlyReportExcelModel monthlyReportExcelModel, ProjectMonthlyReport projectMonthlyReport) {
        MonthlyReportExcelModel monthlyReportExcelModelWithMonthParams = MonthlyReportExcelCalcUtil.getModelWithMonthParams(monthlyReportExcelModel, projectMonthlyReport);
        return monthlyReportExcelModelWithMonthParams;
    }

    @Override
    public MonthlyReportExcelModel getMonthExcelModelWithYearParams(MonthlyReportExcelModel monthlyReportExcelModel, List<ProjectMonthlyReport> yearProjectMonthlyReports) {
        MonthlyReportExcelModel monthlyReportExcelModelWithMonthYearParams = MonthlyReportExcelCalcUtil.getModelWithYearParams(monthlyReportExcelModel, yearProjectMonthlyReports);
        return monthlyReportExcelModelWithMonthYearParams;
    }

    @Override
    public MonthlyReportExcelModel getMonthExcelModelWithSofarParams(MonthlyReportExcelModel monthlyReportExcelModel, List<ProjectMonthlyReport> sofarProjectMonthlyReports, HistoryMonthlyReportExcelStatistics historyMonthlyReportExcelStatistics) {
        return MonthlyReportExcelCalcUtil.getModelWithSofarParams(monthlyReportExcelModel, sofarProjectMonthlyReports, historyMonthlyReportExcelStatistics);
    }
}
