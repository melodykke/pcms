package com.gzzhsl.pcms.enums;

import lombok.Getter;

@Getter
public enum RedisKeyEnum {
    ALLBASEINFO("allBaseInfo"),
    ALLPPROVEDANNUALINVESTMENT("allpprovedAnnualInvestment"),

    ;

    private String key;

    RedisKeyEnum(String key) {
        this.key = key;
    }
}
