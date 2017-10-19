package com.ph.shopping.common.util.order;

public class Constants {
	//会员收货地址
	public static int ISDEFAULT = 1;//是默认
	
	public static int NOTDEFAULT = 2;//不是n默认
	
	
	
	//订单状态
	/** 未审核 */
	public static int UNAUDIT = 0;
	/**待付款*/
	public static int WAIT_PAY = 1;
	/**待发货*/
	public static int WAIT_SEND = 2;
	/**确认收货*/
	public static int CONFIRM_GOODS = 3;
	/**交易完成*/
	public static int FINISH = 4;
	/**交易关闭*/
	public static int CLOSED = 5;
	/**退款中*/
	public static int REFUND = 6;
	/**退款完成*/
	public static int REFUND_FINISH = 7;
	
	
	/**成功状态*/
	public static int SUCCESS = 1;
	
	/**失败状态*/
	public static int FAIL = -1;
	
	//普通会员返回积分比例万分之3
	public static long COMM_MEMBER = 5l;
	//VIP会员返回积分比例万分之5
	public static long VIP_MEMBER = 5l;
	
	public static long BASE_MEMBER = 10000L;
	
	
	public static int ORDER_TIME_OUT = 30; //30分钟
}
