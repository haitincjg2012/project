package com.ph.shopping;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.lorne","com.ph"})
@MapperScan(basePackages = "com.ph.shopping.facade.mapper")
@EnableAspectJAutoProxy(proxyTargetClass = true,exposeProxy = true)
@EnableTransactionManagement(order = 2)
public class SpmApplication {
	
	public static void main(String[] args)throws Exception {
		System.setProperty("dubbo.application.logger","slf4j");
		SpringApplication.run(SpmApplication.class, args);
	}
	
	/**
	 * 设置tomcat端口号
	 * @return
	 */
	@Bean
	public EmbeddedServletContainerFactory servletContainer(){
		TomcatEmbeddedServletContainerFactory factory=new TomcatEmbeddedServletContainerFactory();
		factory.setPort(10000);
		return factory;
	}
}
