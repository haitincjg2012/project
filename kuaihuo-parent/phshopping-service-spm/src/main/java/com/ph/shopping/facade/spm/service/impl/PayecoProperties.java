package com.ph.shopping.facade.spm.service.impl;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.support.PropertiesLoaderUtils;

public class PayecoProperties {
	
	public static String SYNC_ONLINE_CHARGE = "";
	
	static {
		try {
			Properties p = PropertiesLoaderUtils.loadAllProperties("payeco.properties");
			SYNC_ONLINE_CHARGE = p.getProperty("SYNC_ONLINE_CHARGE");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
