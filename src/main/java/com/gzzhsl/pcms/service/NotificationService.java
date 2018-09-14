package com.gzzhsl.pcms.service;


import com.github.pagehelper.PageInfo;
import com.gzzhsl.pcms.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NotificationService {

    Integer save(Notification notification);
    List<Notification> getAllUncheckedByBaseInfoId(String baseInfoId);
    List<Notification> findAllByType(String baseInfoId, String type);
    Notification findById(String notificationId);
    PageInfo<Notification> findPageByType(String type, String baseInfoId, int pageNum, int pageSize);





    List<Notification> getByTypeId(String typeId);
    List<Notification> getAll();
    List<Notification> getByBaseInfoId(String baseInfoId);


}
