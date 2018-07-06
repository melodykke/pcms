package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.entity.OperationLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OperationLogService {
    OperationLog save(OperationLog operationLog);
    List<OperationLog> getOperationLogsByUserId(String userId);
    Page<OperationLog> listAll(Pageable pageable, String userId, String searchParam);
}
