package com.gzzhsl.pcms.model;

import lombok.Data;

import java.util.Date;

@Data
public class PersonInfo {
    private String personId;

    private String address;

    private Date createTime;

    private String email;

    private Integer enableStatus;

    private String gender;

    private String idNum;

    private String name;

    private String nickName;

    private String profileImg;

    private String qq;

    private String tel;

    private String title;

    private Date updateTime;

    private String userId;

    private String wechatAuthId;

    private String city;

    private String country;

    private String province;
}