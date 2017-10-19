package com.alqsoft.service.impl.appversion;

import java.util.List;
import java.util.Map;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.dao.appversion.AppVersionDao;
import com.alqsoft.service.appversion.AppVersionService;

/**
 * Date:     2017年2月28日  15:25:41 <br/>
 * @author   dinglanlan
 * @see 	 
 */

@Service
@Transactional(readOnly=true)
public class AppVersionServiceImpl implements AppVersionService {

	@Autowired
	private AppVersionDao appVersionDao;

	/**
	 * 检测软件更新版本信息
	 * @return
	 */
	@Override
	public Result getAppVersion(String client_type, String version) {
		try {
			List<Map<String,Object>> appVersionList = appVersionDao.getAppVersion();//查询相关版本信息
			
			if(appVersionList.size()>0){
				
				Map<String,Object> appVersion=appVersionList.get(0);
				if("A".equals(client_type)){//设备标识（Android as A、Ios as I）
					
					if(version.equals(appVersion.get("version"))){//比较版本号是否一致
						return ResultUtils.returnError("已经是最新版本");
					}else{
						return ResultUtils.returnSuccess("不是最新版本，请更新", appVersion);
					}
					
					
				}else{
					return ResultUtils.returnError("设备标识有误");
				}
				
			}else{
				return ResultUtils.returnError("已经是最新版本");
			}
		} catch (Exception e) {
			// TODO: handle exception
			return ResultUtils.returnError(e.getMessage()); 
		}
	}
	
	@Override
	public Map<String,Object> getDownLink(){
		return appVersionDao.getDownLink();
	}
}
