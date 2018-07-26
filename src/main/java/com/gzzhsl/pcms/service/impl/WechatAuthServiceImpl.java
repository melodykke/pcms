package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.entity.WechatAuth;
import com.gzzhsl.pcms.repository.WechatAuthRepository;
import com.gzzhsl.pcms.service.WechatAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class WechatAuthServiceImpl implements WechatAuthService {
    @Autowired
    private WechatAuthRepository wechatAuthRepository;

    @Override
    public WechatAuth getWechatAuthByOpenId(String openId) {
        return wechatAuthRepository.findByOpenId(openId);
    }

    @Override
    public WechatAuth register(WechatAuth wechatAuth) {
        return wechatAuthRepository.save(wechatAuth);
    }
}
