package com.phshopping.pay.api.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.http.IPUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.pay.config.SwiftpassConfig;
import com.ph.shopping.facade.pay.dto.SwiftpassPayDTO;
import com.ph.shopping.facade.pay.exception.SwiftPayExceptionEnum;
import com.ph.shopping.facade.pay.utils.swiftpasspay.MD5;
import com.ph.shopping.facade.pay.utils.swiftpasspay.SignUtils;
import com.ph.shopping.facade.pay.utils.swiftpasspay.XmlUtils;

/**
 * @项目：phshopping-facade-order
 * @描述：威富通扫码支付
 * @作者： 张霞
 * @创建时间： 15:29 2017/7/20
 * @Copyright @2017 by zhangxia
 */
@Controller
@RequestMapping(value = "pay/swift")
public class SwiftPassPayController {
    protected Logger log = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "paySwiftCodeUrl")
	public @ResponseBody Result paySwiftCodeUrl(HttpServletRequest request, Model model,
			SwiftpassPayDTO swiftpassPayDTO) {
		log.info("=============进入威富通扫码支付，获取请求地址开始==================");
		log.info("入参参数swiftpassPayDTO={}", JSON.toJSONString(swiftpassPayDTO));

		Result result = ResultUtil.getResult(RespCode.Code.FAIL);
		try {
			String message = swiftpassPayDTO.validateForm();
			if (StringUtils.isNotBlank(message)) {
				result.setMessage(message);
				return result;
			}
			SortedMap<String, String> param = new TreeMap<>();
			param.put("body", swiftpassPayDTO.getBody());// 订单描述
			param.put("out_trade_no", swiftpassPayDTO.getOrderNo());// 订单号
			param.put("total_fee", payMoney(swiftpassPayDTO.getMoney()));// 订单金额
			param.put("mch_create_ip", IPUtil.getIpAddress(request));// 设备ip
			param.put("mch_id", SwiftpassConfig.mch_id);
			param.put("notify_url", SwiftpassConfig.notify_url);
			param.put("nonce_str", String.valueOf(new Date().getTime()));
			param.put("service", SwiftpassConfig.service);
			Map<String, String> params = SignUtils.paraFilter(param);
			StringBuilder buf = new StringBuilder((params.size() + 1) * 10);
			SignUtils.buildPayParams(buf, params, false);
			// 进行字典序排序后
			String preStr = buf.toString();
			log.info("进行字典序排序后字符串的请求参数preStr={}", preStr);
			// 获取Md5加密验签
			String sign = MD5.sign(preStr, "&key=" + SwiftpassConfig.key, "utf-8");
			param.put("sign", sign);
			log.info("获取Md5加密后请求参数sign={}", sign);
			// 获取请求地址
			String reqUrl = SwiftpassConfig.req_url;
			log.info("reqUrl：" + reqUrl);
			log.info("reqParams:" + XmlUtils.parseXML(param));
			CloseableHttpResponse response = null;
			CloseableHttpClient client = null;
			try {
				HttpPost httpPost = new HttpPost(reqUrl);
				StringEntity entityParams = new StringEntity(XmlUtils.parseXML(param), "utf-8");
				httpPost.setEntity(entityParams);
				httpPost.setHeader("Content-Type", "text/xml;charset=ISO-8859-1");
				client = HttpClients.createDefault();
				response = client.execute(httpPost);
				if (response != null && response.getEntity() != null) {
					Map<String, String> resultMap = XmlUtils.toMap(EntityUtils.toByteArray(response.getEntity()),
							"utf-8");
					log.info("请求威富通扫码支付结果：" + resultMap);
					if (resultMap.containsKey("sign")) {
						if (!SignUtils.checkParam(resultMap, SwiftpassConfig.key)) {
							log.info("威富通扫码支付验证签名不通过，订单号={}", swiftpassPayDTO.getOrderNo());
							return ResultUtil.getResult(SwiftPayExceptionEnum.SWIFT_PAY_YANQIAN_FAIL);
						} else {
							if ("0".equals(resultMap.get("status")) && "0".equals(resultMap.get("result_code"))) {
								// 成功返回微信扫描二维码
								String code_img_url = resultMap.get("code_img_url");
								String out_trade_no = param.get("out_trade_no");
								String total_fee = param.get("total_fee");
								String body = param.get("body");
								Map<String, String> resultData = new HashMap<>();
								resultData.put("code_img_url", code_img_url);
								resultData.put("out_trade_no", out_trade_no);
								resultData.put("total_fee", total_fee);
								resultData.put("body", body);
								return ResultUtil.getResult(RespCode.Code.SUCCESS, resultData);
							} else {
								log.info("请求威富通扫码支付返回结果有误，具体如下err_code={};err_msg={}", resultMap.get("err_code"),
										resultMap.get("err_msg"));
								result.setMessage(resultMap.get("err_msg"));
							}
						}
					} else {
						log.warn(SwiftPayExceptionEnum.SWIFT_PAY_RESPONSE_CONTENT.getMsg());
						result.setCode(SwiftPayExceptionEnum.SWIFT_PAY_RESPONSE_CONTENT.getCode());
						result.setMessage(resultMap.get("message"));
						return result;
					}
				} else {
					result.setCode(SwiftPayExceptionEnum.SWIFT_PAY_RESPONSE_FAIL.getCode());
					log.warn(SwiftPayExceptionEnum.SWIFT_PAY_RESPONSE_FAIL.getMsg());
					return result;
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			} finally {
				if (response != null) {
					response.close();
				}
				if (client != null) {
					client.close();
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		log.info("================威富通扫码支付，获取请求地址结束，请求订单号=" + swiftpassPayDTO.getOrderNo() + "，返回结果={}==============",
				JSON.toJSONString(result));
		return result;
	}
	
	private String payMoney(Double money){
		Double p = money * 100;
		int payMoney = p.intValue();
		return payMoney + "";
	}
}

