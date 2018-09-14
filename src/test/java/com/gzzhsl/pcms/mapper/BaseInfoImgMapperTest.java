package com.gzzhsl.pcms.mapper;

import com.gzzhsl.pcms.model.BaseInfoImg;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseInfoImgMapperTest {
    @Autowired
    private BaseInfoImgMapper baseInfoImgMapper;
    @Test
    public void batchSaveBaseInfoImgs() throws Exception {
        List<BaseInfoImg> baseInfoImgs = new ArrayList<>();
        baseInfoImgMapper.batchInsertBaseInfoImgs(baseInfoImgs);
    }

}