/**  
 * @Title:  TokenAspect.java   
 * @Package com.ph.shopping.common.core.other.aspect   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年6月8日 下午5:42:38   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.common.core.other.aspect;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;
import com.ph.shopping.common.core.customenum.CacheKeyEnum;
import com.ph.shopping.common.core.customenum.CacheKeyTypeEnum;
import com.ph.shopping.common.core.exception.user.UserException;
import com.ph.shopping.common.core.other.annotation.Token;
import com.ph.shopping.common.core.other.token.ITokenService;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.http.IPUtil;

/**   
 * @ClassName:  TokenAspect   
 * @Description:token 切面操作   
 * @author: 李杰
 * @date:   2017年6月8日 下午5:42:38     
 * @Copyright: 2017
 */
@Aspect
@Component
@Scope(WebApplicationContext.SCOPE_SESSION)
@Order(1)
public class TokenAspect {

	private static final Logger LOG = LoggerFactory.getLogger(TokenAspect.class);

	@Autowired
	private ITokenService tokenService;

	@Pointcut("execution(* com.**..*Controller.*(..)) "
			+ "&& @annotation(org.springframework.web.bind.annotation.RequestMapping) "
			+ "&& @annotation(com.ph.shopping.common.core.other.annotation.Token)")
	public void tokenChecTrack() {}
	
	// 防止重复提交缓存
	private final ConcurrentMap<String, String> tokenMap = new ConcurrentHashMap<String, String>();
	/**
	 * 
	 * @Title: tokenCheck   
	 * @Description: token 处理   
	 * @param: @param point
	 * @param: @return      
	 * @return: Object
	 * @author：李杰      
	 * @throws
	 */
	@Around("tokenChecTrack()")
	public Object tokenCheck(ProceedingJoinPoint point) throws Exception{
		final String token = getTokenByParam(point, getKey(point));
		final boolean flag = verifyToken(token, point);
		if (!flag) {
			LOG.warn("非法请求,非法请求 IP = {}", JSON.toJSONString(getRequestIp(point)));
			return ResultUtil.getResult(RespCode.Code.PERMISSION_DENIED);
		} else {
			synchronized (this) {
				if (tokenMap.containsKey(token)) {
					return ResultUtil.getResult(RespCode.Code.REPETITION);
				}
				tokenMap.put(token, token);
			}
		}
		try {
			// 执行方法
			return point.proceed();
		} catch (Throwable e) {
			LOG.error("handler token error", e);
			if (e instanceof UserException) {
				throw new UserException(e);
			}
			throw new Exception(e);
		} finally {
			// 释放当前请求的标记
			if (flag) {
				tokenMap.remove(token, token);
			}
		}
	}
	/**
	 * 
	 * @Title: verifyToken   
	 * @Description: 校验token   
	 * @param: @param token
	 * @param: @return      
	 * @return: boolean
	 * @author：李杰      
	 * @throws
	 */
	private boolean verifyToken(String token, JoinPoint point) {
		boolean flag = StringUtils.isNotBlank(token);
		if (flag) {
			if (isCahce(point)) {
				return tokenService.tokenCheckout(token, getCacheKey(point), getCacheKeyType(point));
			}
		}
		return flag;
	}
	/**
	 * 
	 * @Title: getTokenByParam   
	 * @Description: 得到指定的请求参数   
	 * @param: @param point
	 * @param: @return      
	 * @return: String
	 * @author：李杰      
	 * @throws
	 */
	public String getTokenByParam(JoinPoint point, String name) {
		Object obj = AspectHandle.getValueByName(point, name);
		if (null != obj) {
			return obj.toString();
		}
		return null;
	}
	/**
	 * 
	 * @Title: getRequestIp   
	 * @Description: 得到请求IP   
	 * @param: @param point
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	private String getRequestIp(JoinPoint point) {
		String ip = "";
		HttpServletRequest request = getArg(HttpServletRequest.class, point);
		if (request != null) {
			ip = IPUtil.getIpAddress(request);
		}
		return ip;
	}
	/**
	 * 
	 * @Title: getArg   
	 * @Description: 得到方法参数  
	 * @param: @param clas
	 * @param: @param point
	 * @param: @return      
	 * @return: T      
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	private <T> T getArg(Class<T> clas, JoinPoint point) {
		T t = null;
		Object[] objects = point.getArgs();
		if (ArrayUtils.isNotEmpty(objects)) {
			for (Object object : objects) {
				if (clas.isInstance(object)) {
					t = (T) object;
					break;
				}
			}
		}
		return t;
	}
	/**
	 * 
	 * @Title: getKey   
	 * @Description: 得到key   
	 * @param: @param point
	 * @param: @return      
	 * @return: String
	 * @author：李杰      
	 * @throws
	 */
	public String getKey(JoinPoint point) {
		Token token = getAppToken(point);
		if (token != null) {
			return token.key();
		}
		return null;
	}
	/**
	 * 
	 * @Title: isCahce   
	 * @Description: 判断是否缓存   
	 * @param: @param point
	 * @param: @return      
	 * @return: boolean
	 * @author：李杰      
	 * @throws
	 */
	public boolean isCahce(JoinPoint point) {
		Token token = getAppToken(point);
		if (token != null) {
			return token.isCache();
		}
		return false;
	}
	/**
	 * 
	 * @Title: getCacheKey   
	 * @Description: 得到缓存key  
	 * @param: @param point
	 * @param: @return      
	 * @return: String
	 * @author：李杰      
	 * @throws
	 */
	public CacheKeyEnum getCacheKey(JoinPoint point) {
		Token token = getAppToken(point);
		if (token != null) {
			return token.cacheKey();
		}
		return null;
	}
	/**
	 * 
	 * @Title: getCacheKeyType   
	 * @Description: 得到key类型   
	 * @param: @param point
	 * @param: @return      
	 * @return: CacheKeyTypeEnum
	 * @author：李杰      
	 * @throws
	 */
	public CacheKeyTypeEnum getCacheKeyType(JoinPoint point) {
		Token token = getAppToken(point);
		if (token != null) {
			return token.cacheType();
		}
		return null;
	}
	/**
	 * 
	 * @Title: getAppToken   
	 * @Description: 得到token   
	 * @param: @param point
	 * @param: @return      
	 * @return: AppToken
	 * @author：李杰      
	 * @throws
	 */
	private Token getAppToken(JoinPoint point) {
		MethodSignature methodSignature = (MethodSignature) point.getSignature();
		if (methodSignature != null) {
			Method method = methodSignature.getMethod();
			if (method != null) {
				return method.getAnnotation(Token.class);
			}
		}
		return null;
	}
}
