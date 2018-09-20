package com.gzzhsl.pcms.mapper;

import com.gzzhsl.pcms.model.ProjectMonthlyReport;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProjectMonthlyReportMapper {
    int deleteByPrimaryKey(String projectMonthlyReportId);

    int insert(ProjectMonthlyReport record);

    ProjectMonthlyReport selectByPrimaryKey(String projectMonthlyReportId);

    List<ProjectMonthlyReport> selectAll();

    int updateByPrimaryKey(ProjectMonthlyReport record);

    List<ProjectMonthlyReport> findMonthlyReportsByProjectIdAndPeriod(@Param("baseInfoId") String baseInfoId,
                                                                      @Param("startDate") String startDate,
                                                                      @Param("endDate") String endDate);

    ProjectMonthlyReport findWithImgById(@Param("projectMonthlyReportId") String projectMonthlyReportId);

    List<ProjectMonthlyReport> findByBaseInfoId(@Param("baseInfoId") String baseInfoId);

    int approveMonthlyReport(@Param("projectMonthlyReportId") String projectMonthlyReportId, @Param("state") byte state);
}