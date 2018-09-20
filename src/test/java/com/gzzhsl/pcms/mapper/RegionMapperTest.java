package com.gzzhsl.pcms.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RegionMapperTest {

    @Autowired
    private RegionMapper regionMapper;

    @Test
    public void findByParentIdNotIn() throws Exception {
        List<Integer> testList = Arrays.asList(1);
        System.out.println(regionMapper.findByParentIdNotIn(testList));
    }

    @Test
    public void findByParentId() throws Exception {
        System.out.println(regionMapper.findByParentId(2));
    }

}