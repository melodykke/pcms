package com.gzzhsl.pcms.converter;

import com.gzzhsl.pcms.entity.BaseInfo;
import com.gzzhsl.pcms.entity.Contract;
import com.gzzhsl.pcms.vo.BaseInfoVO;
import com.gzzhsl.pcms.vo.ContractVO;
import org.springframework.beans.BeanUtils;

public class Contract2VO {
    public static ContractVO convert(Contract contract) {
        ContractVO contractVO = new ContractVO();
        BeanUtils.copyProperties(contract, contractVO);
        return contractVO;
    }
}
