package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.entity.BaseInfo;
import com.gzzhsl.pcms.entity.PlantProject;
import com.gzzhsl.pcms.entity.Project;
import com.gzzhsl.pcms.repository.BaseInfoRepository;
import com.gzzhsl.pcms.repository.PlantProjectRepository;
import com.gzzhsl.pcms.repository.ProjectRepository;
import com.gzzhsl.pcms.service.BaseInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
public class BaseInfoServiceImpl implements BaseInfoService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private PlantProjectRepository plantProjectRepository;
    @Autowired
    private BaseInfoRepository baseInfoRepository;

    @Override
    public Boolean saveAll() {
        List<Project> projects = projectRepository.findAll();
        List<PlantProject> plantProjects = plantProjectRepository.findAll();
        for (Project project : projects) {
            for (PlantProject plantProject : plantProjects) {
                if (project.getProjectId().toUpperCase().equals(plantProject.getPlantId().toUpperCase())) {
                    BaseInfo baseInfo = new BaseInfo();
                    BeanUtils.copyProperties(plantProject, baseInfo);
                    baseInfo.setCounty(plantProject.getCountyName());
                    BeanUtils.copyProperties(project, baseInfo);
                    baseInfoRepository.save(baseInfo);
                }
            }
        }
        return true;
    }
}
