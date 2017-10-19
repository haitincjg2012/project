package com.alqsoft.dao.appversion;

import java.util.List;
import java.util.Map;

import org.alqframework.orm.mybatis.MyBatisRepository;

@MyBatisRepository
public interface AppVersionDao {

	/**
	 * 检测软件更新版本信息
	 * @return
	 */
	public List<Map<String, Object>> getAppVersion();
	
	/**
	 * 获取下载链接地址
	 * @return
	 */
	public Map<String,Object> getDownLink();

}
