package com.alqsoft.utils.weixin.wxpay;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alqsoft.utils.weixin.wxpay.config.WeiXinPayConfig;


/**
 * 
 * @Title: PayCommonUtil.java
 * @Description: 支付通用工具类
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年2月4日 下午3:27:50 Copyright © 2013 厦门卓讯信息技术有限公司 All rights
 *              reserved.
 *
 */
public class PayCommonUtil {
	private static Logger log = LoggerFactory.getLogger(PayCommonUtil.class);
	public static String CreateNoncestr(int length) {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String res = "";
		for (int i = 0; i < length; i++) {
			Random rd = new Random();
			res += chars.indexOf(rd.nextInt(chars.length() - 1));
		}
		return res;
	}

	public static String CreateNoncestr() {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String res = "";
		for (int i = 0; i < 16; i++) {
			Random rd = new Random();
			res += chars.charAt(rd.nextInt(chars.length() - 1));
		}
		return res;
	}
	/**
	 * 
	 * @Title: createSign
	 * @Description：sign签名
	 * @param characterEncoding 编码格式
	 * @param parameters 请求参数
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String createSign(String characterEncoding,SortedMap<Object,Object> parameters){
		StringBuffer sb = new StringBuffer();
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String k = (String)entry.getKey();
			Object v = entry.getValue();
			if(null != v && !"".equals(v) 
					&& !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + WeiXinPayConfig.API_KEY);
		String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
		return sign;
	}
	/**
	 * 
	 * @Title: getRequestXml
	 * @Description：将请求参数转换为xml格式的string
	 * @param parameters 请求参数
	 * @return String 返回类型
	 * @throws
	 */
	public static String getRequestXml(SortedMap<Object,Object> parameters){
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String k = (String)entry.getKey();
			String v = (String)entry.getValue();
			if ("attach".equalsIgnoreCase(k)||"body".equalsIgnoreCase(k)||"sign".equalsIgnoreCase(k)) {
				sb.append("<"+k+">"+"<![CDATA["+v+"]]></"+k+">");
			}else {
				sb.append("<"+k+">"+v+"</"+k+">");
			}
		}
		sb.append("</xml>");
		return sb.toString();
	}
	/**
	 * 
	 * @Title: setXML
	 * @Description：返回给微信的参数
	 * @param return_code 返回编码
	 * @param return_msg 返回信息
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String setXML(String return_code, String return_msg) {
		return "<xml><return_code><![CDATA[" + return_code
				+ "]]></return_code><return_msg><![CDATA[" + return_msg
				+ "]]></return_msg></xml>";
	}
}
