package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.entity.BaseInfo;
import com.gzzhsl.pcms.entity.PreProgress;
import com.gzzhsl.pcms.entity.PreProgressDefault;
import com.gzzhsl.pcms.entity.PreProgressEntry;

import java.util.List;

public interface PreProgressService {
    PreProgress save(List<PreProgressEntry> preProgressEntries);
    List<PreProgressDefault> getAllPreProgressDefault();
    PreProgress findByBaseInfo(BaseInfo baseInfo);
}
