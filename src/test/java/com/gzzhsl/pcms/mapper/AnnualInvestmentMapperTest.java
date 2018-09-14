package com.gzzhsl.pcms.mapper;

import com.gzzhsl.pcms.model.AnnualInvestment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AnnualInvestmentMapperTest {
    @Autowired
    private AnnualInvestmentMapper annualInvestmentMapper;

    @Test
    public void findByBaseInfoIdAndYear() throws Exception {
        AnnualInvestment annualInvestment = annualInvestmentMapper.findByBaseInfoIdAndYear("4028e4ec64acd3340164acd6159b0007", "2018");
        System.out.println(annualInvestment);
    }

}