/**
 * Project Name:alqsoft-easyui
 * File Name:BackRcvResponse.java
 * Package Name:com.alqsoft.controller.pay.yinlian
 * Date:2015年11月1日下午4:00:47
 * Copyright (c) 2015, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.alqsoft.controller.pay.yinlian;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * ClassName:BackRcvResponse <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年11月1日 下午4:00:47 <br/>
 * @author   zc
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
import org.springframework.web.bind.annotation.ResponseBody;

import com.unionpay.acp.sdk.SDKUtil;

/**
 * 重要：联调测试时请仔细阅读注释！
 * 
 * 产品：跳转网关支付产品<br>
 * 功能：后台通知接收处理示例 <br>
 * 日期： 2015-09<br>
 * 版本： 1.0.0 
 * 版权： 中国银联<br>
 * 说明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己需要，按照技术文档编写。该代码仅供参考。<br>
 * 该接口参考文档位置：open.unionpay.com帮助中心 下载  产品接口规范  《网关支付产品接口规范》，<br>
 *              《平台接入接口规范-第5部分-附录》（内包含应答码接口规范，全渠道平台银行名称-简码对照表），
 * 测试过程中的如果遇到疑问或问题您可以：1）优先在open平台中查找答案：
 * 							        调试过程中的问题或其他问题请在 https://open.unionpay.com/ajweb/help/faq/list 帮助中心 FAQ 搜索解决方案
 *                             测试过程中产生的6位应答码问题疑问请在https://open.unionpay.com/ajweb/help/respCode/respCodeList 输入应答码搜索解决方案
 *                           2） 咨询在线人工支持： open.unionpay.com注册一个用户并登陆在右上角点击“在线客服”，咨询人工QQ测试支持。
 * 交易说明：成功的交易才会发送后台通知，建议此交易与交易状态查询交易结合使用确定交易是否成功
 */
@RequestMapping("pay")
@Controller
public class BackRcvResponse {
	
	@SuppressWarnings("unused")
	private static Logger logger=LoggerFactory.getLogger(BackRcvResponse.class);
	
	@RequestMapping("unipay/backpay/{orderNum}")
	@ResponseBody
	public String backResponse(@PathVariable("orderNum") String orderNum,HttpServletRequest req) throws UnsupportedEncodingException{
		logger.info("银联BackRcvResponse接收后台通知开始");
		req.setCharacterEncoding("ISO-8859-1");
		String encoding = req.getParameter("UTF-8");
		// 获取银联通知服务器发送的后台通知参数
		Map<String, String> reqParam = getAllRequestParam(req);

		Map<String, String> valideData = null;
		if (null != reqParam && !reqParam.isEmpty()) {
			Iterator<Entry<String, String>> it = reqParam.entrySet().iterator();
			valideData = new HashMap<String, String>(reqParam.size());
			while (it.hasNext()) {
				Entry<String, String> e = it.next();
				String key = (String) e.getKey();
				String value = (String) e.getValue();
				value = new String(value.getBytes("ISO-8859-1"), encoding);
				valideData.put(key, value);
			}
		}
		//重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
		if (!SDKUtil.validate(valideData, encoding)) {
			logger.error("验证签名结果[失败].");
			//验签失败，需解决验签问题
			
		} else {
			logger.info("验证签名结果[成功].");
			//交易成功，更新商户订单状态
			//获取后台通知的数据，其他字段也可用类似方式获取
			//String orderNum; 银联传过来的，我们设置的订单号；如果银联收不到我的们返回的请求，他会间隔10分钟发一次，持续4次；此处判断，要做严格判断；防止一笔订单多次付款成功；
			
		}
		logger.info("BackRcvResponse接收后台通知结束");
		//返回给银联服务器http 200状态码
		return "ok";
	}
	
	/**
	 * 获取请求参数中所有的信息
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> getAllRequestParam(final HttpServletRequest request) {
		Map<String, String> res = new HashMap<String, String>();
		Enumeration<?> temp = request.getParameterNames();
		if (null != temp) {
			while (temp.hasMoreElements()) {
				String en = (String) temp.nextElement();
				String value = request.getParameter(en);
				res.put(en, value);
				//在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
				//System.out.println("ServletUtil类247行  temp数据的键=="+en+"     值==="+value);
				if (null == res.get(en) || "".equals(res.get(en))) {
					res.remove(en);
				}
			}
		}
		return res;
	}
}
