package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.entity.BaseInfo;
import com.gzzhsl.pcms.repository.RoleRepository;
import com.gzzhsl.pcms.service.BaseInfoService;
import com.gzzhsl.pcms.service.SysService;
import com.gzzhsl.pcms.service.UserService;
import com.gzzhsl.pcms.shiro.bean.SysRole;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SysServiceImpl implements SysService {
    @Autowired
    private UserService userService;
    @Autowired
    private BaseInfoService baseInfoService;
    @Autowired
    private RoleRepository roleRepository;

    /**
     * 把所有工程的加外键指向userInfo 的 userid
     */
    @Override
    public void projectidToUserinfoFKprojectid() {
      /*  List<UserInfo> allUserInfos = userService.getAllUser();
        List<BaseInfo> allProjects = baseInfoService.getAllProject();

        for (UserInfo userInfo : allUserInfos) {
            for (BaseInfo project : allProjects) {
                if (userInfo.getName().equals(project.getPlantName())) {
                    userInfo.setBaseInfo(project);
                    userService.save(userInfo);
                }
            }
        }*/

    }

    @Override
    public void setAllUserRole() {
    /*    List<UserInfo> allUserInfos = userService.getAllUser();
        SysRole checker = roleRepository.findByRoleId("0169A85A-8E9D-47AZ-43A5-4B651137AC32");
        for (UserInfo userInfo : allUserInfos) {
            List<SysRole> sysRoles = new ArrayList<>();
            sysRoles.add(checker);
            userInfo.setSysRoleList(sysRoles);
            userService.save(userInfo);
        }*/
    }

}
