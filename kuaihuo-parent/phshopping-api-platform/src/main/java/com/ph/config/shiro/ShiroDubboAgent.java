package com.ph.config.shiro;

import com.ph.shopping.common.core.config.properties.WebProperties;
import com.ph.shopping.facade.permission.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;

/**
 * 
 * phshopping-api-platform
 *
 * @description：shiro的dubbo代理，获取服务提供者（接口）
 *
 * @author：Mr.Shu，liuy
 *
 * @createTime：2017年5月10日
 *
 * @Copyright @2017 by Mr.Shu，liuy
 */
@Configuration
public class ShiroDubboAgent {

	@Autowired
	private ApplicationConfig applicationConfig;

	@Autowired
	private RegistryConfig registryConfig;

	@Autowired
	private WebProperties webConfig;
	/**
	 * 获取代理对象方法
     * @createTime 2017年05月10日
	 */
	public <T> T getReference(Class<T> c) { 
		ReferenceConfig<T> reference = new ReferenceConfig<>();
		reference.setApplication(applicationConfig);
		reference.setRegistry(registryConfig);
		reference.setInterface(c);
		reference.setVersion(webConfig.getServiceVersion());
		return reference.get();
	}

	/**
	 * 获取用户Service接口
     * @createTime 2017年05月10日
	 */
	@Bean
	public IUserService getIUserService() {
		return this.getReference(IUserService.class);
	}
	
	/**
	 * 获取菜单Service接口（后面要改成权限）
     * @createTime 2017年05月10日
	 */
	@Bean
	public IMenuService getIMenuService() {
		return this.getReference(IMenuService.class);
	}

    /**
     * 获取按钮Service接口（后面要改成权限）
     *
     * @createTime 2017年05月13日
     */
    @Bean
    public IBtnService getIBtnService() {
        return this.getReference(IBtnService.class);
    }

    /**
	 * 获取角色Service接口
     * @createTime 2017年05月10日
	 */
	@Bean
	public IRoleService getIRoleService() {
		return this.getReference(IRoleService.class);
	}

	/**
	 * 获取角色Service接口
	 *
	 * @createTime 2017年05月10日
	 */
	@Bean
	public ILoginService getILoginService() {
		return this.getReference(ILoginService.class);
	}
	
}
