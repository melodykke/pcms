package com.gzzhsl.pcms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class Contract {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    private String name;
    private String number;
    private String type;
    private String signDate;
    private String partyA;
    private String partyB;
    private BigDecimal price;
    private String content;
    private String remark;
    private Byte label; // 1 合同内  2 合同外
    private Byte state; // 0 未审核 1 通过 -1 未通过
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "contract")
    @JsonManagedReference
    private List<ContractImg> contractImgs;

    @ManyToOne
    @JoinColumn(name = "base_info_id")
    @JsonBackReference
    private BaseInfo baseInfo;

    private Date createTime;
    private Date updateTime;
}
