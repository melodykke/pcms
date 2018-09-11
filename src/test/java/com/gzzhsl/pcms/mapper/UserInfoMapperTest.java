package com.gzzhsl.pcms.mapper;

import com.gzzhsl.pcms.model.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInfoMapperTest {


    @Autowired
    private UserInfoMapper userInfoMapper;

    @Test
    public void findByUsername() throws Exception {
        UserInfo userInfo = userInfoMapper.findByUsername("gaoxin");
        System.out.println(userInfo);
    }
    @Test
    public void findOneWithRolesByUsernameOrId() throws Exception {
        UserInfo userInfo = userInfoMapper.findOneWithRolesByUsernameOrId(null, "AAAAAAAA-AAAA-AAAA-AAAA-AAAAAAAAAAAA");
        System.out.println(userInfo);
    }
    @Test
    public void findOneWithRolesAndPrivilegesByUsernameOrId() throws Exception {
        UserInfo userInfo = userInfoMapper.findOneWithRolesAndPrivilegesByUsernameOrId("gcglb", null);
        System.out.println(userInfo);
    }
}