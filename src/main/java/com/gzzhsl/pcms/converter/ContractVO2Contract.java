package com.gzzhsl.pcms.converter;

import com.gzzhsl.pcms.entity.Contract;
import com.gzzhsl.pcms.vo.ContractVO;
import org.springframework.beans.BeanUtils;

import java.util.Date;


public class ContractVO2Contract {
    public static Contract convert(ContractVO contractVO) {
        Contract contract = new Contract();
        BeanUtils.copyProperties(contractVO, contract);
        contract.setState((byte) 0);
        contract.setCreateTime(new Date());
        contract.setUpdateTime(new Date());
        return contract;
    }
}
