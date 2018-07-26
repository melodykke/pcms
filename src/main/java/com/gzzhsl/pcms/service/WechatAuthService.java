package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.entity.WechatAuth;

public interface WechatAuthService {
    WechatAuth getWechatAuthByOpenId(String openId);
    WechatAuth register(WechatAuth wechatAuth);
}
