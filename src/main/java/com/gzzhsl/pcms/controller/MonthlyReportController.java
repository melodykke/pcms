package com.gzzhsl.pcms.controller;

import com.gzzhsl.pcms.entity.Project;
import com.gzzhsl.pcms.entity.ProjectMonthlyReport;
import com.gzzhsl.pcms.enums.SysEnum;
import com.gzzhsl.pcms.exception.SysException;
import com.gzzhsl.pcms.service.UserService;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import com.gzzhsl.pcms.util.HttpServletRequestUtil;
import com.gzzhsl.pcms.util.ImageUtil;
import com.gzzhsl.pcms.util.PathUtil;
import com.gzzhsl.pcms.util.ResultUtil;
import com.gzzhsl.pcms.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/monthlyreport")
@Slf4j
public class MonthlyReportController {

    @PostMapping("/addfiles")
    @ResponseBody
    public ResultVO saveFiles(HttpServletRequest request) {
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

            File dest = new File(PathUtil.getFileBasePath() + PathUtil.getMonthlyReportImagePath(thisProject
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


    @PostMapping("/savereport")
    @ResponseBody
    public ResultVO saveReport(@RequestBody ProjectMonthlyReport projectMonthlyReport) {
        System.out.println(projectMonthlyReport);
        return ResultUtil.success();
    }
}
