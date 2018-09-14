package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.mapper.BaseInfoImgMapper;
import com.gzzhsl.pcms.model.BaseInfo;
import com.gzzhsl.pcms.model.BaseInfoImg;
import com.gzzhsl.pcms.repository.BaseInfoImgRepository;
import com.gzzhsl.pcms.service.BaseInfoImgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
public class BaseInfoImgServiceImpl implements BaseInfoImgService {

    @Autowired
    private BaseInfoImgMapper baseInfoImgMapper;

    @Override
    public Integer batchSaveBaseInfoImgs(List<BaseInfoImg> baseInfoImgs) {
        if (baseInfoImgs == null || baseInfoImgs.size() == 0) {
            return 0;
        }
        return baseInfoImgMapper.batchInsertBaseInfoImgs(baseInfoImgs);
    }

    @Override
    public List<BaseInfoImg> findBaseInfoImgsByBaseInfoId(String baseInfoId) {
        if (baseInfoId == null || "".equals(baseInfoId)) {
            return new ArrayList<BaseInfoImg>();
        }
        return baseInfoImgMapper.findBaseInfoImgsByBaseInfoId(baseInfoId);
    }

    @Override
    public List<BaseInfoImg> deleteByBaseInfo(BaseInfo baseInfo) {
        return null;
    }


    @Override
    public Integer batchDeleteByBaseInfoId(String baseInfoId) {
        return baseInfoImgMapper.batchDeleteByBaseInfoId(baseInfoId);
    }










    @Override
    public BaseInfoImg getByBaseInfoImgId(String id) {
       // return baseInfoImgRepository.findByBaseInfoImgId(id);
        return null;
    }

}
