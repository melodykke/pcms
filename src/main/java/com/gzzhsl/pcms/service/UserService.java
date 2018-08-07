package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.entity.BaseInfo;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import com.gzzhsl.pcms.vo.UserInfoVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    UserInfo findByUserId(String uid);
    UserInfo getUserByUsername(String username);
    List<UserInfo> getAllUser();
    UserInfo save(UserInfo userInfo);
    UserInfo findParentByUsername(String username);
    Integer updateUserBaseInfo(BaseInfo baseInfo, String userId);
    Integer updateUserOpenId(String openId, String userId);
    UserInfo findByOpenId(String openId);
    Page<UserInfoVO> findAll(Pageable pageable);
    Integer toggleActivate(UserInfo userInfo);
    Integer modifyPassword(String password, UserInfo userInfo);
}
