package com.gzzhsl.pcms.vo;

import lombok.Data;

import java.util.List;

@Data
public class WaterLevelsRtVO {
    private List<WaterLevelVO> data;
    private Boolean resflag;
}
