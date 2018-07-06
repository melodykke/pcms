package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.entity.OperationLog;
import com.gzzhsl.pcms.repository.OperationLogRepositoty;
import com.gzzhsl.pcms.service.OperationLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
public class OperationLogServiceImpl implements OperationLogService {

    @Autowired
    private OperationLogRepositoty operationLogRepositoty;

    @Override
    public OperationLog save(OperationLog operationLog) {
        return operationLogRepositoty.save(operationLog);
    }

    @Override
    public List<OperationLog> getOperationLogsByUserId(String userId) {
        List<OperationLog> operationLogs = null;
        Specification querySpecification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                return cb.equal(root.get("userId"), userId);
            }
        };
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        return operationLogRepositoty.findAll(querySpecification, sort);
    }

    @Override
    public Page<OperationLog> listAll(Pageable pageable, String userId) {
        Specification querySpecification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                return cb.equal(root.get("userId"), userId);
            }
        };
        return operationLogRepositoty.findAll(querySpecification, pageable);
    }
}
