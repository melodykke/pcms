package com.gzzhsl.pcms.entity;

import com.gzzhsl.pcms.shiro.bean.UserInfo;

import java.math.BigDecimal;
import java.util.Date;

public class PlantProject {

    private String plantId;
    private BigDecimal storage; // 库容
    private String scale; // 规模
    private BigDecimal timeLimit; // 工期
    private BigDecimal centralInvestment; // 中央投资
    private BigDecimal provincialInvestment; // 省级投资
    private BigDecimal localInvestment; // 市县投资
    private BigDecimal totalInvestment; // 总投资
    private String overview; // 概况
    private String level; // 工程等别
    private BigDecimal catchmentArea; // 积水面积km2
    private String damType; // 坝型
    private BigDecimal maxDamHeight; // 最大坝高m
    private BigDecimal floodControlElevation; // 度汛高程
    private BigDecimal spillway; // 溢洪道m
    private BigDecimal irrigatedArea; // 灌溉面积
    private BigDecimal watersupply; // 供水量 万m3年
    private BigDecimal installedCapacity; // 装机容积（Kw）
    private BigDecimal areaCoverage; // 工程占地（亩）
    private String landReclamationPlan; // 土地复垦方案
    private String constructionLand; // 建设用地
    private int unitProjectAmount; // 单位工程数
    private String unitProjectOverview; // 单位工程概况
    private String projectTask; // 工程任务及主要建筑物
    private int branchProjectAmount; // 分部工程数
    private String branchProjectOverview; // 分部工程概况
    private int cellProjectAmount; // 单元工程数
    private String cellProjectOverview; // 单元工程概况
    private String mark; // 备注
    private String projectSource; // 项目来源
    private Region county;
    private BigDecimal utilizablCapacity; // 兴利库容
    private String supervisorBid; // 监理、施工招标情况
    private int hasSignedConstructionContract; // 是否签订枢纽工程施工承包合同（是/否）
    private int hasProjectCompleted; // 枢纽工程是否完工（是/否）
    private int hasAcceptCompletion; // 是否竣工验收（是/否）
    private BigDecimal ruralHumanWater; // 农村人饮（万人）
    private BigDecimal livestock; // 大牲畜（万头）
    private BigDecimal waterSupplyPopulation;
    private BigDecimal centralAccumulativePayment ; // 中央累计拨付（万元）
    private BigDecimal provincialAccumulativePayment; // 省级累计拨付（万元）
    private BigDecimal localAccumulativePayment; // 市县累计拨付（万元）
    private BigDecimal provincialLoan; // 省级配套融资贷款（万元）

    private UserInfo owner; // 上报人
    private Date createTime;
    private Date updateTime;


    public String getPlantId() {
        return plantId;
    }

    public void setPlantId(String plantId) {
        this.plantId = plantId;
    }

    public BigDecimal getStorage() {
        return storage;
    }

    public void setStorage(BigDecimal storage) {
        this.storage = storage;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public BigDecimal getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(BigDecimal timeLimit) {
        this.timeLimit = timeLimit;
    }

    public BigDecimal getCentralInvestment() {
        return centralInvestment;
    }

    public void setCentralInvestment(BigDecimal centralInvestment) {
        this.centralInvestment = centralInvestment;
    }

    public BigDecimal getProvincialInvestment() {
        return provincialInvestment;
    }

    public void setProvincialInvestment(BigDecimal provincialInvestment) {
        this.provincialInvestment = provincialInvestment;
    }

    public BigDecimal getLocalInvestment() {
        return localInvestment;
    }

    public void setLocalInvestment(BigDecimal localInvestment) {
        this.localInvestment = localInvestment;
    }

    public BigDecimal getTotalInvestment() {
        return totalInvestment;
    }

    public void setTotalInvestment(BigDecimal totalInvestment) {
        this.totalInvestment = totalInvestment;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public BigDecimal getCatchmentArea() {
        return catchmentArea;
    }

    public void setCatchmentArea(BigDecimal catchmentArea) {
        this.catchmentArea = catchmentArea;
    }

    public String getDamType() {
        return damType;
    }

    public void setDamType(String damType) {
        this.damType = damType;
    }

    public BigDecimal getMaxDamHeight() {
        return maxDamHeight;
    }

    public void setMaxDamHeight(BigDecimal maxDamHeight) {
        this.maxDamHeight = maxDamHeight;
    }

    public BigDecimal getFloodControlElevation() {
        return floodControlElevation;
    }

    public void setFloodControlElevation(BigDecimal floodControlElevation) {
        this.floodControlElevation = floodControlElevation;
    }

    public BigDecimal getSpillway() {
        return spillway;
    }

    public void setSpillway(BigDecimal spillway) {
        this.spillway = spillway;
    }

    public BigDecimal getIrrigatedArea() {
        return irrigatedArea;
    }

    public void setIrrigatedArea(BigDecimal irrigatedArea) {
        this.irrigatedArea = irrigatedArea;
    }

    public BigDecimal getWatersupply() {
        return watersupply;
    }

    public void setWatersupply(BigDecimal watersupply) {
        this.watersupply = watersupply;
    }

    public BigDecimal getInstalledCapacity() {
        return installedCapacity;
    }

    public void setInstalledCapacity(BigDecimal installedCapacity) {
        this.installedCapacity = installedCapacity;
    }

    public BigDecimal getAreaCoverage() {
        return areaCoverage;
    }

    public void setAreaCoverage(BigDecimal areaCoverage) {
        this.areaCoverage = areaCoverage;
    }

    public String getLandReclamationPlan() {
        return landReclamationPlan;
    }

    public void setLandReclamationPlan(String landReclamationPlan) {
        this.landReclamationPlan = landReclamationPlan;
    }

    public String getConstructionLand() {
        return constructionLand;
    }

    public void setConstructionLand(String constructionLand) {
        this.constructionLand = constructionLand;
    }

    public int getUnitProjectAmount() {
        return unitProjectAmount;
    }

    public void setUnitProjectAmount(int unitProjectAmount) {
        this.unitProjectAmount = unitProjectAmount;
    }

    public String getUnitProjectOverview() {
        return unitProjectOverview;
    }

    public void setUnitProjectOverview(String unitProjectOverview) {
        this.unitProjectOverview = unitProjectOverview;
    }

    public String getProjectTask() {
        return projectTask;
    }

    public void setProjectTask(String projectTask) {
        this.projectTask = projectTask;
    }

    public int getBranchProjectAmount() {
        return branchProjectAmount;
    }

    public void setBranchProjectAmount(int branchProjectAmount) {
        this.branchProjectAmount = branchProjectAmount;
    }

    public String getBranchProjectOverview() {
        return branchProjectOverview;
    }

    public void setBranchProjectOverview(String branchProjectOverview) {
        this.branchProjectOverview = branchProjectOverview;
    }

    public int getCellProjectAmount() {
        return cellProjectAmount;
    }

    public void setCellProjectAmount(int cellProjectAmount) {
        this.cellProjectAmount = cellProjectAmount;
    }

    public String getCellProjectOverview() {
        return cellProjectOverview;
    }

    public void setCellProjectOverview(String cellProjectOverview) {
        this.cellProjectOverview = cellProjectOverview;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getProjectSource() {
        return projectSource;
    }

    public void setProjectSource(String projectSource) {
        this.projectSource = projectSource;
    }

    public Region getCounty() {
        return county;
    }

    public void setCounty(Region county) {
        this.county = county;
    }

    public BigDecimal getUtilizablCapacity() {
        return utilizablCapacity;
    }

    public void setUtilizablCapacity(BigDecimal utilizablCapacity) {
        this.utilizablCapacity = utilizablCapacity;
    }

    public String getSupervisorBid() {
        return supervisorBid;
    }

    public void setSupervisorBid(String supervisorBid) {
        this.supervisorBid = supervisorBid;
    }

    public int getHasSignedConstructionContract() {
        return hasSignedConstructionContract;
    }

    public void setHasSignedConstructionContract(int hasSignedConstructionContract) {
        this.hasSignedConstructionContract = hasSignedConstructionContract;
    }

    public int getHasProjectCompleted() {
        return hasProjectCompleted;
    }

    public void setHasProjectCompleted(int hasProjectCompleted) {
        this.hasProjectCompleted = hasProjectCompleted;
    }

    public int getHasAcceptCompletion() {
        return hasAcceptCompletion;
    }

    public void setHasAcceptCompletion(int hasAcceptCompletion) {
        this.hasAcceptCompletion = hasAcceptCompletion;
    }

    public BigDecimal getRuralHumanWater() {
        return ruralHumanWater;
    }

    public void setRuralHumanWater(BigDecimal ruralHumanWater) {
        this.ruralHumanWater = ruralHumanWater;
    }

    public BigDecimal getLivestock() {
        return livestock;
    }

    public void setLivestock(BigDecimal livestock) {
        this.livestock = livestock;
    }

    public BigDecimal getWaterSupplyPopulation() {
        return waterSupplyPopulation;
    }

    public void setWaterSupplyPopulation(BigDecimal waterSupplyPopulation) {
        this.waterSupplyPopulation = waterSupplyPopulation;
    }

    public BigDecimal getCentralAccumulativePayment() {
        return centralAccumulativePayment;
    }

    public void setCentralAccumulativePayment(BigDecimal centralAccumulativePayment) {
        this.centralAccumulativePayment = centralAccumulativePayment;
    }

    public BigDecimal getProvincialAccumulativePayment() {
        return provincialAccumulativePayment;
    }

    public void setProvincialAccumulativePayment(BigDecimal provincialAccumulativePayment) {
        this.provincialAccumulativePayment = provincialAccumulativePayment;
    }

    public BigDecimal getLocalAccumulativePayment() {
        return localAccumulativePayment;
    }

    public void setLocalAccumulativePayment(BigDecimal localAccumulativePayment) {
        this.localAccumulativePayment = localAccumulativePayment;
    }

    public BigDecimal getProvincialLoan() {
        return provincialLoan;
    }

    public void setProvincialLoan(BigDecimal provincialLoan) {
        this.provincialLoan = provincialLoan;
    }

    public UserInfo getOwner() {
        return owner;
    }

    public void setOwner(UserInfo owner) {
        this.owner = owner;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
