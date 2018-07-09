package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.converter.PreProgressEntriesSetPreProgress;
import com.gzzhsl.pcms.entity.*;
import com.gzzhsl.pcms.repository.PreProgressDefaultRepository;
import com.gzzhsl.pcms.repository.PreProgressRepository;
import com.gzzhsl.pcms.service.PreProgressService;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import com.gzzhsl.pcms.util.PathUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
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
    public PreProgress save(List<PreProgressEntry> preProgressEntries, String rtFileTempPath) {
        UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        BaseInfo thisProject = thisUser.getBaseInfo();
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
        List<PreProgressEntry> preProgressEntriesWithPreProgress = preProgressEntries.stream().map(e -> PreProgressEntriesSetPreProgress.set(preProgressRt, e)).collect(Collectors.toList());
        preProgressRt.setPreProgressEntries(preProgressEntriesWithPreProgress);
        if (rtFileTempPath != null) {
            File sourceDestFolder = new File(PathUtil.getFileBasePath(true) + rtFileTempPath);
            String targetDest = PathUtil.getFileBasePath(false) + PathUtil.getPreProgressImagePath(thisProject.getPlantName());
            File[] sourceFiles = sourceDestFolder.listFiles();
            List<PreProgressImg> preProgressImgs = new ArrayList<>();
            if (sourceFiles.length > 0) {
                for (File sourceFile : sourceFiles) {
                    File targetFile = new File(targetDest + sourceFile.getName());
                    if (!targetFile.exists()) {
                        targetFile.getParentFile().mkdirs();
                    }
                    String imgRelativePath = PathUtil.getPreProgressImagePath(thisProject.getPlantName()) + sourceFile.getName();
                    sourceFile.renameTo(targetFile);
                    // 把存储到新地址的图片的相对路径拿出来构建月报图片对象。
                    PreProgressImg preProgressImg = new PreProgressImg();
                    preProgressImg.setCreateTime(new Date());
                    preProgressImg.setImgAddr(imgRelativePath);
                    preProgressImg.setPreProgress(preProgressRt);
                    preProgressImgs.add(preProgressImg);
                }
            }
            preProgressRt.setPreProgressImgs(preProgressImgs);
        }

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
