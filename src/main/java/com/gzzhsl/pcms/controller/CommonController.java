package com.gzzhsl.pcms.controller;

import com.gzzhsl.pcms.cors.MyUsernamePasswordToken;
import com.gzzhsl.pcms.enums.SysEnum;
import com.gzzhsl.pcms.exception.InactivatedException;
import com.gzzhsl.pcms.exception.SysException;
import com.gzzhsl.pcms.form.UserSigninForm;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import com.gzzhsl.pcms.util.ResultUtil;
import com.gzzhsl.pcms.vo.ResultVO;
import com.gzzhsl.pcms.vo.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/")
@Slf4j
public class CommonController {


    @GetMapping("/login")
    private String login(){
        return "login";
    }

    /**
     * 用户账号登录 若在realm中的doAuthentication判断为非法，则出发该方法，否则顺利按照shiroConfiguration中配置的setSuccessUrl跳转。
     * @param userSigninForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/login")
    public String login (@Valid UserSigninForm userSigninForm, BindingResult bindingResult, HttpServletRequest request,
                         Map<String, Object> map){
        if(bindingResult.hasErrors()){
            log.error("【账号错误】 账号登录错误， 参数不正确 userSigninForm = {}", userSigninForm);
            throw new SysException(SysEnum.SIGNIN_PARAM_ERROR);
        }

        String exception = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        String msg = "";
        if (exception != null) {
            if (UnknownAccountException.class.getName().equals(exception)) {
                log.error("UnknownAccountException -- > 账号不存在  userSigninForm = {}", userSigninForm);
                msg = "账号不存在或账号尚未激活，请确认后重试！";
            }  else if (IncorrectCredentialsException.class.getName().equals(exception)) {
                log.error("IncorrectCredentialsException -- > 密码不正确：", userSigninForm);
                msg = "密码不正确，请重新输入！";
            } else if ("kaptchaValidateFailed".equals(exception)) {
                log.error("kaptchaValidateFailed -- > 验证码错误", userSigninForm);
                msg = "验证码错误，请重新输入！";
            } else if (InactivatedException.class.getName().equals(exception) ) {
                log.error("InactivatedException -- > 账号未激活");
                msg = "账号未激活！";
            } else {
                log.error("else -- >" + exception);
                msg = "未知错误，请稍后重试 ";
            }
        }else {
            return "index";
        }
        map.put("msg", msg);
        // 此方法不处理登录成功,由shiro进行处理
        return "login";
    }


    @GetMapping(value = {"/index", "/", ""})
    private String index(){
        return "index";
    }

    @GetMapping("/page403")
    private String page403(){
        return "page403";
    }
}
