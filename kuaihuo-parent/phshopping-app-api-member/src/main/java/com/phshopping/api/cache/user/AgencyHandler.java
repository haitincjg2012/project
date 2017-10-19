package com.phshopping.api.cache.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.ph.shopping.common.core.config.properties.WebProperties;

@Component
public class AgencyHandler implements IAgencyHandler{
	
	@Autowired
	private ApplicationConfig applicationConfig;

	@Autowired
	private RegistryConfig registryConfig;
	
	@Autowired
	private WebProperties webConfig;
	
	@Override
	public <T> T getReference(Class<T> c) {
		ReferenceConfig<T> reference = new ReferenceConfig<>();
		reference.setApplication(applicationConfig);
		reference.setRegistry(registryConfig);
		reference.setInterface(c);
		reference.setVersion(webConfig.getServiceVersion());
		return reference.get();
	}
}
