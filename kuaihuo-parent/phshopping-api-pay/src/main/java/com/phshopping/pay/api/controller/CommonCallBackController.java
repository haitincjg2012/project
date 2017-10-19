package com.phshopping.pay.api.controller;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.shopping.common.core.customenum.PayTypeEnum;
import com.ph.shopping.facade.pay.enums.PayStatusEnum;
import com.ph.shopping.facade.pay.exception.CommonPayException;
import com.ph.shopping.facade.pay.exception.CommonPayExceptionEnum;
import com.ph.shopping.facade.pay.service.IPayService;
import com.ph.shopping.facade.pay.utils.commonpay.RSACommonUtilsTQ;
import com.ph.shopping.facade.pay.vo.PayecoOrderVo;

/**
 * @项目：phshopping-parent
 * @描述：北京通用支付回调controller
 * @作者： Mr.chang @创建时间：2017/5/27
 * @Copyright @2017 by Mr.chang
 */
@Controller
@RequestMapping(value = "/whoAreYou")
public class CommonCallBackController {

	protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Reference(version = "1.0.0")
	private IPayService payService;

	/**
	 * 北京通用支付异步回调方法
	 * @param request
	 * @param response
	 * @throws IOException
	 * @author Mr.Chang
	 * @since 2017.06.29
	 * @description 后期会修改为POST请求方式
	 */
	@RequestMapping(value = "/shenhuiyuan2")
	@ResponseBody
	public String commonpayAsyncCallback(HttpServletRequest request, HttpServletResponse response) {
		log.info("================================开始进入北京通用支付异步回调=============================");
		try {
			//订单号
			String orderNo = request.getParameter("shen");
			//订单支付状态
			String payStatus = request.getParameter("hui");
			//订单金额
			String money = request.getParameter("yuan");
			if (StringUtils.isEmpty(orderNo)||StringUtils.isEmpty(payStatus)||StringUtils.isEmpty(money)){
				log.error("支付回调参数存在空");
				throw new CommonPayException(CommonPayExceptionEnum.COMMON_PAY_PARAM_EXCEPTION);
			}else{
				String orderNumDe= RSACommonUtilsTQ.decryptByPublicKey(orderNo, RSACommonUtilsTQ.CharSet.UTF8);
				String payStatusDe= RSACommonUtilsTQ.decryptByPublicKey(payStatus, RSACommonUtilsTQ.CharSet.UTF8);
				String moneyDe= RSACommonUtilsTQ.decryptByPublicKey(money, RSACommonUtilsTQ.CharSet.UTF8);
				log.info("通用支付回调结果参数：订单号："+orderNumDe+"  支付结果："+payStatusDe+" 订单金额："+moneyDe);
				// 根据订单号查询订单信息
				PayecoOrderVo pv = payService.getOrderByOrderNo(orderNumDe);
				//判断回调金额
				if (org.springframework.util.StringUtils.isEmpty(pv)) {
					log.error("支付回调异常,订单号不存在!");
					throw new CommonPayException(CommonPayExceptionEnum.COMMON_PAY_ORDER_NOT_EXIST);
				}

				BigDecimal returnMoney=new BigDecimal(moneyDe);
				BigDecimal orinMoney=new BigDecimal(pv.getScore());

				BigDecimal payScale=new BigDecimal(100);
				BigDecimal orderScale=new BigDecimal(10000);
				BigDecimal changeOrderMoney=returnMoney.multiply(orderScale).divide(payScale);

				BigDecimal score=new BigDecimal(pv.getScore());

				log.info("北京通用支付回调金额转换后："+changeOrderMoney);
				log.info("订单原始金额转换后："+score);
				//判断金额
				if (changeOrderMoney.compareTo(score)!=0){
					log.error("支付回调异常,订单金额错误");
					throw new CommonPayException(CommonPayExceptionEnum.COMMON_PAY_AMOUNT_EXCEPTION);
				}
				if (payStatusDe.equals("2")){
					payService.checkAndUpdateOrder(orderNumDe, PayTypeEnum.PAY_TYPE_COMMONPAY.getPayType(),PayStatusEnum.PAY_SUCCESS.getCode(), payStatusDe);
				}else{
					payService.checkAndUpdateOrder(orderNumDe, PayTypeEnum.PAY_TYPE_COMMONPAY.getPayType(),PayStatusEnum.PAY_FAILURE.getCode(), payStatusDe);
				}
				log.info("===============================通知北京通用支付回调成功========================");
				// 通知接收成功
				return "success";
			}
		} catch (CommonPayException e){
			log.error("北京通用支付回调更新业务数据异常：" + e);
		}
        log.info("===============================通知北京通用支付回调失败========================");
		return "fail";
	}
}
