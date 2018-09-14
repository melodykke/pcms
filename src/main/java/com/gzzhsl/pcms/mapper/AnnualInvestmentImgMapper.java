package com.gzzhsl.pcms.mapper;

import com.gzzhsl.pcms.model.AnnualInvestmentImg;
import java.util.List;

public interface AnnualInvestmentImgMapper {
    int deleteByPrimaryKey(String annualInvestmentImgId);

    int insert(AnnualInvestmentImg record);

    AnnualInvestmentImg selectByPrimaryKey(String annualInvestmentImgId);

    List<AnnualInvestmentImg> selectAll();

    int updateByPrimaryKey(AnnualInvestmentImg record);
}