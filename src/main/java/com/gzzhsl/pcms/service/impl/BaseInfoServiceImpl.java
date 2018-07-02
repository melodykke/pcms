package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.entity.BaseInfo;
import com.gzzhsl.pcms.repository.BaseInfoRepository;
import com.gzzhsl.pcms.repository.UserRepository;
import com.gzzhsl.pcms.service.BaseInfoService;
import com.gzzhsl.pcms.service.UserService;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
public class BaseInfoServiceImpl implements BaseInfoService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BaseInfoRepository baseInfoRepository;

    @Override
    public List<BaseInfo> getAllProject() {
        return baseInfoRepository.findAll();
    }

    @Override
    public BaseInfo save(BaseInfo baseInfo) {
        return baseInfoRepository.save(baseInfo);
    }

    @Override
    public Boolean connectBaseInfoAndUserInfo() {
        List<UserInfo> userInfos = userRepository.findAll();
        List<BaseInfo> baseInfos = baseInfoRepository.findAll();
        for (UserInfo userInfo : userInfos) {
            for (BaseInfo baseInfo : baseInfos) {
                if (userInfo.getName().equals(baseInfo.getPlantName())) {
                    userInfo.setBaseInfo(baseInfo);
                }
            }
        }
        return true;
    }

}
