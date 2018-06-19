package com.gzzhsl.pcms.controller;

import com.gzzhsl.pcms.converter.ProjectMonthlyReport2ProjectMonthVO;
import com.gzzhsl.pcms.entity.Project;
import com.gzzhsl.pcms.entity.ProjectMonthlyReport;
import com.gzzhsl.pcms.enums.SysEnum;
import com.gzzhsl.pcms.exception.SysException;
import com.gzzhsl.pcms.service.ProjectMonthlyReportService;
import com.gzzhsl.pcms.service.ProjectService;
import com.gzzhsl.pcms.service.UserService;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import com.gzzhsl.pcms.util.*;
import com.gzzhsl.pcms.vo.ProjectMonthVO;
import com.gzzhsl.pcms.vo.ProjectMonthlyReportVO;
import com.gzzhsl.pcms.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sun.plugin.util.UIUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/monthlyreport")
@Slf4j
public class MonthlyReportController {

    @Autowired
    private ProjectMonthlyReportService projectMonthlyReportService;

    @PostMapping("/addfiles")
    @ResponseBody
    public ResultVO saveFiles(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("uploadfile");
        if (files == null || files.size() < 1) { return ResultUtil.failed(); }
        UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        Project thisProject = thisUser.getProject();
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
        projectMonthlyReportService.save(projectMonthlyReportVO);
        return ResultUtil.success();
    }

    @PostMapping("/getmonthlyreports")
    @ResponseBody
    public ResultVO getMonthlyReports(@RequestBody Map<String, Object> params) {
        UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        Project thisProject = thisUser.getProject();
        if (thisProject == null || thisProject.getProjectId() == null ||  thisProject.getProjectId() == "") {
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
        List<ProjectMonthlyReport> projectMonthlyReports = projectMonthlyReportService.getMonthlyReportsByProjectIdAndYear(thisProject.getProjectId(), startDate, endDate);
        if (projectMonthlyReports == null || projectMonthlyReports.size() == 0){
            log.error("【月报错误】获取月报列表空，该工程指定年无月报记录");
            throw new SysException(SysEnum.DATA_CALLBACK_FAILED);
        }
        List<ProjectMonthVO> projectMonthVOs = projectMonthlyReports.stream().map(e -> ProjectMonthlyReport2ProjectMonthVO.convert(e)).collect(Collectors.toList());
        return ResultUtil.success(projectMonthVOs);
    }

}
