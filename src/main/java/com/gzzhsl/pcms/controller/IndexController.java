package com.gzzhsl.pcms.controller;

import com.gzzhsl.pcms.converter.BaseInfo2ManagerIndexVO;
import com.gzzhsl.pcms.entity.BaseInfo;
import com.gzzhsl.pcms.entity.ProjectStatus;
import com.gzzhsl.pcms.entity.TimeLineItem;
import com.gzzhsl.pcms.exception.SysException;
import com.gzzhsl.pcms.service.BaseInfoService;
import com.gzzhsl.pcms.service.ProjectStatusService;
import com.gzzhsl.pcms.service.TimeLineItemService;
import com.gzzhsl.pcms.service.UserService;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import com.gzzhsl.pcms.util.ResultUtil;
import com.gzzhsl.pcms.vo.BaseInfoManagerIndexVO;
import com.gzzhsl.pcms.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static javafx.scene.input.KeyCode.R;

@Controller
@Slf4j
@RequestMapping("/index")
public class IndexController {
    @Autowired
    private BaseInfoService baseInfoService;
    @Autowired
    private ProjectStatusService projectStatusService;

    @RequestMapping("getallbaseinfo")
    @ResponseBody
    @RequiresRoles(value = "manager")
    public ResultVO getAllBaseInfo() {
        List<BaseInfo> baseInfos = baseInfoService.getAllProject();
        List<BaseInfoManagerIndexVO> baseInfoManagerIndexVOs = baseInfos.stream().map(e -> BaseInfo2ManagerIndexVO.convert(e)).collect(Collectors.toList());
        return ResultUtil.success(baseInfoManagerIndexVOs);
    }

    @GetMapping("/toprojectstatus")
    public String toProjectStatus() {
        return "project_status";
    }

    @GetMapping("/updateprojectstatus")
    @ResponseBody
    @RequiresRoles(value = "checker")
    public ResultVO updateProjectStatus(int id) {
        return projectStatusService.updateProjectStatus(id);
    }

    @GetMapping("/getprojectstatus")
    @ResponseBody
    public ResultVO getProjectStatus() {
        List<ProjectStatus> projectStatusList = projectStatusService.getProjectStatus();
        if (projectStatusList == null || projectStatusList.size() == 0) {
            return ResultUtil.failed();
        } else {
            return ResultUtil.success(projectStatusList);
        }
    }
}
