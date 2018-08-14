package com.gzzhsl.pcms.controller;

import com.gzzhsl.pcms.entity.Region;
import com.gzzhsl.pcms.service.RegionService;
import com.gzzhsl.pcms.util.ResultUtil;
import com.gzzhsl.pcms.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/region")
public class RegionController {
    @Autowired
    private RegionService regionService;

    @GetMapping("/getallrootregion")
    @ResponseBody
    public ResultVO getAllRootRegion() {
        List<Region> regionList = regionService.getAllRootRegion();
        return ResultUtil.success(regionList);
    }

}
