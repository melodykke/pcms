package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.enums.SysEnum;
import com.gzzhsl.pcms.exception.SysException;
import com.gzzhsl.pcms.service.AccountService;
import com.gzzhsl.pcms.service.UserService;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import com.gzzhsl.pcms.util.UserUtil;
import com.gzzhsl.pcms.vo.AccountVO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public Boolean modifyPassword(AccountVO accountVO) {
        UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        // 1、判断原密码是否与本账号密码匹配
        String encriNewPassword = UserUtil.getEncriPwd(accountVO.getNewPassword());
        if (encriNewPassword.equals(thisUser.getPassword())) {
            log.error("【账户错误】参数验证错误， 新密码和原密码相同 accountVO = {}", accountVO);
            throw new SysException(SysEnum.ACCOUNT_PASSWORD_INCONSISTENCY);
        }
        // 2、修改密码
        thisUser.setPassword(encriNewPassword);
        userService.save(thisUser);
        return true;
    }
}
