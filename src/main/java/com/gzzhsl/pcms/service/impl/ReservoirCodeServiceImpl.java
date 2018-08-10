package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.entity.ReservoirCode;
import com.gzzhsl.pcms.repository.ReservoirCodeRepository;
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
    private ReservoirCodeRepository reservoirCodeRepository;

    @Override
    public List<ReservoirCode> getAll() {
        return reservoirCodeRepository.findAll();
    }

    @Override
    public ReservoirCode getByName(String name) {
        return reservoirCodeRepository.findByReservoirName(name);
    }
}
