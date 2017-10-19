package com.alqsoft.init;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import com.unionpay.acp.sdk.SDKConfig;

/**
 * 初始化银联的参数
 * @author zc
 *
 */
@Component
public class InitParams {
	
	private Properties properties = new Properties();
	
	/**
	 * 加载银联配置文件
	 */
	@PostConstruct
	public void payparam(){
		SDKConfig.getConfig().loadPropertiesFromSrc();
	}
	
	/**
	 * 加载银联开发配置文件
	 * @throws IOException
	 */
	@PostConstruct
	public void init() throws IOException {
		InputStream inputStream = this.getClass().getResourceAsStream(
				"/init.properties");
		properties.load(inputStream);
		IOUtils.closeQuietly(inputStream);
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}
}
