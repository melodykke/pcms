package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NotificationService {
    Notification save(Notification notification);
    List<Notification> getByTypeId(String typeId);
    List<Notification> getAll();
    List<Notification> getByBaseInfoId(String baseInfoId);
    Notification getById(String notificationId);
    List<Notification> getAllUnchecked(String baseInfoId);
    Page<Notification> findAllByType(Pageable pageable, String baseInfoId, String type);
}
