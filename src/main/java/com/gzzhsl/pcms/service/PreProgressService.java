package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.model.*;
import java.util.List;

public interface PreProgressService {
    PreProgress findByBaseInfoId(String baseInfoId);
    PreProgress findWithImgByBaseInfoId(String baseInfoId);


    PreProgress save(List<PreProgressEntry> preProgressEntries, String rtFileTempPath);
    List<PreProgressDefault> getAllPreProgressDefault();
    PreProgress findByPreProgressId(String preProgressId);
    Feedback approvePreProgress(UserInfo thisUser, Boolean switchState, String checkinfo, String preProgressId);

}
