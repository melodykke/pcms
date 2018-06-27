package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.entity.ProjectMonthlyReport;
import com.gzzhsl.pcms.service.ProjectMonthlyReportService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectMonthlyReportServiceImplTest {

    @Autowired
    private ProjectMonthlyReportService projectMonthlyReportService;

    @Test
    public void getMonthlyReportsByProjectIdAndYear() throws Exception {
        List<ProjectMonthlyReport> projectMonthlyReports = projectMonthlyReportService.getMonthlyReportsByProjectIdAndYear("747d74c5-a7be-442a-903c-72277118eec6",
                "2018-01-01 00:00:00", new Date().toString());
        System.out.println(projectMonthlyReports);
    }

    @Test
    public void findByProjectIdAndState() throws Exception {
        List<ProjectMonthlyReport> projectMonthlyReports = projectMonthlyReportService.findByProjectIdAndState("747d74c5-a7be-442a-903c-72277118eec6", (byte) 0);
        System.out.println(projectMonthlyReports);
    }

}