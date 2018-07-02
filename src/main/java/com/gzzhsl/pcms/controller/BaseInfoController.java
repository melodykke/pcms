package com.gzzhsl.pcms.controller;

import com.gzzhsl.pcms.entity.BaseInfo;
import com.gzzhsl.pcms.entity.ProjectMonthlyReport;
import com.gzzhsl.pcms.enums.SysEnum;
import com.gzzhsl.pcms.exception.SysException;
import com.gzzhsl.pcms.service.BaseInfoService;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import com.gzzhsl.pcms.util.ImageUtil;
import com.gzzhsl.pcms.util.PathUtil;
import com.gzzhsl.pcms.util.ResultUtil;
import com.gzzhsl.pcms.util.UUIDUtils;
import com.gzzhsl.pcms.vo.BaseInfoVO;
import com.gzzhsl.pcms.vo.ProjectMonthlyReportVO;
import com.gzzhsl.pcms.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/baseinfo")
@Slf4j
public class BaseInfoController {
    @Autowired
    private BaseInfoService baseInfoService;

    @GetMapping("/baseinfoshow")
    public String baseInfoShow() {
        return "/base_info_show";
    }


    @GetMapping("/hasbaseinfo")
    @ResponseBody
    public ResultVO hasBaseInfo() {
        UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        if (thisUser.getBaseInfo() == null) {
            return ResultUtil.failed();
        } else {
            return ResultUtil.success();
        }
    }

    @GetMapping("/getbaseinfo")
    @ResponseBody
    public ResultVO getBaseInfo() {
        UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        if (thisUser.getBaseInfo() == null){
            log.error("【基本信息】没有配置该用户的项目基本信息");
            throw new SysException(SysEnum.BASE_INFO_NO_RECORD_ERROR);
        }
        BaseInfoVO baseInfoVO = baseInfoService.getBaseInfoById(thisUser.getBaseInfo().getBaseInfoId());
        return ResultUtil.success(baseInfoVO);
    }

    @PostMapping("/addfiles")
    @ResponseBody
    public ResultVO saveFiles(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("uploadfile");
        if (files == null || files.size() < 1) { return ResultUtil.failed(); }
        UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
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

    @PostMapping("/savebaseinfo")
    @ResponseBody
    public ResultVO saveReport(@Valid @RequestBody BaseInfoVO baseInfoVO, BindingResult bindingResult) {
        if (baseInfoVO == null) {
            log.error("【基本信息错误】 没有收到有效的baseInfoVO , " +
                    "实际baseInfoVO = {}", baseInfoVO);
            throw new SysException(SysEnum.BASE_INFO_VO_PARAMS_ERROR);
        }
        if(bindingResult.hasErrors()){
            log.error("【基本信息错误】参数验证错误， 参数不正确 baseInfoVO = {}， 错误：{}", baseInfoVO, bindingResult.getFieldError().getDefaultMessage());
            throw new SysException(SysEnum.DATA_SUBMIT_FAILED.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        BaseInfo baseInfoRt = baseInfoService.save(baseInfoVO);

        return ResultUtil.success();
    }
}
