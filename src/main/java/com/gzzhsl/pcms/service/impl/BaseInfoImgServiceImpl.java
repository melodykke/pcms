package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.entity.BaseInfo;
import com.gzzhsl.pcms.entity.BaseInfoImg;
import com.gzzhsl.pcms.repository.BaseInfoImgRepository;
import com.gzzhsl.pcms.service.BaseInfoImgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
public class BaseInfoImgServiceImpl implements BaseInfoImgService {
    @Autowired
    private BaseInfoImgRepository baseInfoImgRepository;

    @Override
    public BaseInfoImg getByBaseInfoImgId(String id) {
        return baseInfoImgRepository.findByBaseInfoImgId(id);
    }

    @Override
    public List<BaseInfoImg> deleteByBaseInfo(BaseInfo baseInfo) {
        return baseInfoImgRepository.deleteByBaseInfo(baseInfo);
    }
}
