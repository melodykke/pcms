package com.gzzhsl.pcms.repository;

import com.gzzhsl.pcms.entity.BaseInfo;
import com.gzzhsl.pcms.entity.Tender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TenderRepository extends JpaRepository<Tender, String>, JpaSpecificationExecutor<Tender> {
    List<Tender> findAllByBaseInfo(BaseInfo baseInfo);
    Tender findByTenderId(String tenderId);
}
