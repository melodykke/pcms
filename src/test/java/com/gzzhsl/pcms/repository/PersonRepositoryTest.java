package com.gzzhsl.pcms.repository;

import com.gzzhsl.pcms.entity.PersonInfo;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonRepositoryTest {


    @Autowired
    private PersonRepository personRepository;
    @Test
    public void deleteByPersonId() throws Exception {
        personRepository.deleteByPersonId("21312321");
    }

    @Test
    public void save() throws Exception {
        PersonInfo personInfo = new PersonInfo();
        personInfo.setName("李兢jingjing");

        UserInfo userInfo = new UserInfo();
        userInfo.setUserId("03688AAC-CDE4-4A31-94AF-4E8E98CEC012");

        personInfo.setUserInfo(userInfo);
        PersonInfo personInfo1 = personRepository.save(personInfo);
        System.out.println(personInfo1);
    }
}