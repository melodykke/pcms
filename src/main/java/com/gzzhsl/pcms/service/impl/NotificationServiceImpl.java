package com.gzzhsl.pcms.service.impl;



import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gzzhsl.pcms.mapper.NotificationMapper;
import com.gzzhsl.pcms.model.Notification;
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
    private NotificationMapper notificationMapper;

    @Override
    public Notification findById(String notificationId) {
        return notificationMapper.selectByPrimaryKey(notificationId);
    }

    @Override
    public PageInfo<Notification> findPageByType(String type, String baseInfoId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Notification> notifications = notificationMapper.findAllByTypeAndBaseInfoId(type, baseInfoId);
        PageInfo<Notification> pageInfo = new PageInfo<>(notifications);
        return pageInfo;
    }

    @Override
    public Integer save(Notification notification) {
        if (notification == null) {
            return 0;
        }
        return notificationMapper.insert(notification);
    }

    @Override
    public List<Notification> getByTypeId(String typeId) {
        return notificationMapper.findByTypeId(typeId);
    }

    @Override
    public List<Notification> getAll() {
        return notificationMapper.selectAll();
    }

    @Override
    public List<Notification> getByBaseInfoId(String baseInfoId) {
        List<Notification> notifications = notificationMapper.findByBaseInfoId(baseInfoId);
        return notifications;
    }

    @Override
    public List<Notification> getAllUncheckedByBaseInfoId(String baseInfoId) {
        if (baseInfoId == null || "".equals(baseInfoId)) {
            return null;
        }
        List<Notification> notifications = notificationMapper.findAllUncheckedByBaseInfoId(baseInfoId);
        return notifications;
    }

    @Override
    public List<Notification> findAllByType(String baseInfoId, String type) {
        if (baseInfoId == null || "".equals(baseInfoId) || type == null || "".equals(type)) {
            return null;
        }
        List<Notification> notifications = notificationMapper.findAllByTypeAndBaseInfoId(type, baseInfoId);
        return notifications;
    }


}
