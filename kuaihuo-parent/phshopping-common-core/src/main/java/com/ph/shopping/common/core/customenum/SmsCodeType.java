package com.ph.shopping.common.core.customenum;

import com.alibaba.druid.util.StringUtils;

/**
 * 
* @ClassName: SmsCodeType  
* @Description:短信发送类型枚举，类型 格式 已 PH开头 后面为日期格式 不能重复
* @author lijie   
* @date 2017年3月21日  
*
 */
public enum SmsCodeType {

	//验证码
	BACK_USER_PWD_VC("PH20170101","找回密码",(byte)0),
	BIND_MEMBERBANK_VC("PH20170102","绑定银行卡",(byte)0),
	MEMBER_UPDATE_BANK_VC("PH20170103","修改银行卡",(byte)0),
	SET_PAYPWD_VC("PH20170118","设置支付密码",(byte)0),
	UPDATE_PASSWORD_VC("PH20170104","修改登录密码",(byte)0),
	UPDATE_PAYPWD_VC("PH20170105","修改支付密码",(byte)0),
	MEMBER_CASH_VC("PH20170106","积分提现",(byte)0),
	BIND_MEMBERCARD_VC("PH20170107","绑定会员卡",(byte)0),
	LOSS_MEMBERCARD_VC("PH20170108","挂失会员卡",(byte)0),
	BALANCE_CASH_VC("PH20170109","余额提现",(byte)0),
	MEMBER_REGISTER_VC("PH20170110","注册普惠会员",(byte)0),
	APPLY_AGENT_VC("PH20170111","申请普惠代理",(byte)0),
	SAFEMANAGE_CODE_VC("PH20170112", "安全管理",(byte)0),
	SCORE_PAY_VC("PH20170113","积分支付",(byte)0),
	ADD_SALESMAN_VC("PH20170113","添加业务员",(byte)0),
	
	//密码
	MEMBER_REGISTER_PD("PH20170114","您已成为快火会员",(byte)1),
	RESET_PASSWORD_PD("PH20170115","您的密码重置成功",(byte)1),
	EXAMINE_PASS("PH20170116","您的申请资料已通过审核（针对于商户、代理、供应商的审核），请尽快登陆普惠后台管理系统（main.phds315.com）",(byte)1),
	
	//纯信息
	EXAMINE_NOT_PASS("PH20170117","商户、代理、供应商的审核通过发送密码",(byte)1),
	
	USER_CONSUME_REMIND("PH20170118","消费提醒",(byte)1),
	
	HUNTERMEMBER_REGISTER_PD("PH20170119","您已成为普惠批发商会员",(byte)1),
	
	MEMBER_SHARE("PH20170120","通过分享建立推荐关系",(byte)0),
	
	UPGRADE_MEMBER_PROMOTION_AUTO("PH20170121","会员推广3个商户自动升级为推广师",(byte)0),
	
	MER_REGISTER_VC("PH20170122","申请商户",(byte)0),
	
	MER_REGISTER("Fr170002","申请商户/找回密码",(byte)1),
	
	MER_REGISTER_FR("Fr170001","申请商户/找回密码",(byte)0);
	
	/**
	 * 短信类型编码
	 */
	private String codeType;
	/**
	 * 描述
	 */
	private String desc;
	/**
	 * 是否缓存 0:要缓存
	 */
	private Byte isCache;
	
	private SmsCodeType(String codeType, String desc, Byte isCache) {
		this.codeType = codeType;
		this.desc = desc;
		this.isCache = isCache;
	}

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public Byte getIsCache() {
		return isCache;
	}

	public void setIsCache(Byte isCache) {
		this.isCache = isCache;
	}

	/**
	 * 
	 * @Title: getSmsCodeTypeByCode
	 * @Description: 根据code的值获取SmsCodeType枚举对象
	 * @author liuy
	 * @date  2017年6月1日 下午5:41:15
	 * @param code
	 * @return
	 */
	public static SmsCodeType getSmsCodeTypeByCode(String code){
		for(SmsCodeType smsCodeType : SmsCodeType.values()){
			if(StringUtils.equals(code, smsCodeType.getCodeType())){
				return smsCodeType;
			}
		}
		return null;
	}
	/**
	 * 
	 * @Title: isExists   
	 * @Description: 判断短信类型枚举值是否存在   
	 * @param: @param type
	 * @param: @return      
	 * @return: boolean
	 * @author：李杰      
	 * @throws
	 */
	public static boolean isExists(SmsCodeType type){
		if(null != type){
			for(SmsCodeType smsCodeType : SmsCodeType.values()){
				if(type == smsCodeType){
					return true;
				}
			}
		}
		return false;
	}
}
