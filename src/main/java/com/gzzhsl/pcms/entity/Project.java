package com.gzzhsl.pcms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Slf4j
public class Project {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String projectId;
    @JsonBackReference
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserInfo> userInfoList;
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
    @JsonManagedReference
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProjectMonthlyReport> projectMonthlyReportList;
    @OneToOne(mappedBy = "project")
    private HistoryMonthlyReportExcelStatistics historyMonthlyReportExcelStatistics;
    private Date createTime;
    private Date updateTime;
}
