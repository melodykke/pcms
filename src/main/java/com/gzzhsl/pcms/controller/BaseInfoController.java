package com.gzzhsl.pcms.controller;

import com.gzzhsl.pcms.entity.BaseInfo;
import com.gzzhsl.pcms.entity.Feedback;
import com.gzzhsl.pcms.entity.ProjectMonthlyReport;
import com.gzzhsl.pcms.enums.SysEnum;
import com.gzzhsl.pcms.exception.SysException;
import com.gzzhsl.pcms.service.BaseInfoService;
import com.gzzhsl.pcms.service.UserService;
import com.gzzhsl.pcms.shiro.bean.SysRole;
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
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.xml.transform.Result;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/baseinfo")
@Slf4j
public class BaseInfoController {
    @Autowired
    private BaseInfoService baseInfoService;
    @Autowired
    private UserService userService;

    @GetMapping("/baseinfoshow")
    public String baseInfoShow() {
        return "base_info_show";
    }

    @GetMapping("/hasbaseinfo")
    @ResponseBody
    public ResultVO hasBaseInfo() {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        UserInfo thisUser = userService.getUserByUsername(username);
        if (thisUser.getSysRoleList() == null || thisUser.getSysRoleList().size() == 0) {
            return ResultUtil.failed(SysEnum.SUBACCOUNT_NOT_EXIST_ERROR);
        }
        if (thisUser.getBaseInfo() == null) {
            return ResultUtil.failed();
        } else if (thisUser.getBaseInfo().getState().equals((byte) -1)) {
                return ResultUtil.success(1310, "审批未通过");
        } else {
            return ResultUtil.success();
        }
    }

    @GetMapping("/getinbaseinfo")
    public String getInBaseInfo() {
        return "base_info_show";
    }

    @GetMapping("/getbaseinfo")
    @ResponseBody
    public ResultVO getBaseInfo() {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        UserInfo thisUser = userService.getUserByUsername(username);
        if (userService.getUserByUsername(thisUser.getUsername()).getBaseInfo() == null){
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
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        UserInfo thisUser = userService.getUserByUsername(username);
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

    @PostMapping("/approvebaseinfo")
    @ResponseBody
    @RequiresRoles(value = {"checker"})
    public ResultVO approveBaseInfo(@RequestBody Map<String, Object> params) {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        UserInfo thisUser = userService.getUserByUsername(username);
        Boolean switchState = (boolean) params.get("switchState"); // true: 按钮未通过 false：按钮通过
        String checkinfo = (String) params.get("checkinfo");
        String baseInfoId = (String) params.get("baseInfoId");
        if (baseInfoId == null || baseInfoId == "") {
            log.error("【基础信息错误】审批基础信息错误 基础信息ID baseInfoId为空");
            throw new SysException(SysEnum.BASE_INFO_APPROVAL_ERROR);
        }
        BaseInfoVO baseInfoVORt = baseInfoService.getBaseInfoById(baseInfoId);
        if (baseInfoVORt == null) {
            log.error("【基础信息错误】审批查询的基础信息所对应ID无记录");
            throw new SysException(SysEnum.BASE_INFO_NO_CORRESPOND_RECORD_ERROR);
        }
        if (!thisUser.getBaseInfo().getBaseInfoId().equals(baseInfoVORt.getBaseInfoId())) {
            log.error("【基础信息错误】不能审批不属于本用户所属工程的项目基本信息");
            throw new SysException(SysEnum.BASE_INFO_CHECKED_OTHERS_ERROR);
        }
        if (baseInfoVORt.getState() != (byte) 0) {
            log.error("【基础信息错误】当前项目基础信息已经审批过，不能重复审批");
            throw new SysException(SysEnum.BASE_INFO_CHECK_CHECKED_ERROR);
        }
        Feedback feedback = baseInfoService.approveBaseInfo(thisUser, switchState, checkinfo, baseInfoId);
        if (feedback.equals((byte) 1)) {
            return ResultUtil.success(feedback);
        } else {
            return ResultUtil.failed(feedback);
        }

    }

    @GetMapping("/getbaseinfobyid")
    @ResponseBody
    @RequiresRoles("manager")
    public ResultVO getBaseInfoById(String baseInfoId) {
        return ResultUtil.success(baseInfoService.getBaseInfoById(baseInfoId));
    }
}
