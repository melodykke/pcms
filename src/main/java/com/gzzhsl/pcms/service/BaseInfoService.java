package com.gzzhsl.pcms.service;


import com.gzzhsl.pcms.entity.Feedback;
import com.gzzhsl.pcms.model.BaseInfo;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import com.gzzhsl.pcms.vo.BaseInfoVO;

import java.util.List;

public interface BaseInfoService {
    List<BaseInfo> findAllProject();
    Integer save(BaseInfo baseInfo);
    Integer save(BaseInfoVO baseInfoVO);
    BaseInfo findBaseInfoById(String baseInfoId);
    BaseInfoVO findBaseInfoVOById(String baseInfoId);



    Feedback approveBaseInfo(UserInfo thisUser, Boolean switchState, String checkinfo, String baseInfoId);
    List<BaseInfoVO> findByRegionId(Integer regionId);
}
