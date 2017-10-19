package com.phshopping.api.cache.user;

/**
 * 
 * @ClassName:  IAgencyHandler   
 * @Description:相关代理操作   
 * @author: 李杰
 * @date:   2017年5月26日 下午1:50:37     
 * @Copyright: 2017
 */
public interface IAgencyHandler {
	/**
	 * 
	 * @Title: getMemberService   
	 * @Description: 得到member   
	 * @param: @return      
	 * @return: IMemberService
	 * @author：李杰      
	 * @throws
	 */
	<T> T getReference(Class<T> c);
}
