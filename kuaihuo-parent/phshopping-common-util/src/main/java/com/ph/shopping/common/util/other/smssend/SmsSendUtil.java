package com.ph.shopping.common.util.other.smssend;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.other.smssend.request.SmsCodeData;
import com.ph.shopping.common.util.other.smssend.request.SmsCustomModel;
import com.ph.shopping.common.util.result.Result;

/**
 * 
* @ClassName: SmsSendUtil  
* @Description: 短信发送工具类
* @author lijie  
* @date 2017年3月17日  
*
 */
public class SmsSendUtil {
	
	private static final String SUCCESS = "1";
	/**
	 * 腾讯云短信成功状态码
	 */
	private static final String TXY_SUCCESS = "0";
	
	private static final Logger log = LoggerFactory.getLogger(SmsSendUtil.class);
	/**
	 * 不带短信内容发送短信
	 * @param phone
	 * @param codeType
	 * @return 
	 */
	public static boolean sendSmsCodeByNoMsg(SmsCodeData data) throws Exception {
		boolean flag = false;
		if(data != null){
			StringBuilder sbud = new StringBuilder();
			sbud.append(data.getSendUrl().trim()).append("?phone=").append(data.getPhone()).append("&codeType=")
			.append(data.getCodeType());
			JSONObject json = backSmsSendMsg(sbud.toString());
			if (json == null) {
				return flag;
			}
			if (log.isDebugEnabled()) {
				log.debug("sms send call back data : " + json.toJSONString());
			}
			Object code = json.get("code");
			flag = (code != null && SUCCESS.equals(code.toString()));
		}
		return flag;
	}
	/**
	 * 
	* @Title: backSmsSendMsg  
	* @Description: send request 
	* @param @param url
	* @param @return    参数  
	* @return JSONObject    返回类型  
	* @throws
	 */
	private static JSONObject backSmsSendMsg(String url) {
		if (StringUtils.isNotBlank(url)) {
			String sendGet = getHttpsToGet(url);
			JSONObject json = JSON.parseObject(sendGet);
			return json;
		} else {
			if (log.isDebugEnabled()) {
				log.debug("sms send url is null");
			}
		}
		return null;
	}
	/**
	 * 
	* @Title: verifySmsCode  
	* @Description: 校验短信验证码
	* @param @param data
	* @param @return    参数  
	* @return boolean    返回类型  
	* @throws
	 */
	public static boolean verifySmsCode(SmsCodeData data) {
		if(data != null){
			StringBuilder sbud = new StringBuilder();
			sbud.append(data.getSmsCodeCheckUrl()).append("?phone=").append(data.getPhone())
			.append("&code=").append(data.getSmsCode())
			.append("&codeType=").append(data.getCodeType());
			
			String sendGet = getHttpsToGet(sbud.toString());
			JSONObject json = JSON.parseObject(sendGet);
			Object obj = json.get("code");
			if (obj != null && SUCCESS.equals(obj.toString())) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 
	* @Title: sendSmsCodeByMsg  
	* @Description: 带自定义信息的消息发送  
	* @param @param data
	* @param @return    参数  
	* @return boolean    返回类型  
	* @throws
	 */
	public static Result sendSmsCodeByMsg(SmsCustomModel modelData) throws Exception {
		Result result = new Result();
		result.setCode(RespCode.Code.FAIL.getCode());// 默认失败状态
		result.setSuccess(false);
		if(modelData == null){
			return result;
		}
		long rnd = new Random().nextInt(999999) % (999999 - 100000 + 1) + 100000;
		int sdkappid = Integer.parseInt(modelData.getSdkappid());
		String wholeUrl = String.format("%s?sdkappid=%d&random=%d", modelData.getUrl().trim(), sdkappid, rnd);
		URL object = new URL(wholeUrl);
		HttpURLConnection con = (HttpURLConnection) object.openConnection();
		con.setDoOutput(true);
		con.setDoInput(true);
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "application/json");
		con.setRequestMethod("POST");
		JSONObject json = getSendData(modelData);
		// 构造请求
		DataOutputStream out = new DataOutputStream(new BufferedOutputStream(con.getOutputStream()));
		byte[] xmlData = json.toString().getBytes(Charset.forName("UTF-8"));
		out.write(xmlData);
		out.flush();
		out.close();

		// 返回结果
		int HttpResult = con.getResponseCode();
		if (HttpResult == HttpURLConnection.HTTP_OK) {
			InputStream instr = con.getInputStream();
			byte[] bis = IOUtils.toByteArray(instr);
			String response = new String(bis, "UTF-8");
			JSONObject retJson = JSON.parseObject(response);
			if (retJson != null) {
				Object retCode = retJson.get("result");
				if (retCode != null && TXY_SUCCESS.equals(retCode.toString())) {
					result.setCode(RespCode.Code.SUCCESS.getCode());// 设置成功状态
					result.setSuccess(true);
				}
			}
			result.setData(response);
			if (log.isDebugEnabled()) {
				log.debug("自定义短信信息发送返回结果：" + response);
			}
			instr.close();
		}
		return result;
	}
	/**
	 * 
	* @Title: getHttpsToGet  
	* @Description: 发送GET请求
	* @param @param url
	* @param @return    参数  
	* @return String    返回类型  
	* @throws
	 */
	public static String getHttpsToGet(String url) {
		RestTemplate restTemplate = new RestTemplate();
		String result = (String) restTemplate.getForObject(url, String.class, new Object[0]);
		return result;
	}
	/**
	 * 
	* @Title: getSendData  
	* @Description: 封装 自定义的参数 
	* @param @param modelData
	* @param @return
	* @param @throws NoSuchAlgorithmException    参数  
	* @return JSONObject    返回类型  
	* @throws
	 */
	private static JSONObject getSendData(SmsCustomModel modelData) throws NoSuchAlgorithmException {
		JSONObject data = new JSONObject();
		JSONObject tel = new JSONObject();
		JSONArray params = new JSONArray(modelData.getParams());

		// 封装参数
		tel.put("nationcode", modelData.getNationcode());
		String phone = modelData.getPhone();
		tel.put("phone", phone);
		data.put("sign", modelData.getSign());
		data.put("tpl_id", modelData.getTplId());
		data.put("params", params);
		data.put("type", modelData.getType());
		String sig = stringMD5(modelData.getAppKey().concat(phone));
		data.put("sig", sig);
		data.put("tel", tel);
		return data;
	}

	/**
	 * 
	* @Title: stringMD5  
	* @Description: 加密 
	* @param @param input
	* @param @return
	* @param @throws NoSuchAlgorithmException    参数  
	* @return String    返回类型  
	* @throws
	 */
	private static String stringMD5(String input) throws NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		byte[] inputByteArray = input.getBytes();
		messageDigest.update(inputByteArray);
		byte[] resultByteArray = messageDigest.digest();
		return byteArrayToHex(resultByteArray);
	}

	private static String byteArrayToHex(byte[] byteArray) {
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] resultCharArray = new char[byteArray.length * 2];
		int index = 0;
		for (byte b : byteArray) {
			resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
			resultCharArray[index++] = hexDigits[b & 0xf];
		}
		return new String(resultCharArray);
	}
}
