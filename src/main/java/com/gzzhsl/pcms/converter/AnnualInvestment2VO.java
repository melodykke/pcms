package com.gzzhsl.pcms.converter;

import com.gzzhsl.pcms.model.AnnualInvestment;
import com.gzzhsl.pcms.vo.AnnualInvestmentVO;
import org.springframework.beans.BeanUtils;

public class AnnualInvestment2VO {
    public static AnnualInvestmentVO convert(AnnualInvestment annualInvestment) {
        AnnualInvestmentVO annualInvestmentVO = new AnnualInvestmentVO();
        BeanUtils.copyProperties(annualInvestment, annualInvestmentVO);
        annualInvestmentVO.setBaseInfo(null);
        return annualInvestmentVO;
    }

}
