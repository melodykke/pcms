package com.gzzhsl.pcms.converter;

import com.gzzhsl.pcms.model.BaseInfo;
import com.gzzhsl.pcms.vo.BaseInfoVO;
import org.springframework.beans.BeanUtils;

public class BaseInfo2VO {
    public static BaseInfoVO convert(BaseInfo baseInfo) {
        BaseInfoVO baseInfoVO = new BaseInfoVO();
        BeanUtils.copyProperties(baseInfo, baseInfoVO);
        return baseInfoVO;
    }


}
