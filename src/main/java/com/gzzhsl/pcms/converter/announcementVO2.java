package com.gzzhsl.pcms.converter;

import com.gzzhsl.pcms.entity.Announcement;
import com.gzzhsl.pcms.vo.AnnouncementVO;
import org.springframework.beans.BeanUtils;

public class announcementVO2 {

    public static Announcement convert(AnnouncementVO announcementVO) {
        Announcement announcement = new Announcement();
        BeanUtils.copyProperties(announcementVO, announcement);
        return announcement;
    }

}
