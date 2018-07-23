package com.gzzhsl.pcms.repository;

import com.gzzhsl.pcms.entity.AnnualInvestment;
import com.gzzhsl.pcms.entity.AnnualInvestmentImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnualInvestmentImgRepository extends JpaRepository<AnnualInvestmentImg, String> {
    List<AnnualInvestmentImg> deleteByAnnualInvestment(AnnualInvestment annualInvestment);
    AnnualInvestmentImg findOneByAnnualInvestmentImgId(String annualInvestmentImgId);
}
