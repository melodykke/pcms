package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.entity.AnnualInvestmentImg;
import com.gzzhsl.pcms.repository.AnnualInvestmentImgRepository;
import com.gzzhsl.pcms.service.AnnualInvestmentImgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class AnnualInvestmentImgServiceImpl implements AnnualInvestmentImgService {

    @Autowired
    private AnnualInvestmentImgRepository annualInvestmentImgRepository;

    @Override
    public AnnualInvestmentImg getByAnnualInvestmentImgId(String annualInvestmentImgId) {
        return annualInvestmentImgRepository.findOneByAnnualInvestmentImgId(annualInvestmentImgId);
    }


}
