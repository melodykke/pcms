package com.gzzhsl.pcms.mapper;

import com.gzzhsl.pcms.model.BaseInfoImg;
import java.util.List;

public interface BaseInfoImgMapper {
    int deleteByPrimaryKey(String baseInfoImgId);

    int insert(BaseInfoImg record);

    BaseInfoImg selectByPrimaryKey(String baseInfoImgId);

    List<BaseInfoImg> selectAll();

    int updateByPrimaryKey(BaseInfoImg record);

    int batchDeleteByBaseInfoId(String baseInfoId);

    int batchInsertBaseInfoImgs(List<BaseInfoImg> baseInfoImgs);

    List<BaseInfoImg> findBaseInfoImgsByBaseInfoId(String baseInfoId);
}