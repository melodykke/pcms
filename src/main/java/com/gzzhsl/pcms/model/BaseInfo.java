package com.gzzhsl.pcms.model;

import java.math.BigDecimal;
import java.util.Date;

public class BaseInfo {
    private String baseInfoId;

    private BigDecimal areaCoverage;

    private Integer branchProjectAmount;

    private String branchProjectOverview;

    private BigDecimal catchmentArea;

    private Integer cellProjectAmount;

    private String cellProjectOverview;

    private BigDecimal centralAccumulativePayment;

    private BigDecimal centralInvestment;

    private String constructionLand;

    private String county;

    private Date createTime;

    private String damType;

    private BigDecimal floodControlElevation;

    private String hasAcceptCompletion;

    private String hasProjectCompleted;

    private String hasSignedConstructionContract;

    private BigDecimal installedCapacity;

    private BigDecimal irrigatedArea;

    private String landReclamationPlan;

    private String latitude;

    private String legalPersonId;

    private String legalPersonName;

    private String legalRepresentativeId;

    private String legalRepresentativeName;

    private String level;

    private BigDecimal livestock;

    private BigDecimal localAccumulativePayment;

    private BigDecimal localInvestment;

    private String location;

    private String longitude;

    private BigDecimal maxDamHeight;

    private String overview;

    private String owner;

    private String parentId;

    private String plantName;

    private String projectSource;

    private String projectTask;

    private String projectType;

    private BigDecimal provincialAccumulativePayment;

    private BigDecimal provincialInvestment;

    private BigDecimal provincialLoan;

    private String remark;

    private BigDecimal ruralHumanWater;

    private String scale;

    private BigDecimal spillway;

    private BigDecimal storage;

    private String supervisorBid;

    private BigDecimal timeLimit;

    private BigDecimal totalInvestment;

    private Integer unitProjectAmount;

    private String unitProjectOverview;

    private Date updateTime;

    private BigDecimal utilizablCapacity;

    private BigDecimal waterSupplyPopulation;

    private BigDecimal watersupply;

    private Byte state;

    private Integer repeatTimes;

    private Integer regionId;

    public String getBaseInfoId() {
        return baseInfoId;
    }

    public void setBaseInfoId(String baseInfoId) {
        this.baseInfoId = baseInfoId == null ? null : baseInfoId.trim();
    }

    public BigDecimal getAreaCoverage() {
        return areaCoverage;
    }

    public void setAreaCoverage(BigDecimal areaCoverage) {
        this.areaCoverage = areaCoverage;
    }

    public Integer getBranchProjectAmount() {
        return branchProjectAmount;
    }

    public void setBranchProjectAmount(Integer branchProjectAmount) {
        this.branchProjectAmount = branchProjectAmount;
    }

    public String getBranchProjectOverview() {
        return branchProjectOverview;
    }

    public void setBranchProjectOverview(String branchProjectOverview) {
        this.branchProjectOverview = branchProjectOverview == null ? null : branchProjectOverview.trim();
    }

    public BigDecimal getCatchmentArea() {
        return catchmentArea;
    }

    public void setCatchmentArea(BigDecimal catchmentArea) {
        this.catchmentArea = catchmentArea;
    }

    public Integer getCellProjectAmount() {
        return cellProjectAmount;
    }

    public void setCellProjectAmount(Integer cellProjectAmount) {
        this.cellProjectAmount = cellProjectAmount;
    }

    public String getCellProjectOverview() {
        return cellProjectOverview;
    }

    public void setCellProjectOverview(String cellProjectOverview) {
        this.cellProjectOverview = cellProjectOverview == null ? null : cellProjectOverview.trim();
    }

    public BigDecimal getCentralAccumulativePayment() {
        return centralAccumulativePayment;
    }

    public void setCentralAccumulativePayment(BigDecimal centralAccumulativePayment) {
        this.centralAccumulativePayment = centralAccumulativePayment;
    }

    public BigDecimal getCentralInvestment() {
        return centralInvestment;
    }

    public void setCentralInvestment(BigDecimal centralInvestment) {
        this.centralInvestment = centralInvestment;
    }

    public String getConstructionLand() {
        return constructionLand;
    }

    public void setConstructionLand(String constructionLand) {
        this.constructionLand = constructionLand == null ? null : constructionLand.trim();
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county == null ? null : county.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDamType() {
        return damType;
    }

    public void setDamType(String damType) {
        this.damType = damType == null ? null : damType.trim();
    }

    public BigDecimal getFloodControlElevation() {
        return floodControlElevation;
    }

    public void setFloodControlElevation(BigDecimal floodControlElevation) {
        this.floodControlElevation = floodControlElevation;
    }

    public String getHasAcceptCompletion() {
        return hasAcceptCompletion;
    }

    public void setHasAcceptCompletion(String hasAcceptCompletion) {
        this.hasAcceptCompletion = hasAcceptCompletion == null ? null : hasAcceptCompletion.trim();
    }

    public String getHasProjectCompleted() {
        return hasProjectCompleted;
    }

    public void setHasProjectCompleted(String hasProjectCompleted) {
        this.hasProjectCompleted = hasProjectCompleted == null ? null : hasProjectCompleted.trim();
    }

    public String getHasSignedConstructionContract() {
        return hasSignedConstructionContract;
    }

    public void setHasSignedConstructionContract(String hasSignedConstructionContract) {
        this.hasSignedConstructionContract = hasSignedConstructionContract == null ? null : hasSignedConstructionContract.trim();
    }

    public BigDecimal getInstalledCapacity() {
        return installedCapacity;
    }

    public void setInstalledCapacity(BigDecimal installedCapacity) {
        this.installedCapacity = installedCapacity;
    }

    public BigDecimal getIrrigatedArea() {
        return irrigatedArea;
    }

    public void setIrrigatedArea(BigDecimal irrigatedArea) {
        this.irrigatedArea = irrigatedArea;
    }

    public String getLandReclamationPlan() {
        return landReclamationPlan;
    }

    public void setLandReclamationPlan(String landReclamationPlan) {
        this.landReclamationPlan = landReclamationPlan == null ? null : landReclamationPlan.trim();
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude == null ? null : latitude.trim();
    }

    public String getLegalPersonId() {
        return legalPersonId;
    }

    public void setLegalPersonId(String legalPersonId) {
        this.legalPersonId = legalPersonId == null ? null : legalPersonId.trim();
    }

    public String getLegalPersonName() {
        return legalPersonName;
    }

    public void setLegalPersonName(String legalPersonName) {
        this.legalPersonName = legalPersonName == null ? null : legalPersonName.trim();
    }

    public String getLegalRepresentativeId() {
        return legalRepresentativeId;
    }

    public void setLegalRepresentativeId(String legalRepresentativeId) {
        this.legalRepresentativeId = legalRepresentativeId == null ? null : legalRepresentativeId.trim();
    }

    public String getLegalRepresentativeName() {
        return legalRepresentativeName;
    }

    public void setLegalRepresentativeName(String legalRepresentativeName) {
        this.legalRepresentativeName = legalRepresentativeName == null ? null : legalRepresentativeName.trim();
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

    public BigDecimal getLivestock() {
        return livestock;
    }

    public void setLivestock(BigDecimal livestock) {
        this.livestock = livestock;
    }

    public BigDecimal getLocalAccumulativePayment() {
        return localAccumulativePayment;
    }

    public void setLocalAccumulativePayment(BigDecimal localAccumulativePayment) {
        this.localAccumulativePayment = localAccumulativePayment;
    }

    public BigDecimal getLocalInvestment() {
        return localInvestment;
    }

    public void setLocalInvestment(BigDecimal localInvestment) {
        this.localInvestment = localInvestment;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude == null ? null : longitude.trim();
    }

    public BigDecimal getMaxDamHeight() {
        return maxDamHeight;
    }

    public void setMaxDamHeight(BigDecimal maxDamHeight) {
        this.maxDamHeight = maxDamHeight;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview == null ? null : overview.trim();
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner == null ? null : owner.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName == null ? null : plantName.trim();
    }

    public String getProjectSource() {
        return projectSource;
    }

    public void setProjectSource(String projectSource) {
        this.projectSource = projectSource == null ? null : projectSource.trim();
    }

    public String getProjectTask() {
        return projectTask;
    }

    public void setProjectTask(String projectTask) {
        this.projectTask = projectTask == null ? null : projectTask.trim();
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType == null ? null : projectType.trim();
    }

    public BigDecimal getProvincialAccumulativePayment() {
        return provincialAccumulativePayment;
    }

    public void setProvincialAccumulativePayment(BigDecimal provincialAccumulativePayment) {
        this.provincialAccumulativePayment = provincialAccumulativePayment;
    }

    public BigDecimal getProvincialInvestment() {
        return provincialInvestment;
    }

    public void setProvincialInvestment(BigDecimal provincialInvestment) {
        this.provincialInvestment = provincialInvestment;
    }

    public BigDecimal getProvincialLoan() {
        return provincialLoan;
    }

    public void setProvincialLoan(BigDecimal provincialLoan) {
        this.provincialLoan = provincialLoan;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public BigDecimal getRuralHumanWater() {
        return ruralHumanWater;
    }

    public void setRuralHumanWater(BigDecimal ruralHumanWater) {
        this.ruralHumanWater = ruralHumanWater;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale == null ? null : scale.trim();
    }

    public BigDecimal getSpillway() {
        return spillway;
    }

    public void setSpillway(BigDecimal spillway) {
        this.spillway = spillway;
    }

    public BigDecimal getStorage() {
        return storage;
    }

    public void setStorage(BigDecimal storage) {
        this.storage = storage;
    }

    public String getSupervisorBid() {
        return supervisorBid;
    }

    public void setSupervisorBid(String supervisorBid) {
        this.supervisorBid = supervisorBid == null ? null : supervisorBid.trim();
    }

    public BigDecimal getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(BigDecimal timeLimit) {
        this.timeLimit = timeLimit;
    }

    public BigDecimal getTotalInvestment() {
        return totalInvestment;
    }

    public void setTotalInvestment(BigDecimal totalInvestment) {
        this.totalInvestment = totalInvestment;
    }

    public Integer getUnitProjectAmount() {
        return unitProjectAmount;
    }

    public void setUnitProjectAmount(Integer unitProjectAmount) {
        this.unitProjectAmount = unitProjectAmount;
    }

    public String getUnitProjectOverview() {
        return unitProjectOverview;
    }

    public void setUnitProjectOverview(String unitProjectOverview) {
        this.unitProjectOverview = unitProjectOverview == null ? null : unitProjectOverview.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public BigDecimal getUtilizablCapacity() {
        return utilizablCapacity;
    }

    public void setUtilizablCapacity(BigDecimal utilizablCapacity) {
        this.utilizablCapacity = utilizablCapacity;
    }

    public BigDecimal getWaterSupplyPopulation() {
        return waterSupplyPopulation;
    }

    public void setWaterSupplyPopulation(BigDecimal waterSupplyPopulation) {
        this.waterSupplyPopulation = waterSupplyPopulation;
    }

    public BigDecimal getWatersupply() {
        return watersupply;
    }

    public void setWatersupply(BigDecimal watersupply) {
        this.watersupply = watersupply;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Integer getRepeatTimes() {
        return repeatTimes;
    }

    public void setRepeatTimes(Integer repeatTimes) {
        this.repeatTimes = repeatTimes;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }
}