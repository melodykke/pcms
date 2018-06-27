package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.entity.ProjectMonthlyReport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectMonthlyReportServiceTest {

    @Autowired
    private ProjectMonthlyReportService projectMonthlyReportService;

    @Test
    public void findByProjectIdAndState() throws Exception {

        List<ProjectMonthlyReport> projectMonthlyReportList = projectMonthlyReportService.findByProjectIdAndState("747d74c5-a7be-442a-903c-72277118eec6", (byte) 0);
        System.out.println(projectMonthlyReportList);

    }

}