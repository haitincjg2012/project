/**  
 * @Title:  ISmsService.java   
 * @Package com.ph.shopping.common.core.other.sms.handle   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年5月15日 下午11:14:07   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.common.core.other.sms.handle;

import com.ph.shopping.common.core.other.sms.data.SmsSendData;
import com.ph.shopping.common.util.result.Result;

/**
 * 
 * @ClassName: ISmsDataService
 * @Description: 短信数据服务接口
 * @author liuy
 * @date 2017年6月1日 下午5:22:30
 */
public interface ISmsDataService {

	/**
	 * 
	 * @Title: sendSmsHaveCode
	 * @Description: 发送短信(有验证码/密码)
	 * @author liuy
	 * @date  2017年6月1日 下午5:21:11
	 * @param sendData
	 * @param mobileType 
	 * @return
	 */
	Result sendSmsHaveCode(SmsSendData sendData);
	/**
	 * 
	 * @Title: sendSmsNoCode
	 * @Description: 发送短信 (无验证码/密码)
	 * @author liuy
	 * @date  2017年6月1日 下午5:21:11
	 * @param sendData
	 * @return
	 */
	Result sendSmsNoCode(SmsSendData sendData);
	/**
	 * 
	 * @Title: sendSmsByCustomModelData   
	 * @Description: 根据自定义模板填充数据发送   
	 * @param: @param sendData
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	Result sendSmsByCustomModelData(SmsSendData sendData);
	/**
	 * 
	 * @Title: checkSmsCodeByMonile   
	 * @Description: 校验短信验证码     
	 * @param: @param mobile
	 * @param: @param type
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	Result checkSmsCodeByMobile(SmsSendData sendData);
	
	Result checkSmsCodeByMobile2(SmsSendData sendData);
}
