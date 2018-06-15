package com.gzzhsl.pcms.vo;

import lombok.Data;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;


@Data
public class ProjectMonthlyReportVO {

    private String rtFileTempPath;

    @NotNull(message = "必填项空缺")
    @DecimalMax(value = "999999999999", message = "数据太长！")
    private BigDecimal civilEngineering; // 建筑工程(万元) *
    @NotNull(message = "必填项空缺")
    @DecimalMax(value = "999999999999", message = "数据太长！")
    private BigDecimal metalMechanism; // 金属机构设备及安装工程(万元) *
    @NotNull(message = "必填项空缺")
    @DecimalMax(value = "999999999999", message = "数据太长！")
    private BigDecimal independentCost; // 独立费用(万元) *
    @NotNull(message = "必填项空缺")
    @DecimalMax(value = "999999999999", message = "数据太长！")
    private BigDecimal electromechanicalEquipment; // 机电设备及安装工程(万元) *
    @NotNull(message = "必填项空缺")
    @DecimalMax(value = "999999999999", message = "数据太长！")
    private BigDecimal temporaryWork; // 施工临时工程(万元) *
    @NotNull(message = "必填项空缺")
    @DecimalMax(value = "999999999999", message = "数据太长！")
    private BigDecimal reserveFunds; // 基本预备费(万元) *
    /*移民环境投资*/
    @NotNull(message = "必填项空缺")
    @DecimalMax(value = "999999999999", message = "数据太长！")
    private BigDecimal resettlementArrangement; // 建设征地移民安置补偿费(万元) *
    @NotNull(message = "必填项空缺")
    @DecimalMax(value = "999999999999", message = "数据太长！")
    private BigDecimal environmentalProtection; // 水土保持工程投资(万元) *
    @NotNull(message = "必填项空缺")
    @DecimalMax(value = "999999999999", message = "数据太长！")
    private BigDecimal waterConservation; // 水土保持工程投资(万元) *
    @NotNull(message = "必填项空缺")
    @DecimalMax(value = "999999999999", message = "数据太长！")
    private BigDecimal otherCost; // 其他(万元)
    /*本月完成工程量*/
    @NotNull(message = "必填项空缺")
    @DecimalMax(value = "999999999999", message = "数据太长！")
    private BigDecimal openDug; // 土石方明挖（万/m³） *
    @NotNull(message = "必填项空缺")
    @DecimalMax(value = "999999999999", message = "数据太长！")
    private BigDecimal backfill; // 土石方回填（万/m³） *
    @NotNull(message = "必填项空缺")
    @DecimalMax(value = "999999999999", message = "数据太长！")
    private BigDecimal concrete; // 混泥土（万/m³） *
    @NotNull(message = "必填项空缺")
    @DecimalMax(value = "999999999999", message = "数据太长！")
    private BigDecimal grout; // 灌浆（m或m³） *
    @NotNull(message = "必填项空缺")
    @DecimalMax(value = "999999999999", message = "数据太长！")
    private BigDecimal holeDug; // 土石方洞挖 *
    @NotNull(message = "必填项空缺")
    @DecimalMax(value = "999999999999", message = "数据太长！")
    private BigDecimal masonry; // 砌石（万/m³） *
    @NotNull(message = "必填项空缺")
    @DecimalMax(value = "999999999999", message = "数据太长！")
    private BigDecimal rebar; // 钢筋（t） *
    /*其他情况*/
    @NotNull(message = "必填项空缺")
    @Past(message = "月报时间应小于当前时间")
    private Date submitDate; // 填报月份
    @NotNull(message = "必填项空缺")
    @DecimalMax(value = "999999999999", message = "数据太长！")
    private BigDecimal labourForce; // 劳动力投入（万工日） *
    @Size(min = 0, max = 250, message = "篇幅过长！")
    private String constructionContent; // 主要建设内容
    @Size(min = 0, max = 250, message = "篇幅过长！")
    private String visualProgress; // 工程形象进度及节点工期
    @Size(min = 0, max = 250, message = "篇幅过长！")
    private String difficulty; // 存在困难及问题
    @Size(min = 0, max = 250, message = "篇幅过长！")
    private String measure; // 采取的主要措施
    @Size(min = 0, max = 250, message = "篇幅过长！")
    private String suggestion; // 下一步建议
    @Size(min = 0, max = 250, message = "篇幅过长！")
    private String remark; // 备注

}
