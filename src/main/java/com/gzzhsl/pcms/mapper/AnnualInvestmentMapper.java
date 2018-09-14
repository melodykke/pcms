package com.gzzhsl.pcms.mapper;

import com.gzzhsl.pcms.model.AnnualInvestment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AnnualInvestmentMapper {
    int deleteByPrimaryKey(String annualInvestmentId);

    int insert(AnnualInvestment record);

    AnnualInvestment selectByPrimaryKey(String annualInvestmentId);

    List<AnnualInvestment> selectAll();

    int updateByPrimaryKey(AnnualInvestment record);

    AnnualInvestment findByBaseInfoIdAndYear(@Param("baseInfoId") String baseInfoId, @Param("year") String year);

    List<AnnualInvestment> findAllByBaseInfoId(String baseInfoId);

    List<AnnualInvestment> findPageByBaseInfoIdAndState(@Param("baseInfoId") String baseInfoId, @Param("state") byte state);


}