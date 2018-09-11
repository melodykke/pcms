package com.gzzhsl.pcms.controller.Oauth;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gzzhsl.pcms.cors.MyUsernamePasswordToken;
import com.gzzhsl.pcms.enums.SysEnum;
import com.gzzhsl.pcms.exception.SysException;
import com.gzzhsl.pcms.service.UserService;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import com.gzzhsl.pcms.util.Oauth;
import com.gzzhsl.pcms.vo.OauthTokenVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 获取青云平台认证token
 *
 */
@Slf4j
@Controller
@RequestMapping("/loginOther")
public class OauthController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = { RequestMethod.GET })
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        // 获取青云
        String accessToken = request.getParameter("access_token");
        // 将获取的access_token连同 业务系统注册码 业务系统KEY 权限版本 应用ID 一起返回青云
        // 最后返回的JSON字符串及认证结果信息（成功包含人员信息）
        String rtStr = Oauth.sendGet("http://114.215.150.204:20350/me", accessToken);

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            OauthTokenVO oauthTokenVO = objectMapper.readValue(rtStr, OauthTokenVO.class);
            // 认证未通过 直接跳转百度
            if (oauthTokenVO.getMeta().getCode() != 1) {
                log.error("单点登录未验证通过 oauthTokenVO：", oauthTokenVO);
                response.sendRedirect("https://www.baidu.com/"); // TODO
            }
            String username = oauthTokenVO.getData().getLoginName();
            // 青云的认证通过 则拿到用户名在本系统里获取权限
            if (username == "" || username == null) {
                log.error("单点登录验证通过，但为获取到用户名！ oauthTokenVO：", oauthTokenVO);
                response.sendRedirect("https://www.baidu.com/"); // TODO
            }
            UserInfo thisUser = userService.getUserByUsername(username);
            if (thisUser == null) {
                log.error("单点登录验证通过，但未获取到对应本系统账号信息！ oauthTokenVO：", oauthTokenVO);
                response.sendRedirect("https://www.baidu.com/"); // TODO
            }
            Subject subject = SecurityUtils.getSubject();
            MyUsernamePasswordToken token = new MyUsernamePasswordToken(thisUser.getUsername());
            try {
                subject.login(token);
                response.sendRedirect("/index");
            } catch (AuthenticationException e) {
                log.error("【单点登录对应本系统账号在系统认证时出错】 e -> "+ e.getMessage());
                throw new SysException(SysEnum.LOGIN_INACTIVE_ERROR);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
