package com.gzzhsl.pcms.repository;

import com.gzzhsl.pcms.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, String>, JpaSpecificationExecutor<Contract> {
    //Contract findById(String id);
    List<Contract> findByState(byte state);
}
