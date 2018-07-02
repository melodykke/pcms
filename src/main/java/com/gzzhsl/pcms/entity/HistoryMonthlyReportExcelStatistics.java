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
    private BigDecimal historyReserveFunds;
    private BigDecimal historyResettlementArrangement;
    private BigDecimal historyEnvironmentalProtection;
    private BigDecimal historyWaterConservation;
    private BigDecimal historyOtherCost;
    @OneToOne
    @JoinColumn(name = "base_info_id")
    private BaseInfo baseInfo;


}
