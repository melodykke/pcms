package com.gzzhsl.pcms.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReservoirCodeMapperTest {


    @Autowired
    private ReservoirCodeMapper reservoirCodeMapper;

    @Test
    public void findByReservoirName() throws Exception {
        System.out.println(reservoirCodeMapper.findByReservoirName("务川县冉渡滩水库"));
    }
    @Test
    public void findByBaseInfoId() throws Exception {
        System.out.println(reservoirCodeMapper.findByBaseInfoId("8a8082816458ab31016458ab4ef70143"));
    }
}