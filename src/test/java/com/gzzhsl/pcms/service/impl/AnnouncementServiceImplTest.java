package com.gzzhsl.pcms.service.impl;

import com.github.pagehelper.PageInfo;
import com.gzzhsl.pcms.model.Announcement;
import com.gzzhsl.pcms.service.AnnouncementService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class AnnouncementServiceImplTest {


    @Autowired
    private AnnouncementService announcementService;


    @Test
    public void findAnnouncementByPage() throws Exception {
        PageInfo<Announcement> pageInfo = announcementService.findAnnouncementByPage(3, 2);
        List<Announcement> announcements = pageInfo.getList();
        for (Announcement announcement : announcements) {
            System.out.println(announcement.getTitle());
        }

    }

}