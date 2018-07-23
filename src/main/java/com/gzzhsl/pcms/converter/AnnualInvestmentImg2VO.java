package com.gzzhsl.pcms.converter;

import com.gzzhsl.pcms.entity.AnnualInvestmentImg;
import com.gzzhsl.pcms.entity.ContractImg;
import com.gzzhsl.pcms.vo.AnnualInvestmentImgVO;
import com.gzzhsl.pcms.vo.ContractImgVO;
import org.springframework.beans.BeanUtils;

public class AnnualInvestmentImg2VO {
    public static AnnualInvestmentImgVO convert(AnnualInvestmentImg annualInvestmentImg) {
        AnnualInvestmentImgVO annualInvestmentImgVO = new AnnualInvestmentImgVO();
        BeanUtils.copyProperties(annualInvestmentImg, annualInvestmentImgVO);
        String oriAddr = annualInvestmentImg.getImgAddr();
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
        annualInvestmentImgVO.setThumbnailAddr(thumbnailAddr); // 缩略图
        annualInvestmentImgVO.setImgAddr(annualInvestmentImg.getAnnualInvestmentImgId()); // 实际上给的是文件ID
        return annualInvestmentImgVO;
    }
}
