package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.entity.ProjectStatus;
import com.gzzhsl.pcms.vo.ResultVO;

public interface ProjectStatusService {
    ProjectStatus save(ProjectStatus projectStatus);
    ResultVO updateProjectStatus(int id);
    ResultVO getProjectStatus();
}
