package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.entity.BaseInfo;
import com.gzzhsl.pcms.entity.BaseInfoImg;

import java.util.List;

public interface BaseInfoImgService {
    BaseInfoImg getByBaseInfoImgId(String id);
    List<BaseInfoImg> deleteByBaseInfo(BaseInfo baseInfo);
}
