package com.gzzhsl.pcms.shiro.config;


import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.gzzhsl.pcms.cors.MyFormAuthenticationFilter;
import com.gzzhsl.pcms.cors.MyHashedCredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.cas.CasFilter;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.cas.CasSubjectFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * 这是shiro配置类
 * ----------------------
 * 1. 需要配置ShiroFilterFactory : ShiroFilterFactoryBean
 * 2. 配置SecurityManager
 * @author melodykke
 *
 */

@Configuration  //这是shiro配置类
public class ShiroConfiguration {
    private static final String casFilterUrlPattern = "/shiro-cas";

    @Bean(name = "shiroFilter") //注入： shiroFilter
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager,
                                              CasFilter casFilter,
                                              @Value("${shiro.cas}") String casServerUrlPrefix,
                                              @Value("${shiro.server}") String shiroServerUrlPrefix){

        /**
         * 定义shiro filter 工厂类
         * 1. 定义ShiroFilterFactoryBean
         * 2. 设置securityManager
         * 3. 配置拦截器
         * 4. 返回ShiroFilterFactoryBean
         **/

        //1. 定义ShiroFilterFactoryBean
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //2. 设置securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //3. 配置拦截器: 使用map进行配置,LinkedHashMap是有序的,shiro会根据添加的顺序进行拦截
        //拦截器.
        String loginUrl = casServerUrlPrefix + "/login?service=" + shiroServerUrlPrefix + casFilterUrlPattern;
        shiroFilterFactoryBean.setLoginUrl(loginUrl);
        shiroFilterFactoryBean.setSuccessUrl("/");
        Map<String, Filter> filters = new HashMap<>();
        filters.put("casFilter", casFilter);
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setRedirectUrl(casServerUrlPrefix + "/logout?service=" + shiroServerUrlPrefix);
        filters.put("logout",logoutFilter);


        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();
        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/css/**","anon");
        filterChainDefinitionMap.put("/js/**","anon");
        filterChainDefinitionMap.put("/font-awesome/**","anon");
        filterChainDefinitionMap.put("/fonts/**","anon");
        filterChainDefinitionMap.put("/img/**","anon");
        filterChainDefinitionMap.put("/favicon.ico", "anon");
        filterChainDefinitionMap.put("/img/11.ico", "anon");
        filterChainDefinitionMap.put("/register", "anon");
        filterChainDefinitionMap.put("/wechat", "anon"); // 配置wechat的token验证路径
        filterChainDefinitionMap.put("/wechatlogin/**", "anon"); // 配置wechat的验证验证路径
        filterChainDefinitionMap.put("/wechatloginwebsocket", "anon");
        filterChainDefinitionMap.put("/shiro-cas", "anon");
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "logout");
        //<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        filterChainDefinitionMap.put("/**", "authc");
        filters.put("authc", new MyFormAuthenticationFilter());
        shiroFilterFactoryBean.setFilters(filters);

        //8. 设置未授权界面
        shiroFilterFactoryBean.setUnauthorizedUrl("/page403");
        //9. 把配置的filterChainMap配置到shiroFilterFactoryBean里
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        //10. 返回一个shiroFilterFactoryBean
        loadShiroFilterChain(shiroFilterFactoryBean);
        return shiroFilterFactoryBean;
    }

    /****************************************shiro核心 SecurityManager ***********************************************************/

    @Bean(name = "securityManager") //注入： securityManager
    public DefaultWebSecurityManager securityManager(/*@Qualifier("myShiroRealm") AuthorizingRealm authorizingRealm,*/
                                                     @Qualifier("myShiroCasRealm") MyShiroCasRealm myShiroCasRealm,
                                                     @Qualifier("ehCacheManager") EhCacheManager ehCacheManager,
                                                     @Qualifier("sessionManager") DefaultWebSessionManager sessionManager){

        /**
         * 定义shiro的安全管理器
         * 用shiro自带Web安全管理器
         */

        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();

        //设置自定义realm
        securityManager.setRealm(myShiroCasRealm);
        //设置ehcache缓存管理器
        securityManager.setCacheManager(ehCacheManager);
        securityManager.setSubjectFactory(new CasSubjectFactory());
        //设置rememberMe cookie
        //securityManager.setRememberMeManager(cookieRememberMeManager());
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }
    private void loadShiroFilterChain(ShiroFilterFactoryBean shiroFilterFactoryBean) {
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        filterChainDefinitionMap.put(casFilterUrlPattern, "casFilter");
        filterChainDefinitionMap.put("/logout","logout");
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
    }
    /**
     * CAS Filter
     */
    @Bean(name = "casFilter")
    public CasFilter getCasFilter(@Value("${shiro.cas}") String casServerUrlPrefix,
                                  @Value("${shiro.server}") String shiroServerUrlPrefix) {
        CasFilter casFilter = new CasFilter();
        casFilter.setName("casFilter");
        casFilter.setEnabled(true);
        String loginUrl = casServerUrlPrefix + "/login?service=" + shiroServerUrlPrefix + casFilterUrlPattern;
        casFilter.setFailureUrl(loginUrl);
        return casFilter;
    }
    /****************************************加密算法注入自定义realm***********************************************************/

/*	@Bean(name = "myShiroRealm") //注入自定义realm
	public MyShiroRealm myShiroRealm(){
		System.out.println("ShiroConfiguration.myShiroRealm() initiating...");
		MyShiroRealm myShiroRealm = new MyShiroRealm();
		myShiroRealm.setCredentialsMatcher(myHashedCredentialsMatcher());
		return myShiroRealm;
	}*/
    @Bean(name = "myShiroCasRealm") //注入自定义realm
    public MyShiroCasRealm myShiroCasRealm(@Value("${shiro.cas}") String casServerUrlPrefix,
                                           @Value("${shiro.server}") String shiroServerUrlPrefix){
        System.out.println("ShiroConfiguration.myShiroCasRealm() initiating...");
        MyShiroCasRealm myShiroCasRealm = new MyShiroCasRealm();
        myShiroCasRealm.setCasServerUrlPrefix(casServerUrlPrefix);
        myShiroCasRealm.setCasService(shiroServerUrlPrefix + casFilterUrlPattern);
        return myShiroCasRealm;
    }
    @Bean //注入加密算法
    public MyHashedCredentialsMatcher myHashedCredentialsMatcher(){
        MyHashedCredentialsMatcher myHashedCredentialsMatcher = new MyHashedCredentialsMatcher();
        myHashedCredentialsMatcher.setHashAlgorithmName("md5");//加密算法
        myHashedCredentialsMatcher.setHashIterations(2);//散列次数
        return myHashedCredentialsMatcher;
    }

    /****************************************Shiro生命周期处理器***********************************************************/

    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

/****************************************配置controller里面访问url权限的permission检验，用 aop ***********************************************************/


    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
     * @return
     */

    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }
    @Bean //开启shiro aop注解（检查访问链接者的permission）
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

/****************************************ShiroDialect***********************************************************/


    /**
     * 添加ShiroDialect 为了在thymeleaf里使用shiro的标签的bean
     * @return
     */

    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }

    /****************************************ehcache***********************************************************/

    @Bean(name = "ehCacheManager") //注入ehcache
    public EhCacheManager ehCacheManager(){
        EhCacheManager ehCacheManager = new EhCacheManager();
        //配置缓存文件
        ehCacheManager.setCacheManagerConfigFile("classpath:shiro-config/ehcache-shiro.xml");

        return ehCacheManager;
    }

    /****************************************cookie***********************************************************/
    @Bean(name="sessionDAO")
    public MemorySessionDAO getMemorySessionDAO()
    {
        return new MemorySessionDAO();
    }
    @Bean(name = "simpleCookie") //配置cookie对象
    public SimpleCookie rememberMeCookie(){
        //new一个SimpleCookie，名称为前端checkbox的name属性值（='rememberMe'）
        SimpleCookie simpleCookie = new SimpleCookie("SHRIOSESSIONID");
        //可选；配置cookie的生效时间。单位时秒，下面时1天；
        simpleCookie.setMaxAge(24*60*60);
        return simpleCookie;
    }
    //配置shiro session 的一个管理器
    @Bean(name = "sessionManager")
    public DefaultWebSessionManager getDefaultWebSessionManager(@Qualifier("sessionDAO") MemorySessionDAO sessionDAO,
                                                                @Qualifier("simpleCookie") SimpleCookie simpleCookie)
    {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(sessionDAO);
        sessionManager.setSessionIdCookie(simpleCookie);
        return sessionManager;
    }
    //配置session的缓存管理器
    @Bean(name= "shiroCacheManager")
    public MemoryConstrainedCacheManager getMemoryConstrainedCacheManager()
    {
        return new MemoryConstrainedCacheManager();
    }
	/*@Bean //cookie管理器
	public CookieRememberMeManager cookieRememberMeManager(){
		CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
		cookieRememberMeManager.setCookie(rememberMeCookie());
		return cookieRememberMeManager;
	}*/
}

