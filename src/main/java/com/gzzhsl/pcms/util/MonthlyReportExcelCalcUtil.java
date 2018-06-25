package com.gzzhsl.pcms.util;

import com.gzzhsl.pcms.entity.HistoryMonthlyReportExcelStatistics;
import com.gzzhsl.pcms.entity.ProjectMonthlyReport;
import com.gzzhsl.pcms.vo.MonthlyReportExcelModel;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MonthlyReportExcelCalcUtil {
    public static MonthlyReportExcelModel getModelWithMonthParams(MonthlyReportExcelModel monthlyReportExcelModel, ProjectMonthlyReport projectMonthlyReport){
        BeanUtils.copyProperties(projectMonthlyReport, monthlyReportExcelModel);
        return monthlyReportExcelModel;
    }

    public static MonthlyReportExcelModel getModelWithYearParams(MonthlyReportExcelModel monthlyReportExcelModel,
                                                                  List<ProjectMonthlyReport> yearProjectMonthlyReports){
        BigDecimal yearCivilEngineering = new BigDecimal(0);
        BigDecimal yearElectromechanicalEquipment = new BigDecimal(0);
        BigDecimal yearMetalMechanism = new BigDecimal(0);
        BigDecimal yearIndependentCost = new BigDecimal(0);
        BigDecimal yearTemporaryWork = new BigDecimal(0);
        BigDecimal yearReserveFunds = new BigDecimal(0);
        BigDecimal yearResettlementArrangement = new BigDecimal(0);
        BigDecimal yearEnvironmentalProtection = new BigDecimal(0);
        BigDecimal yearWaterConservation = new BigDecimal(0);
        BigDecimal yearOtherCost = new BigDecimal(0);
        for (ProjectMonthlyReport eachReport : yearProjectMonthlyReports) {
            yearCivilEngineering = yearCivilEngineering.add(eachReport.getCivilEngineering());
            yearElectromechanicalEquipment = yearElectromechanicalEquipment.add(eachReport.getElectromechanicalEquipment());
            yearMetalMechanism = yearMetalMechanism.add(eachReport.getMetalMechanism());
            yearIndependentCost = yearIndependentCost.add(eachReport.getIndependentCost());
            yearTemporaryWork = yearTemporaryWork.add(eachReport.getTemporaryWork());
            yearReserveFunds = yearReserveFunds.add(eachReport.getReserveFunds());
            yearResettlementArrangement = yearResettlementArrangement.add(eachReport.getResettlementArrangement());
            yearEnvironmentalProtection = yearEnvironmentalProtection.add(eachReport.getEnvironmentalProtection());
            yearWaterConservation = yearWaterConservation.add(eachReport.getWaterConservation());
            yearOtherCost = yearOtherCost.add(eachReport.getOtherCost());
        }
        monthlyReportExcelModel.setYearCivilEngineering(yearCivilEngineering);
        monthlyReportExcelModel.setYearElectromechanicalEquipment(yearElectromechanicalEquipment);
        monthlyReportExcelModel.setYearMetalMechanism(yearMetalMechanism);
        monthlyReportExcelModel.setYearIndependentCost(yearIndependentCost);
        monthlyReportExcelModel.setYearTemporaryWork(yearTemporaryWork);
        monthlyReportExcelModel.setYearReserveFunds(yearReserveFunds);
        monthlyReportExcelModel.setYearResettlementArrangement(yearResettlementArrangement);
        monthlyReportExcelModel.setYearEnvironmentalProtection(yearEnvironmentalProtection);
        monthlyReportExcelModel.setYearWaterConservation(yearWaterConservation);
        monthlyReportExcelModel.setYearOtherCost(yearOtherCost);

        return monthlyReportExcelModel;
    }





    public static MonthlyReportExcelModel getModelWithSofarParams(MonthlyReportExcelModel monthlyReportExcelModel,
                                                                         List<ProjectMonthlyReport> sofarProjectMonthlyReports,
                                                                         HistoryMonthlyReportExcelStatistics historyMonthlyReportExcelStatistics){
        BigDecimal sofarCivilEngineering = new BigDecimal(0);
        BigDecimal sofarElectromechanicalEquipment = new BigDecimal(0);
        BigDecimal sofarMetalMechanism = new BigDecimal(0);
        BigDecimal sofarIndependentCost = new BigDecimal(0);
        BigDecimal sofarTemporaryWork = new BigDecimal(0);
        BigDecimal sofarReserveFunds = new BigDecimal(0);
        BigDecimal sofarResettlementArrangement = new BigDecimal(0);
        BigDecimal sofarEnvironmentalProtection = new BigDecimal(0);
        BigDecimal sofarWaterConservation = new BigDecimal(0);
        BigDecimal sofarOtherCost = new BigDecimal(0);
        for (ProjectMonthlyReport eachReport : sofarProjectMonthlyReports) {
            sofarCivilEngineering = sofarCivilEngineering.add(eachReport.getCivilEngineering());
            sofarElectromechanicalEquipment = sofarElectromechanicalEquipment.add(eachReport.getElectromechanicalEquipment());
            sofarMetalMechanism = sofarMetalMechanism.add(eachReport.getMetalMechanism());
            sofarIndependentCost = sofarIndependentCost.add(eachReport.getIndependentCost());
            sofarTemporaryWork = sofarTemporaryWork.add(eachReport.getTemporaryWork());
            sofarReserveFunds = sofarReserveFunds.add(eachReport.getReserveFunds());
            sofarResettlementArrangement = sofarResettlementArrangement.add(eachReport.getResettlementArrangement());
            sofarEnvironmentalProtection = sofarEnvironmentalProtection.add(eachReport.getEnvironmentalProtection());
            sofarWaterConservation = sofarWaterConservation.add(eachReport.getWaterConservation());
            sofarOtherCost = sofarOtherCost.add(eachReport.getOtherCost());
        }
        if (historyMonthlyReportExcelStatistics != null) {
            monthlyReportExcelModel.setSofarCivilEngineering(sofarCivilEngineering.add(historyMonthlyReportExcelStatistics.getHistoryCivilEngineering()));
            monthlyReportExcelModel.setSofarElectromechanicalEquipment(sofarElectromechanicalEquipment.add(historyMonthlyReportExcelStatistics.getHistoryElectromechanicalEquipment()));
            monthlyReportExcelModel.setSofarMetalMechanism(sofarMetalMechanism.add(historyMonthlyReportExcelStatistics.getHistoryMetalMechanism()));
            monthlyReportExcelModel.setSofarIndependentCost(sofarIndependentCost.add(historyMonthlyReportExcelStatistics.getHistoryIndependentCost()));
            monthlyReportExcelModel.setSofarTemporaryWork(sofarTemporaryWork.add(historyMonthlyReportExcelStatistics.getHistoryTemporaryWork()));
            monthlyReportExcelModel.setSofarReserveFunds(sofarReserveFunds.add(historyMonthlyReportExcelStatistics.getHistoryReserveFunds()));
            monthlyReportExcelModel.setSofarResettlementArrangement(sofarResettlementArrangement.add(historyMonthlyReportExcelStatistics.getHistoryResettlementArrangement()));
            monthlyReportExcelModel.setSofarEnvironmentalProtection(sofarEnvironmentalProtection.add(historyMonthlyReportExcelStatistics.getHistoryEnvironmentalProtection()));
            monthlyReportExcelModel.setSofarWaterConservation(sofarWaterConservation.add(historyMonthlyReportExcelStatistics.getHistoryWaterConservation()));
            monthlyReportExcelModel.setSofarOtherCost(sofarOtherCost.add(historyMonthlyReportExcelStatistics.getHistoryOtherCost()));
        } else {
            monthlyReportExcelModel.setSofarCivilEngineering(sofarCivilEngineering);
            monthlyReportExcelModel.setSofarElectromechanicalEquipment(sofarElectromechanicalEquipment);
            monthlyReportExcelModel.setSofarMetalMechanism(sofarMetalMechanism);
            monthlyReportExcelModel.setSofarIndependentCost(sofarIndependentCost);
            monthlyReportExcelModel.setSofarTemporaryWork(sofarTemporaryWork);
            monthlyReportExcelModel.setSofarReserveFunds(sofarReserveFunds);
            monthlyReportExcelModel.setSofarResettlementArrangement(sofarResettlementArrangement);
            monthlyReportExcelModel.setSofarEnvironmentalProtection(sofarEnvironmentalProtection);
            monthlyReportExcelModel.setSofarWaterConservation(sofarWaterConservation);
            monthlyReportExcelModel.setSofarOtherCost(sofarOtherCost);
        }
        return monthlyReportExcelModel;
    }



    public static List<List<String>> buildMonthlyReportExcel(MonthlyReportExcelModel monthlyReportExcelModel) {
        MonthlyReportExcelCalcUtil thisUtil = new MonthlyReportExcelCalcUtil();
        List<List<String>> excelModel = new MonthlyReportExcelCalcUtil().initExcelModel();
        List<String> row1 = excelModel.get(0);
        row1.add(String.valueOf(thisUtil.calcConstructionPartMonth(monthlyReportExcelModel)));
        row1.add(String.valueOf(thisUtil.calcConstructionPartYear(monthlyReportExcelModel)));
        row1.add(String.valueOf(thisUtil.calcConstructionPartSofar(monthlyReportExcelModel)));
        List<String> row2 = excelModel.get(1);
        row2.add(String.valueOf(monthlyReportExcelModel.getCivilEngineering()));
        row2.add(String.valueOf(monthlyReportExcelModel.getYearCivilEngineering()));
        row2.add(String.valueOf(monthlyReportExcelModel.getSofarCivilEngineering()));
        List<String> row3 = excelModel.get(2);
        row3.add(String.valueOf(monthlyReportExcelModel.getElectromechanicalEquipment()));
        row3.add(String.valueOf(monthlyReportExcelModel.getYearElectromechanicalEquipment()));
        row3.add(String.valueOf(monthlyReportExcelModel.getSofarElectromechanicalEquipment()));
        List<String> row4 = excelModel.get(3);
        row4.add(String.valueOf(monthlyReportExcelModel.getMetalMechanism()));
        row4.add(String.valueOf(monthlyReportExcelModel.getYearMetalMechanism()));
        row4.add(String.valueOf(monthlyReportExcelModel.getSofarMetalMechanism()));
        List<String> row5 = excelModel.get(4);
        row5.add(String.valueOf(monthlyReportExcelModel.getTemporaryWork()));
        row5.add(String.valueOf(monthlyReportExcelModel.getYearTemporaryWork()));
        row5.add(String.valueOf(monthlyReportExcelModel.getSofarTemporaryWork()));
        List<String> row6 = excelModel.get(5);
        row6.add(String.valueOf(monthlyReportExcelModel.getIndependentCost()));
        row6.add(String.valueOf(monthlyReportExcelModel.getYearIndependentCost()));
        row6.add(String.valueOf(monthlyReportExcelModel.getSofarIndependentCost()));
        List<String> row7 = excelModel.get(6);
        row7.add(String.valueOf(thisUtil.calc1_5monthTotal(monthlyReportExcelModel)));
        row7.add(String.valueOf(thisUtil.calc1_5yearTotal(monthlyReportExcelModel)));
        row7.add(String.valueOf(thisUtil.calc1_5SofarTotal(monthlyReportExcelModel)));
        List<String> row8 = excelModel.get(7);
        row8.add(String.valueOf(monthlyReportExcelModel.getReserveFunds()));
        row8.add(String.valueOf(monthlyReportExcelModel.getYearReserveFunds()));
        row8.add(String.valueOf(monthlyReportExcelModel.getSofarReserveFunds()));
        List<String> row9 = excelModel.get(8);
        row9.add(String.valueOf(thisUtil.calcImmigrationAndEnvironmentMonth(monthlyReportExcelModel)));
        row9.add(String.valueOf(thisUtil.calcImmigrationAndEnvironmentYear(monthlyReportExcelModel)));
        row9.add(String.valueOf(thisUtil.calcImmigrationAndEnvironmentSofar(monthlyReportExcelModel)));
        List<String> row10 = excelModel.get(9);
        row10.add(String.valueOf(monthlyReportExcelModel.getResettlementArrangement()));
        row10.add(String.valueOf(monthlyReportExcelModel.getYearResettlementArrangement()));
        row10.add(String.valueOf(monthlyReportExcelModel.getSofarResettlementArrangement()));
        List<String> row11 = excelModel.get(10);
        row11.add(String.valueOf(thisUtil.calcWaterConservationInvestmentMonth(monthlyReportExcelModel)));
        row11.add(String.valueOf(thisUtil.calcWaterConservationInvestmentYear(monthlyReportExcelModel)));
        row11.add(String.valueOf(thisUtil.calcWaterConservationInvestmentSofar(monthlyReportExcelModel)));
        List<String> row12 = excelModel.get(11);
        row12.add(String.valueOf(monthlyReportExcelModel.getWaterConservation()));
        row12.add(String.valueOf(monthlyReportExcelModel.getYearWaterConservation()));
        row12.add(String.valueOf(monthlyReportExcelModel.getSofarWaterConservation()));
        List<String> row13 = excelModel.get(12);
        row13.add(String.valueOf(monthlyReportExcelModel.getEnvironmentalProtection()));
        row13.add(String.valueOf(monthlyReportExcelModel.getYearEnvironmentalProtection()));
        row13.add(String.valueOf(monthlyReportExcelModel.getSofarEnvironmentalProtection()));
        List<String> row14 = excelModel.get(13);
        row14.add(String.valueOf(monthlyReportExcelModel.getOtherCost()));
        row14.add(String.valueOf(monthlyReportExcelModel.getYearOtherCost()));
        row14.add(String.valueOf(monthlyReportExcelModel.getSofarOtherCost()));
        List<String> row15 = excelModel.get(14);
        row15.add(String.valueOf(thisUtil.calcStaticTotalInvestmentMonth(monthlyReportExcelModel)));
        row15.add(String.valueOf(thisUtil.calcStaticTotalInvestmentYear(monthlyReportExcelModel)));
        row15.add(String.valueOf(thisUtil.calcStaticTotalInvestmentSofar(monthlyReportExcelModel)));

        return excelModel;
    }

    private List<List<String>> initExcelModel(){
        List<List<String>> monthlyReportExcelModel = new ArrayList<>();
        List<String> row1 = new ArrayList<>();
        row1.add("Ⅰ");
        row1.add("工程部分投资");
        row1.add("0");
        row1.add("0");
        row1.add("0");
        row1.add("0");
        List<String> row2 = new ArrayList<>();
        row2.add("");
        row2.add("第一部分：建筑工程");
        row2.add("0");
        row2.add("0");
        row2.add("0");
        row2.add("0");
        List<String> row3 = new ArrayList<>();
        row3.add("");
        row3.add("第二部分：机电设备及安装工程");
        row3.add("0");
        row3.add("0");
        row3.add("0");
        row3.add("0");
        List<String> row4 = new ArrayList<>();
        row4.add("");
        row4.add("第三部分：金属机构设备及安装工程");
        row4.add("0");
        row4.add("0");
        row4.add("0");
        row4.add("0");
        List<String> row5 = new ArrayList<>();
        row5.add("");
        row5.add("第四部分：施工临时工程");
        row5.add("0");
        row5.add("0");
        row5.add("0");
        row5.add("0");
        List<String> row6 = new ArrayList<>();
        row6.add("");
        row6.add("第五部分：独立费用");
        row6.add("0");
        row6.add("0");
        row6.add("0");
        row6.add("0");
        List<String> row7 = new ArrayList<>();
        row7.add("");
        row7.add("一至五部分合计");
        row7.add("0");
        row7.add("0");
        row7.add("0");
        row7.add("0");
        List<String> row8 = new ArrayList<>();
        row8.add("");
        row8.add("基本预备费");
        row8.add("0");
        row8.add("0");
        row8.add("0");
        row8.add("0");
        List<String> row9 = new ArrayList<>();
        row9.add("Ⅱ");
        row9.add("移民环境投资");
        row9.add("0");
        row9.add("0");
        row9.add("0");
        row9.add("0");
        List<String> row10 = new ArrayList<>();
        row10.add("一");
        row10.add("建设征地移民安置补偿费");
        row10.add("0");
        row10.add("0");
        row10.add("0");
        row10.add("0");
        List<String> row11 = new ArrayList<>();
        row11.add("二");
        row11.add("水保环保投资");
        row11.add("0");
        row11.add("0");
        row11.add("0");
        row11.add("0");
        List<String> row12 = new ArrayList<>();
        row12.add("1");
        row12.add("水土保持工程投资");
        row12.add("0");
        row12.add("0");
        row12.add("0");
        row12.add("0");
        List<String> row13 = new ArrayList<>();
        row13.add("2");
        row13.add("环境保护工程投资");
        row13.add("0");
        row13.add("0");
        row13.add("0");
        row13.add("0");
        List<String> row14 = new ArrayList<>();
        row14.add("三");
        row14.add("其他");
        row14.add("0");
        row14.add("0");
        row14.add("0");
        row14.add("0");
        List<String> row15 = new ArrayList<>();
        row15.add("Ⅲ");
        row15.add("静态总投资（Ⅰ+Ⅱ）");
        row15.add("0");
        row15.add("0");
        row15.add("0");
        row15.add("0");
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
        return monthlyReportExcelModel;
    }
    // 工程部分投资（本月）
    private BigDecimal calcConstructionPartMonth(MonthlyReportExcelModel monthlyReportExcelModel) {
        return new MonthlyReportExcelCalcUtil().calc1_5monthTotal(monthlyReportExcelModel).add(monthlyReportExcelModel.getReserveFunds());
    }
    // 工程部分投资（本年）
    private BigDecimal calcConstructionPartYear(MonthlyReportExcelModel monthlyReportExcelModel) {
        return new MonthlyReportExcelCalcUtil().calc1_5yearTotal(monthlyReportExcelModel).add(monthlyReportExcelModel.getYearReserveFunds());
    }
    // 工程部分投资（至今）
    private BigDecimal calcConstructionPartSofar(MonthlyReportExcelModel monthlyReportExcelModel) {
        return new MonthlyReportExcelCalcUtil(). calc1_5SofarTotal(monthlyReportExcelModel).add(monthlyReportExcelModel.getSofarReserveFunds());
    }
    // 1-5部分月合计
    private BigDecimal calc1_5monthTotal(MonthlyReportExcelModel monthlyReportExcelModel) {
        return monthlyReportExcelModel.getCivilEngineering().add(monthlyReportExcelModel.getElectromechanicalEquipment().add(monthlyReportExcelModel.getMetalMechanism().add(monthlyReportExcelModel.getTemporaryWork().add(monthlyReportExcelModel.getIndependentCost()))));
    }
    // 1-5部分年合计
    private BigDecimal calc1_5yearTotal(MonthlyReportExcelModel monthlyReportExcelModel) {
        return monthlyReportExcelModel.getYearCivilEngineering().add(monthlyReportExcelModel.getYearElectromechanicalEquipment().add(monthlyReportExcelModel.getYearMetalMechanism().add(monthlyReportExcelModel.getYearTemporaryWork().add(monthlyReportExcelModel.getYearIndependentCost()))));
    }
    // 1-5部分至今合计
    private BigDecimal calc1_5SofarTotal(MonthlyReportExcelModel monthlyReportExcelModel) {
        return monthlyReportExcelModel.getSofarCivilEngineering().add(monthlyReportExcelModel.getSofarElectromechanicalEquipment().add(monthlyReportExcelModel.getSofarMetalMechanism().add(monthlyReportExcelModel.getSofarTemporaryWork().add(monthlyReportExcelModel.getSofarIndependentCost()))));
    }

    // 移民环境投资（本月）
    private BigDecimal calcImmigrationAndEnvironmentMonth(MonthlyReportExcelModel monthlyReportExcelModel) {
        return new MonthlyReportExcelCalcUtil().calcWaterConservationInvestmentMonth(monthlyReportExcelModel).add(monthlyReportExcelModel.getResettlementArrangement());
    }
    // 移民环境投资（本年）
    private BigDecimal calcImmigrationAndEnvironmentYear(MonthlyReportExcelModel monthlyReportExcelModel) {
        return new MonthlyReportExcelCalcUtil().calcWaterConservationInvestmentYear(monthlyReportExcelModel).add(monthlyReportExcelModel.getYearResettlementArrangement());
    }
    // 移民环境投资（至今）
    private BigDecimal calcImmigrationAndEnvironmentSofar(MonthlyReportExcelModel monthlyReportExcelModel) {
        return new MonthlyReportExcelCalcUtil().calcWaterConservationInvestmentSofar(monthlyReportExcelModel).add(monthlyReportExcelModel.getSofarResettlementArrangement());
    }
    // 水保环保投资（本月）
    private BigDecimal calcWaterConservationInvestmentMonth(MonthlyReportExcelModel monthlyReportExcelModel) {
        return monthlyReportExcelModel.getWaterConservation().add(monthlyReportExcelModel.getEnvironmentalProtection());
    }
    // 水保环保投资（本年）
    private BigDecimal calcWaterConservationInvestmentYear(MonthlyReportExcelModel monthlyReportExcelModel) {
        return monthlyReportExcelModel.getYearWaterConservation().add(monthlyReportExcelModel.getYearEnvironmentalProtection());
    }
    // 水保环保投资（至今）
    private BigDecimal calcWaterConservationInvestmentSofar(MonthlyReportExcelModel monthlyReportExcelModel) {
        return monthlyReportExcelModel.getSofarWaterConservation().add(monthlyReportExcelModel.getSofarEnvironmentalProtection());
    }

    // 静态总投资（本月）
    private BigDecimal calcStaticTotalInvestmentMonth(MonthlyReportExcelModel monthlyReportExcelModel) {
        return new MonthlyReportExcelCalcUtil().calcConstructionPartMonth(monthlyReportExcelModel).add(new MonthlyReportExcelCalcUtil().calcImmigrationAndEnvironmentMonth(monthlyReportExcelModel));
    }
    // 静态总投资（本年）
    private BigDecimal calcStaticTotalInvestmentYear(MonthlyReportExcelModel monthlyReportExcelModel) {
        return new MonthlyReportExcelCalcUtil().calcConstructionPartYear(monthlyReportExcelModel).add(new MonthlyReportExcelCalcUtil().calcImmigrationAndEnvironmentYear(monthlyReportExcelModel));
    }
    // 静态总投资（至今）
    private BigDecimal calcStaticTotalInvestmentSofar(MonthlyReportExcelModel monthlyReportExcelModel) {
        return new MonthlyReportExcelCalcUtil().calcConstructionPartSofar(monthlyReportExcelModel).add(new MonthlyReportExcelCalcUtil().calcImmigrationAndEnvironmentSofar(monthlyReportExcelModel));
    }

}


