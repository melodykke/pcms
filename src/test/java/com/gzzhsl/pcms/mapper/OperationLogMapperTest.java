package com.gzzhsl.pcms.mapper;

import com.gzzhsl.pcms.model.OperationLog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OperationLogMapperTest {

    @Autowired
    private OperationLogMapper operationLogMapper;

    @Test
    public void findByUserIdAndSearchParam() throws Exception {
        List<OperationLog> operationLogList = operationLogMapper
                .findByUserIdAndSearchParam("a0a2A0aa-aaaa-aaaa-8D4A-9E3E0C9E3DCB", "合同");
        for (OperationLog operationLog : operationLogList) {
            System.out.println(operationLog.getMsg());
        }
    }

}