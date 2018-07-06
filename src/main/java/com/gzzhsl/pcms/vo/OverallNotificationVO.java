package com.gzzhsl.pcms.vo;

import lombok.Data;

import java.util.Map;

@Data
public class OverallNotificationVO {
    private Integer allUncheckedNum;
    private Map<String, String> article; // key: title  value: diffTime
}
