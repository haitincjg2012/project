package com.ph.shopping.facade.member.service.user;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.ph.shopping.common.core.config.properties.BjUrlProperties;
import com.ph.shopping.common.core.customenum.OuterResultEnum;
import com.ph.shopping.common.core.exception.BizException;
import com.ph.shopping.common.util.container.ContainerUtil;
import com.ph.shopping.common.util.http.HttpClientUtils;
import com.ph.shopping.common.util.rsa.bj.BJRSACommonUtils;
import com.ph.shopping.common.util.rsa.bj.BJRSACommonUtils.CharSet;
import com.ph.shopping.facade.member.service.user.response.CheckResponse;
import com.ph.shopping.facade.member.service.user.response.Content;
/**
 * 
 * @ClassName:  LoginRegisterCheckService   
 * @Description:用户同步服务   
 * @author: 李杰
 * @date:   2017年4月27日 上午11:15:46     
 * @Copyright: 2017
 */
public class LoginRegisterCheckService {

	private static final Logger log = LoggerFactory.getLogger(LoginRegisterCheckService.class);
	
	/**
	 * 同步北京相关地址
	 */
	@Autowired
	private BjUrlProperties bjUrlProperties;
	/**
	 * 
	* @Title: loginCheck  
	* @Description: 校验登录  
	* @param @param phone
	* @param @param password
	* @param @return    参数  
	* @return LoginCheckRespnse    返回类型  
	* @throws
	 */
	public CheckResponse loginCheck(String phone, String password) {
		CheckResponse response = null;
		try {
			if (StringUtils.isNotBlank(phone) && StringUtils.isNotBlank(password)) {
				response = new CheckResponse();
				Map<String, String> params = ContainerUtil.map();
				params.put("phone", phone);
				params.put("password", BJRSACommonUtils.encryptByPublicKey(password, CharSet.UTF8));
				String result = HttpClientUtils.sendPost(bjUrlProperties.getLoginCheckUrl(), params).getResponseContent();
				log.info("调用北京接口返回结果：", result);
				if (StringUtils.isNotBlank(result)) {
					response = JSONObject.parseObject(result, CheckResponse.class);
				}
			}
		} catch (Exception e) {
			log.error("校验登录错误", e);
			throw new BizException("调用北京登录校验接口失败", e);
		}
		return response;
	}
	/**
	 * 
	* @Title: registerCheck  
	* @Description: 注册校验  
	* @param @param phone
	* @param @return    参数  
	* @return boolean    返回类型  
	* @throws
	 */
	public CheckResponse registerCheck(String phone) {
		CheckResponse response = null;
		try {
			if (StringUtils.isNotBlank(phone)) {
				response = new CheckResponse();
				Map<String, String> params = ContainerUtil.map();
				params.put("phone", phone);
				String result = HttpClientUtils.sendPost(bjUrlProperties.getRegisterCheckUrl(), params).getResponseContent();
				log.info("调用北京接口返回结果：", result);
				if (StringUtils.isNotBlank(result)) {
					response = JSONObject.parseObject(result, CheckResponse.class);
					if (response != null) {
						boolean flag = false;
						Content cont = response.getContent();
						if (cont != null) {
							// 返回true 表示用户存在 为false 表述不存在
							flag = "true".equals(cont.getUser());
						}
						if (!flag) {
							response.setCode(OuterResultEnum.USER_NOEXISTS.getCode());
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("注册校验错误", e);
			throw new BizException("调用北京注册校验接口失败", e);
		}
		return response;

	}
}
