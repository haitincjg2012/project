package com.phshopping.pay.api.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.ph.shopping.common.util.http.HttpClientUtils;
import com.ph.shopping.common.util.http.HttpResult;
import com.ph.shopping.common.util.order.OrderUtil;
import com.ph.shopping.facade.pay.utils.commonpay.CodeConstExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alqsoft.rpc.pay.RpcPayService;
import com.ph.shopping.common.core.cache.redis.ICacheService;
import com.ph.shopping.common.util.date.DoubleUtils;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.verifycode.VerifyUtil;
import com.ph.shopping.facade.pay.config.AlipayConfig;
import com.ph.shopping.facade.pay.dto.PayOrderDTO;
import com.ph.shopping.facade.pay.enums.AlipayProductCodeEnum;
import com.ph.shopping.facade.pay.exception.AlipayExceptionEnum;
import com.ph.shopping.facade.pay.exception.CommonPayExceptionEnum;
import com.ph.shopping.facade.pay.service.IPayService;
import com.ph.shopping.facade.pay.vo.UnlineOrderVO;

/**
 * @项目：phshopping-parent
 * @描述：支付宝controller @作者： Mr.chang @创建时间：2017/6/2
 * @Copyright @2017 by Mr.chang
 */
@Controller
@RequestMapping("alipay")
public class AlipayController {

	protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ICacheService cacheService;

	@Reference(version = "1.0.0")
	private IPayService payService;

	/**
	 * 支付宝Web签名订单数据
	 *
	 * @param pd
	 * @return
	 */
	@RequestMapping(value = "web/order/sign", method = RequestMethod.POST)
	public ModelAndView signWebParam(PayOrderDTO pd) {
		log.info("==========================开始进入支付宝跳转==========================");
		log.info("支付传入参数：" + JSON.toJSONString(pd));
		ModelAndView mv = new ModelAndView();
		try {
			// 校验参数是否正确
			if (VerifyUtil.verifyEntityField(pd)) {
				log.error("web订单参数校验错误");
				mv.addObject("code", AlipayExceptionEnum.PARAM_ERROR.getCode());
				mv.addObject("msg", AlipayExceptionEnum.PARAM_ERROR.getMsg());
				mv.setViewName("pay/alipay/failure");
				return mv;
			}
			// 从redis获取订单信息
			if (!cacheService.exists(pd.getOrderNum())) {
				mv.addObject("msg", "订单号不存在");
				mv.addObject("code", CommonPayExceptionEnum.COMMON_PAY_PARAM_EXCEPTION.getCode());
				mv.setViewName("error/error");
				return mv;
			}

			PayOrderDTO pod = new PayOrderDTO();
			// 获取redis订单号信息
			Object orderDTO = cacheService.get(pd.getOrderNum());
			JSONObject jsonObj = JSON.parseObject(orderDTO.toString());
			pod = JSON.toJavaObject(jsonObj, PayOrderDTO.class);

			// 校验缓存参数
			pod.setOrderNum(pd.getOrderNum());
			String orderMsg = pod.validateForm();
			if (Objects.nonNull(orderMsg)) {
				mv.addObject("msg", orderMsg);
				mv.addObject("code", CommonPayExceptionEnum.COMMON_PAY_ORDER_NOT_EXIST.getCode());
				mv.setViewName("error/error");
				return mv;
			}
			// 获得初始化的AlipayClient
			AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.GATEWAY_URL, AlipayConfig.APP_ID,
					AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET,
					AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
			// 设置请求参数
			AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
			// 封装请求支付信息
			AlipayTradePagePayModel model = new AlipayTradePagePayModel();
			model.setOutTradeNo(pd.getOrderNum());
			model.setSubject(pd.getDescription());
			model.setTotalAmount(pd.getAmount());
			model.setTimeoutExpress(AlipayConfig.TIMEOUT_EXPRESS);// 设置订单超时时间
			model.setProductCode(AlipayProductCodeEnum.PRODUCT_FAST_INSTANT_CODE.getCode());
			alipayRequest.setBizModel(model);
			// 设置异步通知地址
			alipayRequest.setNotifyUrl(AlipayConfig.ASYNC_NOTIFY_URL);
			String alipayResult = alipayClient.pageExecute(alipayRequest).getBody();
			mv.addObject("data", alipayResult);
			mv.setViewName("pay/alipay/pay");
		} catch (Exception e) {
			log.error("支付宝APP签名数据异常：" + e);
			mv.setViewName("pay/alipay/failure");
			mv.addObject("code", AlipayExceptionEnum.ALIPAY_SIGN_ERROR.getCode());
			mv.addObject("msg", AlipayExceptionEnum.ALIPAY_SIGN_ERROR.getMsg());
		}
		log.info("==========================结束支付宝跳转===========================");
		return mv;
	}


	/**
	 * 快火充值发起支付宝充值加签名
	 * @param ud
	 * @return
	 * @author wangxueyang
	 */
	@RequestMapping(value = "alipay/app/order/sign",method = RequestMethod.POST)
	@ResponseBody
	public Result signParam(@RequestBody PayOrderDTO ud){
		Result result = new Result();
		try {
			log.info( "支付宝APP支付参数内容payOrderDTO={}",JSON.toJSONString( ud ) );
			//校验参数是否正确
			if(VerifyUtil.verifyEntityField(ud)){
				log.error("APP订单参数校验错误");
				result = new Result(false, AlipayExceptionEnum.PARAM_ERROR.getCode(),AlipayExceptionEnum.PARAM_ERROR.getMsg());
				return result;
			}
			log.info("*********************   充值支付发往pay工程请求加签   ***********************");
			//发起参数封装
			String out_trade_no = ud.getOrderNum();// 1. 订单号
			String total_fee = ud.getAmount();//2. 交易金额
			String body = "充值支付"; // 3. 订单描述
			String subject = ud.getDescription(); // 4.商品支付
			String notify_url=AlipayConfig.ASYNC_NOTIFY_URL; // 5. 异步回调地址
			String systemfrom = "";  // 6.支付来源
			String bizCode = OrderUtil.getOrderBizCode(out_trade_no);
			if(bizCode.equals("XXDD")){
				//快火会员支付
				systemfrom = CodeConstExt.PAY_SYSTEM_FROM_KHAPP;
			}else if(bizCode.equals("XSDD")){
				//快火掌柜
				systemfrom = CodeConstExt.PAY_SYSTEM_FROM_KHAPP;
			}else if(bizCode.equals("PFDD")){
				//快火批发
				systemfrom = CodeConstExt.PAY_SYSTEM_FROM_KHAPP;
			}else if(bizCode.equals("CZDD")){
				//商户充值
				systemfrom = CodeConstExt.PAY_SYSTEM_FROM_KHAPP;
			}else if(bizCode.equals("SMDD")){
				//扫码支付
				systemfrom = CodeConstExt.PAY_SYSTEM_FROM_KHAPP;
			} else {
					//支付订单来源异常
					log.error("支付订单来源异常!" );
					result = new Result(false, AlipayExceptionEnum.PARAM_ERROR.getCode(), "获取订单数据异常!");
					return result;
			}
			Map<String, String> params = new HashMap<String, String>();
			params.put("out_trade_no", out_trade_no);
			params.put("total_fee", total_fee);
			params.put("body", body);
			params.put("subject", subject);
			params.put("notify_url", notify_url);
			params.put("systemfrom", systemfrom);
			//发送参数路径  post
			String URL = AlipayConfig.PaySignUrl;
			//接收加签结果
			HttpResult data = HttpClientUtils.sendPost(URL, params);
			//返回结果给app
			String content = data.getResponseContent();
			log.info("pay工程响应结果是：   " + content);
			Map map = JSON.parseObject(content,Map.class);
			String  requestURL="";   //手机发起支付请求
			if(map.get("result").equals("请求参数非法") || map.get("result").equals("系统异常")){
				throw new Exception("获取请求参数异常");
			}else{
				//requestURL = AlipayConfig.GATEWAY_URL+"?sign="+ map.get("result") +"&partner="+map.get("partner")+"&subject="+subject+"&out_trade_no="+out_trade_no+"";
				requestURL = ""+map.get("result");
			}
			result.setData(requestURL);
			result.setSuccess(true);
			result.setCode("200");
			result.setMessage(AlipayExceptionEnum.RESPONSE_SUCCESS.getMsg());
			result.setCount(0);
			log.info( "支付宝APP支付签名结果result={}",JSON.toJSONString( result ) );
		} catch (Exception e) {
			e.printStackTrace();
			log.error("支付宝APP签名数据异常："+e);
		}
		return result;
	}

	/**
	 * 创建人：王雪洋 创建时间：2017年8月27日 上午11:34:47
	 * 快火会员支付发起支付宝支付加签
	 * @param id
	 * @return result
	 */
	@RequestMapping(value = "kuaihuo/app/order/sign", method = RequestMethod.POST)
	@ResponseBody
	public Result signParam(Long id) {
		Result result = new Result();
		try {
			log.info("支付宝APP支付参数内容id= " + id);
			// 校验参数
			if (id == null) {
				log.error("APP订单id为空");
				result = new Result(false, AlipayExceptionEnum.PARAM_ERROR.getCode(),
						AlipayExceptionEnum.PARAM_ERROR.getMsg());
				return result;
			}
			// 校验支付订单
			UnlineOrderVO ov = payService.queryUnlineOrderVO(id);
			if (ov == null) {
				log.error("订单不存在!");
				result = new Result(false, AlipayExceptionEnum.PARAM_ERROR.getCode(), "支付订单不存在!");
				return result;
			}
			if (ov.getOrderMoney() == null || ov.getOrderNo() == null) {
				log.error("获取订单数据异常!");
				result = new Result(false, AlipayExceptionEnum.PARAM_ERROR.getCode(), "获取订单数据异常!");
				return result;
			}
			//  ************************* 发送pay工程所需参数 ***************************
			String out_trade_no = ov.getOrderNo();// 1. 订单号
			// 处理订单金额
			String total_fee = (DoubleUtils.formatRound(ov.getOrderMoney() / 10000.00 , 2))+""; // 2. 订单金额
			String body = "订单支付"; // 3. 订单描述
			String subject = "商品支付"; // 4.商品支付
			String notify_url=AlipayConfig.ASYNC_NOTIFY_URL; // 5. 异步回调地址
			String systemfrom = "";  // 6.支付来源
			String bizCode = OrderUtil.getOrderBizCode(out_trade_no);
			if(bizCode.equals("XXDD")){
				//快火会员支付
				systemfrom = CodeConstExt.PAY_SYSTEM_FROM_KHAPP;
			}else if(bizCode.equals("XSDD")){
				//快火掌柜
				systemfrom = CodeConstExt.PAY_SYSTEM_FROM_KHAPP;
			}else if(bizCode.equals("PFDD")){
				//快火批发
				systemfrom = CodeConstExt.PAY_SYSTEM_FROM_KHAPP;
			}else if(bizCode.equals("CZDD")){
				//商户充值
				systemfrom = CodeConstExt.PAY_SYSTEM_FROM_KHAPP;
			}else if(bizCode.equals("SMDD")){
				//扫码支付
				systemfrom = CodeConstExt.PAY_SYSTEM_FROM_KHAPP;
			}else if(bizCode.equals("ZFDD")){
				//支付订单
				systemfrom = CodeConstExt.PAY_SYSTEM_FROM_KHAPP;
			} else{
				//支付订单来源异常
				log.error("支付订单来源异常!" );
				result = new Result(false, AlipayExceptionEnum.PARAM_ERROR.getCode(), "获取订单数据异常!");
				return result;
			}
			Map<String, String> params = new HashMap<String, String>();
			params.put("out_trade_no", out_trade_no);
			params.put("total_fee", total_fee);
			params.put("body", body);
			params.put("subject", subject);
			params.put("notify_url", notify_url);
			params.put("systemfrom", systemfrom);

			log.info("发起pay工程请求的参数数据：");
			for (Object obj : params.keySet()) {
				log.info(obj+"    :    "+params.get(obj));
			}
			//发送参数路径  post
			String URL = AlipayConfig.PaySignUrl;
			//接收加签结果
			HttpResult data = HttpClientUtils.sendPost(URL, params);
			//返回结果给app
			String content = data.getResponseContent();
			log.info("pay工程响应结果是：   " + content);
			Map map = JSON.parseObject(content,Map.class);
			String  requestURL="";   //手机发起支付请求
			if(map.get("result").equals("请求参数非法") || map.get("result").equals("系统异常")){
				throw new Exception("获取请求参数异常");
			}else{
				//requestURL = AlipayConfig.GATEWAY_URL+"?sign="+ map.get("result") +"&partner="+map.get("partner")+"&subject="+subject+"&out_trade_no="+out_trade_no+"";
				requestURL = ""+map.get("result");
			}
			result.setData(requestURL);
			result.setSuccess(true);
			result.setCode("200");
			result.setMessage(AlipayExceptionEnum.RESPONSE_SUCCESS.getMsg());
			result.setCount(0);
			log.info( "支付宝APP支付签名结果result={}",JSON.toJSONString( result ) );
		} catch (Exception e) {
			e.printStackTrace();
			log.error("支付宝APP签名数据异常：" + e);
			result.setMessage("支付宝支付异常");
		}
		return result;
	}
}