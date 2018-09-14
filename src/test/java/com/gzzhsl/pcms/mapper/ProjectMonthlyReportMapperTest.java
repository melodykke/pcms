package com.gzzhsl.pcms.mapper;

import com.gzzhsl.pcms.model.ProjectMonthlyReport;
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
public class ProjectMonthlyReportMapperTest {

    @Autowired
    private ProjectMonthlyReportMapper projectMonthlyReportMapper;
    @Test
    public void findMonthlyReportsByProjectIdAndPeriod() throws Exception {
        List<ProjectMonthlyReport> projectMonthlyReportList = projectMonthlyReportMapper.findMonthlyReportsByProjectIdAndPeriod("8a8082816458ab31016458ab49d40091",
                "2018-04-01 00:00:00", new Date().toString());
        System.out.println(projectMonthlyReportList);
    }

    @Test
    public void findWithImgById() throws Exception {
        ProjectMonthlyReport projectMonthlyReport = projectMonthlyReportMapper.findWithImgById("4028e40e6583a47b016583a8bad80006");
        System.out.println(projectMonthlyReport);
    }

}