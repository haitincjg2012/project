/**  
 * @Title:  KeyHandle.java   
 * @Package com.ph.shopping.common.core.cache.redis.impl   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年6月7日 下午8:17:00   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.common.core.cache.key;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.ph.shopping.common.core.cache.redis.ICacheService;
import com.ph.shopping.common.core.customenum.CacheKeyEnum;
import com.ph.shopping.common.util.rsa.MD5;
import com.ph.shopping.common.util.spring.SpringUtil;

/**   
 * @ClassName:  KeyHandle   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 李杰
 * @date:   2017年6月7日 下午8:17:00     
 * @Copyright: 2017
 */
public class CacheKeyHandle {

	/**
	 * app user key 签名
	 */
	private static final String APP_SIGN = MD5.getMD5Str("PUHUI_APP_USER_KEY");
	/**
	 * APP HASH KEY
	 */
	private static final String AMHKEY = CacheKeyEnum.APP_MEMBER.name();
	/**
	 * 得到缓存服务
	 */
	@SuppressWarnings("rawtypes")
	private static final ICacheService cacheService = SpringUtil.getBeanByClass(ICacheService.class);
	/**
	 * 
	 * @Title: getShoppingKeyByRequest   
	 * @Description: 商城根据请求得到缓存key  
	 * @param: @param request
	 * @param: @return      
	 * @return: String
	 * @author：李杰      
	 * @throws
	 */
	public static String getShoppingKeyByRequest(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (null == session) {
			session = request.getSession();
		}
		return "SHOPPING_" + session.getId();
	}
	/**
	 * 
	 * @Title: getAppKeyByMobile   
	 * @Description: 根据手机号得到 appkey  
	 * @param: @param mobile
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	public static String getAppKeyByMobile(String mobile) {
		StringBuilder sbud = new StringBuilder("APP_").append(mobile).append(APP_SIGN)
				.append(UUID.randomUUID().toString());
		return MD5.getMD5Str(sbud.toString());
	}
	/**
	 * 
	 * @Title: removeAppCacheByMobile   
	 * @Description: 根据手机号移除app 缓存   
	 * @param: @param mobile      
	 * @return: void
	 * @author：李杰      
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public static void removeAppCacheByMobile(String mobile) {
		if (StringUtils.isNotBlank(mobile)) {
			if (null != cacheService) {
				Object objKey = cacheService.hmGet(AMHKEY, mobile);
				if (null != objKey) {
					cacheService.hmRemove(AMHKEY, objKey, mobile);
				}
			}
		}
	}
	/**
	 * 
	 * @Title: removeShoppingCacheByMobile   
	 * @Description:根据手机号移除商城缓存信息 
	 * @param: @param mobile      
	 * @return: void
	 * @author：李杰      
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public static void removeShoppingCacheByMobile(String mobile) {
		if (StringUtils.isNotBlank(mobile)) {
			if (null != cacheService) {
				Object obj = cacheService.get(mobile);
				if(null != obj){
					cacheService.remove(obj);
					cacheService.remove(mobile);
				}
			}
		}
	}
}
