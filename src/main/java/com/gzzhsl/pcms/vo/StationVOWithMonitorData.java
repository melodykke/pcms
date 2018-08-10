package com.gzzhsl.pcms.vo;

import lombok.Data;

import java.util.List;

@Data
public class StationVOWithMonitorData<T> {
    private String stationId; // 测站编码
    private Integer stationType; // 站点类型： 0雨量水位 1雨量 2水位
    private String name; // 测站名称
    private String lgtd; // 经度
    private String lttd; // 纬度
    private T data;
}
