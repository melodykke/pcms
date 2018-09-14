package com.gzzhsl.pcms.mapper;

import com.gzzhsl.pcms.model.ProjectMonthlyReportImg;
import java.util.List;

public interface ProjectMonthlyReportImgMapper {
    int deleteByPrimaryKey(String projectMonthlyReportImgId);

    int insert(ProjectMonthlyReportImg record);

    ProjectMonthlyReportImg selectByPrimaryKey(String projectMonthlyReportImgId);

    List<ProjectMonthlyReportImg> selectAll();

    int updateByPrimaryKey(ProjectMonthlyReportImg record);
}