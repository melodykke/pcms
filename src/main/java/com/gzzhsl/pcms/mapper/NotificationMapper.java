package com.gzzhsl.pcms.mapper;

import com.gzzhsl.pcms.model.Notification;
import java.util.List;

public interface NotificationMapper {
    int deleteByPrimaryKey(String notificationId);

    int insert(Notification record);

    Notification selectByPrimaryKey(String notificationId);

    List<Notification> selectAll();

    int updateByPrimaryKey(Notification record);

    List<Notification> findByTypeId(String typeId);

    List<Notification> findByBaseInfoId(String baseInfoId);

    List<Notification> findAllUncheckedByBaseInfoId(String baseInfoId);

    List<Notification> findAllByTypeAndBaseInfoId(String type, String baseInfoId);
}