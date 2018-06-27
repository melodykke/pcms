package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.entity.Notification;

import java.util.List;

public interface NotificationService {
    Notification save(Notification notification);
    List<Notification> getByTypeId(String typeId);
    List<Notification> getAll();
    List<Notification> getByProjectId(String projectId);
    Notification getById(String notificationId);
}
