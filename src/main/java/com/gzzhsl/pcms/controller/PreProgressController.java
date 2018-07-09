package com.gzzhsl.pcms.controller;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gzzhsl.pcms.converter.PreProgress2VO;
import com.gzzhsl.pcms.converter.PreProgressImg2VO;
import com.gzzhsl.pcms.entity.BaseInfo;
import com.gzzhsl.pcms.entity.PreProgress;
import com.gzzhsl.pcms.entity.PreProgressEntry;
import com.gzzhsl.pcms.enums.SysEnum;
import com.gzzhsl.pcms.exception.SysException;
import com.gzzhsl.pcms.service.PreProgressService;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import com.gzzhsl.pcms.util.*;
import com.gzzhsl.pcms.vo.PreProgressImgVO;
import com.gzzhsl.pcms.vo.PreProgressVO;
import com.gzzhsl.pcms.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/preprogress")
@Slf4j
public class PreProgressController {
    @Autowired
    private PreProgressService preProgressService;


    @GetMapping("/topreprogress")
    public String toPreprogress() {
        return "pre_progress_show";
    }

    @GetMapping("/haspreprogress")
    @ResponseBody
    public ResultVO hasPreProgress() {
        UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        BaseInfo thisProject = thisUser.getBaseInfo();
        if (thisProject == null || thisProject.getBaseInfoId() == null ||  thisProject.getBaseInfoId() == "") {
            log.error("【项目前期错误】 获取用户所在工程基本信息出错 , thisProject = {}", thisProject);
            throw new SysException(SysEnum.ACCOUNT_NO_PROJECT);
        }
        PreProgress preProgress = preProgressService.findByBaseInfo(thisProject);
        if (preProgress != null) {
            return ResultUtil.success();
        }
        return ResultUtil.failed();
    }
    @PostMapping("/save")
    @ResponseBody
    public ResultVO save(@RequestBody Map<String, Object> params) {
        String preProgressEntriesStr = (String) params.get("preProgressEntries");
        String rtFileTempPath = (String) params.get("rtFileTempPath");
        ObjectMapper mapper = new ObjectMapper();
        JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, PreProgressEntry.class);
        List<PreProgressEntry> preProgressEntries = null;
        try {
            preProgressEntries = mapper.readValue(preProgressEntriesStr, javaType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        preProgressService.save(preProgressEntries, rtFileTempPath);
        return ResultUtil.success();
    }

    @GetMapping("/initthispage")
    @ResponseBody
    public ResultVO initThisPage() {
        return ResultUtil.success(preProgressService.getAllPreProgressDefault());
    }

    @GetMapping("/getpreprogress")
    @ResponseBody
    public ResultVO getPreProgress() {
        UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        BaseInfo thisProject = thisUser.getBaseInfo();
        PreProgress preProgress = preProgressService.findByBaseInfo(thisProject);
        List<PreProgressImgVO> preProgressImgVOList = preProgress.getPreProgressImgs().stream().map(e -> PreProgressImg2VO.convert(e)).collect(Collectors.toList());
        PreProgressVO preProgressVO = PreProgress2VO.convert(preProgress);
        preProgressVO.setPreProgressImgVOs(preProgressImgVOList);
        return ResultUtil.success(preProgressVO);
    }

    @PostMapping("/addfiles")
    @ResponseBody
    public ResultVO saveFiles(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("pre_progress_file");
        if (files == null || files.size() < 1) {
            return ResultUtil.failed();
        }
        UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        return ResultUtil.success(FileUtil.saveFile(thisUser, files));
    }
}
