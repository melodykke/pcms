package com.gzzhsl.pcms.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
public class MonthlyReportExcelModel {
    private BigDecimal civilEngineering; // 建筑工程(万元) *
    private BigDecimal electromechanicalEquipment; // 机电设备及安装工程(万元) *
    private BigDecimal metalMechanism; // 金属机构设备及安装工程(万元) *
    private BigDecimal independentCost; // 独立费用(万元) *
    private BigDecimal temporaryWork; // 施工临时工程(万元) *
    private BigDecimal reserveFunds; // 基本预备费(万元) *
    private BigDecimal resettlementArrangement; // 建设征地移民安置补偿费(万元) *
    private BigDecimal environmentalProtection; // 水土保持工程投资(万元) *
    private BigDecimal waterConservation; // 水土保持工程投资(万元) *
    private BigDecimal otherCost; // 其他(万元)

    private BigDecimal yearCivilEngineering; // 年度建筑工程(万元) *
    private BigDecimal yearElectromechanicalEquipment; // 年度机电设备及安装工程(万元) *
    private BigDecimal yearMetalMechanism; // 年度金属机构设备及安装工程(万元) *
    private BigDecimal yearIndependentCost; // 年度独立费用(万元) *
    private BigDecimal yearTemporaryWork; // 年度施工临时工程(万元) *
    private BigDecimal yearReserveFunds; // 年度基本预备费(万元) *
    private BigDecimal yearResettlementArrangement; // 年度建设征地移民安置补偿费(万元) *
    private BigDecimal yearEnvironmentalProtection; // 年度水土保持工程投资(万元) *
    private BigDecimal yearWaterConservation; // 年度水土保持工程投资(万元) *
    private BigDecimal yearOtherCost; // 年度其他(万元)

    private BigDecimal sofarCivilEngineering; // 至今建筑工程(万元) *
    private BigDecimal sofarElectromechanicalEquipment; // 至今机电设备及安装工程(万元) *
    private BigDecimal sofarMetalMechanism; // 至今金属机构设备及安装工程(万元) *
    private BigDecimal sofarIndependentCost; // 至今独立费用(万元) *
    private BigDecimal sofarTemporaryWork; // 至今施工临时工程(万元) *
    private BigDecimal sofarReserveFunds; // 至今基本预备费(万元) *
    private BigDecimal sofarResettlementArrangement; // 至今建设征地移民安置补偿费(万元) *
    private BigDecimal sofarEnvironmentalProtection; // 至今水土保持工程投资(万元) *
    private BigDecimal sofarWaterConservation; // 至今水土保持工程投资(万元) *
    private BigDecimal sofarOtherCost; // 至今其他(万元)
}
