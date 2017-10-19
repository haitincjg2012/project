package com.phshopping.api.uitl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.ph.shopping.common.core.cache.key.CacheKeyHandle;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.spring.SpringUtil;
import com.ph.shopping.facade.member.entity.Member;
import com.ph.shopping.facade.member.service.IMemberService;
import com.phshopping.api.appenum.AppResultEnum;
import com.phshopping.api.cache.user.IAgencyHandler;
import com.phshopping.api.cache.user.IUserCacheService;
import com.phshopping.api.entity.MemberInfo;

/**
 * 
 * @ClassName:  UserCacheUtil   
 * @Description:用户缓存信息操作工具   
 * @author: 李杰
 * @date:   2017年5月26日 上午11:16:44     
 * @Copyright: 2017
 */
public class UserCacheUtil {

	/**
	 * 日志对象
	 */
	private static final Logger logger = LoggerFactory.getLogger(UserCacheUtil.class);
	/**
	 * 用户处理对象
	 */
	private static final IUserCacheService userCacheService = SpringUtil.getBeanByClass(IUserCacheService.class);
	/**
	 * 会员操作对象
	 */
	private static IMemberService memberService = null;

	static {
		IAgencyHandler handler = SpringUtil.getBeanByClass(IAgencyHandler.class);
		if (null != handler) {
			memberService = handler.getReference(IMemberService.class);
		}
	}
	/**
	 * 
	 * @Title: addUser   
	 * @Description: 添加用户缓存   
	 * @param: @param mobile
	 * @param: @return      
	 * @return: MemberInfo
	 * @author：李杰      
	 * @throws
	 */
	public static MemberInfo addUser(String mobile) {
		MemberInfo memberInfo = null;
		if (StringUtils.isNotBlank(mobile) && null != userCacheService) {
			memberInfo = getMemberInfoByMobile(mobile);
			if (null != memberInfo) {
				String appkey = CacheKeyHandle.getAppKeyByMobile(mobile);
				if (StringUtils.isNotBlank(appkey)) {
					memberInfo.setToken(appkey);
					userCacheService.insert(appkey, memberInfo);
				}
			}
		}
		return memberInfo;
	}
	/**
	 * 
	 * @Title: removeUser   
	 * @Description: 删除用户缓存信息  
	 * @param: @param token      
	 * @return: void
	 * @author：李杰      
	 * @throws
	 */
	public static void removeUser(String token) {
		if (null != userCacheService) {
			userCacheService.deleteByToken(token);
		}
	}
	/**
	 * 
	 * @Title: updateUser   
	 * @Description: 更新缓存信息   
	 * @param: @param token
	 * @param: @return      
	 * @return: MemberInfo
	 * @author：李杰      
	 * @throws
	 */
	public static MemberInfo updateUser(String token) {
		if (StringUtils.isNotBlank(token)) {
			if (null != userCacheService) {
				MemberInfo member = userCacheService.select(token);
				if (null != member) {
					member = getMemberInfoByMobile(member.getTelPhone());
					if (null != member) {
						member.setToken(token);
						userCacheService.update(token, member);
						return member;
					}
				}
			}
		}
		return null;
	}
	/**
	 * 
	 * @Title: getUser   
	 * @Description: 从缓存中得到用户信息
	 * @param: @param token
	 * @param: @return      
	 * @return: MemberInfo
	 * @author：李杰      
	 * @throws
	 */
	public static Result getUser(String token) {
		Result result = ResultUtil.getResult(RespCode.Code.PERMISSION_DENIED);
		MemberInfo memberInfo = null;
		if (StringUtils.isNotBlank(token)) {
			if (null != userCacheService) {
				memberInfo = userCacheService.select(token);
			}
		}
		if (null == memberInfo) {
			logger.warn("根据 token ： " + token + " 没有获取到用户相关信息 ");
			if (userCacheService.isWaste(token)) {
				return ResultUtil.getResult(AppResultEnum.USERNOTEXIST);
			}
		} else {
			result.setData(memberInfo);
			ResultUtil.setResult(result, RespCode.Code.SUCCESS);
		}
		return result;
	}
	/**
	 * 
	 * @Title: getMemberInfoByMobile   
	 * @Description: 根据手机号得到用户信息   
	 * @param: @param mobile
	 * @param: @return      
	 * @return: MemberInfo
	 * @author：李杰      
	 * @throws
	 */
	private static MemberInfo getMemberInfoByMobile(String mobile) {
		MemberInfo memberInfo = null;
		if (null != memberService && StringUtils.isNotBlank(mobile)) {
			Result result = memberService.getMemberInfoByMobile(mobile);
			if (result.isSuccess()) {
				Object obj = result.getData();
				if (obj instanceof Member) {
					Member member = (Member) obj;
					memberInfo = new MemberInfo();
					BeanUtils.copyProperties(member, memberInfo);
				}
			}
		}
		return memberInfo;
	}
}
