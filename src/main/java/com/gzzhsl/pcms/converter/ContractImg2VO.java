package com.gzzhsl.pcms.converter;

import com.gzzhsl.pcms.model.ContractImg;
import com.gzzhsl.pcms.vo.ContractImgVO;
import org.springframework.beans.BeanUtils;

public class ContractImg2VO {
    public static ContractImgVO convert(ContractImg contractImg) {
        ContractImgVO contractImgVO = new ContractImgVO();
        BeanUtils.copyProperties(contractImg, contractImgVO);
        String oriAddr = contractImg.getImgAddr();
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
        contractImgVO.setThumbnailAddr(thumbnailAddr); // 缩略图
        contractImgVO.setImgAddr(contractImg.getContractImgId()); // 实际上给的是文件ID
        return contractImgVO;
    }
}
