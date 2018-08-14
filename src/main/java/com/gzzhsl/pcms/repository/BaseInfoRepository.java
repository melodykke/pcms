package com.gzzhsl.pcms.repository;

import com.gzzhsl.pcms.entity.BaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BaseInfoRepository extends JpaRepository<BaseInfo, String> {
    BaseInfo findByBaseInfoId(String baseInfoId);
    List<BaseInfo> findByRegionId(Integer regionId);
}
