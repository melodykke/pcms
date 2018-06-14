package com.gzzhsl.pcms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Slf4j
public class Project {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String projectId;
    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserInfo userInfo;
    private String plantName;
    private String parentId;
    private String remark;
    private String projectType;
    private String location;
    private String legalRepresentativeId;
    private String legalRepresentativeName;
    private String legalPersonId;
    private String legalPersonName;
    private String longitude;
    private String latitude;
    private Date createTime;
    private Date updateTime;
}
