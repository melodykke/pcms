package com.gzzhsl.pcms.controller;

import com.gzzhsl.pcms.enums.SysEnum;
import com.gzzhsl.pcms.exception.SysException;
import com.gzzhsl.pcms.model.*;
import com.gzzhsl.pcms.service.*;
import com.gzzhsl.pcms.util.MonthlyReportExcelCalcUtil;
import com.gzzhsl.pcms.util.ResultUtil;
import com.gzzhsl.pcms.vo.CardVO;
import com.gzzhsl.pcms.vo.MonthlyReportExcelModel;
import com.gzzhsl.pcms.vo.ResultVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/reporter")
public class ReporterController {
    @Autowired
    private UserService userService;
    @Autowired
    private BaseInfoService baseInfoService;
    @Autowired
    private ProjectMonthlyReportService projectMonthlyReportService;
    @Autowired
    private MonthlyReportExcelService monthlyReportExcelService;
    @Autowired
    private TimeLineItemService timeLineItemService;

    @GetMapping("/projectmonthlyreport")
    @RequiresRoles(value = {"reporter", "checker"}, logical = Logical.OR)
    public String projectMonthlyReport() {
        return "project_monthly_report";
    }

    @GetMapping("/projectmonths")
    @RequiresRoles(value = {"reporter", "checker"}, logical = Logical.OR)
    public String projectMonths() {
        return "project_months";
    }

    @GetMapping("/getmonthlyreportposthistory")
    @ResponseBody
    public ResultVO getMonthlyReportPostHistory(String currentDate, String projectMonthlyReportId, HttpServletRequest request, HttpServletResponse response) {
        // 获取当前用户工程，看是否该工程有截止到2018年1月之前的历史数据
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        UserInfo thisUser = userService.findByUsername(userInfo.getUsername());
        BaseInfo thisProject = baseInfoService.findBaseInfoById(thisUser.getBaseInfoId());
        Date yearEndDate = new Date(); // 当前时间
        String historyPointTime = "2000-01-01 00:00:00";
        String yearEndTime = currentDate+"-28 23:59:59"; // 查询时间范围的截止日期应为当前
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(yearEndDate);
        String yearStartTime = String.valueOf(calendar.get(Calendar.YEAR))+"-01-01 00:00:00"; // 当前时间的年度头一天头一秒
        List<ProjectMonthlyReport> yearProjectMonthlyReports = projectMonthlyReportService.
                findMonthlyReportsByProjectIdAndPeriod(thisProject.getBaseInfoId(), yearStartTime, yearEndTime); // 查询截止目前 本年的统计情况;
        List<ProjectMonthlyReport> sofarProjectMonthlyReports = projectMonthlyReportService.
                findMonthlyReportsByProjectIdAndPeriod(thisProject.getBaseInfoId(), historyPointTime, yearEndTime); // 查询截止目前 历史的统计情况;;
        MonthlyReportExcelModel monthlyReportExcelModel = new MonthlyReportExcelModel();
        MonthlyReportExcelModel monthlyReportExcelModelWithYearParams = monthlyReportExcelService.getMonthExcelModelWithYearParams(monthlyReportExcelModel, yearProjectMonthlyReports);
        MonthlyReportExcelModel monthlyReportExcelModelWithSofarParams = null;
        HistoryMonthlyReportExcelStatistics historyMonthlyReportExcelStatistics = projectMonthlyReportService.getHistoryStatistic();
        if (historyMonthlyReportExcelStatistics != null) {  // 是否存在历史数据
            monthlyReportExcelModelWithSofarParams = monthlyReportExcelService.getMonthExcelModelWithSofarParams(monthlyReportExcelModelWithYearParams, sofarProjectMonthlyReports, historyMonthlyReportExcelStatistics);
        } else {
            monthlyReportExcelModelWithSofarParams = monthlyReportExcelService.getMonthExcelModelWithSofarParams(monthlyReportExcelModelWithYearParams, sofarProjectMonthlyReports, null);
        }
        return ResultUtil.success(monthlyReportExcelModelWithSofarParams);
    }

    @GetMapping("getthiscard")
    @ResponseBody
    public ResultVO getThisCard() {
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        UserInfo thisUser = userService.selectByPrimaryKey(userInfo.getUserId());
        BaseInfo thisProject = baseInfoService.findBaseInfoById(thisUser.getBaseInfoId());
        if (thisProject != null) {
            if (!thisProject.getState().equals((byte) 1)) {
                throw new SysException(SysEnum.NO_PROJECT_IN_THISUSER);
            }
            CardVO cardVO = new CardVO();
            cardVO.setPlantName(thisProject.getPlantName());
            cardVO.setLegalPersonName(thisProject.getLegalPersonName());
            cardVO.setScale(thisProject.getScale());
            cardVO.setLevel(thisProject.getLevel());
            TimeLineItem lastTimeLineItem = timeLineItemService.findLatestOne();
            if (lastTimeLineItem == null) {
                cardVO.setProjectStatus("项目开始");
            } else {
                cardVO.setProjectStatus(lastTimeLineItem.getType());
            }
            return ResultUtil.success(cardVO);
        } else {
            return ResultUtil.failed();
        }
    }

}