package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.entity.Contract;
import com.gzzhsl.pcms.vo.ContractVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContractService {
    Contract findById(String id);
    Contract save(ContractVO contractVO);
    Page<Contract> findByState(Pageable pageable, byte state);
}
