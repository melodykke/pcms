package com.gzzhsl.pcms.controller;

import com.gzzhsl.pcms.dto.UserInfoDTO;
import com.gzzhsl.pcms.enums.SysEnum;
import com.gzzhsl.pcms.exception.SysException;
import com.gzzhsl.pcms.service.AccountService;
import com.gzzhsl.pcms.service.UserService;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import com.gzzhsl.pcms.util.ResultUtil;
import com.gzzhsl.pcms.vo.AccountPasswordVO;
import com.gzzhsl.pcms.vo.AccountVO;
import com.gzzhsl.pcms.vo.ResultVO;
import com.gzzhsl.pcms.vo.UserInfoVO;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.Account;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/account")
@Slf4j
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;

    @GetMapping("/accountconfig")
    @RequiresRoles(value = {"checker", "reporter"}, logical = Logical.OR)
    public String accountConfig(){
        return "account_config";
    }
    @GetMapping("/toaccount")
    @RequiresRoles(value = "manager")
    public String toAccount(){
        return "create_account";
    }
    @GetMapping("/toaccountmanagement")
    @RequiresRoles(value = "manager")
    public String toAccountManagement(){
        return "manage_account";
    }
    @PostMapping("/createaccount")
    @ResponseBody
    @RequiresRoles(value = "manager")
    public ResultVO createAccount(@RequestBody @Valid UserInfoDTO userInfoDTO, BindingResult bindingResult) {
        if (userInfoDTO == null) {
            log.error("【账户错误】 开通账户错误！ userInfoDTO为空！ userInfoDTO={}", userInfoDTO);
            throw new SysException(SysEnum.CREATE_USER_ACCOUNT_ERROR);
        }
        if (bindingResult.hasErrors()) {
            log.error("【账户错误】开通账户错误， 参数不正确 userInfoDTO = {}， 错误：{}", userInfoDTO, bindingResult.getFieldError().getDefaultMessage());
            throw new SysException(SysEnum.DATA_SUBMIT_FAILED.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        UserInfo userInfo = accountService.createUserAccount(userInfoDTO);
        if (userInfo != null && userInfo.getUserId() != null) {
            return ResultUtil.success();
        }else {
            return ResultUtil.failed();
        }
    }

    @GetMapping("/getuseraccounts")
    @ResponseBody
    @RequiresRoles(value = "manager")
    public Page<UserInfoVO> getUserAccounts(@RequestParam(required = false, name = "rows", defaultValue = "15") Integer pageSize,
                                          @RequestParam(required = false, name = "startIndex") Integer startIndex,
                                          @RequestParam(required = false, name = "page", defaultValue = "1") Integer pageIndex,
                                          @RequestParam(required = false, name = "type", defaultValue = "") String type) {
        Integer page = pageIndex-1;
        Integer size = pageSize;
        Sort sort = new Sort(Sort.Direction.ASC, "username");
        PageRequest pageRequest = new PageRequest(page, size, sort);
        Page<UserInfoVO> userInfoVOPage = userService.findAll(pageRequest);
        return userInfoVOPage;
    }

    @PostMapping("/modifypassword")
    @ResponseBody
    public ResultVO modifyPassword(@Valid @RequestBody AccountPasswordVO accountPasswordVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【账户错误】参数验证错误， 参数不正确 AccountPasswordVO = {}， 错误：{}", accountPasswordVO, bindingResult.getFieldError().getDefaultMessage());
            throw new SysException(SysEnum.DATA_SUBMIT_FAILED.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        if (!accountPasswordVO.getNewPassword().equals(accountPasswordVO.getReNewPassword())) {
            log.error("【账户错误】参数验证错误， 新密码和确认密码不一致 AccountPasswordVO = {}", accountPasswordVO);
            throw new SysException(SysEnum.ACCOUNT_PASSWORD_INCONSISTENCY);
        }
        // 判断若新密码与本账号密码是否相同
        if (accountService.modifyPassword(accountPasswordVO)) {
            return ResultUtil.success(SysEnum.DATA_CONFIG_SUCCESS.getCode(), accountPasswordVO.getNewPassword());
        } else {
            return ResultUtil.failed(SysEnum.DATA_SUBMIT_FAILED.getCode(), "密码更新失败，请重试！");
        }
    }

    @PostMapping("/addsubaccount")
    @ResponseBody
    @RequiresRoles(value = {"checker"})
    public ResultVO addSubAccount(@Valid @RequestBody AccountVO accountVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【账户错误】参数验证错误， 参数不正确 accountVO = {}， 错误：{}", accountVO, bindingResult.getFieldError().getDefaultMessage());
            throw new SysException(SysEnum.DATA_SUBMIT_FAILED.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        // 判断密码是否一致
        if (!accountVO.getPassword().equals(accountVO.getRePassword())) {
            log.error("【账户错误】参数验证错误， 密码和确认密码不一致 accountVO = {}", accountVO);
            throw new SysException(SysEnum.ACCOUNT_PASSWORD_INCONSISTENCY);
        }
        UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        List<UserInfo> children = userService.findByUserId(thisUser.getUserId()).getChildren();
        // 目前允许一个账号有一个子账号
        if (children == null || children.size() > 0) {
            log.error("【账户错误】 本账号已存在子账号，目前只允许一个主账号添加一个子账号");
            throw new SysException(SysEnum.ACCOUNT_PASSWORD_INCONSISTENCY);
        }
        if (accountService.createSubAccount(accountVO, thisUser)) {
            return ResultUtil.success(SysEnum.DATA_CALLBACK_SUCCESS.getCode(), accountVO.getUsername());
        } else {
            return ResultUtil.failed();
        }
    }

    @GetMapping("/getsubaccountinfo")
    @ResponseBody
    public ResultVO getSubAccountInfo() {
        UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        List<UserInfo> children = userService.findByUserId(thisUser.getUserId()).getChildren();
        if (children == null || children.size() == 0) {
            return ResultUtil.failed("没有对应的子账号");
        } else if (children.size() == 1){
            UserInfo child = children.get(0);
            UserInfo userInfo = new UserInfo();
            userInfo.setUsername(child.getUsername());
            userInfo.setActive(child.getActive());
            return ResultUtil.success(userInfo);
        } else {
            log.error("【账户错误】 获取子账号出错");
            throw new SysException(SysEnum.Sys_INNER_ERROR);
        }
    }

    @PostMapping("/activate")
    @ResponseBody
    public ResultVO activate() {
        UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        List<UserInfo> children = userService.findByUserId(thisUser.getUserId()).getChildren();
        if (children == null) {
            return ResultUtil.failed();
        } else if (children.size() == 1){
            UserInfo child = children.get(0);
            if(child.getActive()==0){
                child.setActive((byte)1);
            }else if(child.getActive()==1){
                child.setActive((byte)0);
            }
            userService.toggleActivate(child);
            return ResultUtil.success();
        } else {
            log.error("【账户错误】 获取子账号出错，系统错误");
            throw new SysException(SysEnum.Sys_INNER_ERROR);
        }
    }


}
