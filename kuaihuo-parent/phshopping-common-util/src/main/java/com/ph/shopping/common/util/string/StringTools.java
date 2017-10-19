package com.ph.shopping.common.util.string;

/**
 * @项目：phshopping-common-util
 *
 * @描述：字符串处理工具类
 *
 * @作者： Mr.chang
 *
 * @创建时间：2017年4月7日
 *
 * @Copyright @2017 by Mr.chang
 */
public class StringTools {
	
	/**
	 * 将银行卡号限前4后3中间用****填充
	 * @param src
	 * @return
	 * @author Mr.Chang
	 */
	public static String bankNoChange(String src) {
		if (src == null || src.trim().length() <= 0) {
			return "";
		}
		return src.substring(0, 4) + "****" + src.substring(src.length() - 3, src.length());
	}
	
	/**
	 * 将真实姓名限前**后1个名字
	 * @param src
	 * @return
	 * @author Mr.Chang
	 * 
	 */
	public static String realNameChange(String src) {
		if (src == null || src.trim().length() <= 0) {
			return "";
		}
		return "**" + src.charAt(src.length() - 1);
	}

}
