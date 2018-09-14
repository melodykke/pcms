package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.model.BaseInfo;
import com.gzzhsl.pcms.model.BaseInfoImg;

import java.util.List;

public interface BaseInfoImgService {
    Integer batchDeleteByBaseInfoId(String baseInfoId);
    Integer batchSaveBaseInfoImgs(List<BaseInfoImg> baseInfoImgs);
    List<BaseInfoImg> findBaseInfoImgsByBaseInfoId(String baseInfoId);





    BaseInfoImg getByBaseInfoImgId(String id);
    List<BaseInfoImg> deleteByBaseInfo(BaseInfo baseInfo);
}
