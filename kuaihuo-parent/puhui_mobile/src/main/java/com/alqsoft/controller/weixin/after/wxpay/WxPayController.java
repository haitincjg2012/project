package com.alqsoft.controller.weixin.after.wxpay;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.alqframework.encode.EncodeUtils;
import org.jdom.JDOMException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alqsoft.utils.weixin.wxpay.CommonUtil;
import com.alqsoft.utils.weixin.wxpay.PayCommonUtil;
import com.alqsoft.utils.weixin.wxpay.XMLUtil;
import com.alqsoft.utils.weixin.wxpay.config.WeiXinPayConfig;

/**
 * @Title: WxPayController.java
 * @Description: 微信支付入口控制器（模拟测试）
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年2月2日 下午5:05:46 Copyright © 2013 厦门卓讯信息技术有限公司 All rights
 *              reserved.
 * 
 */
@RequestMapping("weixin/after/wxpay")
@Controller
public class WxPayController {

	/**
	 * 
	 * @Title: shouQuan
	 * @Description: 进行授权第一步 获取用户的code，方便第二步获取openId
	 * @param @param model
	 * @param @param request
	 * @param @param response
	 * @param @throws IOException 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	@RequestMapping("shouquan")
	public void shouQuan(Model model, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String backUri = WeiXinPayConfig.ORDER_REDIRECT_URI;
		// 授权后要跳转的链接所需的参数一般有会员号，金额，订单号之类（可自己定义需要的参数方便下个页面获取），
		// 最好自己带上一个加密字符串将金额加上一个自定义的key用MD5签名或者自己写的签名,比如 Sign = %3D%2F%CS%
//		String orderNo = ConfigUtil.APPID + Sha1Util.getTimeStamp();
		String orderNo=request.getParameter("orderNum");
		backUri = backUri + "&orderNo=" + orderNo;
		
		System.out.println("backUri========:"+backUri);
		// URLEncoder.encode 后可以在backUri 的url里面获取传递的所有参数
		backUri = EncodeUtils.urlEncode(backUri);
		// scope 参数视各自需求而定:
		//1:这里用scope=snsapi_base 不弹出授权页面直接授权目的只获取统一支付接口的openid（不弹出授权页面，直接跳转，只能获取用户openid）
		//2:snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?" + "appid=" + WeiXinPayConfig.APPID
				+ "&redirect_uri=" + backUri
				+ "&response_type=code&scope=snsapi_base&state=tigofood#wechat_redirect";
		System.out.println("通过授权获取code请求地址========:"+url);
		response.sendRedirect(url);
	}

	/**
	 * 
	 * @Title: getwxpayJsapi
	 * @Description: 通过code 获取openid 跳转微信支付的页面
	 * @param @param model
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping("turnPay")
	public String getwxpayJsapi(Model model,HttpServletRequest request) {
		// 网页授权后获取传递的参数
		String orderNo = request.getParameter("orderNo");
//		String money = request.getParameter("money");
		String code = request.getParameter("code");
		// 金额转化为分为单位
//		float sessionmoney = Float.parseFloat(money);
//		String finalmoney = String.format("%.2f", sessionmoney);
//		finalmoney = finalmoney.replace(".", "");

		String openId = "";
		//通过授权后得到的code获取openid
		String URL = WeiXinPayConfig.OAUTH2_URL+code;
		System.out.println("oauth2授权接口URL---------:"+URL);
		JSONObject jsonObject = CommonUtil.httpsRequestJson(URL, "GET", null);
		System.out.println("授权返回结果信息=======:" + jsonObject.toString());
		if (null != jsonObject) {
			openId = jsonObject.getString("openid");
		}
		
//		Order order = orderService.getOrderByOrderNum(orderNo);
//		Member member = memberService.getMemberById(WebUtils.getMember().getId());
//		if (order != null) {
//			String addressStr = order.getAddress();
//			String address[] = addressStr.split("@");
//			model.addAttribute("address", address);
//		}
//		model.addAttribute("openId", openId);
//		model.addAttribute("member", member);
//		model.addAttribute("order", order);
		
		return "weixin/after/order/order-pay";
	}

	/**
	 * 
	 * @Title: getWxpayJsapi
	 * @Description: 异步 执行支付操作控制
	 * @param @param model
	 * @param @param request
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping("pay-execute")
	@ResponseBody
	public String getWxpayJsapiPayExecute(Model model, HttpServletRequest request) {
		try {
			//获取回调地址传过来的参数
			String openId=request.getParameter("openId");//获取openId
			String orderNo=request.getParameter("orderNo");//获取orderNo
			// 我们发送给微信服务器的参数是xml格式的string，微信返回来的信息也是xml格式的string
			// 获取package包
			SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
			parameters.put("appid", WeiXinPayConfig.APPID);
			parameters.put("mch_id", WeiXinPayConfig.MCH_ID);
			parameters.put("nonce_str", PayCommonUtil.CreateNoncestr());
			parameters.put("body", "微支付DEMO");
			parameters.put("out_trade_no", orderNo);// 订单号
			parameters.put("total_fee", "1");// 订单总金额，单位为分，不能带小数点
			parameters.put("spbill_create_ip",request.getRemoteAddr());//终端ip
			parameters.put("notify_url", WeiXinPayConfig.ORDER_NOTIFY_URL);//支付成功微信端回调我们接收返回信息的控制器地址
			parameters.put("trade_type", "JSAPI");//交易类型：JSAPI（H5网页调起支付接口）、NATIVE（二维码链接）、MICROPAY、APP
			parameters.put("openid", openId);// 登录授权获取的openid
			
			String sign = PayCommonUtil.createSign("UTF-8", parameters);// 进行签名
			parameters.put("sign", sign);
			// 将请求的参数转为字符串
			String requestXML = PayCommonUtil.getRequestXml(parameters);
			System.out.println("post请求微信支付参数============：" + requestXML);

			/*
			 * 正确提交的参数 <xml> <appid>wx2421b1c4370ec43b</appid>
			 * <attach><![CDATA[att1]]></attach> <body><![CDATA[JSAPI
			 * 支付测试]]></body> <device_info>1000</device_info>
			 * <mch_id>10000100</mch_id>
			 * <nonce_str>b927722419c52622651a871d1d9ed8b2</nonce_str>
			 * <notify_url
			 * >http://wxpay.weixin.qq.com/pub_v2/pay/notify.php</notify_url>
			 * <out_trade_no>1405713376</out_trade_no>
			 * <spbill_create_ip>127.0.0.1</spbill_create_ip>
			 * <total_fee>1</total_fee> <trade_type>JSAPI</trade_type>
			 * <sign><![CDATA[3CA89B5870F944736C657979192E1CF4]]></sign> </xml>
			 */

			// 将这些参数以POST方式调用微信统一支付接口
			String result = CommonUtil.httpsRequest(WeiXinPayConfig.UNIFIED_ORDER_URL, "POST",requestXML);
			// 解析微信返回的信息，以Map形式存储便于取值
			 Map<String, String> map = XMLUtil.doXMLParse(result);

			/*
			 * 正确的返回数据 <xml> <return_code><![CDATA[SUCCESS]]></return_code>
			 * <return_msg><![CDATA[OK]]></return_msg>
			 * <appid><![CDATA[wx2421b1c4370ec43b]]></appid>
			 * <mch_id><![CDATA[10000100]]></mch_id>
			 * <device_info><![CDATA[1000]]></device_info>
			 * <nonce_str><![CDATA[FvYSnPuFFPkAr77M]]></nonce_str>
			 * <sign><![CDATA[63238039D6E43634297CF2A6EB5F3B72]]></sign>
			 * <result_code><![CDATA[SUCCESS]]></result_code>
			 * <openid><![CDATA[oUpF8uN95-Ptaags6E_roPHg7AG0]]></openid>
			 * <is_subscribe><![CDATA[Y]]></is_subscribe>
			 * <trade_type><![CDATA[JSAPI]]></trade_type>
			 * <bank_type><![CDATA[CCB_CREDIT]]></bank_type>
			 * <total_fee>1</total_fee> <coupon_fee>0</coupon_fee>
			 * <fee_type><![CDATA[CNY]]></fee_type>
			 * <transaction_id><![CDATA[1008450740201407220000058756
			 * ]]></transaction_id>
			 * <out_trade_no><![CDATA[1406033828]]></out_trade_no>
			 * 微信公众号支付接口文档V3.3 <attach><![CDATA[att]]></attach>
			 * <time_end><![CDATA[20140722160655]]></time_end> </xml>
			 */

			/*
			 * 在微信浏览器里面打开 H5 网页中执行 JS 调起支付。接口输入输出数据格式为 JSON。
			 * 现在我们将返回的值传入到支付jsp页面，在支付jsp页面调用支付接口。
			 */
			SortedMap<Object, Object> params = new TreeMap<Object, Object>();
			params.put("appId", WeiXinPayConfig.APPID);
			params.put("timeStamp", Long.toString(new Date().getTime()));
			params.put("nonceStr", PayCommonUtil.CreateNoncestr());

			System.out.println("prepay_id==============:" + map.get("prepay_id"));
			params.put("package", "prepay_id=" + map.get("prepay_id"));
			params.put("signType", WeiXinPayConfig.SIGN_TYPE);
			String paySign = PayCommonUtil.createSign("UTF-8", params);// 验签
			params.put("packageValue", "prepay_id=" + map.get("prepay_id")); // 这里用packageValue是预防package是关键字在js获取值出错
			params.put("paySign", paySign); // paySign的生成规则和Sign的生成规则一致
			params.put("sendUrl", WeiXinPayConfig.ORDER_SUCCESS_URL); // 付款成功后跳转的页面
			String userAgent = request.getHeader("user-agent");
			char agent = userAgent.charAt(userAgent.indexOf("MicroMessenger") + 15);
			params.put("agent", new String(new char[] { agent }));// 微信版本号，用于前面提到的判断用户手机微信的版本是否是5.0以上版本。
			String json = JSONObject.fromObject(params).toString();
			return json;
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * 支付成功跳转的页面(回调)
	 * @Title: getPaySuccess
	 * @Description: 微信支付成功回调接收页面（一般该地址指向主工程去） ；在此可进行订单的相关操作
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping("paySuccess")
	public void getPaySuccess(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("---------微信支付成功进入回调页面啦-------------");
		try {
			InputStream inStream = request.getInputStream();
			ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = inStream.read(buffer)) != -1) {
				outSteam.write(buffer, 0, len);
			}
			System.out.println("~~~~~~~~~~~~~~~~付款成功~~~~~~~~~");
			outSteam.close();
			inStream.close();
			String result = new String(outSteam.toByteArray(), "utf-8");// 获取微信调用我们notify_url的返回信息
			Map<Object, Object> map = XMLUtil.doXMLParse(result);//把字符串xml 转为map
			for (Object keyValue : map.keySet()) {
				System.out.println("返回的参数信息（可见4.2.通用通知接口）=====："+keyValue + "=" + map.get(keyValue));
			}
			if (map.get("result_code").toString().equalsIgnoreCase("SUCCESS")) {
				// ----------------------TODO 对数据库的操作（订单相关状态操作）
				response.getWriter().write(PayCommonUtil.setXML("SUCCESS", "")); // 告诉微信服务器，我收到信息了
				//打印返回给微信的参数
				System.out.println("-------------" + PayCommonUtil.setXML("SUCCESS", ""));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
