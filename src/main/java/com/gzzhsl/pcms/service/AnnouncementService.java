package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.entity.Announcement;
import com.gzzhsl.pcms.vo.AnnouncementVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AnnouncementService {

    Announcement getById(String announcementId);

    Announcement save(AnnouncementVO announcementVO);
    Announcement delete(Announcement announcement);
    List<Announcement> getAll();
    Page<Announcement> getNormalLatests(Pageable pageable);
    Page<Announcement> getHotLatests(Pageable pageable);
    Page<Announcement> findAll(Pageable pageable);
    AnnouncementVO getAnnouncementById(String announcementId);
}
