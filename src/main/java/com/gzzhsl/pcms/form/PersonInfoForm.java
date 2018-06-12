package com.gzzhsl.pcms.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PersonInfoForm {
    @NotNull(message = "账号名必填")
    private String name;
    @NotNull(message = "联系电话必填")
    private String tel;
    @NotNull(message = "qq必填")
    private String qq;
    @NotNull(message = "邮箱必填")
    private String email;
    @NotNull(message = "身份证必填")
    private String id_num;
    @NotNull(message = "职位必填")
    private String title;
    @NotNull(message = "通讯地址必填")
    private String address;

}
