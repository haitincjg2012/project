/**  
 * @Title:  YunPianModel.java   
 * @Package com.ph.shopping.common.util.other.smssend   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年5月11日 下午12:33:16   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.common.core.other.sms.model;

import org.springframework.beans.BeanUtils;

import com.ph.shopping.common.util.other.smssend.model.data.ConsumeRemindModelData;

/**   
 * @ClassName:  YunPianModel   
 * @Description:云片短信模板   
 * @author: 李杰
 * @date:   2017年5月11日 下午12:33:16     
 * @Copyright: 2017
 */
public class YunPianSmsModel {

	/**
	 * 
	 * @Title: getSmsNotPass
	 * @Description: 1.	商户、代理、供应商：审核不通过 短信模板
	 * @author liuy
	 * @date  2017年6月1日 下午3:08:49
	 * @param code
	 * @return
	 */
	public static String getSmsNotPass(ModelData data) {
		StringBuilder sbud = new StringBuilder();
		sbud.append("【快火】非常抱歉，您的普惠")
		.append(data.getSmsSource())
		.append("申请未审核通过，请重新确认您的信息，期待您的再次申请。如有疑问请致电4000-8586-315。");
		return sbud.toString();
	}
	/**
	 * 
	 * @Title: getSmsByPwd
	 * @Description: 短信密码模板  
	 * @author liuy
	 * @date  2017年6月1日 下午4:25:30
	 * @param smsSource
	 * @param item
	 * @param telPhone
	 * @param code
	 * @return
	 */
	public static String getSmsByPwd(ModelData data) {
		StringBuilder sbud = new StringBuilder();
		sbud.append("【快火】尊敬的普惠");
		sbud.append(data.getSmsSource());
		sbud.append("，");
		sbud.append(data.getItem());
		sbud.append("，登录账号：");
		sbud.append(data.getMobile());
		sbud.append("，登录密码：");
		sbud.append(data.getCustomData());
		sbud.append("，为保障账户安全，请尽快修改初始密码，如有疑问请致电4000-8586-315。");
		return sbud.toString();
	}
	
	/**
	 * 
	 * @Title: getSmsVerifyCode
	 * @Description: 短信验证码模板  
	 * @author liuy
	 * @date  2017年6月1日 下午4:20:59
	 * @param smsSource 短信来源（类型）
	 * @param item 操作项
	 * @param telPhone 账号
	 * @param code 验证码
	 * @return
	 */
	public static String getSmsVerifyCode(ModelData data) {
		StringBuilder sbud = new StringBuilder();
		sbud.append("【快火】尊敬的普惠");
		sbud.append(data.getSmsSource());
		sbud.append("，您正在进行");
		sbud.append(data.getItem());
		sbud.append("操作，验证码为：");
		sbud.append(data.getCustomData());
		sbud.append("，为保障账户安全，请勿泄露给他人，验证码1分钟内有效。如非本人操作请致电4000-8586-315。");
		return sbud.toString();
	}
	/**
	 * 
	 * @Title: getMemberConsumeRemind   
	 * @Description: 会员消费提醒短信模板   
	 * @param: @param modeldata
	 * @param: @return      
	 * @return: String
	 * @author：李杰      
	 * @throws
	 */
	public static String getMemberConsumeRemind(ModelData data){
		Object obj = data.getCustomData();
		if(null != obj){
			ConsumeRemindModelData modeldata = new ConsumeRemindModelData();
			BeanUtils.copyProperties(obj, modeldata);
			StringBuilder sbud = new StringBuilder();
			sbud.append("【快火】尊敬的普惠")
			.append(modeldata.getSmsAcceptType())
			.append("，您于")
			.append(modeldata.getYear()).append("年")
			.append(modeldata.getMonth()).append("月")
			.append(modeldata.getDay())
			.append("日在快火消费").append(modeldata.getMoney())
			.append("元，订单号")
			.append(modeldata.getOrderNo())
			.append("，获得")
			.append(modeldata.getSubsidyScore())
			.append("补贴积分，目前可用积分")
			.append(modeldata.getUsableScore())
			.append("，待用积分")
			.append(modeldata.getStandbyScore())
			.append("。");
			return sbud.toString();
		}
		return null;
	}

	/**
	 * 
	 * @Title: getUpMemberPromotionAuto
	 * @Description: 会员推广3个商户自动升级为推广师参数
	 * @author liuy
	 * @date  2017年7月26日 下午3:08:49
	 * @param code
	 * @return
	 */
	public static String getUpMemberPromotionAuto(ModelData data) {
		StringBuilder sbud = new StringBuilder();
		sbud.append("【快火】尊敬的普惠")
		.append(data.getSmsSource())
		.append("，您已达到成为推广师的条件，现已为您升级为推广师");
		return sbud.toString();
	}
}
