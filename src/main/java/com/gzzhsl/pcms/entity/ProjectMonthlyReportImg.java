package com.gzzhsl.pcms.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Slf4j
public class ProjectMonthlyReportImg {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String projectMonthlyReportImgId;
    private String imgAddr;
    private String imgDesc;
    private Date createTime;
    @ManyToOne
    @JoinColumn(name = "project_monthly_report_id")
    private ProjectMonthlyReport projectMonthlyReport;


}
