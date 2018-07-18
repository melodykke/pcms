package com.gzzhsl.pcms.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
public class MonthlyReportExcelModel {
    private BigDecimal totalInvestment; // 计划总投资
    private BigDecimal thisYearPlanInvestment; // 本年计划投资
    private BigDecimal civilEngineering; // 建筑工程(万元) *
    private BigDecimal electromechanicalEquipment; // 机电设备及安装工程(万元) *
    private BigDecimal metalMechanism; // 金属机构设备及安装工程(万元) *
    private BigDecimal independentCost; // 独立费用(万元) *
    private BigDecimal temporaryWork; // 施工临时工程(万元) *
    private BigDecimal resettlementArrangement; // 建设征地移民安置补偿费(万元) *
    private BigDecimal environmentalProtection; // 水土保持工程投资(万元) *
    private BigDecimal waterConservation; // 水土保持工程投资(万元) *
    private BigDecimal otherCost; // 其他(万元)
    private BigDecimal sourceCentralInvestment;
    private BigDecimal sourceProvincialInvestment;
    private BigDecimal sourceLocalInvestment;
    private BigDecimal availableCentralInvestment;
    private BigDecimal availableProvincialInvestment;
    private BigDecimal availableLocalInvestment;
    private BigDecimal openDug; // 土石方明挖（万/m³） *
    private BigDecimal backfill; // 土石方回填（万/m³） *
    private BigDecimal concrete; // 混泥土（万/m³） *
    private BigDecimal grout; // 灌浆（m或m³） *
    private BigDecimal holeDug; // 土石方洞挖 *
    private BigDecimal masonry; // 砌石（万/m³） *
    private BigDecimal rebar; // 钢筋（t） *
    private BigDecimal labourForce; // 劳动力投入（万工日） *

    private BigDecimal yearCivilEngineering; // 年度建筑工程(万元) *
    private BigDecimal yearElectromechanicalEquipment; // 年度机电设备及安装工程(万元) *
    private BigDecimal yearMetalMechanism; // 年度金属机构设备及安装工程(万元) *
    private BigDecimal yearIndependentCost; // 年度独立费用(万元) *
    private BigDecimal yearTemporaryWork; // 年度施工临时工程(万元) *
    private BigDecimal yearResettlementArrangement; // 年度建设征地移民安置补偿费(万元) *
    private BigDecimal yearEnvironmentalProtection; // 年度水土保持工程投资(万元) *
    private BigDecimal yearWaterConservation; // 年度水土保持工程投资(万元) *
    private BigDecimal yearOtherCost; // 年度其他(万元)
    private BigDecimal yearSourceCentralInvestment;
    private BigDecimal yearSourceProvincialInvestment;
    private BigDecimal yearSourceLocalInvestment;
    private BigDecimal yearAvailableCentralInvestment;
    private BigDecimal yearAvailableProvincialInvestment;
    private BigDecimal yearAvailableLocalInvestment;
    private BigDecimal yearOpenDug; // 土石方明挖（万/m³） *
    private BigDecimal yearBackfill; // 土石方回填（万/m³） *
    private BigDecimal yearConcrete; // 混泥土（万/m³） *
    private BigDecimal yearGrout; // 灌浆（m或m³） *
    private BigDecimal yearHoleDug; // 土石方洞挖 *
    private BigDecimal yearMasonry; // 砌石（万/m³） *
    private BigDecimal yearRebar; // 钢筋（t） *
    private BigDecimal yearLabourForce; // 劳动力投入（万工日） *

    private BigDecimal sofarCivilEngineering; // 至今建筑工程(万元) *
    private BigDecimal sofarElectromechanicalEquipment; // 至今机电设备及安装工程(万元) *
    private BigDecimal sofarMetalMechanism; // 至今金属机构设备及安装工程(万元) *
    private BigDecimal sofarIndependentCost; // 至今独立费用(万元) *
    private BigDecimal sofarTemporaryWork; // 至今施工临时工程(万元) *
    private BigDecimal sofarResettlementArrangement; // 至今建设征地移民安置补偿费(万元) *
    private BigDecimal sofarEnvironmentalProtection; // 至今水土保持工程投资(万元) *
    private BigDecimal sofarWaterConservation; // 至今水土保持工程投资(万元) *
    private BigDecimal sofarOtherCost; // 至今其他(万元)
    private BigDecimal sofarSourceCentralInvestment;
    private BigDecimal sofarSourceProvincialInvestment;
    private BigDecimal sofarSourceLocalInvestment;
    private BigDecimal sofarAvailableCentralInvestment;
    private BigDecimal sofarAvailableProvincialInvestment;
    private BigDecimal sofarAvailableLocalInvestment;
    private BigDecimal sofarOpenDug; // 土石方明挖（万/m³） *
    private BigDecimal sofarBackfill; // 土石方回填（万/m³） *
    private BigDecimal sofarConcrete; // 混泥土（万/m³） *
    private BigDecimal sofarGrout; // 灌浆（m或m³） *
    private BigDecimal sofarHoleDug; // 土石方洞挖 *
    private BigDecimal sofarMasonry; // 砌石（万/m³） *
    private BigDecimal sofarRebar; // 钢筋（t） *
    private BigDecimal sofarLabourForce; // 劳动力投入（万工日） *
}
