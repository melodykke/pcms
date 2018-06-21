package com.gzzhsl.pcms.converter;

import com.gzzhsl.pcms.entity.ProjectMonthlyReportImg;
import com.gzzhsl.pcms.vo.ProjectMonthlyReportImgVO;
import org.springframework.beans.BeanUtils;

public class ProjectMonthlyReportImg2VO {
    public static ProjectMonthlyReportImgVO convert(ProjectMonthlyReportImg projectMonthlyReportImg) {
        ProjectMonthlyReportImgVO projectMonthlyReportImgVO = new ProjectMonthlyReportImgVO();
        BeanUtils.copyProperties(projectMonthlyReportImg, projectMonthlyReportImgVO);
        String oriAddr = projectMonthlyReportImg.getImgAddr();
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
        projectMonthlyReportImgVO.setThumbnailAddr(thumbnailAddr); // 缩略图

        projectMonthlyReportImgVO.setImgAddr(projectMonthlyReportImg.getProjectMonthlyReportImgId()); // 实际上给的是文件ID
        return projectMonthlyReportImgVO;
    }
}
