package com.gzzhsl.pcms.mapper;

import com.gzzhsl.pcms.model.ProjectStatus;
import java.util.List;

public interface ProjectStatusMapper {
    int deleteByPrimaryKey(String projectStatusId);

    int insert(ProjectStatus record);

    ProjectStatus selectByPrimaryKey(String projectStatusId);

    List<ProjectStatus> selectAll();

    int updateByPrimaryKey(ProjectStatus record);

    List<ProjectStatus> findByBaseInfoId(String baseInfoId);
}