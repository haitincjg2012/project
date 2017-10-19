package com.ph.shopping.common.core.other;

import com.ph.shopping.common.util.container.ParamVerifyUtil;
import com.ph.shopping.common.util.other.certificates.IdCodeAuthUtil;
import com.ph.shopping.common.util.other.certificates.request.CertificatesData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
/**
 * 
 * @ClassName:  IdAuthService   
 * @Description:认证服务   
 * @author: 李杰
 * @date:   2017年4月27日 下午3:39:10     
 * @Copyright: 2017
 */
public class IdAuthService {
	
	private static final Logger log = LoggerFactory.getLogger(IdAuthService.class);
	
	/**
	 * 认证地址
	 */
	@Value("${idcard.check.url}")
	private String authUrl;
	/**
	 * 
	* @Title: idCertificatesAuth  
	* @Description: 身份证件认证 
	* @param @param name 姓名
	* @param @param idCode 证件号码
	* @param @return    参数  
	* @return boolean    返回类型  
	* @throws
	 */
	public boolean idCertificatesAuth(String name,String idCode){
		try {
			CertificatesData data = new CertificatesData();
			data.setAuthUrl(authUrl);
			data.setIdCode(idCode);
			data.setIdName(name);
			String[] fields = { "idName", "idCode", "authUrl" };
			if (!ParamVerifyUtil.entityIsNotNullByField(data, fields)) {
                log.warn("send smsCodeByNoMsg parameter is not complete");
                return false;
            }
            return IdCodeAuthUtil.idCertificatesAuth(data);
        } catch (Exception e) {
			log.error("认证身份证件错误",e);
		}
        return false;
    }
}
