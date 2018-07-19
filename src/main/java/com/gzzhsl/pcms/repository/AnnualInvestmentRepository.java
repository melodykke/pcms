package com.gzzhsl.pcms.repository;

import com.gzzhsl.pcms.entity.AnnualInvestment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AnnualInvestmentRepository extends JpaRepository<AnnualInvestment, String>, JpaSpecificationExecutor<AnnualInvestment>{
}
