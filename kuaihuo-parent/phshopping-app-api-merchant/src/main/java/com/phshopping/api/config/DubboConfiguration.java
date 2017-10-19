package com.phshopping.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.MonitorConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.AnnotationBean;
import com.alibaba.dubbo.rpc.Invoker;

/**
 * @项目：phshopping-service-member
 *
 * @描述：dubbox消费者配置
 *
 * @作者： Mr.chang
 *
 * @创建时间：2017年3月10日
 *
 * @Copyright @2017 by Mr.chang
 */
@Configuration
@ConditionalOnClass(Invoker.class)
@PropertySource(value = "classpath:dubbo.properties")
public class DubboConfiguration {

	@Value("${dubbo.application.name}")
	private String applicationName;

	@Value("${dubbo.registry.protocol}")
	private String protocol;

	@Value("${dubbo.registry.address}")
	private String registryAddress;

	@Value("${dubbo.consumer.timeout}")
	private int timeout;

	@Value("${dubbo.consumer.retries}")
	private int retries;

	/**
	 * 设置dubbo扫描包
	 * 
	 * @param packageName
	 * @return
	 */
	@Bean
	public static AnnotationBean annotationBean(@Value("${dubbo.annotation.package}") String packageName) {
		AnnotationBean annotationBean = new AnnotationBean();
		annotationBean.setPackage(packageName);
		return annotationBean;
	}

	/**
	 * 注入dubbo上下文
	 * 
	 * @return
	 */
	@Bean
	public ApplicationConfig applicationConfig() {
		// 当前应用配置
		ApplicationConfig applicationConfig = new ApplicationConfig();
		applicationConfig.setName(this.applicationName);
		applicationConfig.setLogger("slf4j");
		return applicationConfig;
	}

	/**
	 * 注入dubbo注册中心配置,基于zookeeper
	 * 
	 * @return
	 */
	@Bean
	public RegistryConfig registryConfig() {
		// 连接注册中心配置
		RegistryConfig registry = new RegistryConfig();
		registry.setProtocol(protocol);
		registry.setAddress(registryAddress);
		registry.setCheck(false);// 设置启动时不检查注册中心
		return registry;
	}
	
	/**
	 * dubbo监控中心
	 * @return
	 */
	@Bean
	public MonitorConfig monitorConfig() {
		MonitorConfig mc = new MonitorConfig();
		mc.setProtocol("registry");
		return mc;
	}

	/**
	 * dubbo消费
	 * 
	 * @param applicationConfig
	 * @param registryConfig
	 * @return
	 */
	@Bean(name = "consumer")
	public ConsumerConfig providerConfig(ApplicationConfig applicationConfig, RegistryConfig registryConfig,MonitorConfig monitorConfig) {
		ConsumerConfig consumeConfig = new ConsumerConfig();
		consumeConfig.setApplication(applicationConfig);
		consumeConfig.setRegistry(registryConfig);
		consumeConfig.setCheck(false);// 设置不检查服务提供者
		consumeConfig.setRetries(retries);
		consumeConfig.setTimeout(timeout);
		consumeConfig.setMonitor(monitorConfig);
		return consumeConfig;
	}

	/**
	 * @return the applicationName
	 */
	public String getApplicationName() {
		return applicationName;
	}

	/**
	 * @param applicationName
	 *            the applicationName to set
	 */
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	/**
	 * @return the protocol
	 */
	public String getProtocol() {
		return protocol;
	}

	/**
	 * @param protocol
	 *            the protocol to set
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	/**
	 * @return the registryAddress
	 */
	public String getRegistryAddress() {
		return registryAddress;
	}

	/**
	 * @param registryAddress
	 *            the registryAddress to set
	 */
	public void setRegistryAddress(String registryAddress) {
		this.registryAddress = registryAddress;
	}

	/**
	 * @return the timeout
	 */
	public int getTimeout() {
		return timeout;
	}

	/**
	 * @param timeout
	 *            the timeout to set
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	public int getRetries() {
		return retries;
	}

	public void setRetries(int retries) {
		this.retries = retries;
	}
}