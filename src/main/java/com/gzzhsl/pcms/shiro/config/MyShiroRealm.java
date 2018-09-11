
package com.gzzhsl.pcms.shiro.config;

import com.gzzhsl.pcms.cors.MyUsernamePasswordToken;
import com.gzzhsl.pcms.exception.InactivatedException;
import com.gzzhsl.pcms.model.SysPermission;
import com.gzzhsl.pcms.model.SysRole;
import com.gzzhsl.pcms.model.UserInfo;
import com.gzzhsl.pcms.service.UserService;
import com.gzzhsl.pcms.service.impl.WebSocket;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

public class MyShiroRealm extends AuthorizingRealm{

	@Autowired
	private UserService userService;
    @Autowired
    private SessionDAO sessionDAO;
    @Autowired
	private WebSocket webSocket;

	/**
	 * 身份认证  --- 登陆
	 * @param token
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		/**
		 * 1. 获取用户输入的账号
		 * 2. 通过username 从数据库中查找，获取userbean对象
		 * 3. 加密， 使用SimpleAuthenticationInfo 进行身份处理
		 * 4. 返回身份处理对象
		 */
		MyUsernamePasswordToken myToken = (MyUsernamePasswordToken) token;
		// 1. 获取用户输入的账号
		String username = (String)myToken.getPrincipal();
        // 2. 通过accountName 从数据库中查找，获取userInfo对象
		UserInfo userInfo = userService.findOneWithRolesAndPrivilegesByUsernameOrId(username, null); //这里取到以后，自动放进principals里，下面认证直接取。
        //以下为只允许同一账户单个登录
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        if(sessions.size()>0) {
            for (Session session : sessions) {
                System.out.println("::::::::::::::::" + session);
                //获得session中已经登录用户的名字
                if(null!=session.getAttribute("username")) {
                   /* String loginUsername = ((UserInfo)session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)).getUsername();*/
                    String loginUsername = (String)session.getAttribute("username");
                    System.out.println("::::::::::::::::" + loginUsername);
                    if (username.equals(loginUsername)) {  //这里的username也就是当前登录的username
						//webSocket.sendMsg("session提示信息", "session超时或用户异地登录，您已被迫下线！", "/login", userInfo);
                        session.stop();  //这里就把session清除，
                        System.out.println("重复登录 弹出了之前的用户"+username);
                    }
                }
            }
        }
		// 判断是否有userInfo
		if(userInfo == null){
			return null;
		}
		if (userInfo.getActive() == 0) {
			throw new InactivatedException("账号未激活！");
		}
		// 3. 加密， 使用SimpleAuthenticationInfo 进行身份处理
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(userInfo, userInfo.getPassword(), ByteSource.Util.bytes(userInfo.getSalt()), this.getName());
        System.out.println("MyShiroRealm.doGetAuthenticationInfo()");
		return simpleAuthenticationInfo;
	}

	/**
	 * 权限认证
	 *
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//使用shiro提供的类
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		//获取用户的权限信息
		UserInfo userInfo = (UserInfo)principals.getPrimaryPrincipal();
		for(SysRole sysRole : userInfo.getRoles()){
			//添加角色
			simpleAuthorizationInfo.addRole(sysRole.getRole());
			//添加权限
			for(SysPermission sysPermission : sysRole.getPermissions()){
				simpleAuthorizationInfo.addStringPermission(sysPermission.getPermission());
			}
		}

        SecurityUtils.getSubject().getSession().setAttribute("username", userInfo.getUsername());
        //如果打印信息只执行一次的话，说明缓存生效了，否则不生效. --- 配置缓存成功之后，只会执行1次/每个用户，因为每个用户的权限是不一样的. ehcache起作用就只有一次
        System.out.println("MyShiroRealm.doGetAuthorizationInfo()");
		return simpleAuthorizationInfo;
	}

	// 清除缓存
	public void clearCached() {
		PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
		super.clearCache(principals);
	}
}

