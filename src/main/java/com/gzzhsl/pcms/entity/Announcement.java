package com.gzzhsl.pcms.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class Announcement {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "uuid")
    private String announcementId;
    private String type;
    private Boolean hot;
    private String title;
    private String keyword;
    @Lob
    private String content;
    private Date createTime;
    private Date updateTime;
}
