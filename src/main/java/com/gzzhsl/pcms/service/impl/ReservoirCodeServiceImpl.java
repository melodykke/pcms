package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.mapper.ReservoirCodeMapper;
import com.gzzhsl.pcms.model.ReservoirCode;
import com.gzzhsl.pcms.service.ReservoirCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
public class ReservoirCodeServiceImpl implements ReservoirCodeService {
    @Autowired
    private ReservoirCodeMapper reservoirCodeMapper;

    @Override
    public List<ReservoirCode> findAll() {
        return reservoirCodeMapper.selectAll();
    }

    @Override
    public ReservoirCode findByName(String reservoirName) {
        if (reservoirName == null || "".equals(reservoirName)) {
            return null;
        }
        return reservoirCodeMapper.findByReservoirName(reservoirName);
    }

    @Override
    public ReservoirCode findByBaseInfoId(String baseInfoId) {
        return reservoirCodeMapper.findByBaseInfoId(baseInfoId);
    }
}
