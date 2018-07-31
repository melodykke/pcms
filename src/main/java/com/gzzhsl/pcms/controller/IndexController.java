package com.gzzhsl.pcms.controller;

import com.gzzhsl.pcms.service.BaseInfoService;
import com.gzzhsl.pcms.util.ResultUtil;
import com.gzzhsl.pcms.vo.ResultVO;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static javafx.scene.input.KeyCode.R;

@Controller
@RequestMapping("/index")
public class IndexController {
    @Autowired
    private BaseInfoService baseInfoService;

    @RequestMapping("getallbaseinfo")
    @ResponseBody
    @RequiresRoles(value = "manager")
    public ResultVO getAllBaseInfo() {
        return ResultUtil.success(baseInfoService.getAllProject());
    }

    @GetMapping("/toprojectstatus")
    public String toProjectStatus() {
        return "project_status";
    }
}
