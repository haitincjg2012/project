package com.phshopping.api.controller.personal.order;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.ph.shopping.common.core.config.properties.WebProperties;
import com.ph.shopping.common.core.customenum.CacheKeyTypeEnum;
import com.ph.shopping.common.core.other.annotation.Token;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.date.DateUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.member.service.IMemberService;
import com.ph.shopping.facade.member.service.IMessageService;
import com.ph.shopping.facade.member.service.IPayPasswordService;
import com.ph.shopping.facade.score.service.IScoreService;
import com.ph.shopping.facade.spm.dto.MerchantDTO;
import com.ph.shopping.facade.spm.service.IMerchantService;
import com.ph.shopping.facade.spm.spmEnum.SPMEnum;
import com.ph.shopping.facade.spm.vo.MerchantVO;
import com.phshopping.api.controller.dto.AppAddOrderOnlineDTO;
import com.phshopping.api.uitl.UserCacheUtil;

import cm.ph.shopping.facade.order.dto.AddMemberOrderOnlineDTO2;
import cm.ph.shopping.facade.order.dto.PhMemberOrderUnlineDTO;
import cm.ph.shopping.facade.order.dto.PhMemberOrderUnlineDTOS;
import cm.ph.shopping.facade.order.entity.PhMemberOrderUnline;
import cm.ph.shopping.facade.order.service.IOnlineOrderService;
import cm.ph.shopping.facade.order.service.MemberTradOrderService;
import cm.ph.shopping.facade.order.service.TradOrderService;

	/**
	 * 
	 * @ClassName:  OnlineOrderController   
	 * @Description:线上订单相关接口   
	 * @author: 李治桦
	 * @date:   2017年5月23日 下午3:17:01     
	 * @Copyright: 2017
	 */
	@RestController
	@RequestMapping("api/personal/memberTradOrder")
	public class MemberTradOrderController {

		/**
		 * 日志对象
		 */
		private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
		
		/**线上订单接口*/
	    @Reference(version = "1.0.0")
	    private MemberTradOrderService memberTradOrderService;
	    /*扫码接口*/
	    @Reference(version = "1.0.0")
	    private TradOrderService tradOrderService;
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
		 * @Title: getMemberTradOrder   
		 * @Description: 会员查看交易订单  
		 * @param: @return      
		 * @return: Result
		 * @author：李治桦
		 * @throws
		 */
//		@Token(key = "token", isCache = true, cacheType = CacheKeyTypeEnum.HASH)
		@RequestMapping(value = "/getMemberTradOrder",method = RequestMethod.POST)
		@ResponseBody
		public Result getMemberTradOrder(HttpServletRequest request,PhMemberOrderUnlineDTO member,int page,int pageSize) {
			logger.info("查询交易订单参数：PhMemberOrderUnlineDTO = {} ", JSON.toJSONString(member));
			Result result = ResultUtil.getResult(RespCode.Code.FAIL);
			//获取订单列表
			result=memberTradOrderService.getMemberTradOrder(member,page,pageSize);
			logger.info("查看订单返回数据 result = {} ", JSON.toJSONString(result));
			return result;
		}
		/**
		 * 
		 * @Title: addMemberScanOrder   
		 * @Description: 会员扫码入口
		 * @param: @return      
		 * @return: Result
		 * @author：李治桦
		 * @throws
		 */
		@Token(key = "token", isCache = true, cacheType = CacheKeyTypeEnum.HASH)
		@RequestMapping(value = "/addMemberScanOrder",method = RequestMethod.POST)
		@ResponseBody
		public Result addMemberScanOrder(HttpServletRequest request,PhMemberOrderUnlineDTOS member) {
			logger.info("会员扫码入口订单参数：PhMemberOrderUnline = {} ", JSON.toJSONString(member));
			//参数：  memberId   merchantId  orderMoney 
			Result result = ResultUtil.getResult(RespCode.Code.FAIL);
			byte payType=8;  //支付类型  扫码支付
			//会员扫码
			result=tradOrderService.addTradOrder(member,payType);
			logger.info("会员扫码入口订单返回数据 result = {} ", JSON.toJSONString(result));
			return result;
		}
	}
