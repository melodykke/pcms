package com.gzzhsl.pcms.controller;

import com.gzzhsl.pcms.entity.AnnualInvestment;
import com.gzzhsl.pcms.enums.SysEnum;
import com.gzzhsl.pcms.exception.SysException;
import com.gzzhsl.pcms.service.AnnualInvestmentService;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import com.gzzhsl.pcms.util.FileUtil;
import com.gzzhsl.pcms.util.ResultUtil;
import com.gzzhsl.pcms.vo.AnnualInvestmentVO;
import com.gzzhsl.pcms.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequestMapping("/annualinvestment")
public class AnnualInvestmentController {
    @Autowired
    private AnnualInvestmentService annualInvestmentService;

    @GetMapping("/tonewannualinvestment")
    public String toNewAnnualInvestment() {
        return "annual_investment_plan_new";
    }
    @GetMapping("/toannualinvestmentshow")
    public String toAnnualInvestmentShow() {
        return "annual_investment_plan_show";
    }
    @PostMapping("/addfiles")
    @ResponseBody
    public ResultVO saveFiles(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("annual_investment_file");
        if (files == null || files.size() < 1) {
            return ResultUtil.failed();
        }
        UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        return ResultUtil.success(FileUtil.saveFile(thisUser, files));
    }

    @PostMapping("/save")
    @ResponseBody
    public ResultVO save(@Valid @RequestBody AnnualInvestmentVO annualInvestmentVO, BindingResult bindingResult) {
        if (annualInvestmentVO == null) {
            log.error("【年度投资计划】 年度投资计划申请出错，annualInvestmentVO为空， annualInvestmentVO={}", annualInvestmentVO);
            throw new SysException(SysEnum.ANNUAL_INVESTMENT_APPLY_ERROR);
        }
        if (bindingResult.hasErrors()) {
            log.error("【年度投资计划】 参数验证错误， 参数不正确 annualInvestmentVO = {}， 错误：{}", annualInvestmentVO, bindingResult.getFieldError().getDefaultMessage());
            throw new SysException(SysEnum.DATA_SUBMIT_FAILED.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        String year = annualInvestmentVO.getYear();
        // 如果有同意年份的，并且已经审批通过的，则返回错误。
        if (annualInvestmentService.containsSameYear(year)) {
            log.error("【年度投资计划】 年度投资计划申请出错，计划投资年份重复， year={}", year);
            throw new SysException(SysEnum.ANNUAL_INVESTMENT_APPLY_YEAR_DUPLICATED_ERROR);
        }
        AnnualInvestment annualInvestment = annualInvestmentService.save(annualInvestmentVO);
        if (annualInvestment != null) {
            return ResultUtil.success();
        } else {
            return ResultUtil.failed();
        }
    }

    @GetMapping("/getannualinvestment")
    @ResponseBody
    public ResultVO getAnnualInvestment(@RequestParam(required = false, name = "pageSize", defaultValue = "10") Integer pageSize,
                                        @RequestParam(required = false, name = "startIndex") Integer startIndex,
                                        @RequestParam(required = false, name = "pageIndex", defaultValue = "1") Integer pageIndex,
                                        @RequestParam(required = false, name = "state", defaultValue = "1") byte state) {
        Integer page = pageIndex;
        Integer size = pageSize;
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest pageRequest = new PageRequest(page, size, sort);
        Page<AnnualInvestment> annualInvestments = annualInvestmentService.findByState(pageRequest, state);
        UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        List<String> roles = thisUser.getSysRoleList().stream().map(e -> e.getRole()).collect(Collectors.toList());
        if (roles.contains("checker")) {
            return ResultUtil.success(SysEnum.DATA_CALLBACK_SUCCESS.getCode(), "checker", annualInvestments);
        } else {
            return ResultUtil.success(SysEnum.DATA_CALLBACK_SUCCESS.getCode(), "数据返回成功", annualInvestments);
        }
    }
}
