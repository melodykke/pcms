package com.gzzhsl.pcms.vo;

import lombok.Data;

@Data
public class ProjectMonthVO {
    private String year;
    private String month;
    private String submitter;
    private String pId;
    private Byte state;
    private String thumbnailUrl;
}
