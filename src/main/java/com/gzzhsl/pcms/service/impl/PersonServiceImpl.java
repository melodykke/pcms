package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.entity.PersonInfo;
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
    private PersonRepository personRepository;

    @Override
    public PersonInfo getById(String personId) {
        return personRepository.findByPersonId(personId);
    }

    @Override
    public PersonInfo save(PersonInfo personInfo) {
        return personRepository.save(personInfo);
    }

    @Override
    public PersonInfo getByUserInfo(UserInfo userInfo) {
        return personRepository.findByUserInfo(userInfo);
    }
}
