package com.gzzhsl.pcms.controller.wechat;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.gzzhsl.pcms.cors.MyUsernamePasswordToken;
import com.gzzhsl.pcms.dto.UserAccessToken;
import com.gzzhsl.pcms.dto.WechatUser;
import com.gzzhsl.pcms.entity.PersonInfo;
import com.gzzhsl.pcms.entity.WechatAuth;
import com.gzzhsl.pcms.enums.SysEnum;
import com.gzzhsl.pcms.enums.WechatAuthStateEnum;
import com.gzzhsl.pcms.exception.SysException;
import com.gzzhsl.pcms.service.PersonService;
import com.gzzhsl.pcms.service.UserService;
import com.gzzhsl.pcms.service.WeChatLoginService;
import com.gzzhsl.pcms.service.WechatAuthService;
import com.gzzhsl.pcms.service.impl.WechatLoginWebSocket;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import com.gzzhsl.pcms.util.CodeUtil;
import com.gzzhsl.pcms.util.ResultUtil;
import com.gzzhsl.pcms.util.ShortNetAddressUtil;
import com.gzzhsl.pcms.util.UserUtil;
import com.gzzhsl.pcms.util.wechat.WechatUtil;
import com.gzzhsl.pcms.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * 获取关注公众号之后的微信用户信息的接口，如果在微信浏览器里访问
 * https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc58eaa0636f208e6&redirect_uri=http://sell01.natapp1.cc/wechatlogin/logincheck&role_type=1&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect
 * 则这里将会获取到code,之后再可以通过code获取到access_token 进而获取到用户信息
 * 
 * @author xiangze
 */


@Controller
@RequestMapping("/wechatlogin")
@Slf4j
public class WechatLoginController {

    @Autowired
    private WechatAuthService wechatAuthService;
    @Autowired
    private UserService userService;
    @Autowired
    private PersonService personService;
    @Autowired
    private WechatLoginWebSocket wechatLoginWebSocket;
    @Autowired
    private WeChatLoginService weChatLoginService;

	@RequestMapping(value = "/logincheck", method = { RequestMethod.GET })
	public String doGet(HttpServletRequest request, HttpServletResponse response) {
     /*   log.debug("weixin login get...");
		// 获取微信公众号传输过来的code,通过code可获取access_token,进而获取用户信息
		String code = request.getParameter("code");
		// 这个state可以用来传我们自定义的信息，方便程序调用，这里也可以不用
		// String roleType = request.getParameter("state");
		log.debug("weixin login code:" + code);
		WechatUser user = null;
		String openId = null;
		WechatAuth auth = null;
		UserInfo userInfo = null;
		if (null != code) {  // 扫码后首先获取微信账户信息（获取openId)
			UserAccessToken token;
			try {
				// 通过code获取access_token
				token = WechatUtil.getUserAccessToken(code);
				log.info("weixin login token:" + token.toString());
				// 通过token获取accessToken
				String accessToken = token.getAccessToken();
				// 通过token获取openId
				openId = token.getOpenId();
                // 通过access_token和openId获取用户昵称等信息
				user = WechatUtil.getUserInfo(accessToken, openId);
				log.info("weixin login user:" + user.toString());
				request.getSession().setAttribute("openId", openId);
				auth = wechatAuthService.getWechatAuthByOpenId(openId); // 检查系统中有无wechatAuth信息
                userInfo = userService.findByOpenId(openId); // 检查系统中有无wechatAuth信息
			} catch (IOException e) {
				log.error("error in getUserAccessToken or getUserInfo or findByOpenId: " + e.toString());
				e.printStackTrace();
			}
		}
		// 若微信帐号为空则需要注册微信帐号，同时注册用户信息
		if (auth == null) {
            PersonInfo personInfo = WechatUtil.getPersonInfoFromRequest(user);
            auth = new WechatAuth();
            auth.setOpenId(openId);
            personInfo.setWechatAuth(auth);
            personService.save(personInfo);
            wechatLoginWebSocket.sendMsg(ResultUtil.failed(user));
            return "mobileBindingNote";
        } else if (userInfo == null) {
            wechatLoginWebSocket.sendMsg(ResultUtil.failed(user));
            return "mobileBindingNote";
        } else {
            wechatLoginWebSocket.sendMsg(ResultUtil.success(user));
            return "mobileLoginNote";
        }*/
     return null;
	}

    /**
     * 生成带有url的二维码， 微信扫一扫就能连接到对应的URL里面区
     */
    @RequestMapping(value = "/generateqrcode4login", method = RequestMethod.GET)
    @ResponseBody
    private void generateQRCode4Login(HttpServletRequest request, HttpServletResponse response) {
        UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        long timeStamp = System.currentTimeMillis();
      /*  String content = "{aaauserIdaaa:" + thisUser.getUserId()+",aaacreateTimeaaa:"+timeStamp+"}";*/
        try {
            String longUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc58eaa0636f208e6&redirect_uri=http://pcms.natapp1.cc/wechatlogin/logincheck&response_type=code&scope=snsapi_userinfo&time="+ new Date()+"#wechat_redirect";
            String shortUrl = ShortNetAddressUtil.getShortURL(longUrl);
            BitMatrix qRCodeImg = CodeUtil.generateQRCodeStream(shortUrl, response);
            MatrixToImageWriter.writeToStream(qRCodeImg, "png", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/wechatauthlogin")
    public String toWechatAuthLogin() {
        return "wechatauthlogin";
    }

    @PostMapping("/wechatauthlogin")
    @ResponseBody
    public ResultVO wechatAuthLogin(@RequestBody Map<String, Object> map) {
    /*    System.out.println("wechatauthlogin");
        String openId = (String) map.get("openId");
        System.out.println(openId);
        UserInfo userInfo = userService.findByOpenId(openId);
        if (userInfo != null) {
            Subject subject = SecurityUtils.getSubject();
            MyUsernamePasswordToken token = new MyUsernamePasswordToken(userInfo.getUsername());
            try {
                subject.login(token);
            } catch (AuthenticationException e) {
                log.error("【微信登陆错误】 e -> "+ e.getMessage());
                throw new SysException(SysEnum.LOGIN_INACTIVE_ERROR);
            }
            return ResultUtil.success();
        }
        return ResultUtil.failed();*/
    return null;
    }

    @GetMapping("/wechatbinding")
    public String toWechatBingding() {
        return "wechatBinding";
    }

    @PostMapping("wechatbinding")
    @ResponseBody
    public ResultVO wechatBinding(@RequestBody Map<String, Object> map) {
        String openId = (String) map.get("openId");
        String username = (String) map.get("username");
        String password = (String) map.get("password");
        return weChatLoginService.doWeChatBinding(openId, username, password);
    }



}
