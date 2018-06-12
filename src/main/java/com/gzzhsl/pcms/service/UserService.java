package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.shiro.bean.UserInfo;

import java.util.Set;

public interface UserService {
    UserInfo findByUserId(String uid);
    UserInfo getUserByUsername(String username);

}
