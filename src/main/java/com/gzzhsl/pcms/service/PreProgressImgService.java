package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.entity.PreProgress;
import com.gzzhsl.pcms.entity.PreProgressImg;

import java.util.List;

public interface PreProgressImgService {
    PreProgressImg getByPreProgressImgId(String preProgressImgId);
    List<PreProgressImg> deleteByPreProgress(PreProgress preProgress);
}
