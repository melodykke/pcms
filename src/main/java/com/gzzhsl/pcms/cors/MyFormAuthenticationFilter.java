package com.gzzhsl.pcms.cors;

import com.gzzhsl.pcms.enums.LoginType;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class MyFormAuthenticationFilter extends FormAuthenticationFilter {



    private LoginType loginTypeParamName = LoginType.PASSWORD;

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request
            , ServletResponse response) throws Exception {
        String successUrl = "/index";
        WebUtils.issueRedirect(request,response,successUrl);
        return false;
    }

    @Override
    protected AuthenticationToken createToken(String username, String password, ServletRequest request, ServletResponse response) {
        boolean rememberMe = isRememberMe(request);
        String host = getHost(request);
        String loginType = LoginType.PASSWORD.getCode();

        if(request.getParameter("loginType")!=null && !"".equals(request.getParameter("loginType").trim())){
            loginType = request.getParameter("loginType");
        }

        return new MyUsernamePasswordToken(username, password,loginType,rememberMe,host);
    }

    public LoginType getLoginTypeParamName() {
        return loginTypeParamName;
    }

    public void setLoginTypeParamName(LoginType loginTypeParamName) {
        this.loginTypeParamName = loginTypeParamName;
    }


}