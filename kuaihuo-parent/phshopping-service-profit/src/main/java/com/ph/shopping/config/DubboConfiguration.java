/**
 * 
 */
package com.ph.shopping.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.MonitorConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.AnnotationBean;
import com.alibaba.dubbo.rpc.Exporter;

/**
 * @项目：phshopping-service-member
 *
 * @描述：
 *
 * @作者： Mr.chang
 *
 * @创建时间：2017年3月10日
 *
 * @Copyright @2017 by Mr.chang
 */

@Configuration
@ConditionalOnClass(Exporter.class)
@PropertySource(value="classpath:dubbo.properties")
public class DubboConfiguration {
	
	@Value("${dubbo.application.name}")
    private String applicationName;


    @Value("${dubbo.registry.protocol}")
    private String protocol;

    @Value("${dubbo.registry.address}")
    private String registryAddress;

    @Value("${dubbo.protocol.name}")
    private String protocolName;

    @Value("${dubbo.protocol.port}")
    private int protocolPort;

    @Value("${dubbo.provider.timeout}")
    private int timeout;

    @Value("${dubbo.provider.retries}")
    private int retries;

    @Value("${dubbo.provider.delay}")
    private int delay;

	@Value("${dubbo.consumer.filter}")
	private String filter;
    
    /**
     * 设置dubbo扫描包
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
        registry.setCheck(false);
        return registry;
    }

    /**
     * 默认基于dubbo协议提供服务
     * 
     * @return
     */
    @Bean
    public ProtocolConfig protocolConfig() {
        // 服务提供者协议配置
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName(protocolName);
        protocolConfig.setPort(protocolPort);
        protocolConfig.setThreads(200);
        return protocolConfig;
    }
    
    /**
	 * dubbo监控
	 * @return
	 */
	@Bean
	public MonitorConfig monitorConfig() {
		MonitorConfig mc = new MonitorConfig();
		mc.setProtocol("registry");
		return mc;
	}

    /**
     * dubbo服务提供
     * 
     * @param applicationConfig
     * @param registryConfig
     * @param protocolConfig
     * @return
     */
    @Bean(name="profitProvider")
    public ProviderConfig providerConfig(ApplicationConfig applicationConfig, RegistryConfig registryConfig, ProtocolConfig protocolConfig,MonitorConfig monitorConfig) {
        ProviderConfig providerConfig = new ProviderConfig();
        providerConfig.setTimeout(timeout);
        providerConfig.setRetries(retries);
        providerConfig.setDelay(delay);
        providerConfig.setApplication(applicationConfig);
        providerConfig.setRegistry(registryConfig);
        providerConfig.setProtocol(protocolConfig);
        providerConfig.setMonitor(monitorConfig);
        return providerConfig;
    }

	/**
	 * @return the applicationName
	 */
	public String getApplicationName() {
		return applicationName;
	}

	/**
	 * @param applicationName the applicationName to set
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
	 * @param protocol the protocol to set
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
	 * @param registryAddress the registryAddress to set
	 */
	public void setRegistryAddress(String registryAddress) {
		this.registryAddress = registryAddress;
	}

	/**
	 * @return the protocolName
	 */
	public String getProtocolName() {
		return protocolName;
	}

	/**
	 * @param protocolName the protocolName to set
	 */
	public void setProtocolName(String protocolName) {
		this.protocolName = protocolName;
	}

	/**
	 * @return the protocolPort
	 */
	public int getProtocolPort() {
		return protocolPort;
	}

	/**
	 * @param protocolPort the protocolPort to set
	 */
	public void setProtocolPort(int protocolPort) {
		this.protocolPort = protocolPort;
	}

	/**
	 * @return the timeout
	 */
	public int getTimeout() {
		return timeout;
	}

	/**
	 * @param timeout the timeout to set
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	/**
	 * @return the retries
	 */
	public int getRetries() {
		return retries;
	}

	/**
	 * @param retries the retries to set
	 */
	public void setRetries(int retries) {
		this.retries = retries;
	}

	/**
	 * @return the delay
	 */
	public int getDelay() {
		return delay;
	}

	/**
	 * @param delay the delay to set
	 */
	public void setDelay(int delay) {
		this.delay = delay;
	}


}
