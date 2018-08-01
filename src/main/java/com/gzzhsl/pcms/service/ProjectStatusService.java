package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.entity.ProjectStatus;
import com.gzzhsl.pcms.vo.ResultVO;

import java.util.List;

public interface ProjectStatusService {
    ProjectStatus save(ProjectStatus projectStatus);
    ResultVO updateProjectStatus(int id);
    List<ProjectStatus> getProjectStatus();
}
