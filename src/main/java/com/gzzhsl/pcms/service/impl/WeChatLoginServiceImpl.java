package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.entity.PersonInfo;
import com.gzzhsl.pcms.entity.WechatAuth;
import com.gzzhsl.pcms.enums.SysEnum;
import com.gzzhsl.pcms.exception.SysException;
import com.gzzhsl.pcms.service.PersonService;
import com.gzzhsl.pcms.service.UserService;
import com.gzzhsl.pcms.service.WeChatLoginService;
import com.gzzhsl.pcms.service.WechatAuthService;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import com.gzzhsl.pcms.util.ResultUtil;
import com.gzzhsl.pcms.util.UserUtil;
import com.gzzhsl.pcms.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class WeChatLoginServiceImpl implements WeChatLoginService {

    @Autowired
    private UserService userService;
    @Autowired
    private PersonService personService;
    @Autowired
    private WechatAuthService wechatAuthService;

    @Override
    public ResultVO doWeChatBinding(String openId, String username, String password) {
      /*  UserInfo userInfo = userService.getUserByUsername(username);
        if (userInfo == null) {
            return ResultUtil.failed("查无此用户！！");
        } else {
            if (!userInfo.getPassword().equals(UserUtil.getEncriPwd(password))){
                return ResultUtil.failed("账户密码不正确！！");
            } else {
                Integer intRt = userService.updateUserOpenId(openId, userInfo.getUserId());
                if (intRt != 1) {
                    log.error("【微信模块】 微信绑定出错");
                    throw new SysException(SysEnum.WECHAT_BINDING_ERROR);
                }
                // 绑定微信个人信息，如果该账户没有个人信息和有个人信息的两种情况
                PersonInfo thisPerson = userInfo.getPersonInfo();
                WechatAuth wechatAuth = wechatAuthService.getWechatAuthByOpenId(openId);
                PersonInfo weChatAuthPersonInfo = personService.getByWeChatAuth(wechatAuth);
                if (thisPerson != null) {
                    thisPerson.setNickName(weChatAuthPersonInfo.getNickName());
                    thisPerson.setProfileImg(weChatAuthPersonInfo.getProfileImg());
                    thisPerson.setCountry(weChatAuthPersonInfo.getCountry());
                    thisPerson.setProvince(weChatAuthPersonInfo.getProvince());
                    thisPerson.setCity(weChatAuthPersonInfo.getCity());
                    thisPerson.setWechatAuth(weChatAuthPersonInfo.getWechatAuth());
                    personService.save(thisPerson);
                    personService.deleteByPersonId(weChatAuthPersonInfo.getPersonId());
                } else {
                    weChatAuthPersonInfo.setUserInfo(userInfo);
                    personService.save(weChatAuthPersonInfo);
                }
                return ResultUtil.success();
            }
        }*/
      return null;
    }
}
