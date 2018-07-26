package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.entity.PersonInfo;
import com.gzzhsl.pcms.repository.PersonRepository;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface PersonService {
    PersonInfo getById(String personId);
    PersonInfo save(PersonInfo personInfo);
    PersonInfo getByUserInfo(UserInfo userInfo);

}
