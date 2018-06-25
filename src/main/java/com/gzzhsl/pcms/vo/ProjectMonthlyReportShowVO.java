package com.gzzhsl.pcms.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class ProjectMonthlyReportShowVO {

    @JsonProperty("projectMonthlyReportId")
    private String projectMonthlyReportId;
    @JsonProperty("civilEngineering")
    private BigDecimal civilEngineering; // 建筑工程(万元) *
    @JsonProperty("metalMechanism")
    private BigDecimal metalMechanism; // 金属机构设备及安装工程(万元) *
    @JsonProperty("independentCost")
    private BigDecimal independentCost; // 独立费用(万元) *
    @JsonProperty("electromechanicalEquipment")
    private BigDecimal electromechanicalEquipment; // 机电设备及安装工程(万元) *
    @JsonProperty("temporaryWork")
    private BigDecimal temporaryWork; // 施工临时工程(万元) *
    @JsonProperty("reserveFunds")
    private BigDecimal reserveFunds; // 基本预备费(万元) *
    /*移民环境投资*/
    @JsonProperty("resettlementArrangement")
    private BigDecimal resettlementArrangement; // 建设征地移民安置补偿费(万元) *
    @JsonProperty("environmentalProtection")
    private BigDecimal environmentalProtection; // 水土保持工程投资(万元) *
    @JsonProperty("waterConservation")
    private BigDecimal waterConservation; // 水土保持工程投资(万元) *
    @JsonProperty("otherCost")
    private BigDecimal otherCost; // 其他(万元)
    /*本月完成工程量*/
    @JsonProperty("openDug")
    private BigDecimal openDug; // 土石方明挖（万/m³） *
    @JsonProperty("backfill")
    private BigDecimal backfill; // 土石方回填（万/m³） *
    @JsonProperty("concrete")
    private BigDecimal concrete; // 混泥土（万/m³） *
    @JsonProperty("grout")
    private BigDecimal grout; // 灌浆（m或m³） *
    @JsonProperty("holeDug")
    private BigDecimal holeDug; // 土石方洞挖 *
    @JsonProperty("masonry")
    private BigDecimal masonry; // 砌石（万/m³） *
    @JsonProperty("rebar")
    private BigDecimal rebar; // 钢筋（t） *
    /*其他情况*/
    @JsonProperty("labourForce")
    private BigDecimal labourForce; // 劳动力投入（万工日） *
    @JsonProperty("constructionContent")
    private String constructionContent; // 主要建设内容
    @JsonProperty("visualProgress")
    private String visualProgress; // 工程形象进度及节点工期
    @JsonProperty("difficulty")
    private String difficulty; // 存在困难及问题
    @JsonProperty("measure")
    private String measure; // 采取的主要措施
    @JsonProperty("suggestion")
    private String suggestion; // 下一步建议
    @JsonProperty("remark")
    private String remark; // 备注
    @JsonProperty("projectMonthlyReportImgVOList")
    private List<ProjectMonthlyReportImgVO> projectMonthlyReportImgVOList;
    @JsonProperty("plantName")
    private String plantName;
    @JsonProperty("createTime")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH-mm-ss")
    private Date createTime;
    @JsonProperty("year")
    private Integer year; // 填报年份
    @JsonProperty("month")
    private Integer month; // 填报月份
    @JsonProperty("submitter")
    private String submitter;
    @JsonProperty("state")
    private Byte state;



}
