package com.ph.shopping.facade.member.menum;

import com.ph.shopping.common.util.core.RespCode;

/**
 * 
 * @ClassName:  MemberResultEnum   
 * @Description:会员相关返回状态码   
 * @author: 李杰
 * @date:   2017年4月25日 上午10:55:51     
 * @Copyright: 2017
 */
public enum MemberResultEnum implements RespCode{

	MEMBER_FAILURE("20001", "操作失败"),
	MEMBER_NO_DATA("20004","未查询到相关数据"),
	MEMBER_NATIVE_PWD_MISMATCH("20006","原密码错误"),
	MEMBERUSER_NON_EXISTENT("20007","该会员不存在"),
	MEMBERAUTH_EXIST("20008","已申请或已是推广师"),
	ACCOUNT_EXIST("20009","账号已经被绑定推广师"),
	ACCOUNT_VILIDATE_ERROR("20010","推广师校验出错"),
	MEMBER_FROZEN("20011","会员处于冻结状态"),
	MEMBER_PWD_MISMATCH("20012","密码错误"),
	MEMBER_USER_MISMATCH("20013","用户不存在"),
	BANK_CHECK_FAIL("20014","校验银行卡失败"),
	ID_NOT_CERTIFIED("20015","您未进行实名认证"),
	NO_MEMBERSCORCE_EXECEPTION("20016","无会员积分数据"),
	SMS_CODE_ERROR("20017","短信验证码错误"),
	SMS_CODE_INVALID("20018","短信验证码失效"),
	SMS_CODE_EMPTY("20019","未发送短信验证码"),
	NO_MEMBERCARD_EMPTY("20020","无此会员卡数据"),
	ID_IS_EMPTY("20021","未绑定身份证"),
	NO_CHECKBIND_EXCEPTION("20022", "您没有绑定该银行卡信息"),
	PAY_PWD_EMPTY("20023","未设置支付密码"),
	MEMBER_EXISTS("20025","会员已存在"),
    PAY_PWD_ERROR("20026", "支付密码错误"),

    //会员卡
    ADD_MEMBER_CARD_ERROR("20032", "新增会员卡错误"),
    ADD_ALL_MEMBER_CARD_ERROR("20033", "批量添加会员卡错误"),
    MEMBER_CARD_FROZEN("20034", "会员卡已冻结"),
    MEMBER_CARD_LOSS("20035", "会员卡已挂失"),
    BIND_MEMBER_CARD_ERROR("20036", "绑定会员卡信息错误"),
    LOSS_MEMBER_CARD_ERROR("20037", "挂失会员卡信息错误"),
    FROZEN_MEMBER_CARD_ERROR("20038", "冻结会员卡信息错误"),
    START_MEMBER_CARD_ERROR("20039", "解冻会员卡信息错误"),
    

    //影响分润操作流水新增失败
    ADD_PROFITOPTLOG_ERROR("20040", "新增影响分润操作流水记录错误"),
    
    //推广师不存在
    PROMOTER_NON_EXISTENT("20041", "推广师不存在"),
    PROMOTER_CERTIFICATE_NON("20042", "推广师未实名认证"),
	MEMBER_CERTIFICATE_NON("20043", "会员未实名认证"),

	//证件认证
	CERTIFICATE_ALREADY_EXISTS("20044", "证件已经认证成功,不需要重新认证"),
	CERTIFICATE_TYPE_WRONG("20045", "证件类型错误"),
	FAIL_CHECK_CERTIFICATE("20046", "您的身份信息有误，请重新填写"),
	CERTIFICATE_SUCCESS("20047", "认证成功"),

	//银行卡
	BINDING_SUCCESSFUL("20048", "会员银行卡绑定成功"),
	VERIFICATION_FAILED("20049", "您的银行卡号和姓名不匹配，请重新输入"),
	
	
	SHAPE_CODE_EXIST("20050","会员卡条形码已存在"),
	IC_CODE_EXIST("20051","会员卡卡号已存在"),
	IC_CARD_EXIST("20052","会员卡已存在"),
	IC_CARD_INEXISTENCE("20053","会员卡不存在"),
	IC_CARD_ALREADYBIND("20054","会员卡已被绑定"),
	
	//推广师三方账号认证结果
	ACCOUNT_NOT_EXIST("20055","账号必须为本地电商或新业态"),
	ACCOUNT_PWD_ERROR("20056","密码错误"),
	HEADHUNTING_EXIST("20057","批发商已存在"),
	
	MEMBER_ATTENTION("20058","此手机号已经绑定推荐关系"),
	
	SHARE_MEMBER_EQUAL("20060","分享的账号不能是本人"),
	
	SHARE_MEMBER_EXISTS("20061","分享的账号已经是会员"),
	
	SHARE_MERCHANT_EXISTS("20062","分享的账号已经是商户"),
	
	SHARE_TYPE_NOEXISTS("20063","分享类型不存在")
	;
	
	/**
	 * 编码
	 */
	private String code;
	/**
	 * 描述
	 */
	private String msg;

	MemberResultEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	
}
