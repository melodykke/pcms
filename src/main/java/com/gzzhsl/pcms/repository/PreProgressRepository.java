package com.gzzhsl.pcms.repository;

import com.gzzhsl.pcms.entity.BaseInfo;
import com.gzzhsl.pcms.entity.PreProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PreProgressRepository extends JpaRepository<PreProgress, String>, JpaSpecificationExecutor<PreProgress> {
    PreProgress findByBaseInfo(BaseInfo baseInfo);
    PreProgress findByPreProgressId(String preProgressId);
}
