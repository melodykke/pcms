package com.gzzhsl.pcms.converter;

import com.gzzhsl.pcms.entity.ProjectMonthlyReport;
import com.gzzhsl.pcms.vo.ProjectMonthlyReportVO;
import org.springframework.beans.BeanUtils;

public class ProjectMonthlyReport2VO {
    public static ProjectMonthlyReportVO convert(ProjectMonthlyReport projectMonthlyReport) {
        ProjectMonthlyReportVO projectMonthlyReportVO = new ProjectMonthlyReportVO();
        BeanUtils.copyProperties(projectMonthlyReport, projectMonthlyReportVO);
        return projectMonthlyReportVO;
    }
}
