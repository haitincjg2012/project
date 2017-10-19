/**  
 * @Title:  YunPianSmsUtil.java   
 * @Package com.ph.shopping.common.util.other.smssend   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年5月11日 上午10:05:37   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.common.util.other.smssend;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.ph.shopping.common.util.container.BeanToMap;
import com.ph.shopping.common.util.http.HttpClientUtils;
import com.ph.shopping.common.util.http.HttpResult;
import com.ph.shopping.common.util.other.smssend.request.YunPianSmsRequest;
import com.ph.shopping.common.util.other.smssend.response.YunPianResponse;

/**   
 * @ClassName:  YunPianSmsUtil   
 * @Description:云片短信发送工具类   
 * @author: 李杰
 * @date:   2017年5月11日 上午10:05:37     
 * @Copyright: 2017
 */
public class YunPianSmsUtil {

    /**
     * 编码格式。发送编码格式统一用UTF-8
     */
    private static String ENCODING = "UTF-8";
    /**
	 * 日志
	 */
	private static final Logger log = LoggerFactory.getLogger(YunPianSmsUtil.class);
	/**
	 * 
	 * @Title: sendSms   
	 * @Description: 短信发送   
	 * @param: @param rquest
	 * @param: @return      
	 * @return: YunPianResponse
	 * @author：李杰      
	 * @throws
	 */
	public static YunPianResponse sendSms(YunPianSmsRequest rquest, String url) {
		YunPianResponse response = null;
		if (rquest != null) {
			Map<String, String> paramsMap = BeanToMap.getMapByStr(rquest);
			if (paramsMap != null) {
				String result = post(url, paramsMap);
				log.debug("YunPian reauest result = " + result);
				if (StringUtils.isNotBlank(result)) {
					response = JSON.parseObject(result, YunPianResponse.class);
				}
			}
		}
		return response;
	}
	/**
	 * 
	 * @Title: post   
	 * @Description: 短信验证码发送   
	 * @param: @param url
	 * @param: @param paramsMap
	 * @param: @return      
	 * @return: String
	 * @author：李杰      
	 * @throws
	 */
	private static String post(String url, Map<String, String> paramsMap) {
		String responseText = "";
		try {
			HttpResult result = HttpClientUtils.sendPost(url, paramsMap, ENCODING);
			if (null != result) {
				responseText = result.getResponseContent(ENCODING);
			}
		} catch (Exception e) {
			log.error("send sms error", e);
		}
		return responseText;
	}
	
}
