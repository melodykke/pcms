package com.gzzhsl.pcms.converter;

import com.gzzhsl.pcms.model.ProjectMonthlyReport;
import com.gzzhsl.pcms.model.UserInfo;
import com.gzzhsl.pcms.util.UUIDUtils;
import com.gzzhsl.pcms.vo.ProjectMonthlyReportVO;
import org.springframework.beans.BeanUtils;

import java.util.Date;

public class MonthlyReportVO2MonthlyReport {
    public static ProjectMonthlyReport convert(ProjectMonthlyReportVO projectMonthlyReportVO, UserInfo thisUser) {
        ProjectMonthlyReport projectMonthlyReport = new ProjectMonthlyReport();
        BeanUtils.copyProperties(projectMonthlyReportVO, projectMonthlyReport);
        projectMonthlyReport.setProjectMonthlyReportId(UUIDUtils.getUUIDString());
        projectMonthlyReport.setBaseInfoId(thisUser.getBaseInfoId());
        projectMonthlyReport.setSubmitter(thisUser.getUsername());
        projectMonthlyReport.setState((byte) 0);
        projectMonthlyReport.setCreateTime(new Date());
        return projectMonthlyReport;
    }
}
