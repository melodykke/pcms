package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.entity.PersonInfo;
import com.gzzhsl.pcms.service.PersonService;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonServiceImplTest {
    @Autowired
    private PersonService personService;
    @Test
    public void save() throws Exception {
    }

    @Test
    public void getByUserInfo() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId("01688AAC-CDE4-4A31-94AF-4E8E98CEC012");
        System.out.println(personService.getByUserInfo(userInfo));
    }

}