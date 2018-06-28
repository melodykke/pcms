package com.gzzhsl.pcms.enums;

import lombok.Getter;

@Getter
public enum SysEnum {
    BOOLEAN_RESULT_SUCCESS(1, "成功"),
    BOOLEAN_RESULT_FAIL(0, "失败"),
    NO_PROJECT_IN_THISUSER(100, "该用户没有申报水库项目，或项目未审批通过"),
    Sys_INNER_ERROR(444, "系统内部错误"),
    DATA_CONFIG_SUCCESS(1000, "数据配置成功"),
    SIGNIN_PARAM_ERROR(1001, "用户登录信息错误"),
    DATA_CALLBACK_SUCCESS(1002, "数据成功返回"),
    DATA_CALLBACK_FAILED(1003, "数据返回错误"),
    DATA_SUBMIT_FAILED(1004, "数据提交错误"),

    MONTHLY_REPORT_IMG_ERROR(1100, "月报图片错误"),
    MONTHLY_REPORT_ERROR(1101, "月报错误"),
    MONTHLY_REPORTS_FETCH_ERROR(1102, "获取月报集错误"),
    MONTHLY_REPORTS_INSERT_ERROR(1103, "插入月报与某已存在月报月份一致，并且该已存在月报状态为已审批"),
    MONTHLY_REPORTS_MULTIPLE_PER_MONTH_ERROR(1104, "同一月存在多分月报"),
    MONTHLY_REPORTS_NONE_PER_MONTH_ERROR(1105, "查询月无月报"),
    MONTHLY_REPORTS_APPROVEAL_ERROR(1201, "月报审批错误，月报ID为空"),
    MONTHLY_REPORTS_NO_CORRESPOND_REPORT_ERROR(1202, "审批月报错误，无月报实体对应月报ID"),
    MONTHLY_REPORTS_CHECKED_OTHERS_ERROR(1203, "不能审批不属于本用户所属工程的月报"),
    MONTHLY_REPORTS_CHECK_CHECKED_ERROR(1204, "当前审批月报已经审批过，不能重复审批"),

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
