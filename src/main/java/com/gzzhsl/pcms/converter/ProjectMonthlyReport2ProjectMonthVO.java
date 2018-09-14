package com.gzzhsl.pcms.converter;

import com.gzzhsl.pcms.model.ProjectMonthlyReport;
import com.gzzhsl.pcms.model.ProjectMonthlyReportImg;
import com.gzzhsl.pcms.vo.ProjectMonthVO;
import org.springframework.beans.BeanUtils;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class ProjectMonthlyReport2ProjectMonthVO {
    public static ProjectMonthVO convert(ProjectMonthlyReport projectMonthlyReport) {
        Boolean hasRepresentiveImg = false; // 在一个月报的所有上传的文件中，是否有第一号代表图片。
        ProjectMonthVO projectMonthVO = new ProjectMonthVO();
        BeanUtils.copyProperties(projectMonthlyReport, projectMonthVO);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(projectMonthlyReport.getSubmitDate());
        projectMonthVO.setMonth(String.valueOf(calendar.get(Calendar.MONTH)+1));
        projectMonthVO.setYear(String.valueOf(calendar.get(Calendar.YEAR)));
        List<ProjectMonthlyReportImg> projectMonthlyReportImgList = projectMonthlyReport.getProjectMonthlyReportImgs();
        // 上面取到的不一定是图片，还包括其他一切非结构文档 所以要在前端显示月报缩略图，就必须对该工程月报下的文档格式进行判断
        // 符合JPG、 PNG的图片才能显示
        String[] acceptableExtention = {".jpg", ".png", ".jpeg", ".gif"};
        if (projectMonthlyReportImgList != null && projectMonthlyReportImgList.size() > 0) {
            for (ProjectMonthlyReportImg projectMonthlyReportImg : projectMonthlyReportImgList) {
                String fileRelativePath = projectMonthlyReportImg.getImgAddr();
                String extension = fileRelativePath.substring(fileRelativePath.lastIndexOf("."));
                for (int i = 0; i < acceptableExtention.length; i++) {
                    if (acceptableExtention[i].equals(extension)) {
                        projectMonthVO.setThumbnailUrl("/files/"+(projectMonthlyReportImg.getImgAddr()).replace("\\", "/"));
                        hasRepresentiveImg = true;
                        break;
                    }
                }
                if (hasRepresentiveImg == true) break;
            }
            if (hasRepresentiveImg == false) projectMonthVO.setThumbnailUrl("img/default.png");
        } else {
            if (hasRepresentiveImg == false) projectMonthVO.setThumbnailUrl("img/default.png");
        }
        return projectMonthVO;
    }
}
