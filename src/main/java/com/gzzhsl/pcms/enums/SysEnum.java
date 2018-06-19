package com.gzzhsl.pcms.enums;

import lombok.Getter;

@Getter
public enum SysEnum {
    BOOLEAN_RESULT_SUCCESS(1, "成功"),
    BOOLEAN_RESULT_FAIL(0, "失败"),
    Sys_INNER_ERROR(444, "系统内部错误"),
    DATA_CONFIG_SUCCESS(1000, "数据配置成功"),
    SIGNIN_PARAM_ERROR(1001, "用户登录信息错误"),
    DATA_CALLBACK_SUCCESS(1002, "数据成功返回"),
    DATA_CALLBACK_FAILED(1003, "数据返回错误"),
    DATA_SUBMIT_FAILED(1004, "数据提交错误"),

    MONTHLY_REPORT_IMG_ERROR(1100, "月报图片错误"),
    MONTHLY_REPORT_ERROR(1101, "月报错误"),
    MONTHLY_REPORTS_FETCH_ERROR(1102, "获取月报集错误"),

    ACCOUNT_PASSWORD_INCONSISTENCY(1201, "新输入密码与确认密码不一致或新密码与原密码相同"),
    ACCOUNT_DUPLICATED(1202, "已存在相同账号名的用户"),
    ACCOUNT_NO_PROJECT(1203, "没有水库工程，请优先配置水库工程"),
    ACCOUNT_SUBACCOUNT_ALREDY_EXIST(1204, "本账号已存在子账号，目前只允许一个主账号添加一个子账号")
    ;

    private Integer code;
    private String msg;

    SysEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
