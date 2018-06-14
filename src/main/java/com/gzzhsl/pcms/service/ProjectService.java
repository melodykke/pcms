package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.entity.Project;

import java.util.List;

public interface ProjectService {
    List<Project> getAllProject();
    Project save(Project project);
}
