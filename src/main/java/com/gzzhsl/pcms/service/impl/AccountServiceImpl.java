package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.enums.SysEnum;
import com.gzzhsl.pcms.exception.SysException;
import com.gzzhsl.pcms.repository.RoleRepository;
import com.gzzhsl.pcms.service.AccountService;
import com.gzzhsl.pcms.service.UserService;
import com.gzzhsl.pcms.shiro.bean.SysRole;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import com.gzzhsl.pcms.util.AccountVO2UserInfo;
import com.gzzhsl.pcms.util.UserUtil;
import com.gzzhsl.pcms.vo.AccountPasswordVO;
import com.gzzhsl.pcms.vo.AccountVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
    @Autowired
    private UserService userService;
    @Autowired
    private SysServiceImpl sysService;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public Boolean modifyPassword(AccountPasswordVO accountPasswordVO) {
        UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        // 1、判断原密码是否与本账号密码匹配
        String encriNewPassword = UserUtil.getEncriPwd(accountPasswordVO.getNewPassword());
        if (encriNewPassword.equals(thisUser.getPassword())) {
            log.error("【账户错误】参数验证错误， 新密码和原密码相同 accountPasswordVO = {}", accountPasswordVO);
            throw new SysException(SysEnum.ACCOUNT_PASSWORD_INCONSISTENCY);
        }
        // 2、修改密码
        thisUser.setPassword(encriNewPassword);
        userService.save(thisUser);
        return true;
    }

    @Override
    public Boolean createSubAccount(AccountVO accountVO, UserInfo thisUser) {
        UserInfo otherUser = userService.getUserByUsername(accountVO.getUsername());
        // 判断子账号名是否重复
        if (otherUser != null) {
            log.error("【账户错误】账户名错误，已存在相同的账号 accountVO = {}, 相同账号为 otherUser: {}", accountVO, otherUser);
            throw new SysException(SysEnum.ACCOUNT_DUPLICATED);
        }
        UserInfo subUser = AccountVO2UserInfo.convert(accountVO, thisUser);
        subUser.setParent(thisUser);
        List<SysRole> sysRoles = new ArrayList<>();
        sysRoles.add(roleRepository.findByRole("reporter"));
        subUser.setSysRoleList(sysRoles);
        try {
            userService.save(subUser);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
