package com.gzzhsl.pcms.mapper;

import com.gzzhsl.pcms.model.PersonInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonInfoMapperTest {
    @Autowired
    private PersonInfoMapper personInfoMapper;

    @Test
    public void selectByUserId() throws Exception {
        PersonInfo personInfo = personInfoMapper.selectByUserId("a0a2A0aa-aaaa-aaaa-8D4A-9E3E0C9E3DCB");
        System.out.println(personInfo);
    }

}