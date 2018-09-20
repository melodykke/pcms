package com.gzzhsl.pcms.util;


import com.gzzhsl.pcms.enums.SysEnum;
import com.gzzhsl.pcms.exception.SysException;
import com.gzzhsl.pcms.model.BaseInfo;
import com.gzzhsl.pcms.model.UserInfo;
import com.gzzhsl.pcms.vo.AccountVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Slf4j
public class AccountVO2UserInfo {
    public static UserInfo convert(AccountVO accountVO, UserInfo thisUser) {
        UserInfo subUserInfo = new UserInfo();
        BeanUtils.copyProperties(accountVO, subUserInfo);
        subUserInfo.setName(thisUser.getName());
        subUserInfo.setSalt("salt");
        subUserInfo.setActive((byte) 0);
        String baseInfoId = thisUser.getBaseInfoId();
/*        if (baseInfoId == null || "".equals(baseInfoId) {
            log.error("【账户错误】配置子账户错误，该账号没有优先配置水库工程");
            throw new SysException(SysEnum.ACCOUNT_NO_PROJECT);
        }*/
        subUserInfo.setBaseInfoId(baseInfoId);
        subUserInfo.setCreateTime(new Date());
        subUserInfo.setUpdateTime(new Date());
        subUserInfo.setPassword(UserUtil.getEncriPwd(accountVO.getPassword()));
        return subUserInfo;
    }
}
