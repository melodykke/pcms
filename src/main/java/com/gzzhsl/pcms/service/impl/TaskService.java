package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.enums.RedisKeyEnum;
import com.gzzhsl.pcms.service.AnnualInvestmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class TaskService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private AnnualInvestmentService annualInvestmentService;

    // 将审批后的工程投资计划 （年度投资计划）全部取出同步至redis
    @Scheduled(cron = "0 * * * * ?")
    public void syncAllpprovedAnnualInvestment2redisA() {
        BigDecimal allApprovedFigure = annualInvestmentService.getAllApprovedFigure();
        stringRedisTemplate.opsForValue().set(RedisKeyEnum.ALLPPROVEDANNUALINVESTMENT.getKey(), String.valueOf(allApprovedFigure));
    }
}
