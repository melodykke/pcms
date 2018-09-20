package com.gzzhsl.pcms.mapper;

import com.gzzhsl.pcms.model.PreProgress;
import com.gzzhsl.pcms.model.PreProgressEntry;

import java.util.List;

public interface PreProgressMapper {
    int deleteByPrimaryKey(String preProgressId);

    int insert(PreProgress record);

    PreProgress selectByPrimaryKey(String preProgressId);

    List<PreProgress> selectAll();

    int updateByPrimaryKey(PreProgress record);

    PreProgress findByBaseInfoId(String baseInfoId);

    PreProgress findWithImgByBaseInfoId(String baseInfoId);
}