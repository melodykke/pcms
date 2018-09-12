package com.gzzhsl.pcms.mapper;

import com.gzzhsl.pcms.model.Announcement;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AnnouncementMapperTest {
    @Autowired
    private AnnouncementMapper announcementMapper;

    @Test
    public void findHotLatests() throws Exception {
        List<Announcement> announcementList = announcementMapper.findHotLatests();
        for (Announcement announcement : announcementList) {
            System.out.println(announcement.getTitle());
        }

    }

}