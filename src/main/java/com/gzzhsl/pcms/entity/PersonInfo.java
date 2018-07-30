package com.gzzhsl.pcms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Slf4j
public class PersonInfo {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String personId;
    private String name;
    private String nickName;
    private String gender;
    private String profileImg;
    private Integer enableStatus;
    private String tel;
    private String qq;
    private String email;
    private String id_num;
    private String title;
    private String address;
    private String province;
    private String city;
    private String country;
    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserInfo userInfo;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wechat_auth_id")
    private WechatAuth wechatAuth;
    private Date createTime;
    private Date updateTime;

}
