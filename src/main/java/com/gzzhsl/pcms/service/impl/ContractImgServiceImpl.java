package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.entity.Contract;
import com.gzzhsl.pcms.entity.ContractImg;
import com.gzzhsl.pcms.repository.ContractImgRepository;
import com.gzzhsl.pcms.service.ContractImgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class ContractImgServiceImpl implements ContractImgService {
    @Autowired
    private ContractImgRepository contractImgRepository;

    @Override
    public ContractImg getByContractImgId(String contractImgId) {
        return contractImgRepository.findByContractImgId(contractImgId);
    }

    @Override
    public List<ContractImg> deleteByContract(Contract contract) {
        return contractImgRepository.deleteByContract(contract);
    }
}
