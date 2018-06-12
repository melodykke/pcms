package com.gzzhsl.pcms.util;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.BeanUtils;


public class UserUtil {
   /* public static UserInfo createDefaultUser(UserRegistryForm userRegistryForm, UserInfo parentUserInfo){
        UserInfo userInfo = new UserInfo();

        BeanUtils.copyProperties(userRegistryForm, userInfo);
        userInfo.setParent(parentUserInfo);
        userInfo.setActive(UserAccountStatusEnum.ACCOUNT_INACTIVATED.getCode());
        String randomSalt = SaltUtil.getRandomSalt();
        String salt = randomSalt.concat(userRegistryForm.getUsername());
        userInfo.setSalt(salt);
        //MD5加盐两次散列 new Md5Hash(origin password, salt, times)
        userInfo.setPassword(new Md5Hash(userRegistryForm.getPassword(), salt, 2).toString());
        return userInfo;
    }*/

    public static String getEncriPwd(String password){

        String salt = "salt";

        //MD5加盐两次散列 new Md5Hash(origin password, salt, times)
        return new Md5Hash(password, salt, 2).toString();
    }

    public static void main(String[] args) {
        System.out.println(getEncriPwd("888888"));
    }
}
