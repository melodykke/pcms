package com.gzzhsl.pcms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class TenderImg {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String tenderImgId;
    private String imgAddr;
    private String imgDesc;
    private Date createTime;
    @ManyToOne
    @JoinColumn(name = "tender_id")
    @JsonBackReference
    private Tender tender;


}
