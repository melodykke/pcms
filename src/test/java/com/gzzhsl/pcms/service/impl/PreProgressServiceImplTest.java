package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.service.PreProgressService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PreProgressServiceImplTest {
    @Autowired
    private PreProgressService preProgressService;

    @Test
    public void getAllPreProgressDefault() throws Exception {
        System.out.println(preProgressService.getAllPreProgressDefault());
    }

}