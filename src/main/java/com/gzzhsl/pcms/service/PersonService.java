package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.entity.WechatAuth;
import com.gzzhsl.pcms.model.PersonInfo;
import com.gzzhsl.pcms.repository.PersonRepository;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface PersonService {

    Integer save(PersonInfo personInfo);


    PersonInfo getById(String personId);
    PersonInfo getByUserInfo(UserInfo userInfo);
    PersonInfo getByWeChatAuth(WechatAuth wechatAuth);
    void deleteByPersonId(String personId);
}
