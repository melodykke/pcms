package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.repository.UserRepository;
import com.gzzhsl.pcms.service.UserService;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserInfo findByUserId(String uid) {
        return userRepository.findByUserId(uid);
    }

    @Override
    @Transactional
    public UserInfo getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
