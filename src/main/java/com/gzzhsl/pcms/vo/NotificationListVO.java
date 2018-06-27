package com.gzzhsl.pcms.vo;

import lombok.Data;

import java.util.List;

@Data
public class NotificationListVO {
    private Integer countAllUnread;
    private List<NotificationVO> notificationVOs;
}
