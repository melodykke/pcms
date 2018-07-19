package com.gzzhsl.pcms.enums;

import lombok.Getter;

@Getter
public enum NotificationTypeEnum {
    MONTHLY_REPORT("月报"),
    PROJECT_BASIC_INFO("项目基本信息"),
    PROJECT_PRE_PROGRESS("项目前期信息"),
    PROJECT_CONTRACT("合同备案信息"),
    HISTORY_MONTHLY_STATISTIC("月报历史数据")
    ;


    private String msg;

    NotificationTypeEnum(String msg) {
        this.msg = msg;
    }
}
