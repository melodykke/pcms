package com.gzzhsl.pcms.converter;

import com.gzzhsl.pcms.model.ProjectMonthlyReport;
import com.gzzhsl.pcms.vo.ProjectMonthlyReportShowVO;
import org.springframework.beans.BeanUtils;
import java.util.Calendar;

public class MonthReport2MonthReportShowVO {
    public static ProjectMonthlyReportShowVO convert(ProjectMonthlyReport projectMonthlyReport, String projectName) {
        ProjectMonthlyReportShowVO projectMonthlyReportShowVO = new ProjectMonthlyReportShowVO();
        BeanUtils.copyProperties(projectMonthlyReport, projectMonthlyReportShowVO);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(projectMonthlyReport.getSubmitDate());
        projectMonthlyReportShowVO.setYear(calendar.get(Calendar.YEAR));
        projectMonthlyReportShowVO.setMonth(calendar.get(Calendar.MONTH)+1);
        projectMonthlyReportShowVO.setPlantName(projectName);
        return projectMonthlyReportShowVO;
    }
}
