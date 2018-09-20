package com.gzzhsl.pcms.mapper;

import com.gzzhsl.pcms.model.PreProgressEntry;
import java.util.List;

public interface PreProgressEntryMapper {
    int deleteByPrimaryKey(String preProgressEntryId);

    int insert(PreProgressEntry record);

    PreProgressEntry selectByPrimaryKey(String preProgressEntryId);

    List<PreProgressEntry> selectAll();

    int updateByPrimaryKey(PreProgressEntry record);
}