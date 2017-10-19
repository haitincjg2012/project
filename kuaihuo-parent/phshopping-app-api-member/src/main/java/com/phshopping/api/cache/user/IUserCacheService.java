package com.phshopping.api.cache.user;

import com.phshopping.api.entity.MemberInfo;

/**
 * 
 * @ClassName:  IUserCacheService   
 * @Description:用户缓存相关操作   
 * @author: lijie
 * @date:   2017年5月19日 上午9:34:26     
 * @Copyright: 2017
 */
public interface IUserCacheService {

	/**
	 * 
	 * @Title: insert   
	 * @Description:添加缓存 
	 * @param: @param sign
	 * @param: @param value      
	 * @return: void      
	 * @throws
	 */
	void insert(String token,MemberInfo member);
	/**
	 * 
	 * @Title: update   
	 * @Description: 修改缓存信息   
	 * @param: @param token
	 * @param: @param value
	 * @param: @return      
	 * @return: Object      
	 * @throws
	 */
	void update(String token,MemberInfo member);
	/**
	 * 
	 * @Title: delete   
	 * @Description: 删除缓存数据   
	 * @param: @param token      
	 * @return: void      
	 * @throws
	 */
	void deleteByToken(String token);
	/**
	 * 
	 * @Title: deleteByMobileSign   
	 * @Description: 根据手机签名删除用户信息   
	 * @param: @param token      
	 * @return: void
	 * @author：李杰      
	 * @throws
	 */
	void deleteByMobile(String sign);
	/**
	 * 
	 * @Title: select   
	 * @Description: 查找缓存数据   
	 * @param: @param token
	 * @param: @param clas
	 * @param: @return      
	 * @return: T      
	 * @throws
	 */
	MemberInfo select(String token);
	/**
	 * 
	 * @Title: isWaste   
	 * @Description: 判断该token 是否是被挤出去的   
	 * @param: @param token
	 * @param: @return      
	 * @return: boolean
	 * @author：李杰      
	 * @throws
	 */
	boolean isWaste(String token);
}
