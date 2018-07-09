package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.converter.PreProgressEntriesSetPreProgress;
import com.gzzhsl.pcms.entity.BaseInfo;
import com.gzzhsl.pcms.entity.PreProgress;
import com.gzzhsl.pcms.entity.PreProgressDefault;
import com.gzzhsl.pcms.entity.PreProgressEntry;
import com.gzzhsl.pcms.repository.PreProgressDefaultRepository;
import com.gzzhsl.pcms.repository.PreProgressRepository;
import com.gzzhsl.pcms.service.PreProgressService;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class PreProgressServiceImpl implements PreProgressService {
    @Autowired
    private PreProgressDefaultRepository preProgressDefaultRepository;
    @Autowired
    private PreProgressRepository preProgressRepository;
    @Override
    public PreProgress save(List<PreProgressEntry> preProgressEntries) {
        UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        PreProgress preProgress =  thisUser.getBaseInfo().getPreProgress();
        if (preProgress != null && preProgress.getPreProgressId() != null && !preProgress.getState().equals((byte) 1)) {
            preProgress.setUpdateTime(new Date());
            preProgress.setRepeatTimes(preProgress.getRepeatTimes()+1);
        } else {
            preProgress = new PreProgress();
            preProgress.setBaseInfo(thisUser.getBaseInfo());
            preProgress.setOwner(thisUser.getUsername());
            preProgress.setState((byte) 0);
            preProgress.setRepeatTimes(0);
            preProgress.setCreateTime(new Date());
            preProgress.setUpdateTime(new Date());
        }

        PreProgress preProgressRt = preProgressRepository.save(preProgress);
        preProgressEntries = preProgressEntries.stream().map(e -> PreProgressEntriesSetPreProgress.set(preProgressRt, e)).collect(Collectors.toList());
      /*  preProgressRt.setPreProgressImgs();*/ // TODO
        preProgressRt.setPreProgressEntries(preProgressEntries);
        // TODO log notification
        PreProgress preProgressRtRt = preProgressRepository.save(preProgressRt);
        return preProgressRtRt;
    }

    @Override
    public List<PreProgressDefault> getAllPreProgressDefault() {
        return preProgressDefaultRepository.findAll();
    }

    @Override
    public PreProgress findByBaseInfo(BaseInfo baseInfo) {
        return preProgressRepository.findByBaseInfo(baseInfo);
    }
}
