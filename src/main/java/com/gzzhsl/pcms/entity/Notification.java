package com.gzzhsl.pcms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@Setter
public class Notification {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String notificationId;
    private String type;
    private String typeId; // 就是月报、合同、基础信息等ID
    private String submitter;
    private String yearmonth;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH-mm-ss")
    private Date createTime;
    private boolean checked;
    private String projectId;
    private String url;
}
