package com.gzzhsl.pcms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Tender {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String tenderId;
    private String tenderFilingUnit; // 招标备案单位
    private String nameOfLots; // 标段名称
    private Date bidPlanDate;
    private Date bidCompleteDate;
    private String bidAgent;
    private String tenderAgent;
    @ManyToOne
    @JoinColumn(name = "base_info_id")
    @JsonBackReference
    private BaseInfo baseInfo;
    private Byte state;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tender")
    private List<TenderImg> tenderImgs;
    private String submitter;
    private Date createTime;
    private Date updateTime;
}
