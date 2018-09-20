package com.gzzhsl.pcms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gzzhsl.pcms.mapper.OperationLogMapper;
import com.gzzhsl.pcms.model.OperationLog;
import com.gzzhsl.pcms.repository.OperationLogRepositoty;
import com.gzzhsl.pcms.service.OperationLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
public class OperationLogServiceImpl implements OperationLogService {

    @Autowired
    private OperationLogMapper operationLogMapper;




    @Autowired
    private OperationLogRepositoty operationLogRepositoty;

    @Override
    public int save(OperationLog operationLog) {
        return operationLogMapper.insert(operationLog);
    }

    @Override
    public List<OperationLog> findOperationLogsByUserId(String userId) {
        if (userId == null) {
            return new ArrayList<>();
        }
        List<OperationLog> operationLogs = operationLogMapper.findOperationLogsByUserId(userId);
        return operationLogs;
    }

    @Override
    public PageInfo<OperationLog> findByConditions(int pageNum, int pageSize, String userId, String searchParam) {
        PageHelper.startPage(pageNum, pageSize);
        List<OperationLog> operationLogs = operationLogMapper.findByUserIdAndSearchParam(userId, searchParam);
        PageInfo pageInfo = new PageInfo(operationLogs);
        return pageInfo;
    }
}
