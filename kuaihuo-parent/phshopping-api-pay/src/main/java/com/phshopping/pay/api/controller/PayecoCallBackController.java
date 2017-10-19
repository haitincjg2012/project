package com.phshopping.pay.api.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.shopping.common.core.customenum.PayTypeEnum;
import com.ph.shopping.common.core.exception.BizException;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.transform.MoneyTransUtil;
import com.ph.shopping.facade.pay.enums.PayStatusEnum;
import com.ph.shopping.facade.pay.exception.PayecoException;
import com.ph.shopping.facade.pay.exception.PayecoExceptionEnum;
import com.ph.shopping.facade.pay.service.IPayService;
import com.ph.shopping.facade.pay.utils.payeco.PayecoUtils;
import com.ph.shopping.facade.pay.vo.PayecoOrderVo;

/**
 * @项目：phshopping-parent
 * @描述：易联支付回调controller @作者： Mr.chang @创建时间：2017/5/27
 * @Copyright @2017 by Mr.chang
 */
@Controller
@RequestMapping(value = "payeco/callback")
public class PayecoCallBackController {

	protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Reference(version = "1.0.0")
	private IPayService payService;

	/**
	 * 易联支付异步回调方法
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @author Mr.Chang
	 * @since 2017.03.29
	 */
	@RequestMapping(value = "/async", method = RequestMethod.POST)
	public void payecoAsyncCallback(HttpServletRequest request, HttpServletResponse response) {
		log.info("================================开始进入易联支付异步回调=============================");
		try {
			final String CharSet = "UTF-8";
			response.setContentType("text/html");
			request.setCharacterEncoding(CharSet);
			response.setCharacterEncoding(CharSet);
			PrintWriter out = response.getWriter();
			String response_text = request.getParameter("response_text");

			if (response_text != null && !"".equals(response_text)) {
				// 解析xml报文数据获取支付结果状态
				String decryptXml = PayecoUtils.decryptMiWen(response_text);
				log.info("异步返回报文：" + decryptXml);
				String respCode = StringUtils.substringBetween(decryptXml, "<RespCode>", "<");
				// 回调订单号
				String orderNum = PayecoUtils.getValue(decryptXml, "MerchantOrderNo");
				// 回调返回的MD5报文加密码
				String mac = PayecoUtils.getValue(decryptXml, "MAC");
				// 回调金额
				String amount = PayecoUtils.getValue(decryptXml, "Amount");
				log.info("易联支付回调返回金额:" + amount);
				log.info("金额转换：" + MoneyTransUtil.transMultiDouble(Double.valueOf(amount)));
				log.info("易联支付订单号：" + orderNum + "异步回调返回交易结果：" + respCode);
				// 根据订单号查询订单信息
				PayecoOrderVo pv = payService.getOrderByOrderNo(orderNum);
				if (org.springframework.util.StringUtils.isEmpty(pv)) {
					log.error("支付异常,订单号不存在!");
					throw new BizException();
				}
				Result result = null;
				// 根据易联支付返回的结果进行业务处理
				if (respCode.equals("0000")) {
					// 判断MD5校验是否正确
//					if(!mac.equals(pv.getMd5Str())) {
//						log.error("返回加密字符串：" + mac+ ",数据库保存的加密传MD5:" + pv.getMd5Str() + "，加密传不一致");
//						// 支付失败业务操作 TODO
//						throw new PayecoException(PayecoExceptionEnum.PAYECO_MD5_EXCEPTION);
//					}
					// 判断回调金额是否正确
					if (org.springframework.util.StringUtils.isEmpty(amount) || pv.getScore() != MoneyTransUtil.transMultiDouble(Double.valueOf(amount))) {
						log.error("支付订单：" + orderNum + "支付异常,回调金额不一致");
						// 支付失败业务操作 TODO
						throw new PayecoException(PayecoExceptionEnum.PAYECO_AMOUNT_EXCEPTION);
					}
					// 支付成功业务操作
					result = payService.checkAndUpdateOrder(orderNum, PayTypeEnum.PAY_TYPE_YILIANPAY.getPayType(),
							PayStatusEnum.PAY_SUCCESS.getCode(), respCode);
					log.info("易联支付订单：" + orderNum + "交易成功");
				} else if (respCode.equals("T503")) {
					// 此处处理超时未支付的订单 TODO
                    result = payService.dealT503Status(orderNum, PayTypeEnum.PAY_TYPE_YILIANPAY.getPayType(),
                            PayStatusEnum.PAY_SUCCESS.getCode(), respCode);
				} else {
					// 支付错误处理 TODO
					log.error("订单号：" + orderNum + "支付失败,错误代码：" + respCode);
					result = payService.checkAndUpdateOrder(orderNum, PayTypeEnum.PAY_TYPE_YILIANPAY.getPayType(),
							PayStatusEnum.PAY_SUCCESS.getCode(), respCode);
				}
				// 判断业务处理结果
				if (result != null && result.getCode().equals(RespCode.Code.SUCCESS.getCode())) {
					// 通知易联接收成功
					out.print("0000");
					out.flush();
					out.close();
				} else {
					out.print("FAIL");
					out.flush();
					out.close();
				}
				log.info("================================完成易联支付异步回调=============================");
			}
		} catch (

		PayecoException e)

		{
			log.error("易联支付回调更新业务数据异常：" + e);
		} catch (

		IOException e)

		{
			log.error("易联支付回调更新业务数据异常：" + e);
		}

	}

	/**
	 * 易联同步回调
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author Mr.Chang
	 * @since 2017.06.05
	 */
	@RequestMapping(value = "/sync", method = RequestMethod.GET)
	public ModelAndView payecoSyncCallback(HttpServletRequest request, HttpServletResponse response) {
		log.info("================================开始易联支付同步回调====================================");
		ModelAndView mv = new ModelAndView();
		try {
			final String CharSet = "UTF-8";
			response.setContentType("text/html");
			request.setCharacterEncoding(CharSet);
			response.setCharacterEncoding(CharSet);
			String response_text = request.getParameter("response_text");
			// 解析返回报文
			if (response_text != null && !"".equals(response_text)) {
				String decryptXml;
				decryptXml = PayecoUtils.decryptMiWen(response_text);
				// 解析xml文件查看支付状态
				log.info("同步返回报文：" + decryptXml);
				String respCode = StringUtils.substringBetween(decryptXml, "<RespCode>", "<");
				// 回调订单号
				String orderNum = PayecoUtils.getValue(decryptXml, "MerchantOrderNo");
				// 回调返回的MD5报文加密码
				String mac = PayecoUtils.getValue(decryptXml, "MAC");
				// 回调金额
				String amount = PayecoUtils.getValue(decryptXml, "Amount");

				log.info("同步回调参数===>金额:" + amount + "  MAC值：" + mac);
				log.info("金额转换：" + MoneyTransUtil.transMultiDouble(Double.valueOf(amount)));
				log.info("订单号：" + orderNum + "异步回调返回结果：" + respCode);

				// 判断是否支付回调成功
				if (respCode.equals("0000")) {
					log.error("订单：" + orderNum + "同步回调成功");
					mv.addObject("msg", "订单支付成功");
					mv.setViewName("pay/payeco/success");
				} else {
					// 支付异常处理
					log.error("订单号：" + orderNum + "支付失败,错误代码：" + respCode);
					mv.addObject("msg", "支付失败,错误代码：" + respCode);
					mv.setViewName("pay/payeco/failure");
				}
			} else {
				log.error("未返回订单支付信息");
				mv.addObject("msg", "未返回订单支付信息");
				mv.setViewName("error/unAuthorization");
			}
		} catch (Exception e) {
			log.error("解析报文异常：" + e);
			mv.addObject("msg", "支付异常报文解析异常");
			mv.setViewName("pay/payeco/failure");
		}
		log.info("================================完成易联支付同步回调====================================");
		return mv;
	}

}
