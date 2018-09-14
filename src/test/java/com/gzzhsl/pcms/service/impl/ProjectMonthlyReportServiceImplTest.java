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
    public void findMonthlyReportsByProjectIdAndPeriod() throws Exception {
        projectMonthlyReportService.findMonthlyReportsByProjectIdAndPeriod("8a8082816458ab31016458ab49d40091",
                "2018-04-01 00:00:00", new Date().toString());
    }
}