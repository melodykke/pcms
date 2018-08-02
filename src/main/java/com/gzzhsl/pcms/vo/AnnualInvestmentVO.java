package com.gzzhsl.pcms.vo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gzzhsl.pcms.entity.AnnualInvestment;
import com.gzzhsl.pcms.entity.AnnualInvestmentImg;
import com.gzzhsl.pcms.entity.BaseInfo;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class AnnualInvestmentVO {
    private String rtFileTempPath;
    private String annualInvestmentId;
    @NotBlank(message = "资金计划年份为空")
    private String year;
    @NotNull(message = "资金申请额不能为空")
    private BigDecimal applyFigure;
    @NotNull(message = "资金核准额不能为空")
    private BigDecimal approvedFigure;
    @JsonBackReference
    private BaseInfo baseInfo;
    private Byte state;
    private String submitter;
    private List<AnnualInvestmentImgVO> annualInvestmentImgVOs;
    private Date createTime;
    private Date updateTime;
}
