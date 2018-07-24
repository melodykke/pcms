package com.gzzhsl.pcms.controller;

import com.gzzhsl.pcms.entity.AnnualInvestment;
import com.gzzhsl.pcms.entity.BaseInfo;
import com.gzzhsl.pcms.entity.Feedback;
import com.gzzhsl.pcms.entity.Tender;
import com.gzzhsl.pcms.enums.SysEnum;
import com.gzzhsl.pcms.exception.SysException;
import com.gzzhsl.pcms.service.AnnualInvestmentService;
import com.gzzhsl.pcms.service.TenderService;
import com.gzzhsl.pcms.service.UserService;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import com.gzzhsl.pcms.util.FileUtil;
import com.gzzhsl.pcms.util.ResultUtil;
import com.gzzhsl.pcms.vo.AnnualInvestmentVO;
import com.gzzhsl.pcms.vo.ResultVO;
import com.gzzhsl.pcms.vo.TenderVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
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
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequestMapping("/tender")
public class TenderController {
    @Autowired
    private TenderService tenderService;
    @Autowired
    private UserService userService;

    private String tenderId;

    @GetMapping("/tonewtender")
    public String toNewTender(String tenderId) {
        this.tenderId = tenderId;
        return "tender_new";
    }
    @GetMapping("/isedit")
    @ResponseBody
    public ResultVO isEdit() {
        if (tenderId != null && tenderId != "") {
            TenderVO tenderVO = tenderService.getById(tenderId);
            return ResultUtil.success(tenderVO);
        }
        return null;
    }
    @GetMapping("/totendershow")
    public String toTenderShow() {
        return "tender_show";
    }

    @PostMapping("/addfiles")
    @ResponseBody
    public ResultVO saveFiles(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("tender_new_new_file");
        if (files == null || files.size() < 1) {
            return ResultUtil.failed();
        }
        UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        return ResultUtil.success(FileUtil.saveFile(thisUser, files));
    }

    @PostMapping("/save")
    @ResponseBody
    public ResultVO save(@Valid @RequestBody TenderVO tenderVO, BindingResult bindingResult) {
        if (tenderVO == null) {
            log.error("【招标管理】 新增招标项目出错，tenderVO为空， tenderVO={}", tenderVO);
            throw new SysException(SysEnum.TENDER_NEW_ERROR);
        }
        if (bindingResult.hasErrors()) {
            log.error("【招标管理】 参数验证错误， 参数不正确 tenderVO = {}， 错误：{}", tenderVO, bindingResult.getFieldError().getDefaultMessage());
            throw new SysException(SysEnum.DATA_SUBMIT_FAILED.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        Tender tender = tenderService.save(tenderVO);
        if (tender != null) {
            return ResultUtil.success();
        } else {
            return ResultUtil.failed();
        }
    }

    @GetMapping("/gettender")
    @ResponseBody
    public ResultVO getAnnualInvestment(@RequestParam(required = false, name = "pageSize", defaultValue = "10") Integer pageSize,
                                        @RequestParam(required = false, name = "startIndex") Integer startIndex,
                                        @RequestParam(required = false, name = "pageIndex", defaultValue = "1") Integer pageIndex,
                                        @RequestParam(required = false, name = "state", defaultValue = "1") byte state) {
        Integer page = pageIndex;
        Integer size = pageSize;
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest pageRequest = new PageRequest(page, size, sort);
        Page<Tender> tenders = tenderService.findByState(pageRequest, state);
        UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        List<String> roles = thisUser.getSysRoleList().stream().map(e -> e.getRole()).collect(Collectors.toList());
        if (roles.contains("checker")) {
            return ResultUtil.success(SysEnum.DATA_CALLBACK_SUCCESS.getCode(), "checker", tenders);
        } else {
            return ResultUtil.success(SysEnum.DATA_CALLBACK_SUCCESS.getCode(), "数据返回成功", tenders);
        }
    }

    @GetMapping("gettenderbyid")
    @ResponseBody
    public ResultVO getTenderById(String id) {
        TenderVO tenderVO = tenderService.getById(id);
        return ResultUtil.success(tenderVO);
    }

    @PostMapping("/approvetender")
    @ResponseBody
    @RequiresRoles(value = {"checker"})
    public ResultVO approveTender(@RequestBody Map<String, Object> params) {
        UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        BaseInfo thisProject = userService.findByUserId(thisUser.getUserId()).getBaseInfo();
        List<Tender> tenders = thisProject.getTenders();
        Boolean switchState = (boolean) params.get("switchState"); // true: 按钮未通过 false：按钮通过
        String checkinfo = (String) params.get("checkinfo");
        String tenderId = (String) params.get("tenderId");
        Tender thisTender = tenderService.findById(tenderId);
        if (tenderId == null || tenderId == "") {
            log.error("【招标备案】审批招标备案错误 招标备案ID tenderId为空");
            throw new SysException(SysEnum.TENDER_APPROVAL_ERROR);
        }
        if (thisTender == null) {
            log.error("【招标备案】审批查询的招标备案所对应ID无记录");
            throw new SysException(SysEnum.TENDER_NO_CORRESPOND_RECORD_ERROR);
        }
        if (tenders.size() > 0) {
            boolean noAffiliatedTender = true;
            for (Tender tender : tenders) {
                if (tenderId.equals(tender.getTenderId())) {
                    noAffiliatedTender = false;
                    break;
                }
            }
            if (noAffiliatedTender) {
                log.error("【合同备案错误】不能审批不属于本用户所属工程的合同备案信息");
                throw new SysException(SysEnum.TENDER_NO_CORRESPOND_RECORD_ERROR);
            }
        }
        if (thisTender.getState() != (byte) 0) {
            log.error("【合同备案错误】当前合同备案信息已经审批过，不能重复审批");
            throw new SysException(SysEnum.TENDER_CHECKED_OTHERS_ERROR);
        }
        Feedback feedback = tenderService.approveTender(thisUser, switchState, checkinfo, thisTender);
        if (feedback.getState().equals((byte) 1)) {
            return ResultUtil.success(feedback);
        } else {
            return ResultUtil.failed(feedback);
        }
    }
}
