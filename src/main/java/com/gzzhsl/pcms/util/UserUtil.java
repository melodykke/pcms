package com.gzzhsl.pcms.util;


import com.gzzhsl.pcms.model.SysRole;
import com.gzzhsl.pcms.model.UserInfo;
import org.apache.shiro.crypto.hash.Md5Hash;


import java.util.List;


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

    // 判断用户角色是否是checker
    public static boolean isChecker(UserInfo thisUser) {
        List<SysRole> roles = thisUser.getRoles();
        for (SysRole sysRole : roles) {
            if ("checker".equals(sysRole.getRole())) {
                return true;
            }
        }
        return false;
    }
    // 判断用户角色是否是reporter
    public static boolean isReporter(UserInfo thisUser) {
        List<SysRole> roles = thisUser.getRoles();
        for (SysRole sysRole : roles) {
            if ("reporter".equals(sysRole.getRole())) {
                return true;
            }
        }
        return false;
    }
    public static void main(String[] args) {
        System.out.println(getEncriPwd("888888"));
    }
}
