package com.ph.shopping.facade.pay.constant;

/**
 * @项目：phshopping-parent
 * @描述：提现参数
 * @作者： Mr.chang
 * @创建时间：2017/5/31
 * @Copyright @2017 by Mr.chang
 */
public class CashConstant {

    //单笔提现最小金额100
    public static final long MIN_CASH_MONEY=1000000l;
    //单笔提现最大金额10000
    public static final long MAX_CASH_MONEY=100000000l;
    //当日提现最大金额10000
    public static final long MAX_CASH_MONEY_EVERY_DAY=100000000l;
    //提现手续费5
    public static final long HANDING_CHARGE=50000l;

    /**
     * 修改订单状态操作类型
     */
    public static final Byte OPERATE_TYPE = 2;
    /**
     * 订单操作描述
     */
    public static final String OPERATE_DESCRIPTION = "修改订单为代发货" ;

    /**
     * 订单操作描述
     */
    public static final String OPERATE_DESCRIPTION_CANCEL = "修改订单为代取消订单" ;


    /**
     * 订单超时操作描述
     */
    public static final String OPERATE_DESCRIPTION_CANCEL_TIME = "银行返回T503，订单超时,修改订单为代取消订单" ;

    /**
     * 退款申请拒绝原因
     */
    public static final String REFUND_REASON = "银行退款失败，请重新申请退款" ;

    /**
     * 余额支付
     */
    public static final Byte BALANCE =(byte) 0;

}
