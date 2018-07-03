package com.gzzhsl.pcms.vo;

import com.gzzhsl.pcms.entity.BaseInfoImg;
import lombok.Data;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class BaseInfoVO {
    private String rtFileTempPath;

    private String plantName;
    private String parentId;
    private String remark;
    private String projectType;
    private String location;
    private String legalRepresentativeId;
    private String legalRepresentativeName;
    private String legalPersonId;
    private String legalPersonName;
    private String longitude;
    private String latitude;

    private BigDecimal storage; // 库容
    private String scale; // 规模
    private BigDecimal timeLimit; // 工期
    private BigDecimal centralInvestment; // 中央投资
    private BigDecimal provincialInvestment; // 省级投资
    private BigDecimal localInvestment; // 市县投资
    private BigDecimal totalInvestment; // 总投资
    private String overview; // 概况
    private String level; // 工程等别
    private BigDecimal catchmentArea; // 积水面积km2
    private String damType; // 坝型
    private BigDecimal maxDamHeight; // 最大坝高m
    private BigDecimal floodControlElevation; // 度汛高程
    private BigDecimal spillway; // 溢洪道m
    private BigDecimal irrigatedArea; // 灌溉面积
    private BigDecimal watersupply; // 供水量 万m3年
    private BigDecimal installedCapacity; // 装机容积（Kw）
    private BigDecimal areaCoverage; // 工程占地（亩）
    private String landReclamationPlan; // 土地复垦方案
    private String constructionLand; // 建设用地
    private int unitProjectAmount; // 单位工程数
    private String unitProjectOverview; // 单位工程概况
    private String projectTask; // 工程任务及主要建筑物
    private int branchProjectAmount; // 分部工程数
    private String branchProjectOverview; // 分部工程概况
    private int cellProjectAmount; // 单元工程数
    private String cellProjectOverview; // 单元工程概况
    private String projectSource; // 项目来源
    private String county; // 所在县
    private BigDecimal utilizablCapacity; // 兴利库容
    private String supervisorBid; // 监理、施工招标情况
    private String hasSignedConstructionContract; // 是否签订枢纽工程施工承包合同（是/否）
    private String hasProjectCompleted; // 枢纽工程是否完工（是/否）
    private String hasAcceptCompletion; // 是否竣工验收（是/否）
    private BigDecimal ruralHumanWater; // 农村人饮（万人）
    private BigDecimal livestock; // 大牲畜（万头）
    private BigDecimal waterSupplyPopulation; // 城镇供水人口（万人)
    private BigDecimal centralAccumulativePayment ; // 中央累计拨付（万元）
    private BigDecimal provincialAccumulativePayment; // 省级累计拨付（万元）
    private BigDecimal localAccumulativePayment; // 市县累计拨付（万元）
    private BigDecimal totalAccumulativePayment; // 总的累计拨付
    private BigDecimal provincialLoan; // 省级配套融资贷款（万元）

    private List<BaseInfoImgVO> baseInfoImgVOs;

    private String owner; // 上报人
    private Boolean state;
    private Date createTime;
    private Date updateTime;
}
