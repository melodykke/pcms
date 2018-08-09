package com.gzzhsl.pcms.enums;

import lombok.Getter;

@Getter
public enum RedisKeyEnum {
    ALLBASEINFO("allBaseInfo"), // 所有的水库项目
    ALLAPPROVEDANNUALINVESTMENT("allApprovedAnnualInvestment"), // 所有的审批后的投资计划
    ALLAPPROVEDPROJECTMONTHLYREPORT("allApprovedProjectMonthlyReport"), // 所有的审批后的月报
    ;

    private String key;

    RedisKeyEnum(String key) {
        this.key = key;
    }
}
