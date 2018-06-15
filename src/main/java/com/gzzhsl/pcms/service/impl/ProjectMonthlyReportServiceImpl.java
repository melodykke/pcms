package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.entity.ProjectMonthlyReport;
import com.gzzhsl.pcms.repository.ProjectMonthlyReportRepository;
import com.gzzhsl.pcms.service.ProjectMonthlyReportService;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class ProjectMonthlyReportServiceImpl implements ProjectMonthlyReportService{

    @Autowired
    private ProjectMonthlyReportRepository projectMonthlyReportRepository;

    @Override
    @Transactional
    public ProjectMonthlyReport save(ProjectMonthlyReport projectMonthlyReport) {


        return projectMonthlyReportRepository.save(projectMonthlyReport);
    }
}
