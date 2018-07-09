package com.gzzhsl.pcms.controller;

import com.gzzhsl.pcms.entity.BaseInfo;
import com.gzzhsl.pcms.entity.PreProgress;
import com.gzzhsl.pcms.entity.PreProgressEntry;
import com.gzzhsl.pcms.enums.SysEnum;
import com.gzzhsl.pcms.exception.SysException;
import com.gzzhsl.pcms.service.PreProgressService;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import com.gzzhsl.pcms.util.ResultUtil;
import com.gzzhsl.pcms.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public ResultVO save(@RequestBody @Valid List<PreProgressEntry> preProgressEntries, BindingResult bindingResult) {
        preProgressService.save(preProgressEntries);
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
        return ResultUtil.success(preProgress);
    }
}
