package com.gzzhsl.pcms.repository;

import com.gzzhsl.pcms.shiro.bean.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserInfo, String> {
    UserInfo findByUserId(String uid);
    UserInfo findByUsername(String username);
}
