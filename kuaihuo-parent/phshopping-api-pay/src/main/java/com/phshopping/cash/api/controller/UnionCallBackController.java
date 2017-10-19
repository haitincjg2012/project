package com.phshopping.cash.api.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.pay.service.ICashService;
import com.phshopping.utils.SerializableUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @项目：phshopping-parent
 * @描述：贵州银联回调 @作者： Mr.chang @创建时间：2017/5/27
 * @Copyright @2017 by Mr.chang
 */
@Controller
@RequestMapping("union")
public class UnionCallBackController {

	protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Reference(version = "1.0.0")
	private ICashService cashService;

	/**
	 * 贵州银联提现异步回调
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @author Mr.Chang
	 */
	@RequestMapping(value = "cash/asyncCallBack", method = RequestMethod.POST)
	@ResponseBody
	public String cashAsyncCallBack(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("==================贵州银联代付回调开始====================");
		String call = "";
		BufferedReader buff = request.getReader();
		StringBuilder builder;
		try {
			builder = new StringBuilder(); // 密文
			while (true) {
				String temp = buff.readLine();
				log.info("读取回调密文:" + temp);
				if (null == temp)
					break;
				builder.append(temp);
				if (100000 <= builder.length()) {
					call = "error";
					return call;
				}
			}
			String encReq = builder.toString();
			log.info("贵州银联回调密文:" + encReq);
			// 序列化回调密文
			encReq = SerializableUtils.serialize(encReq);
			// 返回业务处理结果
			Result result = cashService.defayCallBack(encReq);
			if (result == null) {
				call = "success";
				return call;
			}

			// 判断处理业务返回的状态
			if (result.isSuccess()) {
				call = "success";
				response.getOutputStream().println("success");
				return call;
			} else {
				call = "error";
			}
		} catch (Exception e) {
			log.error("贵州银联处理回调业务发生异常：" + e);
			buff.close();
		} finally {
			buff.close();
		}
		log.info("==================贵州银联代付回调结束====================");
		return call;
	}
}
