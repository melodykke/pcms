package com.gzzhsl.pcms.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class Contract {
    private String id;

    private String content;

    private Date createTime;

    private String name;

    private String number;

    private String partya;

    private String partyb;

    private BigDecimal price;

    private String remark;

    private String signDate;

    private String type;

    private Date updateTime;

    private String baseInfoId;

    private Byte label;

    private Byte state;

    private List<ContractImg> contractImgs;
}