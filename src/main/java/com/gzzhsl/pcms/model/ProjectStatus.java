package com.gzzhsl.pcms.model;

import java.util.Date;

public class ProjectStatus {
    private String projectStatusId;

    private Date createTime;

    private Date updateTime;

    private String baseInfoId;

    private Integer timeLineItemId;

    public String getProjectStatusId() {
        return projectStatusId;
    }

    public void setProjectStatusId(String projectStatusId) {
        this.projectStatusId = projectStatusId == null ? null : projectStatusId.trim();
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

    public String getBaseInfoId() {
        return baseInfoId;
    }

    public void setBaseInfoId(String baseInfoId) {
        this.baseInfoId = baseInfoId == null ? null : baseInfoId.trim();
    }

    public Integer getTimeLineItemId() {
        return timeLineItemId;
    }

    public void setTimeLineItemId(Integer timeLineItemId) {
        this.timeLineItemId = timeLineItemId;
    }
}