package com.gzzhsl.pcms.converter;

import com.gzzhsl.pcms.dto.UserInfoDTO;
import com.gzzhsl.pcms.shiro.bean.UserInfo;

public class UserInfoDTO2 {
    public static UserInfo convert(UserInfoDTO userInfoDTO) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(userInfoDTO.getUsername());
        userInfo.setName(userInfoDTO.getName());
        userInfo.setPassword("3c862be459716ebaa6fa55ce75284d49");
        userInfo.setSalt("salt");
        userInfo.setActive((byte) 1);
        return userInfo;
    }
}
