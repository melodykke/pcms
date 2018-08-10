package com.gzzhsl.pcms.vo;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class RainfallsRtVO {
    private List<RainFallVO> data;
    private Boolean resflag;
}
