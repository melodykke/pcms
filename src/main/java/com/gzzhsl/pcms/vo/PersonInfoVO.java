package com.gzzhsl.pcms.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PersonInfoVO {
    @NotBlank(message = "姓名不能为空")
    private String name;
    private String nickName;
    private String gender;
    private String profileImg;
    private Integer enableStatus;
    @NotBlank(message = "联系电话不能为空")
    private String tel;
    @NotBlank(message = "QQ不能为空")
    private String qq;
    @NotBlank(message = "邮件地址不能为空")
    private String email;
    @NotBlank(message = "身份证号不能为空")
    private String idNum;
    @NotBlank(message = "职位不能为空")
    private String title;
    @NotBlank(message = "通讯地址不能为空")
    private String address;
    private String country;
    private String province;
    private String city;
}
