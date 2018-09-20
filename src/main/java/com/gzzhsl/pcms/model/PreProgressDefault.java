package com.gzzhsl.pcms.model;

public class PreProgressDefault {
    private Integer preProgressDefaulId;

    private String planProject;

    private Integer serialNumber;

    public Integer getPreProgressDefaulId() {
        return preProgressDefaulId;
    }

    public void setPreProgressDefaulId(Integer preProgressDefaulId) {
        this.preProgressDefaulId = preProgressDefaulId;
    }

    public String getPlanProject() {
        return planProject;
    }

    public void setPlanProject(String planProject) {
        this.planProject = planProject == null ? null : planProject.trim();
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }
}