package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.dto.UserInfoDTO;
import com.gzzhsl.pcms.model.UserInfo;
import com.gzzhsl.pcms.vo.AccountPasswordVO;
import com.gzzhsl.pcms.vo.AccountVO;

public interface AccountService {
    Boolean modifyPassword(AccountPasswordVO accountPasswordVO);
    Boolean createSubAccount(AccountVO accountVO, UserInfo thisUser);
    UserInfo createUserAccount(UserInfoDTO userInfoDTO);
}
