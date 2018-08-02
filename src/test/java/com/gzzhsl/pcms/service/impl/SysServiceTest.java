package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.service.SysService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysServiceTest {

    @Autowired
    private SysService sysService;

    @Test
    public void setAllUserRole() throws Exception {
        sysService.setAllUserRole();
    }

    @Test
    public void projectidToUserinfoFKprojectid() throws Exception {
        sysService.projectidToUserinfoFKprojectid();
    }


}