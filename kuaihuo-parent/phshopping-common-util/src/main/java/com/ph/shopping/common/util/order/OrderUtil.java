package com.ph.shopping.common.util.order;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @项目：phshopping-common-util
 * @描述：订单工具类
 * @作者： Mr.Chang
 * @创建时间： 14:06 2017/5/31
 * @Copyright @2017 by Mr.Chang
 */
public class OrderUtil {
    // 生成订单编码随机数(根据bizCOde处理业务)
    public synchronized static String createOrderCode(String bziCode) {
			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyySSSMMmmss");
			return "KH" + bziCode + sdf.format(d) + (int)(Math.random() * 89999 + 10000);
		}

	/**
	 * 根据订单号截取支付处理的业务代码
	 * @param orderNum
	 * @return
	 * @author Mr.Chang
	 *
	 */
	public static String getOrderBizCode(String orderNum){
		return orderNum.substring(2, 6);
	}
	
	public static void main(String[] args) {
		System.out.println(createOrderCode("JH01"));
	}
}
