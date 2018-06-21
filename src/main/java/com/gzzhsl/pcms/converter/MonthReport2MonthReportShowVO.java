package com.gzzhsl.pcms.converter;

import com.gzzhsl.pcms.entity.ProjectMonthlyReport;
import com.gzzhsl.pcms.vo.ProjectMonthlyReportShowVO;
import org.springframework.beans.BeanUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MonthReport2MonthReportShowVO {
    public static ProjectMonthlyReportShowVO convert(ProjectMonthlyReport projectMonthlyReport) {
        ProjectMonthlyReportShowVO projectMonthlyReportShowVO = new ProjectMonthlyReportShowVO();
        BeanUtils.copyProperties(projectMonthlyReport, projectMonthlyReportShowVO);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(projectMonthlyReport.getSubmitDate());
        projectMonthlyReportShowVO.setYear(calendar.get(Calendar.YEAR));
        projectMonthlyReportShowVO.setMonth(calendar.get(Calendar.MONTH)+1);
        projectMonthlyReportShowVO.setPlantName(projectMonthlyReport.getProject().getPlantName());
        return projectMonthlyReportShowVO;
    }
}
