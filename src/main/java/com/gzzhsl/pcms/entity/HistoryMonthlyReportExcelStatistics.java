package com.gzzhsl.pcms.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class HistoryMonthlyReportExcelStatistics {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String hId;
    private BigDecimal historyCivilEngineering; // 建筑工程(万元)
    private BigDecimal historyElectromechanicalEquipment; // 机电设备及安装工程(万元) *
    private BigDecimal historyMetalMechanism; // 金属机构设备及安装工程(万元) *
    private BigDecimal historyIndependentCost; // 独立费用(万元) *
    private BigDecimal historyTemporaryWork; // 施工临时工程(万元) *
    private BigDecimal historyResettlementArrangement; // 建设征地移民安置补偿费(万元) *
    private BigDecimal historyEnvironmentalProtection;// 环境保护工程(万元) *
    private BigDecimal historyWaterConservation; // 水土保持工程投资(万元) *
    private BigDecimal historyOtherCost; // 其他(万元)
    private BigDecimal historySourceCentralInvestment; // 按资金来源分中央投资
    private BigDecimal historySourceProvincialInvestment; // 按资金来源分省级投资
    private BigDecimal historySourceLocalInvestment; // 按资金来源分市县投资
    private BigDecimal historyAvailableCentralInvestment; // 到位资金中央投资
    private BigDecimal historyAvailableProvincialInvestment; // 到位资金省级投资
    private BigDecimal historyAvailableLocalInvestment; // 到位资金市县投资
    private BigDecimal historyOpenDug; // 土石方明挖（万/m³） *
    private BigDecimal historyBackfill; // 土石方回填（万/m³） *
    private BigDecimal historyConcrete; // 混泥土（万/m³） *
    private BigDecimal historyGrout; // 灌浆（m或m³） *
    private BigDecimal historyHoleDug; // 土石方洞挖 *
    private BigDecimal historyMasonry; // 砌石（万/m³） *
    private BigDecimal historyRebar; // 钢筋（t） *
    private BigDecimal historyLabourForce; // 劳动力投入（万工日） *
    @OneToOne
    @JoinColumn(name = "base_info_id")
    private BaseInfo baseInfo;


}
