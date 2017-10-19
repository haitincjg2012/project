package com.ph.shopping.common.core.other.token;

import com.ph.shopping.common.core.customenum.CacheKeyEnum;
import com.ph.shopping.common.core.customenum.CacheKeyTypeEnum;

public interface ITokenService {

	/**
	 * 
	 * @Title: isExistsToken   
	 * @Description: 判断token 是否存在   
	 * @param: @param token
	 * @param: @return      
	 * @return: boolean
	 * @author：李杰      
	 * @throws
	 */
	boolean tokenCheckout(String token,CacheKeyEnum key,CacheKeyTypeEnum keyType);
}
