package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.entity.OperationLog;

import java.util.List;

public interface OperationLogService {
    OperationLog save(OperationLog operationLog);
    List<OperationLog> getOperationLogsByUserId(String userId);
}
