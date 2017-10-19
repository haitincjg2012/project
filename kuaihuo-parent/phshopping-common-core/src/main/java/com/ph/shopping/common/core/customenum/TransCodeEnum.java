package com.ph.shopping.common.core.customenum;

/**
 * 交易码
 * 
 * @author WQiang
 * 
 */
public enum TransCodeEnum {

	MEMBER_CONSUME(1000, "会员线上订单购买消费积分", -1, "会员线上订单购买商品消费积分"),

	MEMBER_BACK_SCORE(1001, "商品赠送积分", 1, "购买商品后返回待用积分"),

	MEMBER_DRAWCASH(1002, "提现消费积分", -1, "会员提现消费积分"),
	
	MEMBER_DRAWCASHADD(1003, "提现累计积分", 1, "会员已提现积分累计"),
	
	MEMBER_RETURN_ENABLESCORE(1004, "返回可用积分", 1, "返回可用积分"),
	
	MEMBER_COST_STANDBYSCORE(1005, "消耗待用积分", -1, "消耗待用积分转为可用积分"),
	
	HANDEL_USER_DRAWCASH(1006, "处理用户提现", 1, "数据问题提现失败返回用户余额"),
	
	HANDEL_MEMBER_DRAWCASH(1007, "处理会员提现", 1, "数据问题提现失败返回会员可用积分"),
	
	MERCHANT_UNLINE_ORDER(1008, "创建线下订单", -1, "创建线下订单余额消费"), 
	
	MEMBER_UNLINE_ORDER(1009, "线下消费积分", -1, "线下订单消费积分"),
	
//	MEMBER_CASH(1015,"会员提现",-1,"增加会员提现流水"),
	
	MERCHANT_UNLINE_ORDER_CANCEL(1016, "取消线下订单(退款)", 1, "取消线下订单返回商户余额"),
	
	MEMBER_UNLINE_ORDER_CANCEL(1017, "取消线下消费积分(退款)", 1, "取消线下订单返回用户可用积分"), 

	ONLINE_ORDER_REFUND(1018, "线上订单退款返回会员可用积分", 1, "线上订单退款返回会员可用积分"),
	
	MEMBER_RETURN_STANDBYSCORE(1019, "返回待用积分", 1, "创建线下订单返回待用积分"),
	// PROMOTER_PROFIT(1021, "推广师分润", 1, "线下订单推广师分润"),

	// MERCHANT_CHARGE_ORDER(1010,"商户充值",-1,"商户充值订单"),
	// AGENT_CHARGE_ORDER(1011,"代理商充值",-1,"代理商充值订单"),
	// SUPPLIER_CHARGE_ORDER(1012,"供应商充值",-1,"供应商充值订单"),
	USER_CHARGE_ORDER(1030, "在线充值", 1, "后台用户在线充值(商户,代理商,供应商)"),

	// MERCHANT_WITHDRAWALS(1013,"商户提现",-1,"商户提现操作用户余额记录表"),
	// AGENT_WITHDRAWALS(1014,"代理提现",-1,"代理提现操作用户余额记录表"),
	USER_DRAWCASH_ORDER(1031, "余额提现", -1, "后台用户余额提现(商户,代理商,供应商)"),

	// MERCHANT_STOCK(1007,"商户进货",-1,"商户进货消耗余额"),
	// AGENT_STOCK(1006,"代理商进货",-1,"代理商进货消耗余额"),
	USER_STOCK(1032, "用户进货", -1, "后台用户消费余额进货(商户,代理商,供应商)"),

	USER_CHARGE_ORDER_FAIL(1033, "充值失败", 1, "用户充值失败(商户,代理商,供应商)"), // 不能更新用户余额

	USER_DRAWCASH_ORDER_FAIL(1034, "用户提现失败", 1, "用户提现失败返回余额(商户,代理商,供应商)"),

	USER_RE_STOCK(1035, "用户进货(退款)", 1, "后台用户退换进货余额(商户,代理商,供应商)"),

	MEMBER_DRAWCASH_FAIL(1036, "会员提现失败", 1, "会员提现失败返回可用积分"),

	MEMBER_DRAWCASH_FAIL_REDUCE(1038, "扣除提现累计积分", -1, "会员提现失败扣除提现累计积分"),

	HUNTER_ORDER_RETURN_SCORE(1037, "批发商订单返会员待用积分", 1, "批发商订单返会员待用积分"),

	/*----------------------------------------------分润相关开始------------------------------------------------------------*/
	// 供应链订单分润增加用户余额 交易码
	PURCHASE_ORDER_PROFIT_TRANSCODE(1050, "供应链订单分润增加用户余额 ", 1, "供应链订单分润增加用户余额 "),

	// 线下订单分润增加推广师可用积分 交易码
	UNLINE_ORDER_PROFIT_ADD_SCORE_TRANSCODE(1051, "线下订单分润增加推广师可用积分", 1, "线下订单分润增加推广师可用积分"),

	// 线下订单分润增加用户余额 交易码
	UNLINE_ORDER_PROFIT_TRANSCODE(1052, "线下订单分润增加用户余额", 1, "线下订单分润增加用户余额"),

	// 订单分润管理费 交易码
	ORDER_MANAGE_PROFIT_TRANSCODE(2000, "订单分润管理费", 1, "订单分润管理费"),

	// 订单分润推广费 交易码
	ORDER_RECOMMOND_PROFIT_TRANSCODE(2001, "订单分润推广费", 1, "订单分润推广费"),

	// 订单分润商户分享人 交易码
	ORDER_SHARE_MERCHANT_PROFIT_TRANSCODE(2002, "订单分润商户分享人", 1, "订单分润商户分享人"),

	// 订单分润用户分享人 交易码
	ORDER_SHARE_MEMBER_PROFIT_TRANSCODE(2003, "订单分润用户分享人", 1, "订单分润用户分享人"),

	// 订单分润用户分享人 交易码
	ORDER_STORE_MANAGER_PROFIT_TRANSCODE(2004, "订单分润店面经理", 1, "订单分润店面经理"),

	// 订单分润商户分享人 交易码
	ORDER_SHARE_MERCHANT_WITHDRAW_FAIL_TRANSCODE(2012, "订单分润商户提现失败", 1, "订单分润商户提现失败"),

	// 订单分润用户分享人 交易码
	ORDER_SHARE_MEMBER_WITHDRAW_FAIL_TRANSCODE(2013, "订单分润用户提现失败", 1, "订单分润用户提现失败"),

	// 订单分润用户分享人 交易码
	ORDER_STORE_MANAGER_WITHDRAW_FAIL_TRANSCODE(2014, "订单分润店面经理提现失败", 1, "订单分润店面经理提现失败"),

	// 线上订单分润增加用户余额 交易码(管理)
	ONLINE_ORDER_PROFIT_MANAGE_TRANSCODE(1053, "线上订单分润增加用户余额(管理)", 1, "线上订单分润增加用户余额(管理)"),

	// 线上订单分润增加用户余额 交易码(供应链)
	ONLINE_ORDER_PROFIT_CHAIN_TRANSCODE(1054, "线上订单分润增加用户余额 (供应链)", 1, "线上订单分润增加用户余额(供应链)"),

	// 线上订单分润增加推广师可用积分 交易码
	ONLINE_ORDER_PROFIT_ADD_SCORE_TRANSCODE(1055, "线上订单分润增加推广师可用积分", 1, "线上订单分润增加推广师可用积分"),

	// 批发商订单分润增加用户余额 交易码(管理)
	HUNTER_ORDER_PROFIT_MANAGE_TRANSCODE(1056, "线上订单分润增加用户余额(管理)", 1, "线上订单分润增加用户余额(管理)"),

	// 批发商订单分润增加用户余额 交易码(供应链)
	HUNTER_ORDER_PROFIT_CHAIN_TRANSCODE(1057, "线上订单分润增加用户余额 (供应链)", 1, "线上订单分润增加用户余额(供应链)"),

	// 批发商订单分润增加推广师可用积分 交易码
	HUNTER_ORDER_PROFIT_ADD_SCORE_TRANSCODE(1058, "线上订单分润增加推广师可用积分", 1, "线上订单分润增加推广师可用积分"),

	//普惠批发商城订单分润增加用户余额
	WHOLESALE_ORDER_PROFIT(1059, "普惠批发商城订单分润增加用户余额", 1, "普惠批发商城订单分润增加用户余额")

	/*----------------------------------------------分润相关开结束------------------------------------------------------------*/,

	/*----------------------------------------------结算相关开始(码从1060开始)--------------------------------------------------*/
	// 线下订单结算交易码(增加商户余额)
	UNLINE_ORDER_SETTLE(1060, "线下订单结算", 1, "线下订单结算增加用户余额"),
	// 线上订单结算交易码(增加用户余额)
	ONLINE_ORDER_SETTLE(1061, "线上订单结算", 1, "线上订单结算增加用户余额"),
	// 供链订单结算交易码(增加用户余额)
	SUPPLY_CHAIN_ORDER_SETTLE(1062, "供链订单结算", 1, "供链订单结算增加用户余额"),
	//!!!!!!!!!!!!!!!!!!!!!!!!!!特别注意这里的code是1063!1063!1063! 后面新增加的!!!!!!
	// 线下订单分润增加会员分润积分 交易码
	UNLINE_ORDER_PROFIT_MEMBER_ADD_SCORE_TRANSCODE(1063, "线下订单分润增加会员分润积分", 1, "线下订单分润增加会员分润积分"),
	// 会员推广会员返奖励积分
	RECOMMEND_MEMBER_UNLINE_ORDER_REWARD_SCORE(1064,"会员推广会员返奖励积分",1,"线下消费返会员奖励积分"),
	// 会员推广商户奖励积分
	RECOMMEND_MERCHANT_UNLINE_ORDER_REWARD_SCORE(1065,"会员推广商户返奖励积分",1,"线下消费返会员奖励积分"),

	// 推广会员积分提现
	SHARE_MEMBER_CASH_SCORE(1066,"推广会员积分提现",-1,"线下消费返会员奖励积分"),

	// 推广商户积分提现
	SHARE_MERCHANT_CASH_SCORE(1067,"推广商户积分提现",-1,"线下消费返会员奖励积分"),

	SHARE_MEMBER_CASH_FAIL(1068,"推广用户积分提现失败",1,"推广用户积分"),

	SHARE_MERCHANT_CASH_FAIL(1069,"推广商户积分提现失败",1,"推广商户积分"),

	MERCHANT_CASH_FAIL(1070,"商户提现失败",1,"商户积分"),

	// 刮刮乐积分提现
	GUAGUALE_SCORE_CASH(1,"刮刮乐积分提现",1,"线上消费返会员奖励积分"),

	// 天天返积分提现
	DAYRETURN_SCORE_CASH(0,"天天返积分提现",1,"线上消费返会员奖励积分");


	private TransCodeEnum(int code, String source, int mark, String msg) {
		this.code = code;
		this.source = source;
		this.mark = mark;
		this.msg = msg;
	}

	private int code;
	private String source;
	private int mark;
	private String msg;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}
