package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.shiro.bean.UserInfo;
import com.gzzhsl.pcms.vo.AccountPasswordVO;
import com.gzzhsl.pcms.vo.AccountVO;

public interface AccountService {
    Boolean modifyPassword(AccountPasswordVO accountPasswordVO);
    Boolean createSubAccount(AccountVO accountVO, UserInfo thisUser);
}
