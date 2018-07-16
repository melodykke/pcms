package com.gzzhsl.pcms.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gzzhsl.pcms.entity.ContractImg;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;


import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ContractVO {
    private String rtFileTempPath;
    private String id;
    @NotBlank(message = "合同名不能为空")
    private String name;
    @NotBlank(message = "合同号不能为空")
    private String number;
    @NotBlank(message = "类型不能为空")
    private String type;
    @NotNull(message = "合同签订时间不能为空")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private String signDate;
    @NotBlank(message = "甲方不能为空")
    private String partyA;
    @NotBlank(message = "乙方不能为空")
    private String partyB;
    @NotNull(message = "合同价不能为空")
    private BigDecimal price;
    @NotBlank(message = "合同内容不能为空")
    private String content;
    private Byte state; // 0 未审核 1 通过 -1 未通过
    private String remark;
    private List<ContractImgVO> contractImgVOs;
}
