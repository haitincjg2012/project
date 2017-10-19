package com.phshopping.api.constant;

import com.ph.shopping.common.util.core.RespCode;

/**
 * 商户app结果返回枚举
* @ClassName: MechantAppResultEnum
* @Description: TODO(这里用一句话描述这个类的作用)
* @author Mr.Dong
* @date 2017年6月20日 上午10:51:40
 */
public enum MechantAppResultEnum  implements  RespCode {
	
	SUCCESS("200", "成功"),
	NO_MEMBERSCORCE_EXECEPTION("20016","无会员积分数据"),
	NO_MEMBERCARD_EMPTY("20020","无此会员卡数据"),
	NO_UNLINEORDERDATA_EXCEPTION("60028","获取线下订单异常"),
	NO_MERCHANT_BALANCERECORD("60030","获取商户余额流水列表异常"),
	NO_POWER("60032","无权访问"),
	NO_ROLE("60033","请补全角色信息"),
	NO_LOGIN("60034","用户未登陆"),
	NO_MERCHANT("60035","无此商户"),
	DO_MEMBER_FAULT("60036","创建会员失败"),
	ONLY_MERCHANT_LOGIN("60031","只允许商户登陆"),
	AUTHENTICATION_ERROR("60032","实名认证失败"),
	NO_BANK("60033","暂无银行卡信息"),
	AUTH_REQUERED("60034","请先实名认证"),
	NO_BANK_MESSAGE("60035","暂无此银行数据"),
	NO_FROREN_USER("60036","该用户已冻结请联系客服");
	
	MechantAppResultEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	private String code;
	private String msg;


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
