package com.gzzhsl.pcms.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ProjectMonthlyReportImgVO {
    private String imgAddr;
    private String imgDesc;
    private Date createTime;
    private String thumbnailAddr;
}
