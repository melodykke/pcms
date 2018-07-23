package com.gzzhsl.pcms.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MonthlyReportPostHistoryVO {
    private BigDecimal yearCivilEngineering; // 年建筑工程(万元) *
    private BigDecimal sofarCivilEngineering; // 至今建筑工程(万元) *
    private BigDecimal yearMetalMechanism; // 金属机构设备及安装工程(万元) *
    private BigDecimal sofarMetalMechanism; // 金属机构设备及安装工程(万元) *
    private BigDecimal yearIndependentCost; // 独立费用(万元) *
    private BigDecimal sofarIndependentCost; // 独立费用(万元) *
    private BigDecimal yearElectromechanicalEquipment; // 机电设备及安装工程(万元) *
    private BigDecimal sofarElectromechanicalEquipment; // 机电设备及安装工程(万元) *
    private BigDecimal yearTemporaryWork; // 施工临时工程(万元) *
    private BigDecimal sofarTemporaryWork; // 施工临时工程(万元) *
    private BigDecimal yearReserveFunds; // 基本预备费(万元) *
    private BigDecimal sofarReserveFunds; // 基本预备费(万元) *
    /*移民环境投资*/
    private BigDecimal yearResettlementArrangement; // 建设征地移民安置补偿费(万元) *
    private BigDecimal sofarResettlementArrangement; // 建设征地移民安置补偿费(万元) *
    private BigDecimal yearEnvironmentalProtection; // 环境保护投资(万元) *
    private BigDecimal sofarEnvironmentalProtection; // 环境保护投资(万元) *
    private BigDecimal yearWaterConservation; // 水土保持工程投资(万元) *
    private BigDecimal sofarWaterConservation; // 水土保持工程投资(万元) *
    private BigDecimal yearOtherCost; // 其他(万元)
    private BigDecimal sofarOtherCost; // 其他(万元)
    /*资金来源*/
    private BigDecimal yearSourceCentralInvestment;
    private BigDecimal sofarSourceCentralInvestment;
    private BigDecimal yearSourceProvincialInvestment;
    private BigDecimal sofarSourceProvincialInvestment;
    private BigDecimal yearSourceLocalInvestment;
    private BigDecimal sofarSourceLocalInvestment;
    /*资金到位*/
    private BigDecimal yearAvailableCentralInvestment;
    private BigDecimal sofarAvailableCentralInvestment;
    private BigDecimal yearAvailableProvincialInvestment;
    private BigDecimal sofarAvailableProvincialInvestment;
    private BigDecimal yearAvailableLocalInvestment;
    private BigDecimal sofarAvailableLocalInvestment;
    /*工程量*/
    private BigDecimal yearOpenDug; // 土石方明挖（万/m³） *
    private BigDecimal sofarOpenDug; // 土石方明挖（万/m³） *
    private BigDecimal yearBackfill; // 土石方回填（万/m³） *
    private BigDecimal sofarBackfill; // 土石方回填（万/m³） *
    private BigDecimal yearConcrete; // 混泥土（万/m³） *
    private BigDecimal sofarConcrete; // 混泥土（万/m³） *
    private BigDecimal yearGrout; // 灌浆（m或m³） *
    private BigDecimal sofarGrout; // 灌浆（m或m³） *
    private BigDecimal yearHoleDug; // 土石方洞挖 *
    private BigDecimal sofarHoleDug; // 土石方洞挖 *
    private BigDecimal yearMasonry; // 砌石（万/m³） *
    private BigDecimal sofarMasonry; // 砌石（万/m³） *
    private BigDecimal yearRebar; // 钢筋（t） *
    private BigDecimal sofarRebar; // 钢筋（t） *
}
