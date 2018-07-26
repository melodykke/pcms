
package com.gzzhsl.pcms.shiro.config;

import com.gzzhsl.pcms.cors.MyUsernamePasswordToken;
import com.gzzhsl.pcms.exception.InactivatedException;
import com.gzzhsl.pcms.service.UserService;
import com.gzzhsl.pcms.shiro.bean.SysPermission;
import com.gzzhsl.pcms.shiro.bean.SysRole;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class MyShiroRealm extends AuthorizingRealm{

	@Autowired
	private UserService userService;



/**
	 * 身份认证  --- 登陆
	 * @param token
	 * @return
	 * @throws AuthenticationException
	 */

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("MyShiroRealm.doGetAuthenticationInfo()");


		/**
		 * 1. 获取用户输入的账号
		 * 2. 通过username 从数据库中查找，获取userbean对象
		 * 3. 加密， 使用SimpleAuthenticationInfo 进行身份处理
		 * 4. 返回身份处理对象
		 */

		MyUsernamePasswordToken myToken = (MyUsernamePasswordToken) token;
		// 1. 获取用户输入的账号
		String username = (String)myToken.getPrincipal();
        System.out.println(username);
        System.out.println("token.getCredentials:"+myToken.getCredentials());
        System.out.println(new String((char[])myToken.getCredentials()));
        // 2. 通过accountName 从数据库中查找，获取userInfo对象
		UserInfo userInfo = userService.getUserByUsername(username); //这里取到以后，自动放进principals里，下面认证直接取。
		// 判断是否有userInfo
		if(userInfo == null){
			return null;
		}
		if (userInfo.getActive() == 0) {
			throw new InactivatedException("账号未激活！");
		}
		// 3. 加密， 使用SimpleAuthenticationInfo 进行身份处理
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(userInfo, userInfo.getPassword(), ByteSource.Util.bytes(userInfo.getSalt()), this.getName());
		return simpleAuthenticationInfo;
	}




/**
	 * 权限认证
	 *
	 */


	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//如果打印信息只执行一次的话，说明缓存生效了，否则不生效. --- 配置缓存成功之后，只会执行1次/每个用户，因为每个用户的权限是不一样的. ehcache起作用就只有一次
		System.out.println("MyShiroRealm.doGetAuthorizationInfo()");
		//使用shiro提供的类
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		//获取用户的权限信息
		UserInfo userInfo = (UserInfo)principals.getPrimaryPrincipal();
		for(SysRole sysRole : userInfo.getSysRoleList()){
			//添加角色
			simpleAuthorizationInfo.addRole(sysRole.getRole());
			//添加权限
			for(SysPermission sysPermission : sysRole.getSysPermissionList()){
				simpleAuthorizationInfo.addStringPermission(sysPermission.getPermission());
			}
		}
		return simpleAuthorizationInfo;
	}

	// 清除缓存
	public void clearCached() {
		PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
		super.clearCache(principals);
	}
}

