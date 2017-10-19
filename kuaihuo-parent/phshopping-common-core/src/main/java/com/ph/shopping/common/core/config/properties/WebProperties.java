/**
 * @Title:  WebProperties.java
 * @Package com.ph.shopping.common.core.config.properties
 * @Description:    TODO(用一句话描述该文件做什么)
 * @author: 李杰
 * @date:   2017年6月29日 下午1:43:01
 * @version V1.0
 * @Copyright: 2017
 */
package com.ph.shopping.common.core.config.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @ClassName:  WebProperties
 * @Description:web相关配置
 * @author: 李杰
 * @date:   2017年6月29日 下午1:43:01
 * @Copyright: 2017
 */
@PropertySource("classpath:web.properties")
@Configuration
public class WebProperties {
	/**
	 * app线下订单金额签名字符串
	 */
	@Value("${web.appUnlineOrderMoneySign}")
	private String appUnlineOrderMoneySign;
	/**
	 * 服务版本
	 */
	@Value("${web.serviceVersion}")
	private String serviceVersion;
	/**
	 * ios会员app下载地址
	 */
	@Value("${web.iosMemberAppUploadUrl}")
	private String iosMemberAppUploadUrl;
	/**
	 * ios商户app下载地址
	 */
	@Value("${web.iosMerchantAppUploadUrl}")
	private String iosMerchantAppUploadUrl;
	/**
	 * android会员app下载地址
	 */
	@Value("${web.androidMemberAppUploadUrl}")
	private String androidMemberAppUploadUrl;
	/**
	 * android商户app下载地址
	 */
	@Value("${web.androidMerchantAppUploadUrl}")
	private String androidMerchantAppUploadUrl;

	public String getAppUnlineOrderMoneySign() {
		return appUnlineOrderMoneySign;
	}

	public String getServiceVersion() {
		return serviceVersion;
	}

	public String getIosMemberAppUploadUrl() {
		return iosMemberAppUploadUrl;
	}

	public String getIosMerchantAppUploadUrl() {
		return iosMerchantAppUploadUrl;
	}

	public String getAndroidMemberAppUploadUrl() {
		return androidMemberAppUploadUrl;
	}

	public String getAndroidMerchantAppUploadUrl() {
		return androidMerchantAppUploadUrl;
	}
}
