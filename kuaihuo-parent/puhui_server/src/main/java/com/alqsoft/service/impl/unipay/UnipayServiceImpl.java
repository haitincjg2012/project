/**
 * Project Name:alqsoft-easyui
 * File Name:UnipayServiceImpl.java
 * Package Name:com.alqsoft.service.impl.unipay
 * Date:2015年10月31日下午5:32:48
 * Copyright (c) 2015, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.alqsoft.service.impl.unipay;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alqsoft.init.InitParams;
import com.alqsoft.service.unipay.UnipayService;
import com.unionpay.acp.sdk.HttpClient;
import com.unionpay.acp.sdk.LogUtil;
import com.unionpay.acp.sdk.SDKConfig;
import com.unionpay.acp.sdk.SDKConstants;
import com.unionpay.acp.sdk.SDKUtil;
import com.unionpay.acp.sdk.SecureUtil;
/**
 * ClassName:UnipayServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年10月31日 下午5:32:48 <br/>
 * @author   zc
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@Service
public class UnipayServiceImpl implements UnipayService{
	
	@SuppressWarnings("unused")
	private static Logger logger=LoggerFactory.getLogger(UnipayServiceImpl.class);
	
	@Autowired
	private InitParams initParams;
	
	/**
	 * 返回银联处理过的html内容
	 * @param paynum
	 * @return
	 */
	@Override
	public String getHtml(Long paynum,String orderNum){
		Map<String, String> requestData = new HashMap<String, String>();
		
		/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
		requestData.put("version", "5.0.0");   			  //版本号，全渠道默认值
		requestData.put("encoding", "UTF-8"); 			  //字符集编码，可以使用UTF-8,GBK两种方式
		requestData.put("signMethod", "01");            			  //签名方法，只支持 01：RSA方式证书加密
		requestData.put("txnType", "01");               			  //交易类型 ，01：消费
		requestData.put("txnSubType", "01");            			  //交易子类型， 01：自助消费
		requestData.put("bizType", "000201");           			  //业务类型，B2C网关支付，手机wap支付
		requestData.put("channelType", "07");           			  //渠道类型】，这个字段区分B2C网关支付和手机wap支付；07：PC,08：手机，平板等
		
		/***商户接入参数***/
		requestData.put("merId", "898111448161415");    	          			  //商户号码，请改成自己申请的正式商户号或者open上注册得来的777测试商户号
		requestData.put("accessType", "0");             			  //接入类型，0：直连商户 
		requestData.put("orderId",orderNum);             //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则		
		requestData.put("txnTime", this.getCurrentTime());        //订单发送时间，取系统时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
		requestData.put("currencyCode", "156");         			  //交易币种（境内商户一般是156 人民币）		
		requestData.put("txnAmt", paynum+"");             			      //交易金额，单位分，不要带小数点
		requestData.put("reqReserved", "透传字段");        		      //请求方保留域，透传字段（可以实现商户自定义参数的追踪）本交易的后台通知,对本交易的交易状态查询交易、对账文件中均会原样返回，商户可以按需上传，长度为1-1024个字节		
		String requestFrontUrl = SDKConfig.getConfig().getFrontRequestUrl();  //获取请求银联的前台地址：对应属性文件acp_sdk.properties文件中的acpsdk.frontTransUrl
	//	String requestBackUrl = SDKConfig.getConfig().getBackRequestUrl();  //获取请求银联的后台地址：对应属性文件acp_sdk.properties文件中的acpsdk.frontTransUrl
		String requestBackUrl = "https://101.231.204.80:5000/gateway/api/backTransReq.do";
		//前台通知地址 （需设置为外网能访问 http https均可），支付成功后的页面 点击“返回商户”按钮的时候将异步通知报文post到该地址
		//如果想要实现过几秒中自动跳转回商户页面权限，需联系银联业务申请开通自动返回商户权限
		//异步通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 消费交易 商户通知
		requestData.put("frontUrl", initParams.getProperties().getProperty("acp_frontUrl"));
		
		//后台通知地址（需设置为【外网】能访问 http https均可），支付成功后银联会自动将异步通知报文post到商户上送的该地址，失败的交易银联不会发送后台通知
		//后台通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 消费交易 商户通知
		//注意:1.需设置为外网能访问，否则收不到通知    2.http https均可  3.收单后台通知后需要10秒内返回http200或302状态码 
		//    4.如果银联通知服务器发送通知后10秒内未收到返回状态码或者应答码非http200或302，那么银联会间隔一段时间再次发送。总共发送5次，每次的间隔时间为0,1,2,4分钟。
		//    5.后台通知地址如果上送了带有？的参数，例如：http://abc/web?a=b&c=d 在后台通知处理程序验证签名之前需要编写逻辑将这些字段去掉再验签，否则将会验签失败
		requestData.put("backUrl", initParams.getProperties().getProperty("acp_backUrl"));
		
		//////////////////////////////////////////////////
		//
		//       报文中特殊用法请查看 PCwap网关跳转支付特殊用法.txt
		//
		//////////////////////////////////////////////////
		
		/**请求参数设置完毕，以下对请求参数进行签名并生成html表单，将表单写入浏览器跳转打开银联页面**/
		Map<String, String> submitFromData = this.signData(requestData);  //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		
		//多证书使用方法：如果有多个商户号接入银联,每个商户号对应不同的证书可以使用此方法:传入私钥证书和密码(并且在acp_sdk.properties中 配置 acpsdk.singleMode=false) 
		//Map<String, String> submitFromData = DemoBase.signData(data,"D:\\certs\\PM_700000000000001_acp.pfx", "000000");
		
		String html = this.createHtml(requestFrontUrl, submitFromData);   //生成自动跳转的Html表单
		
		logger.info("打印请求HTML，此为请求报文，为联调排查问题的依据："+html);
		//将生成的html写到浏览器中完成自动跳转打开银联支付页面；这里调用signData之后，将html写到浏览器跳转到银联页面之前均不能对html中的表单项的名称和值进行修改，如果修改会导致验签不通过
		return html;
	}
	// 商户发送交易时间 格式:YYYYMMDDhhmmss
	public static String getCurrentTime() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}
	
	// AN8..40 商户订单号，不能含"-"或"_"
	public static String getOrderId() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}
	/**
	 * 构造HTTP POST交易表单的方法示例
	 * 
	 * @param action
	 *            表单提交地址
	 * @param hiddens
	 *            以MAP形式存储的表单键值
	 * @return 构造好的HTTP POST交易表单
	 */
	public static String createHtml(String action, Map<String, String> hiddens) {
		StringBuffer sf = new StringBuffer();
		sf.append("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset="+"UTF-8"+"\"/></head><body>");
		sf.append("<form id = \"pay_form\" action=\"" + action
				+ "\" method=\"post\">");
		if (null != hiddens && 0 != hiddens.size()) {
			Set<Entry<String, String>> set = hiddens.entrySet();
			Iterator<Entry<String, String>> it = set.iterator();
			while (it.hasNext()) {
				Entry<String, String> ey = it.next();
				String key = ey.getKey();
				String value = ey.getValue();
				sf.append("<input type=\"hidden\" name=\"" + key + "\" id=\""
						+ key + "\" value=\"" + value + "\"/>");
			}
		}
		sf.append("</form>");
		sf.append("</body>");
		sf.append("<script type=\"text/javascript\">");
		sf.append("document.all.pay_form.submit();");
		sf.append("</script>");
		sf.append("</html>");
		return sf.toString();
	}

	/**
	 * java main方法 数据提交 　　 对数据进行签名
	 * 
	 * @param contentData
	 * @return　签名后的map对象
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> signData(Map<String, ?> contentData) {
		Entry<String, String> obj = null;
		Map<String, String> submitFromData = new HashMap<String, String>();
		for (Iterator<?> it = contentData.entrySet().iterator(); it.hasNext();) {
			obj = (Entry<String, String>) it.next();
			String value = obj.getValue();
			if (StringUtils.isNotBlank(value)) {
				// 对value值进行去除前后空处理
				submitFromData.put(obj.getKey(), value.trim());
				System.out
						.println(obj.getKey() + "-->" + String.valueOf(value));
			}
		}
		/**
		 * 签名
		 */
		SDKUtil.sign(submitFromData, "UTF-8");
		return submitFromData;
	}
	
	/**
	 * 多证书签名方法
	 * 如果有多个商户号接入银联,每个商户号对应不同的证书可以使用此方法:传入私钥证书和密码(并且在acp_sdk.properties中 配置 acpsdk.singleMode=false) 
	 * @param contentData
	 * @param certPath
	 * @param certPwd
	 * @return　签名后的map对象
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> signData(Map<String, ?> contentData,String certPath, String certPwd) {
		Entry<String, String> obj = null;
		Map<String, String> submitFromData = new HashMap<String, String>();
		System.out.println("打印请求报文域 :");
		for (Iterator<?> it = contentData.entrySet().iterator(); it.hasNext();) {
			obj = (Entry<String, String>) it.next();
			String value = obj.getValue();
			if (StringUtils.isNotBlank(value)) {
				// 对value值进行去除前后空处理
				submitFromData.put(obj.getKey(), value.trim());
				System.out
						.println(obj.getKey() + "-->" + String.valueOf(value));
			}
		}
		/**
		 * 签名
		 */
		SDKUtil.signByCertInfo(submitFromData, "UTF-8",certPath, certPwd);

		return submitFromData;
	}
	
	

	/**
	 * 功能：后台交易给银联地址发请求
	 * @param contentData
	 * @return 返回报文 map
	 */
	public static Map<String, String> submitUrl(
			Map<String, String> submitFromData,String requestUrl) {
		String resultString = "";
		logger.info("请求银联地址:" + requestUrl);
		/**
		 * 发送后台请求数据
		 */
		HttpClient hc = new HttpClient(requestUrl, 30000, 30000);
		try {
			int status = hc.send(submitFromData, "UTF-8");
			if (200 == status) {
				resultString = hc.getResult();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, String> resData = new HashMap<String, String>();
		/**
		 * 验证签名
		 */
		if (null != resultString && !"".equals(resultString)) {
			// 将返回结果转换为map
			resData = SDKUtil.convertResultStringToMap(resultString);
			if (SDKUtil.validate(resData, "UTF-8")) {
				LogUtil.writeLog("验证签名成功,可以继续后边的逻辑处理");
			} else {
				LogUtil.writeLog("验证签名失败,必须验签签名通过才能继续后边的逻辑...");
			}
		}
		return resData;
	}
	
	/**
	 * 功能：获取批量文件内容并返回使用DEFLATE压缩算法压缩，Base64编码后字符串
	 * 适用到的交易：批量代付，批量代收，批量退货
	 * @param filePath 批量文件路径
	 * @return
	 */
	private static String  enCodeFileContent(String filePath){
		String baseFileContent = "";
		
		File file = new File(filePath);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		InputStream in = null;
		try {
			in = new FileInputStream(file);
			int fl = in.available();
			if (null != in) {
				byte[] s = new byte[fl];
				in.read(s, 0, fl);
				// 压缩编码.
				baseFileContent = new String(SecureUtil.base64Encode(SecureUtil.deflater(s)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return baseFileContent;
	}
	
	/**
	 * 功能：解析返回文件解base64，解DEFLATE压缩并落地
	 * 适用到的交易：对账文件下载，批量交易状态查询
	 * @param resData
	 */
	public static void deCodeFileContent(Map<String, String> resData) {
		// 解析返回文件
		String fileContent = resData.get(SDKConstants.param_fileContent);
		if (null != fileContent && !"".equals(fileContent)) {
			try {
				byte[] fileArray = SecureUtil.inflater(SecureUtil
						.base64Decode(fileContent.getBytes("UTF-8")));
				String root = "D:\\";
				String filePath = null;
				if (SDKUtil.isEmpty(resData.get("fileName"))) {
					filePath = root + File.separator + resData.get("merId")
							+ "_" + resData.get("batchNo") + "_"
							+ resData.get("txnTime") + ".txt";
				} else {
					filePath = root + File.separator + resData.get("fileName");
				}
				File file = new File(filePath);
				if (file.exists()) {
					file.delete();
				}
				file.createNewFile();
				FileOutputStream out = new FileOutputStream(file);
				out.write(fileArray, 0, fileArray.length);
				out.flush();
				out.close();

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 功能：后台交易给银联地址发请求
	 * @param contentData
	 * @return 返回报文 map
	 */
	public static Map<String, String> submitDate(Map<String, ?> contentData,String requestUrl) {
		Map<String, String> submitFromData = (Map<String, String>) signData(contentData);
		return submitUrl(submitFromData,requestUrl);
	}
	
	/**
	 * 持卡人信息域customerInfo构造
	 * 说明：不勾选对敏感信息加密权限 使用旧的构造customerInfo域方式，不对敏感信息进行加密（对cvn2，有效期不加密），但如果送pin的话需加密
	 * @param customerInfoMap 信息域请求参数 key送域名value送值,必送
	 *        例如：customerInfoMap.put("certifTp", "01");					//证件类型
				  customerInfoMap.put("certifId", "341126197709218366");	//证件号码
				  customerInfoMap.put("customerNm", "互联网");				//姓名
				  customerInfoMap.put("phoneNo", "13552535506");			//手机号
				  customerInfoMap.put("smsCode", "123456");					//短信验证码
				  customerInfoMap.put("pin", "111111");						//密码（加密）
				  customerInfoMap.put("cvn2", "123");           			//卡背面的cvn2三位数字（不加密）
				  customerInfoMap.put("expired", "1711");  				    //有效期 年在前月在后（不加密)
	 * @param  accNo  对密码加密使用到的卡号，必送				  
	 * @return base64后的持卡人信息域字段
	 */
	public static String getCustomerInfo(Map<String,String> customerInfoMap,String accNo) {
		
		StringBuffer sf = new StringBuffer("{");
		
		for(Iterator<String> it = customerInfoMap.keySet().iterator(); it.hasNext();){
			String key = it.next();
			String value = customerInfoMap.get(key);
			if(key.equals("pin")){
				value = SDKUtil.encryptPin(accNo,value,"UTF-8");
			}
			if(it.hasNext())
				sf.append(key + SDKConstants.EQUAL+ value + SDKConstants.AMPERSAND);
			else
				sf.append(key + SDKConstants.EQUAL + value);
		}
		sf.append("}");
		
		String customerInfo = sf.toString();
		LogUtil.writeLog("组装的customerInfo明文："+customerInfo);
		try {
			return new String(SecureUtil.base64Encode(sf.toString().getBytes(
					"UTF-8")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return customerInfo;
	}
	
	/**
	 * 持卡人信息域customerInfo构造，勾选对敏感信息加密权限 适用新加密规范，对pin和phoneNo，cvn2，expired加密
	 * 适用到的交易：
	 * @param customerInfoMap 信息域请求参数 key送域名value送值,必送
	 *        例如：customerInfoMap.put("certifTp", "01");					//证件类型
				  customerInfoMap.put("certifId", "341126197709218366");	//证件号码
				  customerInfoMap.put("customerNm", "互联网");				//姓名
				  customerInfoMap.put("smsCode", "123456");					//短信验证码
				  customerInfoMap.put("pin", "111111");						//密码（加密）
				  customerInfoMap.put("phoneNo", "13552535506");			//手机号（加密）
				  customerInfoMap.put("cvn2", "123");           			//卡背面的cvn2三位数字（加密）
				  customerInfoMap.put("expired", "1711");  				    //有效期 年在前月在后（加密）
	 * @param accNo  对密码加密使用到的卡号，必送
	 * @return base64后的持卡人信息域字段
	 */
	public static String getCustomerInfoWithEncrypt(Map<String,String> customerInfoMap,String accNo) {
		
		StringBuffer sf = new StringBuffer("{");
		//敏感信息加密域
		StringBuffer encryptedInfoSb = new StringBuffer("");
		
		for(Iterator<String> it = customerInfoMap.keySet().iterator(); it.hasNext();){
			String key = it.next();
			String value = customerInfoMap.get(key);
			if(key.equals("phoneNo") || key.equals("cvn2") || key.equals("expired")){
				encryptedInfoSb.append(key + SDKConstants.EQUAL+ value + SDKConstants.AMPERSAND);
			}else{
				if(key.equals("pin")){
					value = SDKUtil.encryptPin(accNo,value,"UTF-8");
				}
				if(it.hasNext())
					sf.append(key + SDKConstants.EQUAL+ value + SDKConstants.AMPERSAND);
				else
					sf.append(key + SDKConstants.EQUAL + value);
			}
		}
		
		if(!encryptedInfoSb.toString().equals("")){
			//去掉最后一个&符号
			encryptedInfoSb.setLength(encryptedInfoSb.length()-1);
			
			logger.info("组装的customerInfo encryptedInfo明文："+ encryptedInfoSb.toString());
			if(sf.toString().equals("{")) //如果没有除phoneNo，cvn2，expired之外的元素，不加&
				sf.append("encryptedInfo"+ SDKConstants.EQUAL);
			else
				sf.append(SDKConstants.AMPERSAND + "encryptedInfo"+ SDKConstants.EQUAL);
			
			sf.append(SDKUtil.encryptEpInfo(encryptedInfoSb.toString(), "UTF-8"));
		}
		
		sf.append("}");
		
		String customerInfo = sf.toString();
		logger.info("组装的customerInfo明文："+customerInfo);
		try {
			return new String(SecureUtil.base64Encode(sf.toString().getBytes(
					"UTF-8")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return customerInfo;
	}
	
	
	
	/**
	 * 有卡交易信息域(cardTransData)构造示例<br>
	 * 所有子域需用“{}”包含，子域间以“&”符号链接。格式如下：{子域名1=值&子域名2=值&子域名3=值}<br>
	 * 说明：本示例仅供参考，开发时请根据接口文档中的报文要素组装
	 * 
	 * @param contentData
	 * @param encoding
	 * @return
	 */
	public static String getCardTransData(Map<String, ?> contentData,
			String encoding) {

		StringBuffer cardTransDataBuffer = new StringBuffer();
		
		// 以下测试数据只是用来说明组装cardTransData域的基本步骤,真实数据请以实际业务为准
		String ICCardData = "uduiadniodaiooxnnxnnada";// IC卡数据
		String ICCardSeqNumber = "123";// IC卡的序列号
		String track2Data = "testtrack2Datauidanidnaidiadiada231";// 第二磁道数据
		String track3Data = "testtrack3Datadadaiiuiduiauiduia312117831";// 第三磁道数据
		String transSendMode = "b";// 交易发起方式

		// 第二磁道数据 加密格式如下：merId|orderId|txnTime|txnAmt|track2Data
		StringBuffer track2Buffer = new StringBuffer();
		track2Buffer.append(contentData.get("merId"))
				.append(SDKConstants.COLON).append(contentData.get("orderId"))
				.append(SDKConstants.COLON).append(contentData.get("txnTime"))
				.append(SDKConstants.COLON).append(contentData.get("txnAmt"))
				.append(SDKConstants.COLON).append(track2Data);

		String encryptedTrack2 = SDKUtil.encryptTrack(track2Buffer.toString(),
				encoding);

		// 第三磁道数据 加密格式如下：merId|orderId|txnTime|txnAmt|track3Data
		StringBuffer track3Buffer = new StringBuffer();
		track3Buffer.append(contentData.get("merId"))
				.append(SDKConstants.COLON).append(contentData.get("orderId"))
				.append(SDKConstants.COLON).append(contentData.get("txnTime"))
				.append(SDKConstants.COLON).append(contentData.get("txnAmt"))
				.append(SDKConstants.COLON).append(track3Data);

		String encryptedTrack3 = SDKUtil.encryptTrack(track3Buffer.toString(),
				encoding);

		// 将待组装的数据装入MAP,进行格式处理
		Map<String, String> cardTransDataMap = new HashMap<String, String>();
		cardTransDataMap.put("ICCardData", ICCardData);
		cardTransDataMap.put("ICCardSeqNumber", ICCardSeqNumber);
		cardTransDataMap.put("track2Data", encryptedTrack2);
		cardTransDataMap.put("track3Data", encryptedTrack3);
		cardTransDataMap.put("transSendMode", transSendMode);

		return cardTransDataBuffer.append(SDKConstants.LEFT_BRACE)
				.append(SDKUtil.coverMap2String(cardTransDataMap))
				.append(SDKConstants.RIGHT_BRACE).toString();
	}
	 
	/**
	 * 功能：解析全渠道商户对账文件中的ZM文件并以List<Map>方式返回
	 * 适用交易：对账文件下载后对文件的查看
	 * @param filePath ZM文件全路径
	 * @return 包含每一笔交易中 序列号 和 值 的map序列
	 */
	public static List<Map> parseZMFile(String filePath){
		int lengthArray[] = {3,11,11,6,10,19,12,4,2,21,2,32,2,6,10,13,13,4,15,2,2,6,2,4,32,1,21,15,1,15,32,13,13,8,32,13,13,12,2,1,131};
		return parseFile(filePath,lengthArray);
	}
	
	/**
	 * 功能：解析全渠道商户对账文件中的ZME文件并以List<Map>方式返回
	 * 适用交易：对账文件下载后对文件的查看
	 * @param filePath ZME文件全路径
	 * @return 包含每一笔交易中 序列号 和 值 的map序列
	 */
	public static List<Map> parseZMEFile(String filePath){
		int lengthArray[] = {3,11,11,6,10,19,12,4,2,21,2,32,2,6,10,13,13,4,15,2,2,6,2,4,32,1,21,15,1,15,32,13,13,8,32,13,13,12,2,1,131};
		return parseFile(filePath,lengthArray);
	}
	
	/**
	 * 功能：解析全渠道商户 ZM,ZME对账文件
	 * @param filePath
	 * @param lengthArray 参照《全渠道平台接入接口规范 第3部分 文件接口》 全渠道商户对账文件 6.1 ZM文件和6.2 ZME 文件 格式的类型长度组成int型数组
	 * @return
	 */
	 private static List<Map> parseFile(String filePath,int lengthArray[]){
	 	List<Map> ZmDataList = new ArrayList<Map>();
	 	try {
            String encoding="UTF-8";
            File file=new File(filePath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                	//解析的结果MAP，key为对账文件列序号，value为解析的值
        		 	Map<Integer,String> ZmDataMap = new LinkedHashMap<Integer,String>();
                    //左侧游标
                    int leftIndex = 0;
                    //右侧游标
                    int rightIndex = 0;
                    for(int i=0;i<lengthArray.length;i++){
                    	rightIndex = leftIndex + lengthArray[i];
                    	String filed = lineTxt.substring(leftIndex,rightIndex);
                    	leftIndex = rightIndex+1;
                    	ZmDataMap.put(i, filed);
                    }
                    ZmDataList.add(ZmDataMap);
                }
                read.close();
        }else{
            System.out.println("找不到指定的文件");
        }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
	 	for(int i=0;i<ZmDataList.size();i++){
	 		System.out.println("行数: "+ (i+1));
	 		Map<Integer,String> ZmDataMapTmp = ZmDataList.get(i);
	 		
	 		for(Iterator<Integer> it = ZmDataMapTmp.keySet().iterator();it.hasNext();){
	 			Integer key = it.next();
	 			String value = ZmDataMapTmp.get(key);
		 		System.out.println("序号："+ key + " 值: '"+ value +"'");
		 	}
	 	}
		return ZmDataList;	
	}
}
