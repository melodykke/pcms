package com.gzzhsl.pcms.converter;

import com.gzzhsl.pcms.model.UserInfo;
import com.gzzhsl.pcms.vo.UserInfoVO;
import org.springframework.beans.BeanUtils;

public class UserInfo2VO {
    public static UserInfoVO convert(UserInfo userInfo) {
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(userInfo, userInfoVO);
        return userInfoVO;
    }
}
