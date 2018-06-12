package com.gzzhsl.pcms.repository;

import com.gzzhsl.pcms.service.UserService;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    public void findByUserId() throws Exception {
        UserInfo userInfo = userRepository.findByUserId("03779E28-8687-4EF8-8594-B62020151791");
        System.out.println(userInfo);
    }

    @Test
    public void findByUsername() throws Exception {
        UserInfo userInfo = userRepository.findByUsername("ptwlbsk");
        System.out.println(userInfo);
    }


}