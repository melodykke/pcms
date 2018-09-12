package com.gzzhsl.pcms.service;


import com.github.pagehelper.PageInfo;
import com.gzzhsl.pcms.model.Announcement;
import com.gzzhsl.pcms.vo.AnnouncementVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AnnouncementService {

    PageInfo<Announcement> findAnnouncementByPage(int pageNum, int pageSize);
    Integer save(AnnouncementVO announcementVO);
    PageInfo<Announcement> getHotLatests(int pageNum, int pageSize);


    Announcement getById(String announcementId);
    Announcement delete(Announcement announcement);
    List<Announcement> getAll();
    Page<Announcement> getNormalLatests(Pageable pageable);

    Page<Announcement> findAll(Pageable pageable);
    AnnouncementVO getAnnouncementById(String announcementId);
}
