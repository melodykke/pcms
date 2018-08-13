/*
package com.gzzhsl.pcms.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gzzhsl.pcms.converter.BaseInfo2ManagerIndexVO;
import com.gzzhsl.pcms.entity.BaseInfo;
import com.gzzhsl.pcms.enums.RedisKeyEnum;
import com.gzzhsl.pcms.service.AnnualInvestmentService;
import com.gzzhsl.pcms.service.BaseInfoService;
import com.gzzhsl.pcms.service.ProjectMonthlyReportService;
import com.gzzhsl.pcms.vo.AnnualInvestmentVO;
import com.gzzhsl.pcms.vo.BaseInfoManagerIndexVO;
import com.gzzhsl.pcms.vo.ProjectMonthlyReportVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class TaskService {
    @Autowired
    private BaseInfoService baseInfoService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private AnnualInvestmentService annualInvestmentService;
    @Autowired
    private ProjectMonthlyReportService projectMonthlyReportService;

    // 将所有工程项目 （概况）全部取出同步至redis
    @Scheduled(cron = "0 * * * * ?")
    public void syncAllBaseInfo2redis() {
        List<BaseInfo> baseInfos = baseInfoService.getAllProject();
        List<BaseInfoManagerIndexVO> baseInfoManagerIndexVOs = baseInfos.stream().map(e -> BaseInfo2ManagerIndexVO.convert(e)).collect(Collectors.toList());
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(baseInfoManagerIndexVOs);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        stringRedisTemplate.opsForValue().set("allBaseInfo", jsonString);
        stringRedisTemplate.opsForValue().set(RedisKeyEnum.ALLAPPROVEDPROJECTMONTHLYREPORT.getKey(), jsonString);
    }
    // 将审批后的工程投资计划 （年度投资计划）全部取出同步至redis
    @Scheduled(cron = "0 * * * * ?")
    public void syncAllpprovedAnnualInvestment2redis() {
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
    // 将审批后的所有月报信息同步至redis
    @Scheduled(cron = "0 * * * * ?")
    public void syncAllApprovedMonthlyReport2redis() {
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
*/
