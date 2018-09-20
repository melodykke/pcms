package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.model.ReservoirCode;

import java.util.List;

public interface ReservoirCodeService {
    List<ReservoirCode> findAll();

    ReservoirCode findByName(String reservoirName);

    ReservoirCode findByBaseInfoId(String baseInfoId);
}
