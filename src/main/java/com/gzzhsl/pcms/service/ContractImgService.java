package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.entity.Contract;
import com.gzzhsl.pcms.entity.ContractImg;
import com.gzzhsl.pcms.entity.PreProgress;
import com.gzzhsl.pcms.entity.PreProgressImg;

import java.util.List;

public interface ContractImgService {
    ContractImg getByContractImgId(String contractImgId);
    List<ContractImg> deleteByContract(Contract contract);
}
