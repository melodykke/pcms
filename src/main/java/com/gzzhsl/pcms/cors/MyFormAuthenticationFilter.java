package com.gzzhsl.pcms.cors;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class MyFormAuthenticationFilter extends FormAuthenticationFilter {


    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request
            , ServletResponse response) throws Exception {
        String successUrl = "/index";
        WebUtils.issueRedirect(request,response,successUrl);
        return false;
    }
}