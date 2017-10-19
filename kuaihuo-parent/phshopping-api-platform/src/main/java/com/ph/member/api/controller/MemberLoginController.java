package com.ph.member.api.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.ph.shopping.common.core.customenum.OuterResultEnum;
import com.ph.shopping.common.util.container.ContainerUtil;
import com.ph.shopping.common.util.result.OuterResult;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.member.entity.Member;
import com.ph.shopping.facade.member.service.IMemberService;
/**
 * 
 * @ClassName:  MemberLoginController   
 * @Description:提供给北京调用   
 * @author: 李杰
 * @date:   2017年4月27日 下午3:20:16     
 * @Copyright: 2017
 */
@RestController
@RequestMapping("/member/usersync")
public class MemberLoginController {

	private static Logger log = LoggerFactory.getLogger(MemberLoginController.class);
	
	//会员接口
	@Reference(version = "1.0.0", retries = 0, timeout = 30000)
	private IMemberService memberService;
	/**
	 * 
	* @Title: registerVerify  
	* @Description: 注册验证 
	* @param @param phone
	* @param @return    参数  
	* @return Map<String,Object>    返回类型  
	* @throws
	 */
	@RequestMapping(value = "registerVerify", method = RequestMethod.POST)
	@ResponseBody
	public OuterResult registerVerify(String phone) {
		log.info("会员验证注册参数 phone：" + phone);
		OuterResult oresult = new OuterResult();
		oresult.setCode(OuterResultEnum.USER_NOEXISTS.getCode());
		oresult.setMsg(OuterResultEnum.USER_NOEXISTS.getMsg());
		oresult.setContent("false");
		if (StringUtils.isBlank(phone)) {
			oresult.setCode(OuterResultEnum.DATA_NOTALL.getCode());
			oresult.setMsg(OuterResultEnum.DATA_NOTALL.getMsg());
			return oresult;
		}
		try {
			Result result = memberService.getMemberInfoByMobileUpToken(phone);
			log.info("查询会员信息返回数据 Result = {} ", JSON.toJSONString(result));
			if (result != null && result.isSuccess()) {
				oresult.setCode(OuterResultEnum.SUCCESS.getCode());
				oresult.setMsg(OuterResultEnum.SUCCESS.getMsg());
				
				Object data = result.getData();
				if (data instanceof Member) {
					oresult.setContent("true");
					Member member = (Member) data;
					oresult.setToken(member.getToken());
				}
			}
		} catch (Exception e) {
			oresult.setCode(OuterResultEnum.SERVER_ERROR.getCode());
			oresult.setMsg(OuterResultEnum.SERVER_ERROR.getMsg());
			log.error("会员验证错误", e);
		}
		return oresult;
	}
	
	/**
	 * 
	* @Title: loginVerify  
	* @Description:会员登录验证  
	* @param @param phone
	* @param @param pwd
	* @param @return    参数  
	* @return Map<String,Object>    返回类型  
	* @throws
	 */
	@RequestMapping(value = "loginVerify", method = RequestMethod.POST)
	@ResponseBody
	public OuterResult loginVerify(HttpServletRequest request, String phone, String password) {
		log.info("会员登录验证参数 phone：" + phone + " password :" + password);
		OuterResult oresult = new OuterResult();
		oresult.setCode(OuterResultEnum.USER_NOEXISTS.getCode());
		oresult.setMsg(OuterResultEnum.USER_NOEXISTS.getMsg());
		if (StringUtils.isBlank(phone)) {
			oresult.setCode(OuterResultEnum.DATA_NOTALL.getCode());
			oresult.setMsg(OuterResultEnum.DATA_NOTALL.getMsg());
			return oresult;
		}
		try {
			Result result = memberService.getMemberInfoByMobileUpToken(phone);
			log.info("查询会员信息返回数据 Result = {} ", JSON.toJSONString(result));
			if (result != null && result.isSuccess()) {
				Object data = result.getData();
				if (data instanceof Member) {
					Member member = (Member) data;
					if (null != password && password.equals(member.getMemberPwd())) {
						oresult.setCode(OuterResultEnum.SUCCESS.getCode());
						oresult.setMsg(OuterResultEnum.SUCCESS.getMsg());
						Map<String, Object> map = ContainerUtil.map();
						map.put("phone", member.getTelPhone());
						map.put("name", member.getMemberName());
						map.put("idCard", member.getIdCardNo());
						oresult.setContent(map);
						oresult.setToken(member.getToken());
					} else {
						oresult.setCode(OuterResultEnum.PWD_ERROR.getCode());
						oresult.setMsg(OuterResultEnum.PWD_ERROR.getMsg());
					}
				}
			}
		} catch (Exception e) {
			oresult.setCode(OuterResultEnum.SERVER_ERROR.getCode());
			oresult.setMsg(OuterResultEnum.SERVER_ERROR.getMsg());
			log.error("会员登录验证错误", e);
		}
		return oresult;
	}
	
}
