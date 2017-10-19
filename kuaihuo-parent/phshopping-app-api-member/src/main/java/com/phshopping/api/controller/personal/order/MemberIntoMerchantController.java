package com.phshopping.api.controller.personal.order;


import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.ph.shopping.common.core.customenum.CacheKeyTypeEnum;
import com.ph.shopping.common.core.other.annotation.Token;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.date.DateUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.member.dto.MerchantDishDTO;
import com.ph.shopping.facade.member.service.IMemberIntoMerchantService;
import com.ph.shopping.facade.member.service.IMemberService;
import com.ph.shopping.facade.member.service.IMessageService;
import com.ph.shopping.facade.member.service.IPayPasswordService;
import com.ph.shopping.facade.score.service.IScoreService;
import com.ph.shopping.facade.spm.service.IMerchantService;

import cm.ph.shopping.facade.order.entity.PhMemberOrderUnline;
import cm.ph.shopping.facade.order.service.MemberTradOrderService;

	/**
	 * 
	 * @ClassName:  OnlineOrderController   
	 * @Description:线上订单相关接口   
	 * @author: 李治桦
	 * @date:   2017年5月23日 下午3:17:01     
	 * @Copyright: 2017
	 */
	@RestController
	@RequestMapping("api/personal/memberIntoMerchant")
	public class MemberIntoMerchantController {

		/**
		 * 日志对象
		 */
		private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
		
		/**商户信息接口*/
	    @Reference(version = "1.0.0")
	    private IMemberIntoMerchantService memberIntomerchantService;
	    /**积分接口*/
	    @Reference(version = "1.0.0")
	    private IScoreService scoreService;
	    /**会员接口*/
	    @Reference(version = "1.0.0")
		private IMemberService memberService;
	    /**消息推送*/
	    @Reference(version = "1.0.0", retries = 0, timeout = 30000)
		private IMessageService messageService;
	    /**支付密码服务*/
	    @Reference(version = "1.0.0", retries = 0)
		private IPayPasswordService payPasswordService;
	    /**商户接口*/
	    @Reference(version = "1.0.0")
	    private IMerchantService iMerchantService;
	    /**
		 * 
		 * @Title: getMerchantInfo   
		 * @Description: 会员进入商户得到商户基本信息  
		 * @param: @return      
		 * @return: Result
		 * @author：李治桦
		 * @throws
		 */
		@RequestMapping(value = "/getMerchantInfo",method = RequestMethod.POST)
		@ResponseBody
		public Result getMemberTradOrder(HttpServletRequest request,Long merchantId) {
			logger.info("查询商户基本信息参数：merchantId = {} ", JSON.toJSONString(merchantId));
			Result result = ResultUtil.getResult(RespCode.Code.FAIL);
			//获取
			result=memberIntomerchantService.getMerchantInfo(merchantId);
			logger.info("查询商户基本信息参数 result = {} ", JSON.toJSONString(result));
			return result;
		}
		/**
		 * 
		 * @Title: getMerchantInfo   
		 * @Description: 会员进入商户得到商户拥有的菜品信息 
		 * @param: @return      
		 * @return: Result
		 * @author：李治桦
		 * @throws
		 */
		@RequestMapping(value = "/getDishAll",method = RequestMethod.POST)
		@ResponseBody
		public Result getDishAll(HttpServletRequest request,MerchantDishDTO dish) {
			logger.info("查询商户菜品信息：MerchantDishDTO = {} ", JSON.toJSONString(dish));
			Result result = ResultUtil.getResult(RespCode.Code.FAIL);
	
			//获取
			result=memberIntomerchantService.getDishAll(dish);
			logger.info("查询商户菜品信息 result = {} ", JSON.toJSONString(result));
			return result;
		}
		
	}
