package com.gzzhsl.pcms.repository;

import com.gzzhsl.pcms.entity.OperationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface OperationLogRepositoty extends JpaRepository<OperationLog, String>, JpaSpecificationExecutor<OperationLog> {
}
