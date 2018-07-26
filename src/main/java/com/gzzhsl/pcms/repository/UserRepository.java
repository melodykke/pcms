package com.gzzhsl.pcms.repository;

import com.gzzhsl.pcms.entity.BaseInfo;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<UserInfo, String> {
    UserInfo findByUserId(String uid);
    UserInfo findByUsername(String username);
    UserInfo findParentByUsername(String username);
    @Transactional
    @Modifying
    @Query("update UserInfo userInfo set userInfo.baseInfo = :baseInfo where userInfo.userId = :userId")
    Integer updateUserBaseInfo(@Param(value = "baseInfo") BaseInfo baseInfo, @Param(value = "userId") String userId);
    @Transactional
    @Modifying
    @Query("update UserInfo userInfo set userInfo.openId = :openId where userInfo.userId = :userId")
    Integer updateUserOpenId(@Param(value = "openId") String openId, @Param(value = "userId") String userId);
    UserInfo findByOpenId(String openId);
}
