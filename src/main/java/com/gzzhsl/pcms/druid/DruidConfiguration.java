package com.gzzhsl.pcms.druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DruidConfiguration {
   @Value("${spring.datasource.url}")
   private String dbUrl;

   @Value("${spring.datasource.username}")
   private String username;

   @Value("${spring.datasource.password}")
   private String password;

   @Value("${spring.datasource.driverClassName}")
   private String driverClassName;

   @Value("${spring.datasource.initial-size}")
   private int initialSize;

   @Value("${spring.datasource.min-idle}")
   private int minIdle;

   @Value("${spring.datasource.max-active}")
   private int maxActive;

   @Value("${spring.datasource.maxWait}")
   private int maxWait;

   @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
   private int timeBetweenEvictionRunsMillis;

   @Value("${spring.datasource.minEvictableIdleTimeMillis}")
   private int minEvictableIdleTimeMillis;

   @Value("${spring.datasource.validationQuery}")
   private String validationQuery;

   @Value("${spring.datasource.testWhileIdle}")
   private boolean testWhileIdle;

   @Value("${spring.datasource.testOnBorrow}")
   private boolean testOnBorrow;

   @Value("${spring.datasource.testOnReturn}")
   private boolean testOnReturn;

   @Value("${spring.datasource.poolPreparedStatements}")
   private boolean poolPreparedStatements;

   @Value("${spring.datasource.filters}")
   private String filters;

   @Value("${spring.datasource.logSlowSql}")
   private String logSlowSql;
   @Bean
   @Primary
   public DataSource dataSource(){
      //@Primary 注解作用是当程序选择dataSource时选择被注解的这个
      DruidDataSource datasource = new DruidDataSource();
      datasource.setUrl(dbUrl);
      datasource.setUsername(username);
      datasource.setPassword(password);
      datasource.setDriverClassName(driverClassName);
      datasource.setInitialSize(initialSize);
      datasource.setMinIdle(minIdle);
      datasource.setMaxActive(maxActive);
      datasource.setMaxWait(maxWait);
      datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
      datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
      datasource.setValidationQuery(validationQuery);
      datasource.setTestWhileIdle(testWhileIdle);
      datasource.setTestOnBorrow(testOnBorrow);
      datasource.setTestOnReturn(testOnReturn);
      datasource.setPoolPreparedStatements(poolPreparedStatements);
      try {
         datasource.setFilters(filters);
      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      return datasource;
   }

 

   @Bean
   public ServletRegistrationBean druidServlet() {

      ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();

      servletRegistrationBean.setServlet(new StatViewServlet());

      servletRegistrationBean.addUrlMappings("/druid/*");

      Map<String,String> initParameters = new HashMap<String, String>();

  /*    initParameters.put("loginUsername","root");// 用户名

      initParameters.put("loginPassword","Y223183q!");// 密码*/

      initParameters.put("resetEnable","false");// 禁用HTML页面上的“Reset All”功能

      initParameters.put("allow",""); // IP白名单 (没有配置或者为空，则允许所有访问)

      //initParameters.put("deny", "192.168.20.38");// IP黑名单

      //(存在共同时，deny优先于allow)

      servletRegistrationBean.setInitParameters(initParameters);

      return servletRegistrationBean;

   }

 

   @Bean
   public FilterRegistrationBean filterRegistrationBean() {

      FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();

      filterRegistrationBean.setFilter(new WebStatFilter());

      filterRegistrationBean.addUrlPatterns("/*");

      //@WebInitParam(name="exclusions",value="*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*")//

      //忽略资源

      filterRegistrationBean.addInitParameter("exclusions",

           "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");

      return filterRegistrationBean;

   }

 

}