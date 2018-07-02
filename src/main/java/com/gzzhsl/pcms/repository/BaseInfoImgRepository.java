package com.gzzhsl.pcms.repository;

import com.gzzhsl.pcms.entity.BaseInfoImg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseInfoImgRepository extends JpaRepository<BaseInfoImg, String>{
    BaseInfoImg findByBaseInfoImgId(String id);
}
