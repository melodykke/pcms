package com.gzzhsl.pcms.controller;

import com.gzzhsl.pcms.converter.MonthReport2MonthReportShowVO;
import com.gzzhsl.pcms.converter.ProjectMonthlyReport2ProjectMonthVO;
import com.gzzhsl.pcms.converter.ProjectMonthlyReportImg2VO;
import com.gzzhsl.pcms.entity.*;
import com.gzzhsl.pcms.enums.SysEnum;
import com.gzzhsl.pcms.exception.SysException;
import com.gzzhsl.pcms.service.*;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import com.gzzhsl.pcms.util.*;
import com.gzzhsl.pcms.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/monthlyreport")
@Slf4j
public class MonthlyReportController {

    @Autowired
    private ProjectMonthlyReportService projectMonthlyReportService;
    @Autowired
    private MonthlyReportExcelService monthlyReportExcelService;
    @Autowired
    private UserService userService;
    @Autowired
    private AnnualInvestmentService annualInvestmentService;
    @Autowired
    private BaseInfoService baseInfoService;


    @PostMapping("/addfiles")
    @ResponseBody
    public ResultVO saveFiles(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("uploadfile");
        if (files == null || files.size() < 1) { return ResultUtil.failed(); }
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        UserInfo thisUser = userService.getUserByUsername(username);
        BaseInfo thisProject = thisUser.getBaseInfo();
        if (thisUser == null || thisProject.getPlantName() == null || thisProject.getPlantName() == "") {
            log.error("【月报错误】 所登录账号不具备月报图片上传功能 , thisUser = {}, thisProject = {}"
                    , thisUser, thisProject);
            throw new SysException(SysEnum.MONTHLY_REPORT_IMG_ERROR);
        }
        String midPath = thisUser.getUserId()+ "/" + UUIDUtils.getUUIDString()+"/";
        for (MultipartFile file : files) {
            String oriFileName = file.getOriginalFilename();
            String suffixName = ImageUtil.getFileExtension(oriFileName);
            String destFileName = ImageUtil.getRandomFileName() + suffixName;

            File dest = new File(PathUtil.getFileBasePath(true) + midPath + destFileName);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                file.transferTo(dest);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ResultUtil.success(midPath);
    }


    @PostMapping("/savereport")
    @ResponseBody
    public ResultVO saveReport(@Valid @RequestBody ProjectMonthlyReportVO projectMonthlyReportVO, BindingResult bindingResult) {
        if (projectMonthlyReportVO == null) {
            log.error("【月报错误】 存储月报错误，没有收到有效的projectMonthlyReportVO , " +
                    "实际projectMonthlyReportVO = {}", projectMonthlyReportVO);
            throw new SysException(SysEnum.MONTHLY_REPORT_ERROR);
        }
        if(bindingResult.hasErrors()){
            log.error("【月报错误】参数验证错误， 参数不正确 projectMonthlyReportVO = {}， 错误：{}", projectMonthlyReportVO, bindingResult.getFieldError().getDefaultMessage());
            throw new SysException(SysEnum.DATA_SUBMIT_FAILED.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        ProjectMonthlyReport projectMonthlyReportRt = projectMonthlyReportService.save(projectMonthlyReportVO);

        return ResultUtil.success();
    }

    @PostMapping("/getmonthlyreports")
    @ResponseBody
    public ResultVO getMonthlyReports(@RequestBody Map<String, Object> params) {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        UserInfo thisUser = userService.getUserByUsername(username);
        BaseInfo thisProject = thisUser.getBaseInfo();
        if (thisProject == null || thisProject.getBaseInfoId() == null ||  thisProject.getBaseInfoId() == "") {
            log.error("【月报错误】 获取用户所在工程月报集出错 , thisProject = {}", thisProject);
            throw new SysException(SysEnum.MONTHLY_REPORTS_FETCH_ERROR);
        }
        String year = (String) params.get("year"); // 从前端取到年份
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String endTime = simpleDateFormat.format(new Date()); // 查询时间范围的截止日期应为当前
        String endDate = new Date().toString();
        if (year == null || year == "") { // 如果从前端没有取到查询年份，则默认当前时间年份
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            year = String.valueOf(calendar.get(Calendar.YEAR));
        }
        String startDate = year+"-01-01 00:00:00";
        List<ProjectMonthlyReport> projectMonthlyReports = projectMonthlyReportService.getMonthlyReportsByProjectIdAndYear(thisProject.getBaseInfoId(), startDate, endDate);
        if (projectMonthlyReports == null || projectMonthlyReports.size() == 0){
            log.error("【月报错误】获取月报列表空，该工程指定年无月报记录");
            throw new SysException(SysEnum.DATA_CALLBACK_FAILED);
        }
        List<ProjectMonthVO> projectMonthVOs = projectMonthlyReports.stream().map(e -> ProjectMonthlyReport2ProjectMonthVO.convert(e)).collect(Collectors.toList());
        return ResultUtil.success(projectMonthVOs);
    }

    // 进入某一个月报展示页面（projectMonthlyReportId）
    @GetMapping("/projectmonthlyreportshow")
    public String projectMonthlyReportShow(String projectMonthlyReportId, Model model){
        if (projectMonthlyReportId != null || !"".equals(projectMonthlyReportId)) {
            model.addAttribute("projectMonthlyReportId", projectMonthlyReportId);
        }
        return "project_monthly_report_show";
    }

    @PostMapping("/getprojectmonthlyreportbyprojectmonthlyreportid")
    @ResponseBody
    public ResultVO getProjectMonthlyReportProjectMonthlyReportId(String projectMonthlyReportId){
        if (projectMonthlyReportId == "" || projectMonthlyReportId == null) {
            log.error("【月报错误】内部projectMonthlyReportId错误");
            throw new SysException(SysEnum.Sys_INNER_ERROR);
        }
        ProjectMonthlyReport projectMonthlyReport = projectMonthlyReportService.getByProjectMonthlyReportId(projectMonthlyReportId);
        ProjectMonthlyReportShowVO projectMonthlyReportShowVO = MonthReport2MonthReportShowVO.convert(projectMonthlyReport);
        List<ProjectMonthlyReportImgVO> projectMonthlyReportImgVOList = projectMonthlyReport.getProjectMonthlyReportImgList().stream().map(e -> ProjectMonthlyReportImg2VO.convert(e)).collect(Collectors.toList());
        projectMonthlyReportShowVO.setProjectMonthlyReportImgVOList(projectMonthlyReportImgVOList);
        return ResultUtil.success(projectMonthlyReportShowVO);
    }



    @PostMapping("/getprojectmonthlyreportshowbytime")
    @ResponseBody
    public ResultVO getProjectMonthlyReportShow(@RequestBody Map<String, Object> params) {
        String time = (String) params.get("time");
        String startDate = time + "-01 00:00:00";
        String endDate = time + "-28 23:59:59";
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        UserInfo thisUser = userService.getUserByUsername(username);
        String projectId = thisUser.getBaseInfo().getBaseInfoId();
        List<ProjectMonthlyReport> projectMonthlyReportList = projectMonthlyReportService.getMonthlyReportsByProjectIdAndYear(projectId, startDate, endDate);
        if (projectMonthlyReportList == null || projectMonthlyReportList.size() == 0) {
            log.error("【月报错误】读取月报错误 未在指定时间区间内读取到月报内容");
            throw new SysException(SysEnum.MONTHLY_REPORTS_NONE_PER_MONTH_ERROR);
        }
        if (projectMonthlyReportList.size() > 1) {
            log.error("【月报错误】读取月报错误 同一月存在多分月报");
            throw new SysException(SysEnum.MONTHLY_REPORTS_MULTIPLE_PER_MONTH_ERROR);
        }
        ProjectMonthlyReport projectMonthlyReport = projectMonthlyReportList.get(0);
        ProjectMonthlyReportShowVO projectMonthlyReportShowVO = MonthReport2MonthReportShowVO.convert(projectMonthlyReport);
        List<ProjectMonthlyReportImgVO> projectMonthlyReportImgVOList = projectMonthlyReport.getProjectMonthlyReportImgList().stream().map(e -> ProjectMonthlyReportImg2VO.convert(e)).collect(Collectors.toList());
        projectMonthlyReportShowVO.setProjectMonthlyReportImgVOList(projectMonthlyReportImgVOList);
        return ResultUtil.success(projectMonthlyReportShowVO);
    }

    @GetMapping("/getmonthlyreportexcelbyprojectid")
    @ResponseBody
    public ResultVO getMonthlyReportExcelByProjectMonthlyReportId(String currentDate, String projectMonthlyReportId, HttpServletRequest request, HttpServletResponse response) {
        // 获取当前用户工程，看是否该工程有截止到2018年1月之前的历史数据
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        UserInfo thisUser = userService.getUserByUsername(username);
        BaseInfo thisProject = thisUser.getBaseInfo();
        ProjectMonthlyReport projectMonthlyReport = projectMonthlyReportService.getByProjectMonthlyReportId(projectMonthlyReportId);
        String projectId = ((BaseInfo) request.getSession().getAttribute("thisProject")).getBaseInfoId();
        Date yearEndDate = new Date(); // 当前时间
        String historyPointTime = "2000-01-01 00:00:00";
        String yearEndTime = currentDate+"-28 23:59:59"; // 查询时间范围的截止日期应为当前
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(yearEndDate);
        String yearStartTime = String.valueOf(calendar.get(Calendar.YEAR))+"-01-01 00:00:00"; // 当前时间的年度头一天头一秒
        List<ProjectMonthlyReport> yearProjectMonthlyReports = projectMonthlyReportService.
                getMonthlyReportsByProjectIdAndYear(projectId, yearStartTime, yearEndTime); // 查询截止目前 本年的统计情况;
        List<ProjectMonthlyReport> sofarProjectMonthlyReports = projectMonthlyReportService.
                getMonthlyReportsByProjectIdAndYear(projectId, historyPointTime, yearEndTime); // 查询截止目前 历史的统计情况;;
        MonthlyReportExcelModel monthlyReportExcelModel = new MonthlyReportExcelModel();
        monthlyReportExcelModel.setTotalInvestment(thisProject.getTotalInvestment());
        // 取得年份
        String year = currentDate.substring(0, currentDate.lastIndexOf("-"));
        // 查询该年份有无已审批通过的年度投资计划 如果有设置上，如果没 null
        List<AnnualInvestment> annualInvestments = annualInvestmentService.getByYearAndProject(year, thisProject);
        if (annualInvestments.size()==1) {
            if (annualInvestments.get(0).getState().equals((byte) 1)) {
                monthlyReportExcelModel.setThisYearPlanInvestment(annualInvestments.get(0).getApplyFigure()); // 设置年度投融资计划
            }
        }
        MonthlyReportExcelModel monthlyReportExcelModelWithMonthParams = monthlyReportExcelService.getMonthExcelModelWithMonthParams(monthlyReportExcelModel, projectMonthlyReport);
        MonthlyReportExcelModel monthlyReportExcelModelWithMonthYearParams = monthlyReportExcelService.getMonthExcelModelWithYearParams(monthlyReportExcelModelWithMonthParams, yearProjectMonthlyReports);

        MonthlyReportExcelModel monthlyReportExcelModelWithSofarParams = null;
        if (thisProject.getHistoryMonthlyReportExcelStatistics() != null) {  // 是否存在历史数据
            monthlyReportExcelModelWithSofarParams = monthlyReportExcelService.getMonthExcelModelWithSofarParams(monthlyReportExcelModelWithMonthYearParams, sofarProjectMonthlyReports, thisProject.getHistoryMonthlyReportExcelStatistics());
        } else {
            monthlyReportExcelModelWithSofarParams = monthlyReportExcelService.getMonthExcelModelWithSofarParams(monthlyReportExcelModelWithMonthYearParams, sofarProjectMonthlyReports, null);
        }
        return ResultUtil.success(MonthlyReportExcelCalcUtil.buildMonthlyReportExcel(monthlyReportExcelModelWithSofarParams));
    }

    @PostMapping("/approvemonthlyreport")
    @ResponseBody
    @RequiresRoles(value = {"checker"})
    public ResultVO approveMonthlyReport(@RequestBody Map<String, Object> params) {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        UserInfo thisUser = userService.getUserByUsername(username);
        Boolean switchState = (boolean) params.get("switchState"); // true: 按钮未通过 false：按钮通过
        String checkinfo = (String) params.get("checkinfo");
        String projectMonthlyReportId = (String) params.get("projectMonthlyReportId");
        if (projectMonthlyReportId == null || projectMonthlyReportId == "") {
            log.error("【月报错误】审批月报错误 月报ID projectMonthlyReportId为空");
            throw new SysException(SysEnum.MONTHLY_REPORTS_APPROVEAL_ERROR);
        }
        ProjectMonthlyReport projectMonthlyReportRt = projectMonthlyReportService.getByProjectMonthlyReportId(projectMonthlyReportId);
        if (projectMonthlyReportRt == null) {
            log.error("【月报错误】审批月报错误，无月报实体对应月报ID");
            throw new SysException(SysEnum.MONTHLY_REPORTS_NO_CORRESPOND_REPORT_ERROR);
        }
        if (!thisUser.getBaseInfo().getBaseInfoId().equals(projectMonthlyReportRt.getBaseInfo().getBaseInfoId())) {
            log.error("【月报错误】月报错误，不能审批不属于本用户所属工程的月报");
            throw new SysException(SysEnum.MONTHLY_REPORTS_CHECKED_OTHERS_ERROR);
        }
        if (projectMonthlyReportRt.getState() != (byte) 0) {
            log.error("【月报错误】当前审批月报已经审批过，不能重复审批");
            throw new SysException(SysEnum.MONTHLY_REPORTS_CHECK_CHECKED_ERROR);
        }
        return ResultUtil.success(projectMonthlyReportService.approveMonthlyReport(thisUser, switchState, checkinfo, projectMonthlyReportId, projectMonthlyReportRt));
    }

    @GetMapping("/tomonthshistory")
    public String toMonthsHistory() {
        return "history_info_new";
    }

    @GetMapping("/hashistorystatistic")
    @ResponseBody
    public ResultVO hasHistoryStatistic() {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        UserInfo thisUser = userService.getUserByUsername(username);
        BaseInfo thisProject = thisUser.getBaseInfo();
        if (thisProject.getHistoryMonthlyReportExcelStatistics() != null && thisProject.getHistoryMonthlyReportExcelStatistics().getState().equals((byte) 1)) {
            return ResultUtil.success();
        } else if (thisProject.getHistoryMonthlyReportExcelStatistics() != null && thisProject.getHistoryMonthlyReportExcelStatistics().getState().equals((byte) 0)) {
            return ResultUtil.failed(1004, "历史数据已设置，正在审批中...");
        } else if (thisProject.getHistoryMonthlyReportExcelStatistics() != null && thisProject.getHistoryMonthlyReportExcelStatistics().getState().equals((byte) -1)) {
            return ResultUtil.failed(1005, "历史数据审批未通过！");
        } else {
            return ResultUtil.failed();
        }
    }

    @PostMapping("/savehistorystatistic")
    @ResponseBody
    public ResultVO saveHistoryStatistic(@Valid @RequestBody HistoryMonthlyReportStatisticVO historyMonthlyReportStatisticVO, BindingResult bindingResult) {
        if (historyMonthlyReportStatisticVO == null) {
            log.error("【月报错误】 存储月报历史数据错误，没有收到有效的historyMonthlyReportStatisticVO , " +
                    "实际historyMonthlyReportStatisticVO = {}", historyMonthlyReportStatisticVO);
            throw new SysException(SysEnum.HISTORY_MONTHLY_REPORT_ERROR);
        }
        if(bindingResult.hasErrors()){
            log.error("【月报错误】参数验证错误， 参数不正确 historyMonthlyReportStatisticVO = {}， 错误：{}", historyMonthlyReportStatisticVO, bindingResult.getFieldError().getDefaultMessage());
            throw new SysException(SysEnum.DATA_SUBMIT_FAILED.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        HistoryMonthlyReportExcelStatistics historyMonthlyReportExcelStatistics = projectMonthlyReportService.saveHistoryStatistic(historyMonthlyReportStatisticVO);
        if (historyMonthlyReportExcelStatistics != null) {
            return ResultUtil.success();
        }
        return ResultUtil.failed();
    }

    @GetMapping("/tomonthshistoryshow")
    public String toMonthsHistoryShow() {
        return "history_info_show";
    }

    @GetMapping("/gethistorystatistic")
    @ResponseBody
    public ResultVO getHistoryStatistic() {
        HistoryMonthlyReportExcelStatistics historyMonthlyReportExcelStatistics = projectMonthlyReportService.getHistoryStatistic();
        HistoryMonthlyReportStatisticVO historyMonthlyReportStatisticVO = new HistoryMonthlyReportStatisticVO();
        if (historyMonthlyReportExcelStatistics != null) {
            BeanUtils.copyProperties(historyMonthlyReportExcelStatistics, historyMonthlyReportStatisticVO);
            return ResultUtil.success(historyMonthlyReportStatisticVO);
        } else {
            return ResultUtil.failed();
        }
    }

    @PostMapping("/approvehistorymonthlystatistic")
    @ResponseBody
    @RequiresRoles(value = {"checker"})
    public ResultVO approveHistoryMonthlyStatistic(@RequestBody Map<String, Object> params) {
        Boolean switchState = (boolean) params.get("switchState"); // true: 按钮未通过 false：按钮通过
        String checkinfo = (String) params.get("checkinfo");
        HistoryMonthlyReportExcelStatistics historyMonthlyReportExcelStatistics = projectMonthlyReportService.getHistoryStatistic();
        if (historyMonthlyReportExcelStatistics == null) {
            log.error("【月报错误】审批月报历史数据错误，无对应月报历史数据");
            throw new SysException(SysEnum.HISTORY_MONTHLY_STATISTIC_NO_CORRESPOND_DATA_ERROR);
        }
        if (historyMonthlyReportExcelStatistics.getState() != (byte) 0) {
            log.error("【月报错误】当前审批月报历史数据已经审批过，不能重复审批");
            throw new SysException(SysEnum.HISTORY_MONTHLY_STATISTIC_CHECK_CHECKED_ERROR);
        }
        return ResultUtil.success(projectMonthlyReportService.approveHistoryMonthlyStatistic(switchState, checkinfo, historyMonthlyReportExcelStatistics));
    }

    @PostMapping("/managergetmonthlyreports")
    @ResponseBody
    @RequiresRoles("manager")
    public ResultVO managerGetMonthlyReports(@RequestBody Map<String, Object> params) {
        String year = (String) params.get("year"); // 从前端取到年份
        String baseInfoId = (String) params.get("baseInfoId"); // 从前端取到年份
        BaseInfoVO thisProject = baseInfoService.getBaseInfoById(baseInfoId);
        if (thisProject == null || thisProject.getBaseInfoId() == null ||  thisProject.getBaseInfoId() == "") {
            log.error("【月报错误】 获取用户所在工程月报集出错 , thisProject = {}", thisProject);
            throw new SysException(SysEnum.MONTHLY_REPORTS_FETCH_ERROR);
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String endTime = simpleDateFormat.format(new Date()); // 查询时间范围的截止日期应为当前
        String endDate = new Date().toString();
        if (year == null || year == "") { // 如果从前端没有取到查询年份，则默认当前时间年份
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            year = String.valueOf(calendar.get(Calendar.YEAR));
        }
        String startDate = year+"-01-01 00:00:00";
        List<ProjectMonthlyReport> projectMonthlyReports = projectMonthlyReportService.getMonthlyReportsByProjectIdAndYear(thisProject.getBaseInfoId(), startDate, endDate);
        if (projectMonthlyReports == null || projectMonthlyReports.size() == 0){
            log.error("【月报错误】获取月报列表空，该工程指定年无月报记录");
            throw new SysException(SysEnum.DATA_CALLBACK_FAILED);
        }
        List<ProjectMonthVO> projectMonthVOs = projectMonthlyReports.stream().map(e -> ProjectMonthlyReport2ProjectMonthVO.convert(e)).collect(Collectors.toList());
        return ResultUtil.success(projectMonthVOs);
    }
    @GetMapping("/managergetmonthlyreportexcelbyprojectid")
    @ResponseBody
    @RequiresRoles("manager")
    public ResultVO managerGetMonthlyReportExcelByProjectMonthlyReportId(String baseInfoId, String currentDate, String projectMonthlyReportId, HttpServletRequest request, HttpServletResponse response) {
        // 获取当前用户工程，看是否该工程有截止到2018年1月之前的历史数据
        BaseInfo thisProject = baseInfoService.findBaseInfoById(baseInfoId);
        ProjectMonthlyReport projectMonthlyReport = projectMonthlyReportService.getByProjectMonthlyReportId(projectMonthlyReportId);
        String projectId = thisProject.getBaseInfoId();
        Date yearEndDate = new Date(); // 当前时间
        String historyPointTime = "2000-01-01 00:00:00";
        String yearEndTime = currentDate+"-28 23:59:59"; // 查询时间范围的截止日期应为当前
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(yearEndDate);
        String yearStartTime = String.valueOf(calendar.get(Calendar.YEAR))+"-01-01 00:00:00"; // 当前时间的年度头一天头一秒
        List<ProjectMonthlyReport> yearProjectMonthlyReports = projectMonthlyReportService.
                getMonthlyReportsByProjectIdAndYear(projectId, yearStartTime, yearEndTime); // 查询截止目前 本年的统计情况;
        List<ProjectMonthlyReport> sofarProjectMonthlyReports = projectMonthlyReportService.
                getMonthlyReportsByProjectIdAndYear(projectId, historyPointTime, yearEndTime); // 查询截止目前 历史的统计情况;;
        MonthlyReportExcelModel monthlyReportExcelModel = new MonthlyReportExcelModel();
        monthlyReportExcelModel.setTotalInvestment(thisProject.getTotalInvestment());
        // 取得年份
        String year = currentDate.substring(0, currentDate.lastIndexOf("-"));
        // 查询该年份有无已审批通过的年度投资计划 如果有设置上，如果没 null
        List<AnnualInvestment> annualInvestments = annualInvestmentService.managerGetByYearAndProject(year, thisProject);
        if (annualInvestments.size()==1) {
            if (annualInvestments.get(0).getState().equals((byte) 1)) {
                monthlyReportExcelModel.setThisYearPlanInvestment(annualInvestments.get(0).getApplyFigure()); // 设置年度投融资计划
            }
        }
        MonthlyReportExcelModel monthlyReportExcelModelWithMonthParams = monthlyReportExcelService.getMonthExcelModelWithMonthParams(monthlyReportExcelModel, projectMonthlyReport);
        MonthlyReportExcelModel monthlyReportExcelModelWithMonthYearParams = monthlyReportExcelService.getMonthExcelModelWithYearParams(monthlyReportExcelModelWithMonthParams, yearProjectMonthlyReports);

        MonthlyReportExcelModel monthlyReportExcelModelWithSofarParams = null;
        if (thisProject.getHistoryMonthlyReportExcelStatistics() != null) {  // 是否存在历史数据
            monthlyReportExcelModelWithSofarParams = monthlyReportExcelService.getMonthExcelModelWithSofarParams(monthlyReportExcelModelWithMonthYearParams, sofarProjectMonthlyReports, thisProject.getHistoryMonthlyReportExcelStatistics());
        } else {
            monthlyReportExcelModelWithSofarParams = monthlyReportExcelService.getMonthExcelModelWithSofarParams(monthlyReportExcelModelWithMonthYearParams, sofarProjectMonthlyReports, null);
        }
        return ResultUtil.success(MonthlyReportExcelCalcUtil.buildMonthlyReportExcel(monthlyReportExcelModelWithSofarParams));
    }

}
