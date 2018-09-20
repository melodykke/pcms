package com.gzzhsl.pcms.service;

import com.github.pagehelper.PageInfo;
import com.gzzhsl.pcms.model.OperationLog;;

import java.util.List;

public interface OperationLogService {
    int save(OperationLog operationLog);
    List<OperationLog> findOperationLogsByUserId(String userId);
    PageInfo<OperationLog> findByConditions(int pageNum, int pageSize, String userId, String searchParam);
}
