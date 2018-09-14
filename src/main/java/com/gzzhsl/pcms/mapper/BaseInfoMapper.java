package com.gzzhsl.pcms.mapper;

import com.gzzhsl.pcms.model.BaseInfo;
import java.util.List;

public interface BaseInfoMapper {
    int deleteByPrimaryKey(String baseInfoId);

    int insert(BaseInfo record);

    BaseInfo selectByPrimaryKey(String baseInfoId);

    List<BaseInfo> selectAll();

    int updateByPrimaryKey(BaseInfo record);
}