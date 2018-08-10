package com.gzzhsl.pcms.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class RainFallVO {
    private String stcd; // 测站编码
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH-mm-ss")
    private Date tm; // 数据时间
    private double drp; // 雨量值 毫米
}
