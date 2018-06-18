package com.gzzhsl.pcms.controller;

import com.gzzhsl.pcms.enums.SysEnum;
import com.gzzhsl.pcms.exception.SysException;
import com.gzzhsl.pcms.service.AccountService;
import com.gzzhsl.pcms.util.ResultUtil;
import com.gzzhsl.pcms.vo.AccountVO;
import com.gzzhsl.pcms.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/account")
@Slf4j
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/accountconfig")
    public String accountConfig(){
        return "account_config";
    }

    @PostMapping("/modifypassword")
    @ResponseBody
    public ResultVO modifyPassword(@Valid @RequestBody AccountVO accountVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【账户错误】参数验证错误， 参数不正确 accountVO = {}， 错误：{}", accountVO, bindingResult.getFieldError().getDefaultMessage());
            throw new SysException(SysEnum.DATA_SUBMIT_FAILED.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        if (!accountVO.getNewPassword().equals(accountVO.getReNewPassword())) {
            log.error("【账户错误】参数验证错误， 新密码和确认密码不一致 accountVO = {}", accountVO);
            throw new SysException(SysEnum.ACCOUNT_PASSWORD_INCONSISTENCY);
        }
        // 判断若新密码与本账号密码是否相同
        if (accountService.modifyPassword(accountVO)) {
            return ResultUtil.success(SysEnum.DATA_CONFIG_SUCCESS.getCode(), accountVO.getNewPassword());
        } else {
            return ResultUtil.failed(SysEnum.DATA_SUBMIT_FAILED.getCode(), "密码更新失败，请重试！");
        }
    }


}
