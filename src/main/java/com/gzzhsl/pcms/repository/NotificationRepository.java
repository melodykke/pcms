package com.gzzhsl.pcms.repository;

import com.gzzhsl.pcms.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, String>, JpaSpecificationExecutor<Notification> {
    List<Notification> findByTypeId(String typeId);
    List<Notification> findByProjectId(String projectId);
}
