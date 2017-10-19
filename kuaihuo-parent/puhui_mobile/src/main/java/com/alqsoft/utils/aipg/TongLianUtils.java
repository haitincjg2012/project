package com.alqsoft.utils.aipg;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.alqsoft.init.InitParam;

/**
 * 
 * @Title: TongLianUtils.java
 * @Description: 通联支付工具类
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年3月18日 下午6:16:00
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 *
 */
public class TongLianUtils {
	
	/**
	 * 
	* @Title: getPayMentParams 
	* @Description: 获取签名原字符串
	* @param @param orderNo
	* @param @param orderAmount
	* @param @param orderDateTime
	* @param @param bankName
	* @param @param memberId  会员id
	* @param @param orderKeyId 订单id
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String getPayMentParams(InitParam initParam,String orderNo,Integer orderAmount,String orderDateTime,String bankName,float transportFee,Long memberId,Long orderKeyId){
		Properties properties=initParam.getProperties();
		
		String paramStr="?memberIdStr="+memberId+"-"+orderKeyId;//同步返回需要的参数
		
		int inputCharset=Integer.parseInt(properties.getProperty("inputCharset"));
		String pickupUrl=properties.getProperty("pickupUrl")+paramStr;
		String receiveUrl=properties.getProperty("receiveUrl");
		String version=properties.getProperty("version");
		int language=Integer.parseInt(properties.getProperty("language"));
		int signType=Integer.parseInt(properties.getProperty("signType"));
		String merchantId=properties.getProperty("merchantId");
		int orderCurrency=Integer.parseInt(properties.getProperty("orderCurrency"));
		int payType=Integer.parseInt(properties.getProperty("payType"));
		int orderExpireDatetime=Integer.parseInt(properties.getProperty("orderExpireDatetime"));//订单超时时间 （单位：分钟）
		
		String ext1=String.valueOf(transportFee);//扩展字符串 --代表运费
		
//		String issuerId=getIssuerIdByCardName(bankName);//获取发卡发机构代码
	
		StringBuffer signSrc=new StringBuffer();//签名串原串
		signSrc.append("inputCharset="+inputCharset);
		signSrc.append("&pickupUrl="+pickupUrl);
		signSrc.append("&receiveUrl="+receiveUrl);
		signSrc.append("&version="+version);
		signSrc.append("&language="+language);
		signSrc.append("&signType="+signType);
		signSrc.append("&merchantId="+merchantId);
		signSrc.append("&orderNo="+orderNo);
		signSrc.append("&orderAmount="+orderAmount);
		signSrc.append("&orderCurrency="+orderCurrency);
		signSrc.append("&orderDatetime="+orderDateTime);
		signSrc.append("&orderExpireDatetime="+orderExpireDatetime);
		signSrc.append("&ext1="+ext1);
		signSrc.append("&payType="+payType);
//		signSrc.append("&issuerId="+issuerId);//测试环境只能使用“虚拟银行”进行支付，提交交易时，paytype=0 issureId为空。
		
		System.out.println("签名原字符串===："+signSrc.toString());
		return  signSrc.toString();
		
	}
	
	
	/**
	 * 
	* @Title: getPayMentParams 
	* @Description: 获取签名原字符串
	* @param @param orderNo
	* @param @param orderAmount
	* @param @param orderDateTime
	* @param @param bankName
	* @param @param memberId  会员id
	* @param @param orderKeyId 订单id
	* @param @return    设定文件 
	* @return map    返回类型 
	* @throws
	 */
	public static  Map<String, String> getPayMentParamsMap(InitParam initParam,String orderNo,String productName,Integer productNum, Integer orderAmount,String orderDateTime,String bankName,float transportFee,Long memberId,Long orderKeyId){
		
		Map<String, String> map=new HashMap<String, String>();
		 
		Properties properties=initParam.getProperties();
		
		String paramStr="?memberIdStr="+memberId+"-"+orderKeyId;//同步返回需要的参数
		
		int inputCharset=Integer.parseInt(properties.getProperty("inputCharset"));
		String pickupUrl=properties.getProperty("pickupUrl")+paramStr;
		String receiveUrl=properties.getProperty("receiveUrl");
		String version=properties.getProperty("version");
		int language=Integer.parseInt(properties.getProperty("language"));
		int signType=Integer.parseInt(properties.getProperty("signType"));
		String merchantId=properties.getProperty("merchantId");
		int orderCurrency=Integer.parseInt(properties.getProperty("orderCurrency"));
		int payType=Integer.parseInt(properties.getProperty("payType"));
		int orderExpireDatetime=Integer.parseInt(properties.getProperty("orderExpireDatetime"));//订单超时时间 （单位：分钟）
		String ext1="aipg@"+String.valueOf(transportFee);//扩展字符串 --代表运费
		
		String key=properties.getProperty("md5Key");
		
		StringBuffer signSrc=new StringBuffer();//签名串原串
		signSrc.append("inputCharset="+inputCharset);
		signSrc.append("&pickupUrl="+pickupUrl);
		signSrc.append("&receiveUrl="+receiveUrl);
		signSrc.append("&version="+version);
		signSrc.append("&language="+language);
		signSrc.append("&signType="+signType);
		signSrc.append("&merchantId="+merchantId);
		signSrc.append("&orderNo="+orderNo);
		signSrc.append("&orderAmount="+orderAmount);
		signSrc.append("&orderCurrency="+orderCurrency);
		signSrc.append("&orderDatetime="+orderDateTime);
		signSrc.append("&orderExpireDatetime="+orderExpireDatetime);
		signSrc.append("&productName="+StringUtils.trim(productName));
		signSrc.append("&productNum="+productNum);
		signSrc.append("&ext1="+ext1);
		signSrc.append("&payType="+payType);
		
		
		String signMsg=TongLianUtils.getSignMsg(signSrc.toString(), key);//验签字符串
		
		
		map.put("inputCharset", String.valueOf(inputCharset));
		map.put("pickupUrl",pickupUrl);
		map.put("receiveUrl",receiveUrl);
		map.put("version",version);
		map.put("language",String.valueOf(language));
		map.put("signType",String.valueOf(signType));
		map.put("merchantId", merchantId);
		map.put("orderNo",orderNo);
		map.put("orderAmount",String.valueOf(orderAmount));
		map.put("orderCurrency",String.valueOf(orderCurrency));
		map.put("orderDatetime",orderDateTime);
		map.put("orderExpireDatetime",String.valueOf(orderExpireDatetime));
		map.put("productName",StringUtils.trim(productName));
		map.put("productNum",String.valueOf(productNum));
		map.put("ext1",ext1);
		map.put("payType",String.valueOf(payType));
		map.put("signMsg",signMsg);
		
		return  map;
		
	}
	
	
	

	/**
	 * 
	* @Title: getPayMentParamsAndroid 
	* @Description: 获取签名原字符串---android 或ios 客户端
	* @param @param orderNo 订单号
	* @param @param orderAmount 订单总金额
	* @param @param orderDateTime 下单时间
	* @param @param transportFee 运费
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static JSONObject getPayMentParamsAndroid(InitParam initParam,String orderNo,Long orderAmount,String orderDateTime,String goodName){
		Properties properties=initParam.getProperties();
		
		String md5Key=properties.getProperty("md5Key");
		int inputCharset=Integer.parseInt(properties.getProperty("inputCharset"));
		String receiveUrl=properties.getProperty("receiveUrl");
		String version=properties.getProperty("version");
//		int language=Integer.parseInt(properties.getProperty("language"));
		int signType=Integer.parseInt(properties.getProperty("signType"));
		String merchantId=properties.getProperty("merchantId");
		int orderCurrency=Integer.parseInt(properties.getProperty("orderCurrency"));
		int payType=Integer.parseInt(properties.getProperty("payType"));
//		int orderExpireDatetime=Integer.parseInt(properties.getProperty("orderExpireDatetime"));//订单超时时间 （单位：分钟）
		
		/*StringBuffer signSrc=new StringBuffer();//签名串原串
		signSrc.append("inputCharset="+inputCharset);
		signSrc.append("&receiveUrl="+receiveUrl);
		signSrc.append("&version="+version);
		signSrc.append("&signType="+signType);
		signSrc.append("&merchantId="+merchantId);
		signSrc.append("&orderNo="+orderNo);
		signSrc.append("&orderAmount="+orderAmount);
		signSrc.append("&orderCurrency="+orderCurrency);
		signSrc.append("&orderDatetime="+orderDateTime);
		signSrc.append("&productName="+goodName.trim());
		signSrc.append("&ext1="+ext1);
		signSrc.append("&payType="+payType);
		
		String signMsg=TongLianUtils.getSignMsg(signSrc.toString(), md5Key);//验签字符串
		
		System.out.println("待签名字符串===："+signSrc.toString());*/
		
		JSONObject paaParams = new JSONObject();
		try {
			paaParams.put("inputCharset",inputCharset);
			paaParams.put("receiveUrl", receiveUrl);
			paaParams.put("version", version);
			paaParams.put("signType", signType);
			paaParams.put("merchantId",merchantId);
			paaParams.put("orderNo", orderNo);
			paaParams.put("orderAmount", orderAmount);
			paaParams.put("orderCurrency",orderCurrency);
			paaParams.put("orderDatetime",orderDateTime);
			paaParams.put("productName", goodName.trim());
			paaParams.put("payType",payType);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		String[] paaParamsArray = {String.valueOf(inputCharset),"inputCharset", receiveUrl,"receiveUrl", version, "version",String.valueOf(signType), "signType",
				merchantId, "merchantId", orderNo, "orderNo", "" + orderAmount,"orderAmount",String.valueOf(orderCurrency), "orderCurrency",orderDateTime, "orderDatetime",
				goodName.trim(), "productName",String.valueOf(payType), "payType",md5Key, "key", };

		String paaStr = "";
		for (int i = 0; i < paaParamsArray.length; i++) {
			paaStr += paaParamsArray[i + 1] + "=" + paaParamsArray[i] + "&";
			i++;
		}
		
		System.out.println("md5加密字符串=========："+paaStr.substring(0, paaStr.length() - 1));
		String md5Str = md5(paaStr.substring(0, paaStr.length() - 1));
		try {
			paaParams.put("signMsg", md5Str);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		return paaParams;
	}
	
	
	/**
	 * 
	* @Title: getSignMsg 
	* @Description: 获取签名字符串
	* @param @param signSrc 签名串原串
	* @param @param key
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String getSignMsg(String signSrc,String key){
		signSrc+="&key="+key;
		
		System.out.println("待签名字符串signSrc=====："+signSrc);
		String signMsg=SecurityUtil.MD5Encode(signSrc);//进行验签，获取签名字符串
		System.out.println("签名字符串signMsg===:"+signMsg);
		
		return signMsg;
		
	}
	
	
	/**
	 * 
	* @Title: getIssuerIdByCardName 
	* @Description: 通过银行名称获取机构代码
	* @param @param bankName 银行名称
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String getIssuerIdByCardName(String bankName){
		
//		return "vbank";//虚拟银行，测试环境
		
		if ("招商银行".equals(bankName)) {
			return "cmb";
		}else if("中国建设银行".equals(bankName)){
			return "ccb";
		}else if("中国邮政储蓄银行".equals(bankName)){
			return "psbc";
		}else if("交通银行".equals(bankName)){
			return "boc";
		}else if("中国银行".equals(bankName)){
			return "boc";
		}else if("中国工商银行".equals(bankName)){
			return "icbc";
		}else if("中国光大银行".equals(bankName)){
			return "cib";
		}else if("中国农业银行".equals(bankName)){
			return "abc";
		}else if("兴业银行".equals(bankName)){
			return "cib";
		}else if("中信银行".equals(bankName)){
			return "citic";
		}else if("浦发银行".equals(bankName)){
			return "spdb";
		}else if("中国民生银行".equals(bankName)){
			return "cmbc";
		}else if("平安银行".equals(bankName)){
			return "pingan";
		}else if("上海银行".equals(bankName)){
			return "bos";
		}else if("广东发展银行".equals(bankName)){
			return "cgb";
		}else if("华夏银行".equals(bankName)){
			return "cgb";
		}else{
			return "";
		}
		
	}
	
	
	
	public static String md5(String string) {
		byte[] hash;
		try {
			hash = MessageDigest.getInstance("MD5").digest(
					string.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Huh, MD5 should be supported?", e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Huh, UTF-8 should be supported?", e);
		}

		return hexString(hash);
	}

	public static final String hexString(byte[] bytes) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			buffer.append(hexString(bytes[i]));
		}
		return buffer.toString();
	}

	public static final String hexString(byte byte0) {
		// 字节到十六进制的ASCII码转换
		char ac[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };
		char ac1[] = new char[2];
		ac1[0] = ac[byte0 >>> 4 & 0xf];
		ac1[1] = ac[byte0 & 0xf];
		String s = new String(ac1);
		return s;
	}

}
