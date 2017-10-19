package com.phshopping.api.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ph.shopping.common.core.cache.redis.ICacheService;
import com.ph.shopping.common.core.customenum.RoleEnum;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.verifycode.VerifyUtil;
import com.phshopping.api.aop.annotation.AccessToken;
import com.phshopping.api.constant.MechantAppResultEnum;

@Aspect
@Component
public class AccessTokenAspect {

	@Autowired
	private ICacheService<String, String> redisService;

	@Around("@annotation(at)")
	public Object validateAccessToken(ProceedingJoinPoint pjp, AccessToken at) throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String merchantAppKey = redisService.get(getMerchantAppKey(request.getHeader("token"), RoleEnum.MERCHANT)) + "";

		if (VerifyUtil.isNotEmpty(merchantAppKey)) {
			return pjp.proceed();
		} else {
			return ResultUtil.getResult(MechantAppResultEnum.NO_LOGIN);
		}
	}

	private static String getMerchantAppKey(String token, RoleEnum roleEnum) {
		StringBuilder bud = new StringBuilder(roleEnum.name()).append("_").append(token);
		return bud.toString();
	}
}
