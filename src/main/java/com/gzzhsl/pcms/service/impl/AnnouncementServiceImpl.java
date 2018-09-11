package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.converter.announcementVO2;
import com.gzzhsl.pcms.entity.Announcement;
import com.gzzhsl.pcms.repository.AnnouncementRepository;
import com.gzzhsl.pcms.service.AnnouncementService;
import com.gzzhsl.pcms.vo.AnnouncementVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@Transactional
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;


    @Override
    public Announcement getById(String announcementId) {
        return null;
    }

    @Override
    public Announcement save(AnnouncementVO announcementVO) {
        Announcement announcement = announcementVO2.convert(announcementVO);
        if (announcement.getAnnouncementId() == null || "".equals(announcement.getAnnouncementId())) { // 新增
            announcement.setCreateTime(new Date());
        }
        announcement.setUpdateTime(new Date());
        return announcementRepository.save(announcement);
    }

    @Override
    public Announcement delete(Announcement announcement) {
        return null;
    }

    @Override
    public List<Announcement> getAll() {
        return announcementRepository.findAll();
    }

    @Override
    public Page<Announcement> getNormalLatests(Pageable pageable) {
        Page<Announcement> announcements = null;
        announcements = announcementRepository.findAll(pageable);
        return announcements;
    }


    @Override
    public Page<Announcement> getHotLatests(Pageable pageable) {
        Page<Announcement> announcements = null;
        Specification querySpecification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                return cb.equal(root.get("hot").as(Boolean.class), (byte) 1);
            }
        };
        announcements = announcementRepository.findAll(querySpecification, pageable);
        return announcements;
    }

    @Override
    public Page<Announcement> findAll(Pageable pageable) {
        Page<Announcement> announcements = announcementRepository.findAll(pageable);
        return announcements;
    }

    @Override
    public AnnouncementVO getAnnouncementById(String announcementId) {
        //Announcement announcement = announcementRepository.findOne(announcementId);
        AnnouncementVO announcementVO = new AnnouncementVO();
        //BeanUtils.copyProperties(announcement, announcementVO);
        return null;
    }
}
