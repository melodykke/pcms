package com.gzzhsl.pcms.controller;

import com.gzzhsl.pcms.entity.Region;
import com.gzzhsl.pcms.service.RegionService;
import com.gzzhsl.pcms.util.ResultUtil;
import com.gzzhsl.pcms.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.transform.Result;
import java.util.List;

@Controller
@RequestMapping("/manage")
@Slf4j
@RequiresRoles("manager")
public class ManageController {

    @Autowired
    private RegionService regionService;

    @GetMapping("/toreservoirdic")
    public String toReservoirDic() {
        return "all_reservoir";
    }
    @GetMapping("/toreservoirindex")
    public String toReservoirIndex(String baseInfoId, Model model) {
        model.addAttribute("baseInfoId", baseInfoId);
        return "reservoir_index";
    }

    @GetMapping("/getregionchildren")
    @ResponseBody
    public ResultVO getRegionChildren(@RequestParam(required = false, name = "regionId", defaultValue = "1") int regionId) {
        List<Region> regionList = regionService.getChildrenRegion(regionId);
        if (regionList != null || regionList.size() != 0) {
            return ResultUtil.success(regionList);
        } else {
            return ResultUtil.failed();
        }
    }
}
