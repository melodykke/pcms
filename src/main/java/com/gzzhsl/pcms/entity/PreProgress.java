package com.gzzhsl.pcms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.xml.internal.rngom.parse.host.Base;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Slf4j
public class PreProgress {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String PreProgressId;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "preProgress")
    private List<PreProgressEntry> preProgressEntries;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "preProgress")
    private List<PreProgressImg> preProgressImgs;
    private String owner; // 上报人
    private Byte state;
    private Integer repeatTimes;

    @OneToOne
    @JoinColumn(name = "base_info_id")
    @JsonBackReference
    private BaseInfo baseInfo;

    private Date createTime;
    private Date updateTime;
}
