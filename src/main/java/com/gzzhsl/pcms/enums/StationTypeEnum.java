package com.gzzhsl.pcms.enums;

import lombok.Getter;

@Getter
public enum StationTypeEnum {
    RAINFALL_WATER_LEVEL(0, "雨量水位站"),
    RAINFALL(1, "雨量站"),
    WATER_LEVEL(2, "水位站")
    ;
    private Integer code;
    private String type;
    StationTypeEnum(int code, String type) {
        this.code = code;
        this.type = type;
    }
}
