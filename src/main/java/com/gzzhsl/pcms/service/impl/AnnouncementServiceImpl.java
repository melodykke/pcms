package com.gzzhsl.pcms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gzzhsl.pcms.converter.announcementVO2;
import com.gzzhsl.pcms.mapper.AnnouncementMapper;
import com.gzzhsl.pcms.model.Announcement;
import com.gzzhsl.pcms.repository.AnnouncementRepository;
import com.gzzhsl.pcms.service.AnnouncementService;
import com.gzzhsl.pcms.vo.AnnouncementVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@Transactional
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired
    private AnnouncementMapper announcementMapper;

    @Autowired
    private AnnouncementRepository announcementRepository;


    @Override
    public PageInfo<Announcement> findAnnouncementByPage(int pageNum, int pageSize) {
        //设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
        PageHelper.startPage(pageNum, pageSize);
        List<Announcement> announcements = announcementMapper.selectAll();        //全部商品
        PageInfo result = new PageInfo(announcements);
        return result;
    }
    @Override
    public PageInfo<Announcement> getHotLatests(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Announcement> announcements = announcementMapper.findHotLatests();
        PageInfo result = new PageInfo(announcements);
        return result;
    }







    @Override
    public Announcement getById(String announcementId) {
        return null;
    }

    @Override
    public Integer save(AnnouncementVO announcementVO) {
        Announcement announcement = announcementVO2.convert(announcementVO);
        if (announcement.getAnnouncementId() == null || "".equals(announcement.getAnnouncementId())) { // 新增
            announcement.setCreateTime(new Date());
        }
        announcement.setUpdateTime(new Date());
        return announcementMapper.insert(announcement);
    }

    @Override
    public Announcement delete(Announcement announcement) {
        return null;
    }

    @Override
    public List<Announcement> getAll() {
        return announcementMapper.selectAll();
    }

    @Override
    public Page<Announcement> getNormalLatests(Pageable pageable) {
        Page<Announcement> announcements = null;
        //announcements = announcementMapper.s(pageable);
        return announcements;
    }

    @Override
    public Page<Announcement> findAll(Pageable pageable) {
        //Page<Announcement> announcements = announcementRepository.findAll(pageable);
        return null;
    }

    @Override
    public AnnouncementVO getAnnouncementById(String announcementId) {
        //Announcement announcement = announcementRepository.findOne(announcementId);
        AnnouncementVO announcementVO = new AnnouncementVO();
        //BeanUtils.copyProperties(announcement, announcementVO);
        return null;
    }
}
