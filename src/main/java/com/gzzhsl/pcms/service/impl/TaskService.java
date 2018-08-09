package com.gzzhsl.pcms.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gzzhsl.pcms.enums.RedisKeyEnum;
import com.gzzhsl.pcms.service.AnnualInvestmentService;
import com.gzzhsl.pcms.service.ProjectMonthlyReportService;
import com.gzzhsl.pcms.vo.AnnualInvestmentVO;
import com.gzzhsl.pcms.vo.ProjectMonthlyReportVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Component
@Transactional
public class TaskService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private AnnualInvestmentService annualInvestmentService;
    @Autowired
    private ProjectMonthlyReportService projectMonthlyReportService;

    // 将审批后的工程投资计划 （年度投资计划）全部取出同步至redis
    @Scheduled(cron = "0 * * * * ?")
    public void syncAllpprovedAnnualInvestment2redisA() {
        List<AnnualInvestmentVO> allApprovedAnnualInvestmentVO = annualInvestmentService.getAllApprovedAnnualInvestment();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(allApprovedAnnualInvestmentVO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        stringRedisTemplate.opsForValue().set(RedisKeyEnum.ALLAPPROVEDANNUALINVESTMENT.getKey(), jsonString);
    }

    @Scheduled(cron = "0 * * * * ?")
    public void syncAllaccumulatedInvestmentComplete2redisA() {
        List<ProjectMonthlyReportVO> projectMonthlyReportVOs = projectMonthlyReportService.getAllApprovedMonthlyReport();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(projectMonthlyReportVOs);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        stringRedisTemplate.opsForValue().set(RedisKeyEnum.ALLAPPROVEDPROJECTMONTHLYREPORT.getKey(), jsonString);
    }


}
