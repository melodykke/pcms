package com.gzzhsl.pcms.model;

import lombok.Data;

import java.util.Date;

@Data
public class AnnualInvestmentImg {
    private String annualInvestmentImgId;

    private Date createTime;

    private String imgAddr;

    private String imgDesc;

    private String annualInvestmentId;
}