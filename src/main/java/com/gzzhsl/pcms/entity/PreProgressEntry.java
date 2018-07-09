package com.gzzhsl.pcms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Slf4j
public class PreProgressEntry {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String preProgressEntryId;

    private Integer serialNumber; // 编号
    private String planProject; // 计划项目
    private String approvalStatus; // 批复状态
    private String compileUnit; // 编制单位
    private String approvalUnit; // 编制单位
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date approvalDate; // 批复时间
    private String referenceNumber; // 文号

    @ManyToOne
    @JoinColumn(name = "pre_progress_id")
    @JsonBackReference
    private PreProgress preProgress;
    private Date createTime;
}
