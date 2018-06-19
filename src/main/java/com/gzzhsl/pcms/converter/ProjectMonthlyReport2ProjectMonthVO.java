package com.gzzhsl.pcms.converter;

import com.gzzhsl.pcms.entity.ProjectMonthlyReport;
import com.gzzhsl.pcms.vo.ProjectMonthVO;
import org.springframework.beans.BeanUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ProjectMonthlyReport2ProjectMonthVO {
    public static ProjectMonthVO convert(ProjectMonthlyReport projectMonthlyReport) {
        ProjectMonthVO projectMonthVO = new ProjectMonthVO();
        BeanUtils.copyProperties(projectMonthlyReport, projectMonthVO);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(projectMonthlyReport.getSubmitDate());
        projectMonthVO.setMonth(String.valueOf(calendar.get(Calendar.MONTH)+1));
        projectMonthVO.setYear(String.valueOf(calendar.get(Calendar.YEAR)));
        return projectMonthVO;
    }
}
