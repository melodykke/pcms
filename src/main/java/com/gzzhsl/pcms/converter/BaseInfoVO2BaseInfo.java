package com.gzzhsl.pcms.converter;

import com.gzzhsl.pcms.model.BaseInfo;
import com.gzzhsl.pcms.util.UUIDUtils;
import com.gzzhsl.pcms.vo.BaseInfoVO;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import java.util.Date;

@Data
public class BaseInfoVO2BaseInfo {
    public static BaseInfo convert(BaseInfoVO baseInfoVO) {
        BaseInfo baseInfo = new BaseInfo();
        BeanUtils.copyProperties(baseInfoVO, baseInfo);
        baseInfo.setBaseInfoId(UUIDUtils.getUUIDString());
        baseInfo.setState((byte) 0);
        baseInfo.setCreateTime(new Date());
        baseInfo.setUpdateTime(new Date());
        return baseInfo;
    }
}
