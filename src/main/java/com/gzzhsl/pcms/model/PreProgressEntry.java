package com.gzzhsl.pcms.model;

import java.util.Date;

public class PreProgressEntry {
    private String preProgressEntryId;

    private Date approvalDate;

    private String approvalStatus;

    private String compileUnit;

    private Date createTime;

    private String planProject;

    private String referenceNumber;

    private Integer serialNumber;

    private String preProgressId;

    private String approvalUnit;

    public String getPreProgressEntryId() {
        return preProgressEntryId;
    }

    public void setPreProgressEntryId(String preProgressEntryId) {
        this.preProgressEntryId = preProgressEntryId == null ? null : preProgressEntryId.trim();
    }

    public Date getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus == null ? null : approvalStatus.trim();
    }

    public String getCompileUnit() {
        return compileUnit;
    }

    public void setCompileUnit(String compileUnit) {
        this.compileUnit = compileUnit == null ? null : compileUnit.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPlanProject() {
        return planProject;
    }

    public void setPlanProject(String planProject) {
        this.planProject = planProject == null ? null : planProject.trim();
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber == null ? null : referenceNumber.trim();
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getPreProgressId() {
        return preProgressId;
    }

    public void setPreProgressId(String preProgressId) {
        this.preProgressId = preProgressId == null ? null : preProgressId.trim();
    }

    public String getApprovalUnit() {
        return approvalUnit;
    }

    public void setApprovalUnit(String approvalUnit) {
        this.approvalUnit = approvalUnit == null ? null : approvalUnit.trim();
    }
}