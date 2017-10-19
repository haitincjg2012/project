package com.ph.shopping.common.util.order;

/**
 *
 * @ClassName: OrderBizCode
 * @Description: 支付业务处理
 * @author WQiang
 * @date 2017年3月29日 上午11:32:45
 */
/**
 * @项目：phshopping-common-util
 *
 * @描述：订单对应业务编码
 *
 * @作者： Mr.chang
 *
 * @创建时间：2017年3月29日
 *
 * @Copyright @2017 by Mr.chang
 */
public final class OrderBizCode {
	/**
	 * ==============================充值订单编码================================
	 */
	/** 充值订单统一业务编号 */
	public static final String CHARGE_ORDER = "CZDD";

//	/**商户充值*/
//	public static final String MERCHANT_CAHRGE = "CZ01";// 商户充值订单业务编码
//
//	/**代理商充值*/
//	public static final String AGENT_CAHRGE = "CZ02";// 代理商充值订单业务编码
//
//	/**供应商充值*/
//	public static final String SUPPLIER_CAHRGE = "CZ03";// 供应商充值订单业务编码

	/**
	 * ==============================进货订单编码================================
	 */

	/**供链进货*/
	public static final String PURCHASE_GOODS = "JH01";// 供应链进货子订单业务编码

	/**代理商进货*/
//	public static final String MAIN_PURCHASE_GOODS = "JHZ01";// 供应链进货总订单业务编码
	public static final String MAIN_PURCHASE_GOODS = "JHZD";// 供应链进货总订单业务编码


	/**
	 * ==============================提现订单编码================================
	 */
	/** 后台用户提现订单统一编码 */
	public static final String DRAW_CASH_NO = "TXDD";

//	/**商户提现*/
//	public static final String MERCHANT_DRAW_CASH = "TX01";// 供应商提现订单业务编码
//
//	/**代理商提现*/
//	public static final String AGENT_DRAW_CASH = "TX02";// 代理商提现订单业务编码
//
//	/**供应商提现*/
//	public static final String SUPPLIER_DRAW_CASH = "TX03";// 供应商提现订单业务编码

	/**会员提现*/
	public static final String MEMBER_DRAW_CASH = "TX04";// 会员提现订单业务编码

	public static final String SHARE_MEMBER_DRAW_CASH = "TX05";// 会员提现订单业务编码
	public static final String SHARE_MERCHANT_DRAW_CASH = "TX06";// 商户提现订单业务编码

	public static final String GUAGUALE_SCORE_CASH = "TX07";//刮刮乐提现订单业务编码

	public static final String DAYRETURN_SCORE_CASH = "TX08"; //刮刮乐提现订单业务编码

	/**
	 * ==============================会员订单编码================================
	 */

	/**会员下单*/
//	public static final String SUB_MEMBER_ORDER = "MO01";// 会员下单订单业务编码
	public static final String SUB_MEMBER_ORDER = "XS01";// 会员线上订单子订单
	public static final String MAIN_MEMBER_ORDER = "XSZD";//会员线上订单主订单
	/**
	 * ==============================会员订单编码================================
	 */
	/**商户创建线下订单*/
//	public static final String MEMBER_UNLINEORDER = "XXO01";// 会员线下订单业务编码
	public static final String MEMBER_UNLINEORDER = "XXOD";// 会员线下订单业务编码

}
