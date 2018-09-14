package com.gzzhsl.pcms.converter;

import com.gzzhsl.pcms.model.BaseInfo;
import com.gzzhsl.pcms.vo.BaseInfoManagerIndexVO;
import org.springframework.beans.BeanUtils;

public class BaseInfo2ManagerIndexVO {
    public static BaseInfoManagerIndexVO convert(BaseInfo baseInfo) {
        BaseInfoManagerIndexVO baseInfoManagerIndexVO = new BaseInfoManagerIndexVO();
        BeanUtils.copyProperties(baseInfo, baseInfoManagerIndexVO);
        return baseInfoManagerIndexVO;
    }
}
