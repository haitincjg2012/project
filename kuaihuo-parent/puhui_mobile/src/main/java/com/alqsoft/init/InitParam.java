package com.alqsoft.init;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alqsoft.entity.constantmanager.ConstantManager;
import com.alqsoft.service.constantmanager.ConstantManagerService;

/**
 * 
 * @Title: InitParam.java
 * @Description: 初始化配置文件参数工具类
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年3月12日 下午8:40:56 Copyright © 2013 厦门卓讯信息技术有限公司 All rights
 *              reserved.
 *
 */
@Component
public class InitParam {

	private Properties properties = new Properties();

	// 获取后台常量并保存到缓存中
	private Map<String, String> constantMap = new HashMap<String, String>();
	@Autowired
	private ConstantManagerService constantManagerService;

	@PostConstruct
	public void init() throws IOException {
		// 获取常驻内存的常量数据列表
		List<ConstantManager> constantManagers = constantManagerService
				.findConstantManagerByIsMemory(1);
		// 将数据添加到map数组中去
		while (!constantManagers.isEmpty()) {
			ConstantManager constantManager = constantManagers.remove(0);
			constantMap.put(constantManager.getEnglishName(), constantManager.getConstantValue());
		}
	}

	public Map<String, String> getConstantMap() {
		return constantMap;
	}

	public void setConstantMap(Map<String, String> constantMap) {
		this.constantMap = constantMap;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}
}
