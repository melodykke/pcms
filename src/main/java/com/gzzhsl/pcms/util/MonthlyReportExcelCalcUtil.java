package com.gzzhsl.pcms.util;

import com.gzzhsl.pcms.entity.HistoryMonthlyReportExcelStatistics;
import com.gzzhsl.pcms.entity.ProjectMonthlyReport;
import com.gzzhsl.pcms.vo.MonthlyReportExcelModel;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MonthlyReportExcelCalcUtil {

    public static MonthlyReportExcelModel getModelWithMonthParams(MonthlyReportExcelModel monthlyReportExcelModel, ProjectMonthlyReport projectMonthlyReport) {
        BeanUtils.copyProperties(projectMonthlyReport, monthlyReportExcelModel);
        return monthlyReportExcelModel;
    }

    public static MonthlyReportExcelModel getModelWithYearParams(MonthlyReportExcelModel monthlyReportExcelModel,
                                                                 List<ProjectMonthlyReport> yearProjectMonthlyReports) {
        BigDecimal yearCivilEngineering = new BigDecimal(0);
        BigDecimal yearElectromechanicalEquipment = new BigDecimal(0);
        BigDecimal yearMetalMechanism = new BigDecimal(0);
        BigDecimal yearIndependentCost = new BigDecimal(0);
        BigDecimal yearTemporaryWork = new BigDecimal(0);
        BigDecimal yearResettlementArrangement = new BigDecimal(0);
        BigDecimal yearEnvironmentalProtection = new BigDecimal(0);
        BigDecimal yearWaterConservation = new BigDecimal(0);
        BigDecimal yearOtherCost = new BigDecimal(0);
        BigDecimal yearSourceCentralInvestment = new BigDecimal(0);
        ;
        BigDecimal yearSourceProvincialInvestment = new BigDecimal(0);
        ;
        BigDecimal yearSourceLocalInvestment = new BigDecimal(0);
        ;
        BigDecimal yearAvailableCentralInvestment = new BigDecimal(0);
        ;
        BigDecimal yearAvailableProvincialInvestment = new BigDecimal(0);
        ;
        BigDecimal yearAvailableLocalInvestment = new BigDecimal(0);
        ;
        BigDecimal yearOpenDug = new BigDecimal(0);
        ; // 土石方明挖（万/m³） *
        BigDecimal yearBackfill = new BigDecimal(0);
        ; // 土石方回填（万/m³） *
        BigDecimal yearConcrete = new BigDecimal(0);
        ; // 混泥土（万/m³） *
        BigDecimal yearGrout = new BigDecimal(0);
        ; // 灌浆（m或m³） *
        BigDecimal yearHoleDug = new BigDecimal(0);
        ; // 土石方洞挖 *
        BigDecimal yearMasonry = new BigDecimal(0);
        ; // 砌石（万/m³） *
        BigDecimal yearRebar = new BigDecimal(0);
        ; // 钢筋（t） *
        BigDecimal yearLabourForce = new BigDecimal(0);
        ; // 劳动力投入（万工日） *
        for (ProjectMonthlyReport eachReport : yearProjectMonthlyReports) {
            yearCivilEngineering = yearCivilEngineering.add(eachReport.getCivilEngineering());
            yearElectromechanicalEquipment = yearElectromechanicalEquipment.add(eachReport.getElectromechanicalEquipment());
            yearMetalMechanism = yearMetalMechanism.add(eachReport.getMetalMechanism());
            yearIndependentCost = yearIndependentCost.add(eachReport.getIndependentCost());
            yearTemporaryWork = yearTemporaryWork.add(eachReport.getTemporaryWork());
            yearResettlementArrangement = yearResettlementArrangement.add(eachReport.getResettlementArrangement());
            yearEnvironmentalProtection = yearEnvironmentalProtection.add(eachReport.getEnvironmentalProtection());
            yearWaterConservation = yearWaterConservation.add(eachReport.getWaterConservation());
            yearOtherCost = yearOtherCost.add(eachReport.getOtherCost());
            yearSourceCentralInvestment = yearSourceCentralInvestment.add(eachReport.getSourceCentralInvestment());
            yearSourceProvincialInvestment = yearSourceProvincialInvestment.add(eachReport.getSourceProvincialInvestment());
            yearSourceLocalInvestment = yearSourceLocalInvestment.add(eachReport.getSourceLocalInvestment());
            yearAvailableCentralInvestment = yearAvailableCentralInvestment.add(eachReport.getAvailableCentralInvestment());
            yearAvailableProvincialInvestment = yearAvailableProvincialInvestment.add(eachReport.getAvailableProvincialInvestment());
            yearAvailableLocalInvestment = yearAvailableLocalInvestment.add(eachReport.getAvailableLocalInvestment());
            yearOpenDug = yearOpenDug.add(eachReport.getOpenDug());
            yearBackfill = yearBackfill.add(eachReport.getBackfill());
            yearConcrete = yearConcrete.add(eachReport.getConcrete());
            yearGrout = yearGrout.add(eachReport.getGrout());
            yearHoleDug = yearHoleDug.add(eachReport.getHoleDug());
            yearMasonry = yearMasonry.add(eachReport.getMasonry());
            yearRebar = yearRebar.add(eachReport.getRebar());
            yearLabourForce = yearLabourForce.add(eachReport.getLabourForce());
        }
        monthlyReportExcelModel.setYearCivilEngineering(yearCivilEngineering);
        monthlyReportExcelModel.setYearElectromechanicalEquipment(yearElectromechanicalEquipment);
        monthlyReportExcelModel.setYearMetalMechanism(yearMetalMechanism);
        monthlyReportExcelModel.setYearIndependentCost(yearIndependentCost);
        monthlyReportExcelModel.setYearTemporaryWork(yearTemporaryWork);
        monthlyReportExcelModel.setYearResettlementArrangement(yearResettlementArrangement);
        monthlyReportExcelModel.setYearEnvironmentalProtection(yearEnvironmentalProtection);
        monthlyReportExcelModel.setYearWaterConservation(yearWaterConservation);
        monthlyReportExcelModel.setYearOtherCost(yearOtherCost);
        monthlyReportExcelModel.setYearSourceCentralInvestment(yearSourceCentralInvestment);
        monthlyReportExcelModel.setYearSourceProvincialInvestment(yearSourceProvincialInvestment);
        monthlyReportExcelModel.setYearSourceLocalInvestment(yearSourceLocalInvestment);
        monthlyReportExcelModel.setYearAvailableCentralInvestment(yearAvailableCentralInvestment);
        monthlyReportExcelModel.setYearAvailableProvincialInvestment(yearAvailableProvincialInvestment);
        monthlyReportExcelModel.setYearAvailableLocalInvestment(yearAvailableLocalInvestment);
        monthlyReportExcelModel.setYearOpenDug(yearOpenDug);
        monthlyReportExcelModel.setYearBackfill(yearBackfill);
        monthlyReportExcelModel.setYearConcrete(yearConcrete);
        monthlyReportExcelModel.setYearGrout(yearGrout);
        monthlyReportExcelModel.setYearHoleDug(yearHoleDug);
        monthlyReportExcelModel.setYearMasonry(yearMasonry);
        monthlyReportExcelModel.setYearRebar(yearRebar);
        monthlyReportExcelModel.setYearLabourForce(yearLabourForce);
        return monthlyReportExcelModel;
    }

    public static MonthlyReportExcelModel getModelWithSofarParams(MonthlyReportExcelModel monthlyReportExcelModel,
                                                                  List<ProjectMonthlyReport> sofarProjectMonthlyReports,
                                                                  HistoryMonthlyReportExcelStatistics historyMonthlyReportExcelStatistics) {
        BigDecimal sofarCivilEngineering = new BigDecimal(0);
        BigDecimal sofarElectromechanicalEquipment = new BigDecimal(0);
        BigDecimal sofarMetalMechanism = new BigDecimal(0);
        BigDecimal sofarIndependentCost = new BigDecimal(0);
        BigDecimal sofarTemporaryWork = new BigDecimal(0);
        BigDecimal sofarResettlementArrangement = new BigDecimal(0);
        BigDecimal sofarEnvironmentalProtection = new BigDecimal(0);
        BigDecimal sofarWaterConservation = new BigDecimal(0);
        BigDecimal sofarOtherCost = new BigDecimal(0);
        BigDecimal sofarSourceCentralInvestment = new BigDecimal(0);
        BigDecimal sofarSourceProvincialInvestment = new BigDecimal(0);
        BigDecimal sofarSourceLocalInvestment = new BigDecimal(0);
        BigDecimal sofarAvailableCentralInvestment = new BigDecimal(0);
        BigDecimal sofarAvailableProvincialInvestment = new BigDecimal(0);
        BigDecimal sofarAvailableLocalInvestment = new BigDecimal(0);
        BigDecimal sofarOpenDug = new BigDecimal(0);
        BigDecimal sofarBackfill = new BigDecimal(0);
        BigDecimal sofarConcrete = new BigDecimal(0);
        BigDecimal sofarGrout = new BigDecimal(0);
        BigDecimal sofarHoleDug = new BigDecimal(0);
        BigDecimal sofarMasonry = new BigDecimal(0);
        BigDecimal sofarRebar = new BigDecimal(0);
        BigDecimal sofarLabourForce = new BigDecimal(0);
        for (ProjectMonthlyReport eachReport : sofarProjectMonthlyReports) {
            sofarCivilEngineering = sofarCivilEngineering.add(eachReport.getCivilEngineering());
            sofarElectromechanicalEquipment = sofarElectromechanicalEquipment.add(eachReport.getElectromechanicalEquipment());
            sofarMetalMechanism = sofarMetalMechanism.add(eachReport.getMetalMechanism());
            sofarIndependentCost = sofarIndependentCost.add(eachReport.getIndependentCost());
            sofarTemporaryWork = sofarTemporaryWork.add(eachReport.getTemporaryWork());
            sofarResettlementArrangement = sofarResettlementArrangement.add(eachReport.getResettlementArrangement());
            sofarEnvironmentalProtection = sofarEnvironmentalProtection.add(eachReport.getEnvironmentalProtection());
            sofarWaterConservation = sofarWaterConservation.add(eachReport.getWaterConservation());
            sofarOtherCost = sofarOtherCost.add(eachReport.getOtherCost());
            sofarSourceCentralInvestment = sofarSourceCentralInvestment.add(eachReport.getSourceCentralInvestment());
            sofarSourceProvincialInvestment = sofarSourceProvincialInvestment.add(eachReport.getSourceProvincialInvestment());
            sofarSourceLocalInvestment = sofarSourceLocalInvestment.add(eachReport.getSourceLocalInvestment());
            sofarAvailableCentralInvestment = sofarAvailableCentralInvestment.add(eachReport.getAvailableCentralInvestment());
            sofarAvailableProvincialInvestment = sofarAvailableProvincialInvestment.add(eachReport.getAvailableProvincialInvestment());
            sofarAvailableLocalInvestment = sofarAvailableLocalInvestment.add(eachReport.getAvailableLocalInvestment());
            sofarOpenDug = sofarOpenDug.add(eachReport.getOpenDug());
            sofarBackfill = sofarBackfill.add(eachReport.getBackfill());
            sofarConcrete = sofarConcrete.add(eachReport.getConcrete());
            sofarGrout = sofarGrout.add(eachReport.getGrout());
            sofarHoleDug = sofarHoleDug.add(eachReport.getHoleDug());
            sofarMasonry = sofarMasonry.add(eachReport.getMasonry());
            sofarRebar = sofarRebar.add(eachReport.getRebar());
            sofarLabourForce = sofarLabourForce.add(eachReport.getLabourForce());
        }
        if (historyMonthlyReportExcelStatistics != null) {
            monthlyReportExcelModel.setSofarCivilEngineering(sofarCivilEngineering.add(historyMonthlyReportExcelStatistics.getHistoryCivilEngineering()));
            monthlyReportExcelModel.setSofarElectromechanicalEquipment(sofarElectromechanicalEquipment.add(historyMonthlyReportExcelStatistics.getHistoryElectromechanicalEquipment()));
            monthlyReportExcelModel.setSofarMetalMechanism(sofarMetalMechanism.add(historyMonthlyReportExcelStatistics.getHistoryMetalMechanism()));
            monthlyReportExcelModel.setSofarIndependentCost(sofarIndependentCost.add(historyMonthlyReportExcelStatistics.getHistoryIndependentCost()));
            monthlyReportExcelModel.setSofarTemporaryWork(sofarTemporaryWork.add(historyMonthlyReportExcelStatistics.getHistoryTemporaryWork()));
            monthlyReportExcelModel.setSofarResettlementArrangement(sofarResettlementArrangement.add(historyMonthlyReportExcelStatistics.getHistoryResettlementArrangement()));
            monthlyReportExcelModel.setSofarEnvironmentalProtection(sofarEnvironmentalProtection.add(historyMonthlyReportExcelStatistics.getHistoryEnvironmentalProtection()));
            monthlyReportExcelModel.setSofarWaterConservation(sofarWaterConservation.add(historyMonthlyReportExcelStatistics.getHistoryWaterConservation()));
            monthlyReportExcelModel.setSofarOtherCost(sofarOtherCost.add(historyMonthlyReportExcelStatistics.getHistoryOtherCost()));
            monthlyReportExcelModel.setSofarSourceCentralInvestment(sofarSourceCentralInvestment.add(historyMonthlyReportExcelStatistics.getHistorySourceCentralInvestment()));
            monthlyReportExcelModel.setSofarSourceProvincialInvestment(sofarSourceProvincialInvestment.add(historyMonthlyReportExcelStatistics.getHistorySourceProvincialInvestment()));
            monthlyReportExcelModel.setSofarSourceLocalInvestment(sofarSourceLocalInvestment.add(historyMonthlyReportExcelStatistics.getHistorySourceLocalInvestment()));
            monthlyReportExcelModel.setSofarAvailableCentralInvestment(sofarAvailableCentralInvestment.add(historyMonthlyReportExcelStatistics.getHistoryAvailableCentralInvestment()));
            monthlyReportExcelModel.setSofarAvailableProvincialInvestment(sofarAvailableProvincialInvestment.add(historyMonthlyReportExcelStatistics.getHistoryAvailableProvincialInvestment()));
            monthlyReportExcelModel.setSofarAvailableLocalInvestment(sofarAvailableLocalInvestment.add(historyMonthlyReportExcelStatistics.getHistoryAvailableLocalInvestment()));
            monthlyReportExcelModel.setSofarOpenDug(sofarOpenDug.add(historyMonthlyReportExcelStatistics.getHistoryOpenDug()));
            monthlyReportExcelModel.setSofarBackfill(sofarBackfill.add(historyMonthlyReportExcelStatistics.getHistoryBackfill()));
            monthlyReportExcelModel.setSofarConcrete(sofarConcrete.add(historyMonthlyReportExcelStatistics.getHistoryConcrete()));
            monthlyReportExcelModel.setSofarGrout(sofarGrout.add(historyMonthlyReportExcelStatistics.getHistoryGrout()));
            monthlyReportExcelModel.setSofarHoleDug(sofarHoleDug.add(historyMonthlyReportExcelStatistics.getHistoryHoleDug()));
            monthlyReportExcelModel.setSofarMasonry(sofarMasonry.add(historyMonthlyReportExcelStatistics.getHistoryMasonry()));
            monthlyReportExcelModel.setSofarRebar(sofarRebar.add(historyMonthlyReportExcelStatistics.getHistoryRebar()));
            monthlyReportExcelModel.setSofarLabourForce(sofarLabourForce.add(historyMonthlyReportExcelStatistics.getHistoryLabourForce()));
        } else {
            monthlyReportExcelModel.setSofarCivilEngineering(sofarCivilEngineering);
            monthlyReportExcelModel.setSofarElectromechanicalEquipment(sofarElectromechanicalEquipment);
            monthlyReportExcelModel.setSofarMetalMechanism(sofarMetalMechanism);
            monthlyReportExcelModel.setSofarIndependentCost(sofarIndependentCost);
            monthlyReportExcelModel.setSofarTemporaryWork(sofarTemporaryWork);
            monthlyReportExcelModel.setSofarResettlementArrangement(sofarResettlementArrangement);
            monthlyReportExcelModel.setSofarEnvironmentalProtection(sofarEnvironmentalProtection);
            monthlyReportExcelModel.setSofarWaterConservation(sofarWaterConservation);
            monthlyReportExcelModel.setSofarOtherCost(sofarOtherCost);
            monthlyReportExcelModel.setSofarSourceCentralInvestment(sofarSourceCentralInvestment);
            monthlyReportExcelModel.setSofarSourceProvincialInvestment(sofarSourceProvincialInvestment);
            monthlyReportExcelModel.setSofarSourceLocalInvestment(sofarSourceLocalInvestment);
            monthlyReportExcelModel.setSofarAvailableCentralInvestment(sofarAvailableCentralInvestment);
            monthlyReportExcelModel.setSofarAvailableProvincialInvestment(sofarAvailableProvincialInvestment);
            monthlyReportExcelModel.setSofarAvailableLocalInvestment(sofarAvailableLocalInvestment);
            monthlyReportExcelModel.setSofarOpenDug(sofarOpenDug);
            monthlyReportExcelModel.setSofarBackfill(sofarBackfill);
            monthlyReportExcelModel.setSofarConcrete(sofarConcrete);
            monthlyReportExcelModel.setSofarGrout(sofarGrout);
            monthlyReportExcelModel.setSofarHoleDug(sofarHoleDug);
            monthlyReportExcelModel.setSofarMasonry(sofarMasonry);
            monthlyReportExcelModel.setSofarRebar(sofarRebar);
            monthlyReportExcelModel.setSofarLabourForce(sofarLabourForce);
        }
        return monthlyReportExcelModel;
    }

    // 构建月报表模板
    private List<List<String>> initExcelModel() {
        List<List<String>> monthlyReportExcelModel = new ArrayList<>();
        List<String> row1 = new ArrayList<>();
        row1.add("1");
        row1.add("计划总投资");
        row1.add("万元");
        row1.add("/");
        row1.add("/");
        row1.add("0");
        List<String> row2 = new ArrayList<>();
        row2.add("2");
        row2.add("本年计划投资");
        row2.add("万元");
        row2.add("/");
        row2.add("0");
        row2.add("/");
        List<String> row3 = new ArrayList<>();
        row3.add("3");
        row3.add("完成投资");
        row3.add("/");
        row3.add("本月");
        row3.add("本年累计");
        row3.add("开工累计");
        List<String> row4 = new ArrayList<>();
        row4.add("3.1");
        row4.add("按概算构成分");
        row4.add("万元");
        row4.add("0");
        row4.add("0");
        row4.add("0");
        List<String> row5 = new ArrayList<>();
        row5.add("3.1.1");
        row5.add("建筑工程");
        row5.add("万元");
        row5.add("0");
        row5.add("0");
        row5.add("0");
        List<String> row6 = new ArrayList<>();
        row6.add("3.1.2");
        row6.add("金属机构设备及安装工程");
        row6.add("万元");
        row6.add("0");
        row6.add("0");
        row6.add("0");
        List<String> row7 = new ArrayList<>();
        row7.add("3.1.3");
        row7.add("机电设备及安装工程");
        row7.add("万元");
        row7.add("0");
        row7.add("0");
        row7.add("0");
        List<String> row8 = new ArrayList<>();
        row8.add("3.1.4");
        row8.add("施工临时工程");
        row8.add("万元");
        row8.add("0");
        row8.add("0");
        row8.add("0");
        List<String> row9 = new ArrayList<>();
        row9.add("3.1.5");
        row9.add("独立费用");
        row9.add("万元");
        row9.add("0");
        row9.add("0");
        row9.add("0");
        List<String> row10 = new ArrayList<>();
        row10.add("3.1.6");
        row10.add("征地及移民投资");
        row10.add("万元");
        row10.add("0");
        row10.add("0");
        row10.add("0");
        List<String> row11 = new ArrayList<>();
        row11.add("3.1.7");
        row11.add("水土保持工程");
        row11.add("万元");
        row11.add("0");
        row11.add("0");
        row11.add("0");
        List<String> row12 = new ArrayList<>();
        row12.add("3.1.8");
        row12.add("环境保护工程");
        row12.add("万元");
        row12.add("0");
        row12.add("0");
        row12.add("0");
        List<String> row13 = new ArrayList<>();
        row13.add("3.1.9");
        row13.add("其他");
        row13.add("万元");
        row13.add("0");
        row13.add("0");
        row13.add("0");
        List<String> row14 = new ArrayList<>();
        row14.add("3.2");
        row14.add("按资金来源分");
        row14.add("万元");
        row14.add("0");
        row14.add("0");
        row14.add("0");
        List<String> row15 = new ArrayList<>();
        row15.add("3.2.1");
        row15.add("中央投资");
        row15.add("万元");
        row15.add("0");
        row15.add("0");
        row15.add("0");
        List<String> row16 = new ArrayList<>();
        row16.add("3.2.2");
        row16.add("省级投资");
        row16.add("万元");
        row16.add("0");
        row16.add("0");
        row16.add("0");
        List<String> row17 = new ArrayList<>();
        row17.add("3.2.3");
        row17.add("市县投资");
        row17.add("万元");
        row17.add("0");
        row17.add("0");
        row17.add("0");
        List<String> row18 = new ArrayList<>();
        row18.add("4");
        row18.add("到位资金");
        row18.add("万元");
        row18.add("0");
        row18.add("0");
        row18.add("0");
        List<String> row19 = new ArrayList<>();
        row19.add("4.1");
        row19.add("中央投资");
        row19.add("万元");
        row19.add("0");
        row19.add("0");
        row19.add("0");
        List<String> row20 = new ArrayList<>();
        row20.add("4.2");
        row20.add("省级投资");
        row20.add("万元");
        row20.add("0");
        row20.add("0");
        row20.add("0");
        List<String> row21 = new ArrayList<>();
        row21.add("4.3");
        row21.add("市县投资");
        row21.add("万元");
        row21.add("0");
        row21.add("0");
        row21.add("0");
        List<String> row22 = new ArrayList<>();
        row22.add("5");
        row22.add("已完成工程量");
        row22.add("/");
        row22.add("本月");
        row22.add("本年累计");
        row22.add("开工累计");
        List<String> row23 = new ArrayList<>();
        row23.add("5.1");
        row23.add("土石方明挖");
        row23.add("万立方米");
        row23.add("0");
        row23.add("0");
        row23.add("0");
        List<String> row24 = new ArrayList<>();
        row24.add("5.2");
        row24.add("土石方洞挖");
        row24.add("万立方米");
        row24.add("0");
        row24.add("0");
        row24.add("0");
        List<String> row25 = new ArrayList<>();
        row25.add("5.3");
        row25.add("土石方回填");
        row25.add("万立方米");
        row25.add("0");
        row25.add("0");
        row25.add("0");
        List<String> row26 = new ArrayList<>();
        row26.add("5.4");
        row26.add("砌石");
        row26.add("万立方米");
        row26.add("0");
        row26.add("0");
        row26.add("0");
        List<String> row27 = new ArrayList<>();
        row27.add("5.5");
        row27.add("混凝土");
        row27.add("万立方米");
        row27.add("0");
        row27.add("0");
        row27.add("0");
        List<String> row28 = new ArrayList<>();
        row28.add("5.5");
        row28.add("钢筋");
        row28.add("吨");
        row28.add("0");
        row28.add("0");
        row28.add("0");
        List<String> row29 = new ArrayList<>();
        row29.add("5.6");
        row29.add("灌浆");
        row29.add("米");
        row29.add("0");
        row29.add("0");
        row29.add("0");
        List<String> row30 = new ArrayList<>();
        row30.add("6");
        row30.add("劳动力投入");
        row30.add("/");
        row30.add("本月");
        row30.add("本年累计");
        row30.add("开工累计");
        List<String> row31 = new ArrayList<>();
        row31.add("6.1");
        row31.add("劳动力投入");
        row31.add("万工日");
        row31.add("0");
        row31.add("0");
        row31.add("0");
        monthlyReportExcelModel.add(row1);
        monthlyReportExcelModel.add(row2);
        monthlyReportExcelModel.add(row3);
        monthlyReportExcelModel.add(row4);
        monthlyReportExcelModel.add(row5);
        monthlyReportExcelModel.add(row6);
        monthlyReportExcelModel.add(row7);
        monthlyReportExcelModel.add(row8);
        monthlyReportExcelModel.add(row9);
        monthlyReportExcelModel.add(row10);
        monthlyReportExcelModel.add(row11);
        monthlyReportExcelModel.add(row12);
        monthlyReportExcelModel.add(row13);
        monthlyReportExcelModel.add(row14);
        monthlyReportExcelModel.add(row15);
        monthlyReportExcelModel.add(row16);
        monthlyReportExcelModel.add(row17);
        monthlyReportExcelModel.add(row18);
        monthlyReportExcelModel.add(row19);
        monthlyReportExcelModel.add(row20);
        monthlyReportExcelModel.add(row21);
        monthlyReportExcelModel.add(row22);
        monthlyReportExcelModel.add(row23);
        monthlyReportExcelModel.add(row24);
        monthlyReportExcelModel.add(row25);
        monthlyReportExcelModel.add(row26);
        monthlyReportExcelModel.add(row27);
        monthlyReportExcelModel.add(row28);
        monthlyReportExcelModel.add(row29);
        monthlyReportExcelModel.add(row30);
        monthlyReportExcelModel.add(row31);
        return monthlyReportExcelModel;
    }

    // 构建实际的月报表
    public static List<List<String>> buildMonthlyReportExcel(MonthlyReportExcelModel monthlyReportExcelModel) {
        MonthlyReportExcelCalcUtil thisUtil = new MonthlyReportExcelCalcUtil();
        List<List<String>> excelModel = new MonthlyReportExcelCalcUtil().initExcelModel();
        List<String> row1 = excelModel.get(0);
        row1.set(5, String.valueOf(monthlyReportExcelModel.getTotalInvestment()));  // 计划总投资
        List<String> row2 = excelModel.get(1);
        row2.set(4, String.valueOf(monthlyReportExcelModel.getThisYearPlanInvestment()));  // 本年计划投资

        List<String> row4 = excelModel.get(3); // 3.1
        row4.set(3, String.valueOf(thisUtil.calcInvestCompleteMonthTotal(monthlyReportExcelModel)));
        row4.set(4, String.valueOf(thisUtil.calcInvestCompleteYearTotal(monthlyReportExcelModel)));
        row4.set(5, String.valueOf(thisUtil.calcInvestCompleteSofarTotal(monthlyReportExcelModel)));
        List<String> row5 = excelModel.get(4);
        row5.set(3, String.valueOf(monthlyReportExcelModel.getCivilEngineering()));
        row5.set(4, String.valueOf(monthlyReportExcelModel.getYearCivilEngineering()));
        row5.set(5, String.valueOf(monthlyReportExcelModel.getSofarCivilEngineering()));
        List<String> row6 = excelModel.get(5);
        row6.set(3, String.valueOf(monthlyReportExcelModel.getMetalMechanism()));
        row6.set(4, String.valueOf(monthlyReportExcelModel.getYearMetalMechanism()));
        row6.set(5, String.valueOf(monthlyReportExcelModel.getSofarMetalMechanism()));
        List<String> row7 = excelModel.get(6);
        row7.set(3, String.valueOf(monthlyReportExcelModel.getElectromechanicalEquipment()));
        row7.set(4, String.valueOf(monthlyReportExcelModel.getYearElectromechanicalEquipment()));
        row7.set(5, String.valueOf(monthlyReportExcelModel.getSofarElectromechanicalEquipment()));
        List<String> row8 = excelModel.get(7);
        row8.set(3, String.valueOf(monthlyReportExcelModel.getTemporaryWork()));
        row8.set(4, String.valueOf(monthlyReportExcelModel.getYearTemporaryWork()));
        row8.set(5, String.valueOf(monthlyReportExcelModel.getSofarTemporaryWork()));
        List<String> row9 = excelModel.get(8);
        row9.set(3, String.valueOf(monthlyReportExcelModel.getIndependentCost()));
        row9.set(4, String.valueOf(monthlyReportExcelModel.getYearIndependentCost()));
        row9.set(5, String.valueOf(monthlyReportExcelModel.getSofarIndependentCost()));
        List<String> row10 = excelModel.get(9);
        row10.set(3, String.valueOf(monthlyReportExcelModel.getResettlementArrangement()));
        row10.set(4, String.valueOf(monthlyReportExcelModel.getYearResettlementArrangement()));
        row10.set(5, String.valueOf(monthlyReportExcelModel.getSofarResettlementArrangement()));
        List<String> row11 = excelModel.get(10);
        row11.set(3, String.valueOf(monthlyReportExcelModel.getWaterConservation()));
        row11.set(4, String.valueOf(monthlyReportExcelModel.getYearWaterConservation()));
        row11.set(5, String.valueOf(monthlyReportExcelModel.getSofarWaterConservation()));
        List<String> row12 = excelModel.get(11);
        row12.set(3, String.valueOf(monthlyReportExcelModel.getEnvironmentalProtection()));
        row12.set(4, String.valueOf(monthlyReportExcelModel.getYearEnvironmentalProtection()));
        row12.set(5, String.valueOf(monthlyReportExcelModel.getSofarEnvironmentalProtection()));
        List<String> row13 = excelModel.get(12);
        row13.set(3, String.valueOf(monthlyReportExcelModel.getOtherCost()));
        row13.set(4, String.valueOf(monthlyReportExcelModel.getYearOtherCost()));
        row13.set(5, String.valueOf(monthlyReportExcelModel.getSofarOtherCost()));

        List<String> row14 = excelModel.get(13); // 3.2
        row14.set(3, String.valueOf(thisUtil.calcFundSourceMonthTotal(monthlyReportExcelModel)));
        row14.set(4, String.valueOf(thisUtil.calcFundSourceYearTotal(monthlyReportExcelModel)));
        row14.set(5, String.valueOf(thisUtil.calcFundSourceSofarTotal(monthlyReportExcelModel)));
        List<String> row15 = excelModel.get(14);
        row15.set(3, String.valueOf(monthlyReportExcelModel.getSourceCentralInvestment()));
        row15.set(4, String.valueOf(monthlyReportExcelModel.getYearSourceCentralInvestment()));
        row15.set(5, String.valueOf(monthlyReportExcelModel.getSofarSourceCentralInvestment()));
        List<String> row16 = excelModel.get(15);
        row16.set(3, String.valueOf(monthlyReportExcelModel.getSourceProvincialInvestment()));
        row16.set(4, String.valueOf(monthlyReportExcelModel.getYearSourceProvincialInvestment()));
        row16.set(5, String.valueOf(monthlyReportExcelModel.getSofarSourceProvincialInvestment()));
        List<String> row17 = excelModel.get(16);
        row17.set(3, String.valueOf(monthlyReportExcelModel.getSourceLocalInvestment()));
        row17.set(4, String.valueOf(monthlyReportExcelModel.getYearSourceLocalInvestment()));
        row17.set(5, String.valueOf(monthlyReportExcelModel.getSofarSourceLocalInvestment()));

        List<String> row18 = excelModel.get(17); // 4
        row18.set(3, String.valueOf(thisUtil.calcFundAvailableMonthTotal(monthlyReportExcelModel)));
        row18.set(4, String.valueOf(thisUtil.calcFundAvailableYearTotal(monthlyReportExcelModel)));
        row18.set(5, String.valueOf(thisUtil.calcFundAvailableSofarTotal(monthlyReportExcelModel)));
        List<String> row19 = excelModel.get(18);
        row19.set(3, String.valueOf(monthlyReportExcelModel.getAvailableCentralInvestment()));
        row19.set(4, String.valueOf(monthlyReportExcelModel.getYearAvailableCentralInvestment()));
        row19.set(5, String.valueOf(monthlyReportExcelModel.getYearAvailableCentralInvestment()));
        List<String> row20 = excelModel.get(19);
        row20.set(3, String.valueOf(monthlyReportExcelModel.getAvailableProvincialInvestment()));
        row20.set(4, String.valueOf(monthlyReportExcelModel.getYearAvailableProvincialInvestment()));
        row20.set(5, String.valueOf(monthlyReportExcelModel.getSofarAvailableProvincialInvestment()));
        List<String> row21 = excelModel.get(20);
        row21.set(3, String.valueOf(monthlyReportExcelModel.getAvailableLocalInvestment()));
        row21.set(4, String.valueOf(monthlyReportExcelModel.getYearAvailableLocalInvestment()));
        row21.set(5, String.valueOf(monthlyReportExcelModel.getSofarAvailableLocalInvestment()));
        // 5
        List<String> row23 = excelModel.get(22);
        row23.set(3, String.valueOf(monthlyReportExcelModel.getOpenDug()));
        row23.set(4, String.valueOf(monthlyReportExcelModel.getYearOpenDug()));
        row23.set(5, String.valueOf(monthlyReportExcelModel.getSofarOpenDug()));
        List<String> row24 = excelModel.get(23);
        row24.set(3, String.valueOf(monthlyReportExcelModel.getHoleDug()));
        row24.set(4, String.valueOf(monthlyReportExcelModel.getYearHoleDug()));
        row24.set(5, String.valueOf(monthlyReportExcelModel.getSofarHoleDug()));
        List<String> row25 = excelModel.get(24);
        row25.set(3, String.valueOf(monthlyReportExcelModel.getBackfill()));
        row25.set(4, String.valueOf(monthlyReportExcelModel.getYearBackfill()));
        row25.set(5, String.valueOf(monthlyReportExcelModel.getSofarBackfill()));
        List<String> row26 = excelModel.get(25);
        row26.set(3, String.valueOf(monthlyReportExcelModel.getMasonry()));
        row26.set(4, String.valueOf(monthlyReportExcelModel.getYearMasonry()));
        row26.set(5, String.valueOf(monthlyReportExcelModel.getSofarMasonry()));
        List<String> row27 = excelModel.get(26);
        row27.set(3, String.valueOf(monthlyReportExcelModel.getConcrete()));
        row27.set(4, String.valueOf(monthlyReportExcelModel.getYearConcrete()));
        row27.set(5, String.valueOf(monthlyReportExcelModel.getSofarConcrete()));
        List<String> row28 = excelModel.get(27);
        row28.set(3, String.valueOf(monthlyReportExcelModel.getRebar()));
        row28.set(4, String.valueOf(monthlyReportExcelModel.getYearRebar()));
        row28.set(5, String.valueOf(monthlyReportExcelModel.getSofarRebar()));
        List<String> row29 = excelModel.get(28);
        row29.set(3, String.valueOf(monthlyReportExcelModel.getGrout()));
        row29.set(4, String.valueOf(monthlyReportExcelModel.getYearGrout()));
        row29.set(5, String.valueOf(monthlyReportExcelModel.getSofarGrout()));
        // 6
        List<String> row31 = excelModel.get(30);
        row31.set(3, String.valueOf(monthlyReportExcelModel.getLabourForce()));
        row31.set(4, String.valueOf(monthlyReportExcelModel.getYearLabourForce()));
        row31.set(5, String.valueOf(monthlyReportExcelModel.getSofarLabourForce()));
        return excelModel;
    }

    // 3.1.1-3.1.9部分月合计
    private BigDecimal calcInvestCompleteMonthTotal(MonthlyReportExcelModel monthlyReportExcelModel) {
        return monthlyReportExcelModel.getCivilEngineering()
                .add(monthlyReportExcelModel.getElectromechanicalEquipment()
                        .add(monthlyReportExcelModel.getMetalMechanism()
                                .add(monthlyReportExcelModel.getTemporaryWork()
                                        .add(monthlyReportExcelModel.getIndependentCost()
                                                .add(monthlyReportExcelModel.getResettlementArrangement()
                                                        .add(monthlyReportExcelModel.getWaterConservation()
                                                                .add(monthlyReportExcelModel.getEnvironmentalProtection()
                                                                        .add(monthlyReportExcelModel.getOtherCost()))))))));
    }
    // 3.1.1-3.1.9部分年合计
    private BigDecimal calcInvestCompleteYearTotal(MonthlyReportExcelModel monthlyReportExcelModel) {
        return monthlyReportExcelModel.getYearCivilEngineering()
                .add(monthlyReportExcelModel.getYearElectromechanicalEquipment()
                        .add(monthlyReportExcelModel.getYearMetalMechanism()
                                .add(monthlyReportExcelModel.getYearTemporaryWork()
                                        .add(monthlyReportExcelModel.getYearIndependentCost()
                                                .add(monthlyReportExcelModel.getYearResettlementArrangement()
                                                        .add(monthlyReportExcelModel.getYearWaterConservation()
                                                                .add(monthlyReportExcelModel.getYearEnvironmentalProtection()
                                                                        .add(monthlyReportExcelModel.getYearOtherCost()))))))));
    }
    // 3.1.1-3.1.9部分至今合计
    private BigDecimal calcInvestCompleteSofarTotal(MonthlyReportExcelModel monthlyReportExcelModel) {
        return monthlyReportExcelModel.getSofarCivilEngineering()
                .add(monthlyReportExcelModel.getSofarElectromechanicalEquipment()
                        .add(monthlyReportExcelModel.getSofarMetalMechanism()
                                .add(monthlyReportExcelModel.getSofarTemporaryWork()
                                        .add(monthlyReportExcelModel.getSofarIndependentCost()
                                                .add(monthlyReportExcelModel.getSofarResettlementArrangement()
                                                        .add(monthlyReportExcelModel.getSofarWaterConservation()
                                                                .add(monthlyReportExcelModel.getSofarEnvironmentalProtection()
                                                                        .add(monthlyReportExcelModel.getSofarOtherCost()))))))));
    }
    // 3.2.1-3.2.3部分月合计
    private BigDecimal calcFundSourceMonthTotal(MonthlyReportExcelModel monthlyReportExcelModel) {
        return monthlyReportExcelModel.getSourceCentralInvestment()
                .add(monthlyReportExcelModel.getSourceProvincialInvestment())
                .add(monthlyReportExcelModel.getSourceLocalInvestment());
    }
    // 3.2.1-3.2.3部分年合计
    private BigDecimal calcFundSourceYearTotal(MonthlyReportExcelModel monthlyReportExcelModel) {
        return monthlyReportExcelModel.getYearSourceCentralInvestment()
                .add(monthlyReportExcelModel.getYearSourceProvincialInvestment())
                .add(monthlyReportExcelModel.getYearSourceLocalInvestment());
    }
    // 3.2.1-3.2.3部分至今合计
    private BigDecimal calcFundSourceSofarTotal(MonthlyReportExcelModel monthlyReportExcelModel) {
        return monthlyReportExcelModel.getSofarSourceCentralInvestment()
                .add(monthlyReportExcelModel.getSofarSourceProvincialInvestment())
                .add(monthlyReportExcelModel.getSofarSourceLocalInvestment());
    }
    // 4.1-4.3部分月合计
    private BigDecimal calcFundAvailableMonthTotal(MonthlyReportExcelModel monthlyReportExcelModel) {
        return monthlyReportExcelModel.getAvailableCentralInvestment()
                .add(monthlyReportExcelModel.getAvailableProvincialInvestment()
                        .add(monthlyReportExcelModel.getAvailableLocalInvestment()));
    }
    // 4.1-4.3部分年合计
    private BigDecimal calcFundAvailableYearTotal(MonthlyReportExcelModel monthlyReportExcelModel) {
        return monthlyReportExcelModel.getYearAvailableCentralInvestment()
                .add(monthlyReportExcelModel.getYearAvailableProvincialInvestment()
                        .add(monthlyReportExcelModel.getYearAvailableLocalInvestment()));
    }
    // 4.1-4.3部分至今合计
    private BigDecimal calcFundAvailableSofarTotal(MonthlyReportExcelModel monthlyReportExcelModel) {
        return monthlyReportExcelModel.getSofarAvailableCentralInvestment()
                .add(monthlyReportExcelModel.getSofarAvailableProvincialInvestment()
                        .add(monthlyReportExcelModel.getSofarAvailableLocalInvestment()));
    }

}


