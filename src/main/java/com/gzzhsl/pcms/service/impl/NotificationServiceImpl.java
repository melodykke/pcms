package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.entity.Notification;
import com.gzzhsl.pcms.repository.NotificationRepository;
import com.gzzhsl.pcms.service.NotificationService;
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
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getByTypeId(String typeId) {
        return notificationRepository.findByTypeId(typeId);
    }

    @Override
    public List<Notification> getAll() {
        return notificationRepository.findAll();
    }

    @Override
    public List<Notification> getByBaseInfoId(String baseInfoId) {
        List<Notification> notifications = null;
        Specification querySpecification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                Predicate predicate = cb.equal(root.get("baseInfoId"), baseInfoId);
                return predicate;
            }
        };
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        return notificationRepository.findAll(querySpecification, sort);
    }

    @Override
    public Notification getById(String notificationId) {
       // return notificationRepository.findOne(notificationId);
       return null;
    }

    @Override
    public List<Notification> getAllUnchecked(String baseInfoId) {
        Boolean checked = false;
        Specification querySpecification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (StringUtils.isNotBlank(baseInfoId)) {
                    predicates.add(cb.equal(root.get("baseInfoId"), baseInfoId));
                }
                predicates.add(cb.equal(root.get("checked"), checked));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        return notificationRepository.findAll(querySpecification, sort);
    }

    @Override
    public Page<Notification> findAllByType(Pageable pageable, String baseInfoId, String type) {
        Specification querySpecification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (StringUtils.isNotBlank(baseInfoId)) {
                    predicates.add(cb.equal(root.get("baseInfoId"), baseInfoId));
                }
                if (StringUtils.isNotBlank(type)) {
                    predicates.add(cb.equal(root.get("type"), type));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return notificationRepository.findAll(querySpecification, pageable);
    }
}
