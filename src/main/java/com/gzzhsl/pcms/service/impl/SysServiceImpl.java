package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.entity.Project;
import com.gzzhsl.pcms.service.ProjectService;
import com.gzzhsl.pcms.service.UserService;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SysServiceImpl {
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;

    public void userinfoidToprojectFKuserid() {
        List<UserInfo> allUserInfos = userService.getAllUser();
        List<Project> allProjects = projectService.getAllProject();

        for (UserInfo userInfo : allUserInfos) {
            for (Project project : allProjects) {
                if (userInfo.getName().equals(project.getPlantName())) {
                    project.setUserInfo(userInfo);
                    projectService.save(project);
                }
            }
        }
    }


}
