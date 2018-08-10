package com.gzzhsl.pcms.vo;

import lombok.Data;

@Data
public class WaterLevelVO {
    private String stcd; // 测站编码
    private String tm; // 数据时间
    private double z; // 水位 米
    private double q; // 流量 立方米/秒
}
