package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.model.ProjectStatus;
import com.gzzhsl.pcms.vo.ResultVO;
import java.util.List;

public interface ProjectStatusService {
    Integer save(ProjectStatus projectStatus);

    ResultVO updateProjectStatus(int id);

    List<ProjectStatus> findThisProjectStatus();
}
