package com.gzzhsl.pcms.converter;

import com.gzzhsl.pcms.entity.Project;
import com.gzzhsl.pcms.entity.ProjectMonthlyReport;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import com.gzzhsl.pcms.vo.ProjectMonthlyReportVO;
import org.springframework.beans.BeanUtils;

import java.util.Date;

public class MonthlyReportVO2MonthlyReport {
    public static ProjectMonthlyReport convert(ProjectMonthlyReportVO projectMonthlyReportVO, UserInfo thisUser) {
        ProjectMonthlyReport projectMonthlyReport = new ProjectMonthlyReport();
        BeanUtils.copyProperties(projectMonthlyReportVO, projectMonthlyReport);
        projectMonthlyReport.setProject(thisUser.getProject());
        projectMonthlyReport.setSubmitter(thisUser.getUsername());
        projectMonthlyReport.setState((byte) 0);
        projectMonthlyReport.setCreateTime(new Date());
        return projectMonthlyReport;
    }
}
