package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.entity.AnnualInvestment;
import com.gzzhsl.pcms.entity.AnnualInvestmentImg;

import java.util.List;

public interface AnnualInvestmentImgService {
    AnnualInvestmentImg getByAnnualInvestmentImgId(String annualInvestmentImgId);
    //List<AnnualInvestmentImg> deleteByAnnualInvestmentImg(AnnualInvestment annualInvestment);
}
