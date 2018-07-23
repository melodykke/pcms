package com.gzzhsl.pcms.converter;

import com.gzzhsl.pcms.entity.AnnualInvestmentImg;
import com.gzzhsl.pcms.entity.Tender;
import com.gzzhsl.pcms.entity.TenderImg;
import com.gzzhsl.pcms.vo.AnnualInvestmentImgVO;
import com.gzzhsl.pcms.vo.TenderImgVO;
import org.springframework.beans.BeanUtils;

public class TenderImg2VO {
    public static TenderImgVO convert(TenderImg tenderImg) {
        TenderImgVO tenderImgVO = new TenderImgVO();
        BeanUtils.copyProperties(tenderImg, tenderImgVO);
        String oriAddr = tenderImg.getImgAddr();
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
        tenderImgVO.setThumbnailAddr(thumbnailAddr); // 缩略图
        tenderImgVO.setImgAddr(tenderImg.getTenderImgId()); // 实际上给的是文件ID
        return tenderImgVO;
    }
}
