package com.gzzhsl.pcms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import javax.servlet.MultipartConfigElement;

@SpringBootApplication
@EnableScheduling
@MapperScan(value = {"com.gzzhsl.pcms.mapper"})
public class PcmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PcmsApplication.class, args);
	}


	/**
	 * 文件上传配置
	 * @return
	 */
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		//单个文件最大
		factory.setMaxFileSize("1024000KB"); //KB,MB
		/// 设置总上传数据总大小
		factory.setMaxRequestSize("10240000KB");
		return factory.createMultipartConfig();
	}
}
