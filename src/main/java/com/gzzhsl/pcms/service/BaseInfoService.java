package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.entity.BaseInfo;

import java.util.List;

public interface BaseInfoService {

    List<BaseInfo> getAllProject();
    BaseInfo save(BaseInfo baseInfo);
    Boolean connectBaseInfoAndUserInfo();
}
