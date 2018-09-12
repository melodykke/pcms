package com.gzzhsl.pcms.mapper;

import com.gzzhsl.pcms.model.Announcement;
import java.util.List;

public interface AnnouncementMapper {
    int deleteByPrimaryKey(String announcementId);

    int insert(Announcement record);

    Announcement selectByPrimaryKey(String announcementId);

    List<Announcement> selectAll();

    int updateByPrimaryKey(Announcement record);

    List<Announcement> findHotLatests();
}