package com.gzzhsl.pcms.converter;

import com.gzzhsl.pcms.entity.BaseInfo;
import com.gzzhsl.pcms.entity.PreProgress;
import com.gzzhsl.pcms.entity.PreProgressEntry;
import com.gzzhsl.pcms.shiro.bean.UserInfo;

import java.util.Date;
import java.util.List;

public class PreProgressEntriesSetPreProgress {
    public static PreProgressEntry set(PreProgress preProgress, PreProgressEntry preProgressEntry) {
        preProgressEntry.setPreProgress(preProgress);
        return preProgressEntry;
    }
}
