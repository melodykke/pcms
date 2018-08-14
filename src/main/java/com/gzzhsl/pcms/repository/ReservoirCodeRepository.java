package com.gzzhsl.pcms.repository;

import com.gzzhsl.pcms.entity.ReservoirCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservoirCodeRepository extends JpaRepository<ReservoirCode, Integer> {
    ReservoirCode findByReservoirName(String reservoirName);
    ReservoirCode findByBaseInfoId(String baseInfoId);
}
