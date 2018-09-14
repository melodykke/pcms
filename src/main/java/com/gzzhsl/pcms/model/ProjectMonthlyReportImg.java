package com.gzzhsl.pcms.model;

import lombok.Data;

import java.util.Date;

@Data
public class ProjectMonthlyReportImg {
    private String projectMonthlyReportImgId;

    private Date createTime;

    private String imgAddr;

    private String imgDesc;

    private String projectMonthlyReportId;
}