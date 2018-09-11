package com.gzzhsl.pcms.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserInfo {
    private String userId;

    private Byte active;

    private Date createTime;

    private String name;

    private String password;

    private String salt;

    private Date updateTime;

    private String username;

    private String baseInfoId;

    private String parentId;

    private String openId;

    private List<SysRole> roles;

}