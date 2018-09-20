package com.gzzhsl.pcms.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PreProgress {
    private String preProgressId;

    private Date createTime;

    private String owner;

    private Integer repeatTimes;

    private Byte state;

    private Date updateTime;

    private String baseInfoId;

    private List<PreProgressImg> preProgressImgs;
}