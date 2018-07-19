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
    PERSON_INFO_DUPLICATED(1005, "重复提交个人信息"),
    USER_INFO_DUPLICATED(1006, "存在重复的用户名"),

    MONTHLY_REPORT_IMG_ERROR(1100, "月报图片错误"),
    MONTHLY_REPORT_ERROR(1101, "月报错误"),
    MONTHLY_REPORTS_FETCH_ERROR(1102, "获取月报集错误"),
    MONTHLY_REPORTS_INSERT_ERROR(1103, "插入月报与某已存在月报月份一致，并且该已存在月报状态为已审批"),
    MONTHLY_REPORTS_MULTIPLE_PER_MONTH_ERROR(1104, "同一月存在多分月报"),
    MONTHLY_REPORTS_NONE_PER_MONTH_ERROR(1105, "查询月无月报"),
    MONTHLY_REPORT_BASE_INFO_STATE_ERROR(1106, "请优先配置水库项目基础信息，待通过后重试"),
    MONTHLY_REPORTS_APPROVEAL_ERROR(1201, "月报审批错误，月报ID为空"),
    MONTHLY_REPORTS_NO_CORRESPOND_REPORT_ERROR(1202, "审批月报错误，无月报实体对应月报ID"),
    MONTHLY_REPORTS_CHECKED_OTHERS_ERROR(1203, "不能审批不属于本用户所属工程的月报"),
    MONTHLY_REPORTS_CHECK_CHECKED_ERROR(1204, "当前审批月报已经审批过，不能重复审批"),
    HISTORY_MONTHLY_REPORT_ERROR(1150, "月报历史数据错误"),
    HISTORY_UNABLE_MODIFY_ERROR(1151, "月报历史数据已经设置，并且被审批通过，无法更改！"),
    HISTORY_MONTHLY_STATISTIC_NO_CORRESPOND_DATA_ERROR(1152, "审批月报历史数据错误，无对应月报历史数据"),
    HISTORY_MONTHLY_STATISTIC_CHECK_CHECKED_ERROR(1153, "当前审批月报历史数据已经审批过，不能重复审批"),

    BASE_INFO_NO_RECORD_ERROR(1304, "请优先配置项目基本信息"),
    BASE_INFO_VO_PARAMS_ERROR(1305, "项目基本信息VO参数错误"),
    BASE_INFO_DUPLICATED(1306, "该账户已经存在配置过的项目基本信息，无需重新配置"),
    BASE_INFO_APPROVAL_ERROR(1401, "审批基础信息错误，ID为空"),
    BASE_INFO_NO_CORRESPOND_RECORD_ERROR(1402, "审批基础信息错误，审批查询的基础信息所对应ID无记录"),
    BASE_INFO_CHECKED_OTHERS_ERROR(1403, "不能审批不属于本用户所属工程的项目基本信息"),
    BASE_INFO_CHECK_CHECKED_ERROR(1404, "当前项目基础信息已经审批过，不能重复审批"),
    BASE_INFO_SUBMIT_NO_PARENT_ERROR(1405, "项目基础信息提交错误，无审批单位"),
    BASE_INFO_APPROVAL_PASSED_ERROR(1406, "不能审批已通过项目"),

    PRE_PROGRESS_APPROVAL_ERROR(1500, "审批项目前期错误，ID为空"),
    PRE_PROGRESS_CHECKED_OTHERS_ERROR(1502, "不能审批不属于本用户所属工程的项目前期信息"),
    PRE_PROGRESS_CHECK_CHECKED_ERROR(1503, "当前项目前期信息已经审批过，不能重复审批"),
    PRE_PROGRESS_NO_CORRESPOND_RECORD_ERROR(1504, "审批基础前期错误，审批查询的基础前期所对应ID无记录"),
    PRE_PROGRESS_APPROVAL_PASSED_ERROR(1505, "不能审批已通过项目"),
    PRE_PROGRESS_INFO_ERROR(1506, "提交的项目前期信息出错，若一直无法正常提交，请联系系统管理员"),

    CONTRACT_VO_PARAMS_ERROR(1600, "合同信息VO参数错误"),
    CONTRACT_NO_PROJECT_ERROR(1601, "请优先配置水库基础信息"),
    CONTRACT_APPROVAL_ERROR(1602, "审批合同备案错误，ID为空"),
    CONTRACT_NO_CORRESPOND_RECORD_ERROR(1603, "审批合同备案错误，审批查询的合同备案所对应ID无记录"),
    CONTRACT_CHECKED_OTHERS_ERROR(1604, "不能审批不属于本用户所属工程的合同备案信息"),
    CONTRACT_CHECK_CHECKED_ERROR(1605, "当前合同备案信息已经审批过，不能重复审批"),

    ACCOUNT_PASSWORD_INCONSISTENCY(2201, "新输入密码与确认密码不一致或新密码与原密码相同"),
    ACCOUNT_DUPLICATED(2202, "已存在相同账号名的用户"),
    ACCOUNT_NO_PROJECT(2203, "没有水库工程，请优先配置水库工程"),
    ACCOUNT_SUBACCOUNT_ALREDY_EXIST(2204, "本账号已存在子账号，目前只允许一个主账号添加一个子账号"),

    ANNUAL_INVESTMENT_APPLY_ERROR(3000, "年度投融资计划申报出错"),
    ANNUAL_INVESTMENT_APPLY_YEAR_DUPLICATED_ERROR(3001, "年度投融资计划申报出错, 本次申报计划投资年份已存在！"),
    ANNUAL_INVESTMENT_APPLY_YEAR_HAS_DUPLICATED_ERROR(3002, "年度投融资计划申报出错, 申报计划投资年份已存在多个副本！请联系系统管理员！"),
    ANNUAL_INVESTMENT_NO_PROJECT_ERROR(3003, "请优先配置水库基础信息"),
    ;

    private Integer code;
    private String msg;

    SysEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
