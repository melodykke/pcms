package com.gzzhsl.pcms.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class TimeLineItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer timeLineItemId;
    private String type;
    private String msg;
    @OneToOne(mappedBy = "timeLineItem")
    private ProjectStatus projectStatus;
    private Date createTime;
    private Date updateTime;

}
