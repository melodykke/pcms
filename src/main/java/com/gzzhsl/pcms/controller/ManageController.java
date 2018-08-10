package com.gzzhsl.pcms.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manage")
@Slf4j
@RequiresRoles("manager")
public class ManageController {
    @GetMapping("/toreservoirdic")
    public String toReservoirDic() {
        return "all_reservoir";
    }
    @GetMapping("/toreservoirindex")
    public String toReservoirIndex(String baseInfoId, Model model) {
        model.addAttribute("baseInfoId", baseInfoId);
        return "reservoir_index";
    }
}
