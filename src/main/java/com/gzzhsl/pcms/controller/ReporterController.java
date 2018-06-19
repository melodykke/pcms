package com.gzzhsl.pcms.controller;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reporter")
public class ReporterController {

    @GetMapping("/projectmonthlyreport")
    @RequiresRoles(value = {"reporter", "checker"}, logical = Logical.OR)
    public String projectMonthlyReport() {
        return "/project_monthly_report";
    }

    @GetMapping("/projectmonths")
    @RequiresRoles(value = {"reporter", "checker"}, logical = Logical.OR)
    public String projectMonths() {
        return "/project_months";
    }
}