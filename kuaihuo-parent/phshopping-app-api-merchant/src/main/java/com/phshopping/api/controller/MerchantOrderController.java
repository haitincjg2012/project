package com.phshopping.api.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ph.shopping.common.core.cache.redis.ICacheService;
import com.ph.shopping.common.core.customenum.RoleEnum;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.phshopping.api.aop.annotation.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.ph.shopping.common.util.result.Result;

import cm.ph.shopping.facade.order.dto.AddMemberOrderOnlineDTO2;
import cm.ph.shopping.facade.order.service.IOnlineOrderService;

@Controller
@RequestMapping("merchantOrder")
@ResponseBody
public class MerchantOrderController extends BaseMerchantController{
	
	private static final Logger logger = LoggerFactory.getLogger(MerchantOrderController.class);
	
	@Reference(version = "1.0.0")
	private IOnlineOrderService iOnlineOrderService;

	@Autowired
	private ICacheService redisService;
	/**
	 * <pre>AgreeOrder(作用：商户接单)   
	 * 创建人：赵俊彪   
	 * 创建时间：2017年8月28日 下午6:26:47
	 * @param addDto
	 * @param response
	 * @return
	 * @throws Exception</pre>
	 */
	@RequestMapping(value="agreeOrder")
	public Result AgreeOrder(AddMemberOrderOnlineDTO2 addDto,HttpServletResponse response) throws Exception{
		logger.info("接收到的订单"+addDto.getOrderNo());
		//根据订单号查询相关数据
		AddMemberOrderOnlineDTO2 findOrder = iOnlineOrderService.queryOrderDate(addDto.getOrderNo());
		logger.info("订单详情数据={}",JSON.toJSONString(findOrder));
		//传入预定时间
		addDto.setHopeServiceDate(findOrder.getHopeServiceDate());
		//传入商户ID
		addDto.setMerchantId(findOrder.getMerchantId());
		addDto.setMemberId(findOrder.getMemberId());
		addDto.setDishId(findOrder.getDishId());
		addDto.setType(findOrder.getType());
		addDto.setMemberTelphone(findOrder.getMemberTelphone());
		addDto.setCreateTime(new Date());
		Result result = iOnlineOrderService.findOrderNoStatus(addDto);
		return result;
	}

	@RequestMapping(value="cancelOrder")
	@AccessToken
	public Result cancelOrder(HttpServletRequest request, Long orderId){
		Result result = ResultUtil.getResult(RespCode.Code.FAIL);
		String merchantAppKey = redisService.get(getMerchantAppKey(request.getHeader("token"), RoleEnum.MERCHANT))
				.toString();
		logger.info("商户取消订单传入订单id:"+orderId+"，商户id："+merchantAppKey);
		try {
			if (orderId == null ){
				throw new Exception("订单号传入空值！");
			}
			if (merchantAppKey == null || merchantAppKey.equals("")){
				throw new Exception("会员号传入空值！");
			}
			result = iOnlineOrderService.cancelOrderByMerchant(orderId,Long.valueOf(merchantAppKey));
		}catch (Exception e){
			logger.info(e.getMessage());
			result = ResultUtil.setResult(false, "取消订单失败！");
		}
		return result;
	}

}
