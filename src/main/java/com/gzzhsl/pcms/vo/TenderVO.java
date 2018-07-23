package com.gzzhsl.pcms.vo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gzzhsl.pcms.entity.BaseInfo;
import com.gzzhsl.pcms.entity.TenderImg;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class TenderVO {
    private String rtFileTempPath;
    private String tenderId;
    private String tenderFilingUnit; // 招标备案单位
    private BigDecimal nameOfLots; // 标段名称
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date bidPlanDate;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date bidCompleteDate;
    private String bidAgent;
    private String tenderAgent;
    @JsonBackReference
    private BaseInfo baseInfo;
    private Byte state;

    private List<TenderImgVO> tenderImgVOs;
    private String submitter;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH-mm-ss")
    private Date createTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH-mm-ss")
    private Date updateTime;
}
