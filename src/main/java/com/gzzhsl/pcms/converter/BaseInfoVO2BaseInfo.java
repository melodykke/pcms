package com.gzzhsl.pcms.converter;

import com.gzzhsl.pcms.entity.BaseInfo;
import com.gzzhsl.pcms.entity.BaseInfoImg;
import com.gzzhsl.pcms.vo.BaseInfoVO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;

@Data
public class BaseInfoVO2BaseInfo {
    public static BaseInfo convert(BaseInfoVO baseInfoVO) {
        BaseInfo baseInfo = new BaseInfo();
        BeanUtils.copyProperties(baseInfoVO, baseInfo);
        baseInfo.setState((byte) 0);
        baseInfo.setCreateTime(new Date());
        baseInfo.setUpdateTime(new Date());
        return baseInfo;
    }
}
