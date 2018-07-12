package com.gzzhsl.pcms.repository;

import com.gzzhsl.pcms.entity.BaseInfo;
import com.gzzhsl.pcms.entity.BaseInfoImg;
import com.gzzhsl.pcms.entity.Contract;
import com.gzzhsl.pcms.entity.ContractImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractImgRepository extends JpaRepository<ContractImg, String>{
    ContractImg findByContractImgId(String contractImgId);
    List<ContractImg> deleteByContract(Contract contract);
}
