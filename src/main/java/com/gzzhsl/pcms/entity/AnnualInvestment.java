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
public class AnnualInvestment {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String annualInvestmentId;
    private String year;
    private BigDecimal applyFigure;
    private BigDecimal approvedFigure;

    @ManyToOne
    @JoinColumn(name = "base_info_id")
    @JsonBackReference
    private BaseInfo baseInfo;
    private Byte state;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "annualInvestment")
    private List<AnnualInvestmentImg> annualInvestmentImgs;
    private String submitter;
    private Date createTime;
    private Date updateTime;
}
