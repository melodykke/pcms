package com.gzzhsl.pcms.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class AnnualInvestment {
    private String annualInvestmentId;

    private BigDecimal applyFigure;

    private BigDecimal approvedFigure;

    private Date createTime;

    private Byte state;

    private Date updateTime;

    private String year;

    private String baseInfoId;

    private String submitter;

    private List<AnnualInvestmentImg> annualInvestmentImgs;
}