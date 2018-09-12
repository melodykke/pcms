package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.converter.UserInfo2VO;
import com.gzzhsl.pcms.entity.BaseInfo;
import com.gzzhsl.pcms.enums.SysEnum;
import com.gzzhsl.pcms.exception.SysException;
import com.gzzhsl.pcms.mapper.PersonInfoMapper;
import com.gzzhsl.pcms.mapper.UserInfoMapper;
import com.gzzhsl.pcms.model.PersonInfo;
import com.gzzhsl.pcms.model.UserInfo;
import com.gzzhsl.pcms.repository.UserRepository;
import com.gzzhsl.pcms.service.UserService;
import com.gzzhsl.pcms.vo.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private PersonInfoMapper personInfoMapper;


    @Override
    public int deleteByPrimaryKey(String userId) {
        return userInfoMapper.deleteByPrimaryKey(userId);
    }

    @Override
    public int insert(UserInfo userInfo) {
        return userInfoMapper.insert(userInfo);
    }

    @Override
    public UserInfo selectByPrimaryKey(String userId) {
        return userInfoMapper.selectByPrimaryKey(userId);
    }

    @Override
    public List<UserInfo> selectAll() {
        return userInfoMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(UserInfo userInfo) {
        return userInfoMapper.updateByPrimaryKey(userInfo);
    }

    @Override
    public UserInfo findByUsername(String username) {
        return userInfoMapper.findByUsername(username);
    }

    @Override
    public UserInfo findOneWithRolesByUsernameOrId(String username, String userId) {
        return userInfoMapper.findOneWithRolesByUsernameOrId(username, userId);
    }

    @Override
    public UserInfo findOneWithRolesAndPrivilegesByUsernameOrId(String username, String userId) {
        return userInfoMapper.findOneWithRolesAndPrivilegesByUsernameOrId(username, userId);
    }

    @Override
    public PersonInfo findPersonInfoByUserId(String userId) {
        PersonInfo personInfo = personInfoMapper.selectByUserId(userId);
        return personInfo;
    }





    @Override
    @Transactional
    public UserInfo findByUserId(String uid) {
        return userInfoMapper.selectByPrimaryKey(uid);
    }

    @Override
    @Transactional
    public UserInfo getUserByUsername(String username) {
        return userInfoMapper.findByUsername(username);
    }

    @Override
    public List<UserInfo> getAllUser() {
        return userInfoMapper.selectAll();
    }

    @Override
    public UserInfo save(UserInfo userInfo) {
        UserInfo duplicateUser = userInfoMapper.findByUsername(userInfo.getUsername());
        if (duplicateUser != null) {
            log.error("【用户错误】 新增用户错误， 重复的用户名：{}", userInfo.getUsername());
            throw new SysException(SysEnum.USER_INFO_DUPLICATED);
        }
        //return userRepository.save(userInfo);
        return null;
    }

    @Override
    public UserInfo findParentByUsername(String username) {
        //return userRepository.findParentByUsername(username);
        return null;
    }

    @Override
    public Integer updateUserBaseInfo(BaseInfo baseInfo, String userId) {
        // return userInfoMapper.updateUserBaseInfo(baseInfo, userId);
        return null;
    }

    @Override
    public Integer updateUserOpenId(String openId, String userId) {
        //  return userRepository.updateUserOpenId(openId, userId);
        return null;
    }

    @Override
    public UserInfo findByOpenId(String openId) {
        // return userRepository.findByOpenId(openId);
        return null;
    }

    @Override
    public Page<UserInfoVO> findAll(Pageable pageable) {
      /*  Page<UserInfo> pageUserInfos = userInfoMapper.findAll(pageable);
        List<UserInfo> userInfos = pageUserInfos.getContent();
        List<UserInfoVO> userInfoVOs = userInfos.stream().map(e -> UserInfo2VO.convert(e)).collect(Collectors.toList());
        Page<UserInfoVO> userInfoVOPage = new PageImpl<UserInfoVO>(userInfoVOs, pageable, pageUserInfos.getTotalElements());
        return userInfoVOPage;*/
        return null;
    }

    @Override
    public Integer toggleActivate(UserInfo userInfo) {
        // return userRepository.toggleActivate(userInfo.getActive(), userInfo.getUserId());
        return null;
    }

    @Override
    public Integer modifyPassword(String password, UserInfo userInfo) {
        //  return userRepository.modifyPassword(password, userInfo.getUserId());
        return null;
    }


}
