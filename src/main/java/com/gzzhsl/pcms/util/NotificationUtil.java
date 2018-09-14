package com.gzzhsl.pcms.util;

import com.gzzhsl.pcms.enums.NotificationTypeEnum;
import com.gzzhsl.pcms.model.Notification;
import com.gzzhsl.pcms.model.UserInfo;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NotificationUtil {
    public static Notification buildNotification(UserInfo thisUser, String type, String baseInfoId, String typeId, Date time, String url) {
        Notification notification = new Notification();
        notification.setCreateTime(new Date());
        notification.setSubmitter(thisUser.getUsername());
        notification.setType(type);
        notification.setBaseInfoId(baseInfoId);
        notification.setTypeId(typeId); // 这里是项目基础信息ID
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        notification.setYearmonth(formatter.format(time));
        notification.setChecked(false);
        notification.setUrl(url);
        return notification;
    }
}
