package com.gzzhsl.pcms.repository;

import com.gzzhsl.pcms.entity.AnnualInvestment;
import com.gzzhsl.pcms.entity.BaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface AnnualInvestmentRepository extends JpaRepository<AnnualInvestment, String>, JpaSpecificationExecutor<AnnualInvestment>{
    List<AnnualInvestment> findAllByBaseInfo(BaseInfo baseInfo);
}
