package com.gzzhsl.pcms.converter;

import com.gzzhsl.pcms.entity.BaseInfo;
import com.gzzhsl.pcms.vo.BaseInfoVO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class BaseInfoVO2BaseInfo {
    public static BaseInfo convert(BaseInfoVO baseInfoVO) {
        BaseInfo baseInfo = new BaseInfo();
        BeanUtils.copyProperties(baseInfoVO, baseInfo);
        return baseInfo;
    }
}
