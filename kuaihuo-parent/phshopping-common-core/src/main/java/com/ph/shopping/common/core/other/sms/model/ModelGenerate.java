/**  
 * @Title:  ModelGenerate.java   
 * @Package com.ph.shopping.common.core.other.sms   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年7月12日 下午2:50:54   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.common.core.other.sms.model;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ph.shopping.common.core.customenum.SmsCodeType;

/**   
 * @ClassName:  ModelGenerate   
 * @Description:短信模板生成   
 * @author: 李杰
 * @date:   2017年7月12日 下午2:50:54     
 * @Copyright: 2017
 */
public class ModelGenerate {
	
	private static final Logger log = LoggerFactory.getLogger(ModelGenerate.class);
	/**
	 * 短息模板容器
	 */
	private static Map<SmsCodeType, String> MODEL = new HashMap<SmsCodeType, String>();
	/**
	 * 初始化容器数据:需要新增时则新增对应的模板方法信息
	 */
	static {
		// 初始化短信模板容器
		MODEL.put(SmsCodeType.MEMBER_REGISTER_PD, "getSmsByPwd");
		MODEL.put(SmsCodeType.RESET_PASSWORD_PD, "getSmsByPwd");
		MODEL.put(SmsCodeType.EXAMINE_PASS, "getSmsByPwd");
		MODEL.put(SmsCodeType.EXAMINE_NOT_PASS, "getSmsNotPass");
		MODEL.put(SmsCodeType.USER_CONSUME_REMIND, "getMemberConsumeRemind");
		MODEL.put(SmsCodeType.UPGRADE_MEMBER_PROMOTION_AUTO, "getUpMemberPromotionAuto");
	}
	/**
	 * 
	 * @Title: getModel   
	 * @Description:    
	 * @param: @param mobile
	 * @param: @param code
	 * @param: @param smsSource
	 * @param: @param smsCodeType
	 * @param: @return      
	 * @return: String
	 * @author：李杰      
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	public static String getSmsModel(final ModelData data) {
		String result = "";
		final SmsCodeType type = data.getSmsCodeType();
		if(!SmsCodeType.isExists(type)){
			log.info("当前短信发送类型枚举值不存在");
			return result;
		}
		String methodStr = MODEL.get(type);
		if (StringUtils.isBlank(methodStr)) {
			// 如果没有获取到枚举类型则使用默认的短信模板方法
			methodStr = "getSmsVerifyCode";
		}
		Class clas = YunPianSmsModel.class;
		Method[] methods = clas.getDeclaredMethods();
		int size = methods.length;
		// 得到执行的方法
		Method execute = null;
		for (int i = 0; i < size; i++) {
			if (methods[i].getName().equals(methodStr)) {
				execute = methods[i];
				break;
			}
		}
		if (null == execute) {
			return result;
		} else {
			Object[] arguments = new Object[] { data };
			try {
				Object obj = execute.invoke(clas.newInstance(), arguments);
				if (null != obj) {
					result = obj.toString();
				}
			} catch (Exception e) {
				log.error("短信模板生成错误", e);
			}
		}
		return result;
	}
}
