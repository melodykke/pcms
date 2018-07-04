package com.gzzhsl.pcms.repository;

import com.gzzhsl.pcms.entity.BaseInfo;
import com.gzzhsl.pcms.entity.BaseInfoImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BaseInfoImgRepository extends JpaRepository<BaseInfoImg, String>{
    BaseInfoImg findByBaseInfoImgId(String id);
    List<BaseInfoImg> deleteByBaseInfo(BaseInfo baseInfo);
}
