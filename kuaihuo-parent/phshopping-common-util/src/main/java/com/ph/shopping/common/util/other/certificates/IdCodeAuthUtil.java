package com.ph.shopping.common.util.other.certificates;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ph.shopping.common.util.container.ContainerUtil;
import com.ph.shopping.common.util.http.HttpClientUtils;
import com.ph.shopping.common.util.other.certificates.request.CertificatesData;
/**
 * 
* @ClassName: IdCodeAuthUtil  
* @Description: 身份认证util 
* @author lijie  
* @date 2017年3月17日  
*
 */
public class IdCodeAuthUtil {
	
	private static final Logger log = LoggerFactory.getLogger(IdCodeAuthUtil.class);
			
	private static final String SUCCESS = "true";
	
	/**
	* @Title: idCertificatesAuth  
	* @Description: 身份证件请求发送  
	* @param @param data
	* @param @return
	* @param @throws Exception    参数  
	* @return boolean    返回类型  
	* @throws
	 */
	public static boolean idCertificatesAuth(CertificatesData data) throws Exception {
		Map<String, String> params = ContainerUtil.map();
		params.put("idCard", data.getIdCode());
		params.put("name", data.getIdName());
		// 构造请求
		String reoponse = HttpClientUtils.sendPost(data.getAuthUrl().trim(), params).getResponseContent();
		if (log.isDebugEnabled()) {
			log.debug("身份认证返回值" + reoponse);
		}
		return SUCCESS.equalsIgnoreCase(reoponse);
	}
}
