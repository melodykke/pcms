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
    ;

    private Integer code;
    private String msg;

    SysEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
