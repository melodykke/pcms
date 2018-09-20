package com.gzzhsl.pcms.model;

import java.util.Date;

public class TimeLineItem {
    private Integer timeLineItemId;

    private Date createTime;

    private String msg;

    private String type;

    private Date updateTime;

    public Integer getTimeLineItemId() {
        return timeLineItemId;
    }

    public void setTimeLineItemId(Integer timeLineItemId) {
        this.timeLineItemId = timeLineItemId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}