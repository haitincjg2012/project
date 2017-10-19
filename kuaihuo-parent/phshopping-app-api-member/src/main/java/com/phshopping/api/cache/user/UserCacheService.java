package com.phshopping.api.cache.user;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ph.shopping.common.core.cache.redis.ICacheService;
import com.ph.shopping.common.core.customenum.CacheKeyEnum;
import com.ph.shopping.common.core.exception.user.UserException;
import com.phshopping.api.entity.MemberInfo;

/**
 * 
 * @ClassName:  UserCacheService   
 * @Description:用户相关服务具体操作  
 * @author: lijie
 * @date:   2017年5月19日 上午9:34:43     
 * @Copyright: 2017
 */
@Component
public class UserCacheService implements IUserCacheService{

	@Autowired
	private ICacheService<String, Object> redisService;

	private static final String HKEY = CacheKeyEnum.APP_MEMBER.name();

	private static final String WASTE_KEY = CacheKeyEnum.WASTE_MEMBER.name();
	
	@Override
	public void insert(String token, MemberInfo member) {
		if (StringUtils.isBlank(token)) {
			throw new UserException("save user info key is null ");
		}
		if (null == member) {
			throw new UserException("save user info key is null ");
		}
		// 判断当前用户是否登录过，如果已登录则移除 重新登录
		final String mobile = member.getTelPhone();
		Object userKey = redisService.hmGet(HKEY, mobile);
		if (null != userKey) {
			// 移除当前登录的信息
			redisService.hmRemove(HKEY, mobile, userKey);
			// 保存被挤掉的信息
			redisService.hmSet(WASTE_KEY, userKey, mobile);
		}
		// 保存用户信息
		redisService.hmSet(HKEY, token, member);
		// 根据手机号 映射token
		redisService.hmSet(HKEY, mobile, token);
	}

	@Override
	public void update(String token, MemberInfo member) {
		if (redisService.hmExists(HKEY, token)) {
			redisService.hmSet(HKEY, token, member);
		}
	}

	@Override
	public void deleteByMobile(String mobile) {
		Object userKey = redisService.hmGet(HKEY, mobile);
		if (null != userKey) {
			removeToken(userKey, mobile);
		}
	}

	@Override
	public MemberInfo select(String token) {
		MemberInfo memberInfo = null;
		Object obj = redisService.hmGet(HKEY, token);
		if (obj instanceof MemberInfo) {
			memberInfo = (MemberInfo) obj;
		}
		return memberInfo;
	}

	@Override
	public void deleteByToken(String token) {
		Object obj = redisService.hmGet(HKEY, token);
		if (obj instanceof MemberInfo) {
			MemberInfo memberInfo = (MemberInfo) obj;
			removeToken(token, memberInfo.getTelPhone());
		}
	}
	/**
	 * 
	 * @Title: removeToken   
	 * @Description: 移除token信息   
	 * @param: @param token
	 * @param: @param mobile      
	 * @return: void
	 * @author：李杰      
	 * @throws
	 */
	private void removeToken(Object token, String mobile) {
		redisService.hmRemove(HKEY, token, mobile);
	}
	
	@Override
	public boolean isWaste(String token) {
		if (redisService.hmExists(WASTE_KEY, token)) {
			redisService.hmRemove(WASTE_KEY, token);
			return true;
		}
		return false;
	}
}
