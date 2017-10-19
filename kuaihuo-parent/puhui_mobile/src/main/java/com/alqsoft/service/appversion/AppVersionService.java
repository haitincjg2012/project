package com.alqsoft.service.appversion;

import java.util.Map;

import org.alqframework.result.Result;

/**
 * Date:     2017年2月28日  15:25:41 <br/>
 * @author   dinglanlan
 * @see 	 
 */
public interface AppVersionService {

	/**
	 * 检测软件更新版本信息
	 * @return
	 */
	public Result getAppVersion(String client_type, String version);
	
	/**
	 * 安卓下载地址
	 * @return
	 */
	public Map<String,Object> getDownLink();


}
