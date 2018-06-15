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
    private String tel;
    private String qq;
    private String email;
    private String id_num;
    private String title;
    private String address;
    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserInfo userInfo;
    private Date createTime;
    private Date updateTime;

}
