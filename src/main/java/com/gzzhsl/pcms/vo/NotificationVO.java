package com.gzzhsl.pcms.vo;

import lombok.Data;

@Data
public class NotificationVO<T> {
    private String url;
    private String timeDiff;
    private Integer countUnread;
    private String type;
    private T object;
}
