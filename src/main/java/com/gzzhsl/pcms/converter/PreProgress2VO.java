package com.gzzhsl.pcms.converter;

import com.gzzhsl.pcms.entity.PreProgress;
import com.gzzhsl.pcms.vo.BaseInfoVO;
import com.gzzhsl.pcms.vo.PreProgressVO;
import org.springframework.beans.BeanUtils;

public class PreProgress2VO {
    public static PreProgressVO convert(PreProgress preProgress) {
        PreProgressVO preProgressVO = new PreProgressVO();
        BeanUtils.copyProperties(preProgress, preProgressVO);
        return preProgressVO;
    }


}
