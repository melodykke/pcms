package com.gzzhsl.pcms.cors;

import com.gzzhsl.pcms.enums.LoginType;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

public class MyHashedCredentialsMatcher extends HashedCredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        MyUsernamePasswordToken mupt = (MyUsernamePasswordToken)token;
        if (mupt.getLoginType().equals(LoginType.NOPASSWD.getCode())) {
            return true;
        } if (mupt.getLoginType().equals(LoginType.PASSWORD.getCode())) {
            return super.doCredentialsMatch(token, info);
        } else {
            return false;
        }
    }
}
