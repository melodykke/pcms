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
    private BigDecimal historyCivilEngineering;
    private BigDecimal historyElectromechanicalEquipment;
    private BigDecimal historyMetalMechanism;
    private BigDecimal historyIndependentCost;
    private BigDecimal historyTemporaryWork;
    private BigDecimal historyResettlementArrangement;
    private BigDecimal historyEnvironmentalProtection;
    private BigDecimal historyWaterConservation;
    private BigDecimal historyOtherCost;
    private BigDecimal historySourceCentralInvestment;
    private BigDecimal historySourceProvincialInvestment;
    private BigDecimal historySourceLocalInvestment;
    private BigDecimal historyAvailableCentralInvestment;
    private BigDecimal historyAvailableProvincialInvestment;
    private BigDecimal historyAvailableLocalInvestment;
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
