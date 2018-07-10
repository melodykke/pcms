package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.entity.*;
import com.gzzhsl.pcms.shiro.bean.UserInfo;

import java.util.List;

public interface PreProgressService {
    PreProgress save(List<PreProgressEntry> preProgressEntries, String rtFileTempPath);
    List<PreProgressDefault> getAllPreProgressDefault();
    PreProgress findByBaseInfo(BaseInfo baseInfo);
    PreProgress findByPreProgressId(String preProgressId);
    Feedback approvePreProgress(UserInfo thisUser, Boolean switchState, String checkinfo, String preProgressId);

}
