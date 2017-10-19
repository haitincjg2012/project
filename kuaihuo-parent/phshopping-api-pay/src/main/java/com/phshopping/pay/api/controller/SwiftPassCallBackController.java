package com.phshopping.pay.api.controller;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.ph.shopping.common.core.customenum.PayTypeEnum;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.pay.config.SwiftpassConfig;
import com.ph.shopping.facade.pay.enums.PayStatusEnum;
import com.ph.shopping.facade.pay.exception.SwiftPayExceptionEnum;
import com.ph.shopping.facade.pay.exception.SwiftpayException;
import com.ph.shopping.facade.pay.service.IPayService;
import com.ph.shopping.facade.pay.utils.swiftpasspay.SignUtils;
import com.ph.shopping.facade.pay.utils.swiftpasspay.XmlUtils;
import com.ph.shopping.facade.pay.vo.PayecoOrderVo;

/**
 * @项目：phshopping-facade-order
 * @描述：威富通扫码支付回调controller
 * @作者： 张霞
 * @创建时间： 18:36 2017/7/20
 * @Copyright @2017 by zhangxia
 */
@Controller
@RequestMapping(value = "swiftpasspay/callback")
public class SwiftPassCallBackController {
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Reference(version="1.0.0")
    private IPayService payService;
    
	@RequestMapping(value = "/saync")
	public void swiftpayCallBack(HttpServletRequest request, HttpServletResponse response) {
		log.info("===================开始威富通扫码支付回调======================");
		String respString = "fail";
		try {
			String resString = XmlUtils.parseRequst(request);
			log.info("回调通知内容：" + resString);
			if (resString != null && !"".equals(resString)) {
				Map<String, String> map = XmlUtils.toMap(resString.getBytes(), "utf-8");
				String res = XmlUtils.toXml(map);
				log.info("通知内容：" + res);
				if (map.containsKey("sign")) {
					if (!SignUtils.checkParam(map, SwiftpassConfig.key)) {
						res = "验证签名不通过";
						log.info(res);
						respString = "fail";
					} else {
						String status = map.get("status");
						String message = map.get("message");
						if (status != null && "0".equals(status)) {
							log.info("威富通扫码支付回调通知成功");
							String result_code = map.get("result_code");
							String err_code = map.get("err_code");// 错误代码
							String err_msg = map.get("err_code");// 错误代码描述
							if (result_code != null && "0".equals(result_code)) {
								// 此处可以在添加相关处理业务，校验通知参数中的商户订单号out_trade_no和金额total_fee是否和商户业务系统的单号和金额是否一致，一致后方可更新数据库表中的记录。
								log.info("威富通扫码支付端业务处理成功,下面进行相关业务处理");
								// 处理业务--star
								String transaction_id = map.get("transaction_id");// 威富通交易订单号
								log.info("威富通交易订单号：" + transaction_id);
								String pay_result = map.get("pay_result");// 支付结果
								log.info("支付结果：" + pay_result);
								String out_trade_no = map.get("out_trade_no");// 我们系统支付订单号
								String total_fee = map.get("total_fee");// 总金额--以分为单位
								String time_end = map.get("time_end");// 支付完成时间
								log.info("支付完成时间：" + time_end);
								// 查询回调处理的订单号
								PayecoOrderVo po = payService.getOrderByOrderNo(out_trade_no);
								// 验证金额--star
								// 由于保存在数据库里面的金额*10000这里要先除以100后再进行比对（单位是分）
								BigDecimal scale = new BigDecimal(100);
								BigDecimal orderMoney = new BigDecimal(po.getScore()).divide(scale);
								int backfee = Integer.valueOf(total_fee);
								if (backfee != orderMoney.intValue()) {
									log.error("支付订单:" + out_trade_no + "回调金额不一致");
									throw new SwiftpayException(SwiftPayExceptionEnum.SWIFT_PAY_AMOUNT_ERROR);
								}
								// 验证金额--end
								// 更新订单支付状态
								Result result = payService.checkAndUpdateOrder(out_trade_no,
										PayTypeEnum.PAY_TYPE_WEIXINPAY.getPayType(),
										PayStatusEnum.PAY_SUCCESS.getCode(), result_code);
								log.info("更新订单支付状态 返回结果 Result = {}", JSON.toJSONString(result));
								if (result.isSuccess()) {
									respString = "success";
								}
								// 处理业务--star
							} else {
								log.info("威富通扫码支付端业务处理失败，返回result_code={}，错误代码err_code={},错误代码描述={}", result_code,
										err_code, err_msg);
							}
						} else {
							log.info("威富通扫码支付回调通知失败，status={},返回信息message={}", status, message);
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("威富通扫码支付回调更新业务异常：{}", e);
		}
		responseWrite(response, respString);
		log.info("============================威富通扫码支付回调更新业务完成=================================");
	}

	private void responseWrite(HttpServletResponse response, String respString) {
		try {
			PrintWriter pw = response.getWriter();
			pw.write(respString);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
