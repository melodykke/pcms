package com.gzzhsl.pcms.model;

import java.util.Date;

public class PreProgressImg {
    private String preProgressImgId;

    private Date createTime;

    private String imgAddr;

    private String imgDesc;

    private String preProgressId;

    public String getPreProgressImgId() {
        return preProgressImgId;
    }

    public void setPreProgressImgId(String preProgressImgId) {
        this.preProgressImgId = preProgressImgId == null ? null : preProgressImgId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getImgAddr() {
        return imgAddr;
    }

    public void setImgAddr(String imgAddr) {
        this.imgAddr = imgAddr == null ? null : imgAddr.trim();
    }

    public String getImgDesc() {
        return imgDesc;
    }

    public void setImgDesc(String imgDesc) {
        this.imgDesc = imgDesc == null ? null : imgDesc.trim();
    }

    public String getPreProgressId() {
        return preProgressId;
    }

    public void setPreProgressId(String preProgressId) {
        this.preProgressId = preProgressId == null ? null : preProgressId.trim();
    }
}