package com.alqsoft.controller.pay.yinlian;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alqsoft.service.gzylreturn.GZYLReturnService;

/**
 * @ClassName  GZYLReturnController
 * Date:     2017年3月13日  14:45:41 <br/>
 * @author   dinglanlan
 * @version  批发商提现回调
 * @see 	 
 */
@RequestMapping("pay/yinlian/gzylcash")
@Controller
public class GZYLReturnController {


	private static Log logger = LogFactory.getLog(GZYLReturnController.class);
	
	@Autowired
	private GZYLReturnService gZYLReturnService;

	@RequestMapping("GZYLCashPay")
	@ResponseBody
	public String gzylCashPay(HttpServletRequest request,HttpServletResponse response)  {
		logger.info("贵州银联代付回调开始------------------------------------------------");
        // begin 接收post请求报文
        String call = "error";
        BufferedReader buff=null;
		try {
			buff = request.getReader();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			  return "error";
		}
		StringBuilder builder = new StringBuilder(); // 密文
		try {
			buff = request.getReader();
			builder = new StringBuilder(); // 密文
			while (true) {
			    String temp = buff.readLine();
			    if (null == temp)
			        break;
			    builder.append(temp);
			    if (100000 <= builder.length()) {
			    	//errorRsp(response, "02000", "网关数据请求错误！");
			        return "error";
			    }
			}
			String encReq = builder.toString();
			
			call = gZYLReturnService.validationParam(request,response,encReq);
		} catch (Exception e) {
			logger.info("回调方法处理中,发生异常");
			try {
				buff.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				 return "error";
			}
		} finally {
			try {
				buff.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				 return "error";
			}
		}
		if ("success".equals(call)) {
			try {
				response.getOutputStream().println("success");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "success";
			}
			logger.info("回调方法处理完毕,返回的字符串"+call+",通知贵州银联收到回调");
			return "success";
		}
		return "shibai";
	}
	
}
