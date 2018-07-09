package com.gzzhsl.pcms.converter;

import com.gzzhsl.pcms.entity.BaseInfoImg;
import com.gzzhsl.pcms.entity.PreProgressImg;
import com.gzzhsl.pcms.vo.BaseInfoImgVO;
import com.gzzhsl.pcms.vo.PreProgressImgVO;
import org.springframework.beans.BeanUtils;

public class PreProgressImg2VO {
    public static PreProgressImgVO convert(PreProgressImg preProgressImg) {
        PreProgressImgVO preProgressImgVO = new PreProgressImgVO();
        BeanUtils.copyProperties(preProgressImg, preProgressImgVO);
        String oriAddr = preProgressImg.getImgAddr();
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
        preProgressImgVO.setThumbnailAddr(thumbnailAddr); // 缩略图
        preProgressImgVO.setImgAddr(preProgressImg.getPreProgressImgId()); // 实际上给的是文件ID
        return preProgressImgVO;
    }
}
