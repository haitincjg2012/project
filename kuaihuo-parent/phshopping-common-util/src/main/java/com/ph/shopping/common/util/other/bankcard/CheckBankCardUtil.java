package com.ph.shopping.common.util.other.bankcard;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.ph.shopping.common.util.container.ContainerUtil;
import com.ph.shopping.common.util.http.HttpClientUtils;
import com.ph.shopping.common.util.other.bankcard.request.CheckBankCardData;
import com.ph.shopping.common.util.other.bankcard.response.CheckBankResponse;
/**
 * 
 * @ClassName:  CheckBankCardUtil   
 * @Description:校验银行卡信息工具类    
 * @author: 李杰
 * @date:   2017年4月27日 下午3:37:57     
 * @Copyright: 2017
 */
public class CheckBankCardUtil {
	
	private static final Logger log = LoggerFactory.getLogger(CheckBankCardUtil.class);
	/**
	 * 
	 * @Title: bankCardCheck   
	 * @Description: 认证身份证   
	 * @param: @param request
	 * @param: @return      
	 * @return: CheckResponse      
	 * @throws
	 */
	public static CheckBankResponse bankCardCheck(CheckBankCardData request) {
		CheckBankResponse response = null;
		if (request != null) {
			Map<String, String> params = ContainerUtil.map();
			params.put("name", request.getName());
			params.put("cardNum", request.getCardNum());
			params.put("bankCardNo", request.getBankCardNo());
			String result = null;
			try {
				result = HttpClientUtils.sendPost(request.getCheckUrl().trim(), params).getResponseContent();
			} catch (Exception e) {
				log.error("bind bank error", e);
			}
			if (log.isDebugEnabled()) {
				log.debug("银行卡校验返回值 result ： " + result);
			}
			if (StringUtils.isNotBlank(result)) {
				response = JSONObject.parseObject(result, CheckBankResponse.class);
			}
		}
		return response;
	}
}
