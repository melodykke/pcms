package com.gzzhsl.pcms.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gzzhsl.pcms.entity.BaseInfo;
import com.gzzhsl.pcms.entity.PreProgressEntry;
import com.gzzhsl.pcms.entity.PreProgressImg;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class PreProgressVO {
    private String rtFileTempPath;
    private String PreProgressId;
    private List<PreProgressEntry> preProgressEntries;
    private Byte state;
    private Integer repeatTimes;
/*    private BaseInfo baseInfo;*/
    private List<PreProgressImgVO> preProgressImgVOs;
    private String owner; // 上报人
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH-mm-ss")
    private Date createTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH-mm-ss")
    private Date updateTime;
}
