package com.alqsoft.controller.mobile.view.wxpay;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alqsoft.rpc.mobile.RpcZhiFuService;

/**
 * 支付宝回调
 * @author Administrator
 *
 */
@RestController
@RequestMapping("mobile/view/alipay")
public class AlipayController {
	private static Logger logger = LoggerFactory.getLogger(AlipayController.class);
	
	@Autowired
	private RpcZhiFuService rpcZhiFuService;
	
	@RequestMapping(value="payback", method=RequestMethod.POST)
	public void aLiPayBack(HttpServletRequest request, HttpServletResponse response){
		logger.info("==>>>>>>>>>>>>>>>>>进入支付宝回调<<<<<<<<<<<<<<<<<<<<<<==");
		logger.info("支付宝回调参数："+request.getParameterMap());
		String backparams="";
		try {
			Map<String,String> params = new HashMap<String,String>();
			Map requestParams = request.getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			    String name = (String) iter.next();
			    String[] values = (String[]) requestParams.get(name);
			    String valueStr = "";
			    for (int i = 0; i < values.length; i++) {
			        valueStr = (i == values.length - 1) ? valueStr + values[i]
			                    : valueStr + values[i] + ",";
			  }
			    valueStr= StringEscapeUtils.unescapeXml(valueStr);
			  //乱码解决，这段代码在出现乱码时使用。
			  //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			  params.put(name, valueStr);
			  backparams=backparams+"&"+name+"="+valueStr;
			 }
			logger.info("===支付宝回调参数："+backparams);
			String backresult = rpcZhiFuService.AlipayNotify(params);
			BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());  
	        out.write(backresult.getBytes());  //给微信后台返回接收结果通知
	        out.flush();  
	        out.close();  
		} catch (Exception e) {
			logger.error("=========支付宝回调处理异常"+e.getMessage());
			e.printStackTrace();
		}
		
		//切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
		//boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
	}
}
