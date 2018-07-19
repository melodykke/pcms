package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.entity.AnnualInvestment;
import com.gzzhsl.pcms.vo.AnnualInvestmentVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnnualInvestmentService {
    AnnualInvestment save(AnnualInvestmentVO annualInvestmentVO);
    boolean containsSameYear(String year);
    Page<AnnualInvestment> findByState(Pageable pageable, byte state);
}
