package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.entity.WechatAuth;
import com.gzzhsl.pcms.mapper.PersonInfoMapper;
import com.gzzhsl.pcms.model.PersonInfo;
import com.gzzhsl.pcms.repository.PersonRepository;
import com.gzzhsl.pcms.service.PersonService;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonInfoMapper personInfoMapper;

    @Override
    public Integer save(PersonInfo personInfo) {
        return personInfoMapper.insert(personInfo);
    }





    @Override
    public PersonInfo getById(String personId) {
        return personInfoMapper.selectByPrimaryKey(personId);
    }



    @Override
    public PersonInfo getByUserInfo(UserInfo userInfo) {
       // return personRepository.findByUserInfo(userInfo);
        return null;
    }

    @Override
    public PersonInfo getByWeChatAuth(WechatAuth wechatAuth) {
       // return personRepository.findByWechatAuth(wechatAuth);
        return null;
    }

    @Override
    public void deleteByPersonId(String personId) {
        //personRepository.deleteByPersonId(personId);
    }
}
