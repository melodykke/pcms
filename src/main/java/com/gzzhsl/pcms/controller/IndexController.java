package com.gzzhsl.pcms.controller;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gzzhsl.pcms.converter.BaseInfo2ManagerIndexVO;
import com.gzzhsl.pcms.entity.BaseInfo;
import com.gzzhsl.pcms.entity.ProjectStatus;
import com.gzzhsl.pcms.enums.RedisKeyEnum;
import com.gzzhsl.pcms.service.BaseInfoService;
import com.gzzhsl.pcms.service.ProjectStatusService;
import com.gzzhsl.pcms.util.ResultUtil;
import com.gzzhsl.pcms.vo.BaseInfoManagerIndexVO;
import com.gzzhsl.pcms.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@Slf4j
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private BaseInfoService baseInfoService;
    @Autowired
    private ProjectStatusService projectStatusService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("getallbaseinfo")
    @ResponseBody
    @RequiresRoles(value = "manager")
    public ResultVO getAllBaseInfo() {

        List<BaseInfoManagerIndexVO> baseInfoManagerIndexVOs = null;
        if (!stringRedisTemplate.hasKey(RedisKeyEnum.ALLBASEINFO.getKey())) {
            List<BaseInfo> baseInfos = baseInfoService.getAllProject();
            baseInfoManagerIndexVOs = baseInfos.stream().map(e -> BaseInfo2ManagerIndexVO.convert(e)).collect(Collectors.toList());
        } else {
            ObjectMapper objectMapper = new ObjectMapper();
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, BaseInfoManagerIndexVO.class);
            String jsonString = stringRedisTemplate.opsForValue().get(RedisKeyEnum.ALLBASEINFO.getKey());
            try {
                baseInfoManagerIndexVOs = objectMapper.readValue(jsonString, javaType);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
