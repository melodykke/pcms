package com.gzzhsl.pcms.repository;

import com.gzzhsl.pcms.entity.WechatAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WechatAuthRepository extends JpaRepository<WechatAuth, String> {
    WechatAuth findByOpenId(String openId);
}
