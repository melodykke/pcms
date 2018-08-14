package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.entity.ReservoirCode;

import java.util.List;

public interface ReservoirCodeService {
    List<ReservoirCode> getAll();
    ReservoirCode getByName(String name);
    ReservoirCode getByBaseInfoId(String baseInfoId);
}
