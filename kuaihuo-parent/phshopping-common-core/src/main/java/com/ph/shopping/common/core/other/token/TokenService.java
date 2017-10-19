package com.ph.shopping.common.core.other.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ph.shopping.common.core.cache.redis.ICacheService;
import com.ph.shopping.common.core.customenum.CacheKeyEnum;
import com.ph.shopping.common.core.customenum.CacheKeyTypeEnum;

@SuppressWarnings({ "unchecked", "rawtypes" })
@Component
public class TokenService implements ITokenService{

	@Autowired
	private ICacheService redisService;

	@Override
	public boolean tokenCheckout(String token, CacheKeyEnum key, CacheKeyTypeEnum keyType) {
		boolean flag = isWaste(token);
		if (!flag) {
			switch (keyType) {
			case HASH:
				flag = redisService.hmExists(key.name(), token);
				break;
			case SINGLE:
				flag = redisService.exists(token);
				break;
			default:
				break;
			}
		}
		return flag;
	}
	/**
	 * 
	 * @Title: isWaste   
	 * @Description: 校验会员缓存是否是被挤掉（如有其他要求可在后面新增）   
	 * @param: @param token
	 * @param: @return      
	 * @return: boolean
	 * @author：李杰      
	 * @throws
	 */
	private boolean isWaste(String token){
		
		return redisService.hmExists(CacheKeyEnum.WASTE_MEMBER.name(), token);
	}
}
