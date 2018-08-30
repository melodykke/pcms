package com.gzzhsl.pcms.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gzzhsl.pcms.converter.UserInfo2VO;
import com.gzzhsl.pcms.entity.BaseInfo;
import com.gzzhsl.pcms.enums.SysEnum;
import com.gzzhsl.pcms.exception.SysException;
import com.gzzhsl.pcms.repository.UserRepository;
import com.gzzhsl.pcms.service.UserService;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import com.gzzhsl.pcms.vo.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
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

    @Override
    public List<UserInfo> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public UserInfo save(UserInfo userInfo) {
        UserInfo duplicateUser = userRepository.findByUsername(userInfo.getUsername());
        if (duplicateUser != null) {
            log.error("【用户错误】 新增用户错误， 重复的用户名：{}", userInfo.getUsername());
            throw new SysException(SysEnum.USER_INFO_DUPLICATED);
        }
        return userRepository.save(userInfo);
    }

    @Override
    public UserInfo findParentByUsername(String username) {
        return userRepository.findParentByUsername(username);
    }

    @Override
    public Integer updateUserBaseInfo(BaseInfo baseInfo, String userId) {
        return userRepository.updateUserBaseInfo(baseInfo, userId);
    }

    @Override
    public Integer updateUserOpenId(String openId, String userId) {
        return userRepository.updateUserOpenId(openId, userId);
    }

    @Override
    public UserInfo findByOpenId(String openId) {
        return userRepository.findByOpenId(openId);
    }

    @Override
    public Page<UserInfoVO> findAll(Pageable pageable) {
        Page<UserInfo> pageUserInfos = userRepository.findAll(pageable);
        List<UserInfo> userInfos = pageUserInfos.getContent();
        List<UserInfoVO> userInfoVOs = userInfos.stream().map(e -> UserInfo2VO.convert(e)).collect(Collectors.toList());
        Page<UserInfoVO> userInfoVOPage = new PageImpl<UserInfoVO>(userInfoVOs, pageable, pageUserInfos.getTotalElements());
        return userInfoVOPage;
    }

    @Override
    public Integer toggleActivate(UserInfo userInfo) {
        return userRepository.toggleActivate(userInfo.getActive(), userInfo.getUserId());
    }

    @Override
    public Integer modifyPassword(String password, UserInfo userInfo) {
        return userRepository.modifyPassword(password, userInfo.getUserId());
    }

}
