package com.gzzhsl.pcms.converter;

import com.gzzhsl.pcms.entity.ProjectMonthlyReport;
import com.gzzhsl.pcms.vo.ProjectMonthlyReportVO;
import org.springframework.beans.BeanUtils;

public class MonthlyReportVO2MonthlyReport {
    public static ProjectMonthlyReport convert(ProjectMonthlyReportVO projectMonthlyReportVO) {
        ProjectMonthlyReport projectMonthlyReport = new ProjectMonthlyReport();
        BeanUtils.copyProperties(projectMonthlyReportVO, projectMonthlyReport);
        return projectMonthlyReport;
    }
}
