package com.gzzhsl.pcms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Slf4j
public class ProjectMonthlyReport {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String projectMonthlyReportId;
    /*工程部分投资*/
    private BigDecimal civilEngineering; // 建筑工程(万元) *
    private BigDecimal metalMechanism; // 金属机构设备及安装工程(万元) *
    private BigDecimal independentCost; // 独立费用(万元) *
    private BigDecimal electromechanicalEquipment; // 机电设备及安装工程(万元) *
    private BigDecimal temporaryWork; // 施工临时工程(万元) *
    private BigDecimal reserveFunds; // 基本预备费(万元) *
    /*移民环境投资*/
    private BigDecimal resettlementArrangement; // 建设征地移民安置补偿费(万元) *
    private BigDecimal environmentalProtection; // 水土保持工程投资(万元) *
    private BigDecimal waterConservation; // 水土保持工程投资(万元) *
    private BigDecimal otherCost; // 其他(万元)
    /*本月完成工程量*/
    private BigDecimal openDug; // 土石方明挖（万/m³） *
    private BigDecimal backfill; // 土石方回填（万/m³） *
    private BigDecimal concrete; // 混泥土（万/m³） *
    private BigDecimal grout; // 灌浆（m或m³） *
    private BigDecimal holeDug; // 土石方洞挖 *
    private BigDecimal masonry; // 砌石（万/m³） *
    private BigDecimal rebar; // 钢筋（t） *
    /*其他情况*/
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM")
    private Date submitDate; // 填报月份
    private BigDecimal labourForce; // 劳动力投入（万工日） *
    private String constructionContent; // 主要建设内容
    private String visualProgress; // 工程形象进度及节点工期
    private String difficulty; // 存在困难及问题
    private String measure; // 采取的主要措施
    private String suggestion; // 下一步建议
    private String remark; // 备注
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "projectMonthlyReport")
    private List<ProjectMonthlyReportImg> projectMonthlyReportImgList;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
    private String submitter;
    private Byte state;

    private Date createTime;
}
