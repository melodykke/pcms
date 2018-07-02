package com.gzzhsl.pcms.converter;

import com.gzzhsl.pcms.entity.BaseInfoImg;
import com.gzzhsl.pcms.entity.ProjectMonthlyReportImg;
import com.gzzhsl.pcms.vo.BaseInfoImgVO;
import com.gzzhsl.pcms.vo.ProjectMonthlyReportImgVO;
import org.springframework.beans.BeanUtils;

public class BaseInfoImg2VO {
    public static BaseInfoImgVO convert(BaseInfoImg baseInfoImg) {
        BaseInfoImgVO baseInfoImgVO = new BaseInfoImgVO();
        BeanUtils.copyProperties(baseInfoImg, baseInfoImgVO);
        String oriAddr = baseInfoImg.getImgAddr();
        String extension = oriAddr.substring(oriAddr.lastIndexOf("."));
        String thumbnailAddr = "";
        if (".xlsx".equals(extension) || ".xls".equals(extension)) {
            thumbnailAddr = "img/excel.jpeg";
        } else if (".docx".equals(extension) || ".doc".equals(extension)) {
            thumbnailAddr = "img/word.jpg";
        } else if (".pdf".equals(extension)) {
            thumbnailAddr = "img/pdf.jpg";
        } else if (".jpg".equals(extension) || ".jpeg".equals(extension) || ".png".equals(extension)) {
            thumbnailAddr = "/files/"+(oriAddr.replace("\\", "/"));
        } else {
            thumbnailAddr = "img/default.png";
        }
        baseInfoImgVO.setThumbnailAddr(thumbnailAddr); // 缩略图
        baseInfoImgVO.setImgAddr(baseInfoImg.getBaseInfoImgId()); // 实际上给的是文件ID
        return baseInfoImgVO;
    }
}
