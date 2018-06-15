package com.gzzhsl.pcms.controller;

import com.gzzhsl.pcms.entity.Project;
import com.gzzhsl.pcms.entity.ProjectMonthlyReport;
import com.gzzhsl.pcms.enums.SysEnum;
import com.gzzhsl.pcms.exception.SysException;
import com.gzzhsl.pcms.service.ProjectMonthlyReportService;
import com.gzzhsl.pcms.service.ProjectService;
import com.gzzhsl.pcms.service.UserService;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import com.gzzhsl.pcms.util.*;
import com.gzzhsl.pcms.vo.ProjectMonthlyReportVO;
import com.gzzhsl.pcms.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sun.plugin.util.UIUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    public ResultVO saveReport(@RequestBody ProjectMonthlyReportVO projectMonthlyReportVO) {
        UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        
        if (projectMonthlyReportVO == null) {
            log.error("【月报错误】 存储月报错误，没有收到有效的projectMonthlyReportVO , " +
                            "实际projectMonthlyReportVO = {}", projectMonthlyReportVO);
            throw new SysException(SysEnum.MONTHLY_REPORT_ERROR);
        }
        if (projectMonthlyReportVO.getRtFileTempPath() == null|| projectMonthlyReportVO.getRtFileTempPath() == "") {
            // 没有上传图片的情况，直接对表格进行存储
            if (projectMonthlyReportVO.getProjectMonthlyReport() == null) {
                log.error("【月报错误】 存储月报信息为空，没有收到有效的projectMonthlyReportVO , " +
                        "实际projectMonthlyReportVO = {}", projectMonthlyReportVO);
                throw new SysException(SysEnum.MONTHLY_REPORT_ERROR);
            } else {
                ProjectMonthlyReport projectMonthlyReport = projectMonthlyReportVO.getProjectMonthlyReport();
                projectMonthlyReport.setProject(thisUser.getProject()); // TODO 如果当前用户没有想对应的project水库工程，则.....;
                projectMonthlyReportService.save(projectMonthlyReport);
            }
        } else {
            // 上传图片的情况，需考虑转存
            if (projectMonthlyReportVO.getProjectMonthlyReport() == null) {
                log.error("【月报错误】 存储月报信息为空，没有收到有效的projectMonthlyReportVO , " +
                        "实际projectMonthlyReportVO = {}", projectMonthlyReportVO);
                throw new SysException(SysEnum.MONTHLY_REPORT_ERROR);
            } else {
                Project thisProject = thisUser.getProject();
                ProjectMonthlyReport projectMonthlyReport = projectMonthlyReportVO.getProjectMonthlyReport();
                String rtFileTempPath = projectMonthlyReportVO.getRtFileTempPath();
                ProjectMonthlyReport projectMonthlyReportRt = projectMonthlyReportService.save(projectMonthlyReport);
                Calendar cal = Calendar.getInstance();
                cal.setTime(projectMonthlyReportRt.getSubmitDate());
                String date = String.valueOf(cal.get(Calendar.YEAR))+ "/" + String.valueOf(cal.get(Calendar.MONTH)+1);
                String sourceDest = PathUtil.getFileBasePath(true) + rtFileTempPath;
                String targetDest = PathUtil.getFileBasePath(false)+PathUtil.getMonthlyReportImagePath(thisProject.getPlantName(), date);

                File sourceFile = new File(sourceDest);
                File[] files = sourceFile.listFiles();
                if (files.length > 0) {
                    for (File file : files) {
                        File targetFile = new File(PathUtil.getFileBasePath(false)+PathUtil.getMonthlyReportImagePath(thisProject.getPlantName(), date) + file.getName());
                        if (!targetFile.exists()) {
                            targetFile.getParentFile().mkdirs();
                        }
                       file.renameTo(targetFile);
                    }
                }
            }
        }

        System.out.println(projectMonthlyReportVO);
        return ResultUtil.success();
    }
}
  /*  public ResultVO saveFiles(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("uploadfile");
        if (files == null || files.size() < 1) { return ResultUtil.failed(); }
        String date = HttpServletRequestUtil.getString(request, "date");
        UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        Project thisProject = thisUser.getProject();
        if (thisUser == null || thisProject.getPlantName() == null || thisProject.getPlantName() == "") {
            log.error("【月报错误】 所登录账号不具备月报图片上传功能 , thisUser = {}, thisProject = {}"
                    , thisUser, thisProject);
            throw new SysException(SysEnum.MONTHLY_REPORT_IMG_ERROR);
        }
        for (MultipartFile file : files) {
            String oriFileName = file.getOriginalFilename();
            String suffixName = ImageUtil.getFileExtension(oriFileName);
            String destFileName = ImageUtil.getRandomFileName() + suffixName;

            File dest = new File(PathUtil.getFileBasePath(false) + PathUtil.getMonthlyReportImagePath(thisProject
                    .getPlantName(), date) + destFileName);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                file.transferTo(dest);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ResultUtil.success();
    }
*/