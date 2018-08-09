package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.entity.BaseInfo;
import com.gzzhsl.pcms.entity.Feedback;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import com.gzzhsl.pcms.vo.BaseInfoVO;

import java.util.List;

public interface BaseInfoService {

    /*Boolean connectBaseInfoAndUserInfo();*/
    List<BaseInfo> getAllProject();
    BaseInfo save(BaseInfo baseInfo);
    BaseInfoVO getBaseInfoById(String baseInfoId);
    BaseInfo save(BaseInfoVO baseInfoVO);
    Feedback approveBaseInfo(UserInfo thisUser, Boolean switchState, String checkinfo, String baseInfoId);
}
