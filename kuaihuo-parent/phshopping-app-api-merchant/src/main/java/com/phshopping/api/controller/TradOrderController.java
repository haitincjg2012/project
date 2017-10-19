package com.phshopping.api.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.ph.shopping.common.core.config.properties.WebProperties;
import com.ph.shopping.common.core.customenum.CacheKeyTypeEnum;
import com.ph.shopping.common.core.customenum.PayTypeEnum;
import com.ph.shopping.common.core.other.annotation.Token;
import com.ph.shopping.common.util.container.ContainerUtil;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.date.DateUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.rsa.MD5;
import com.ph.shopping.common.util.transform.MoneyTransUtil;
import com.ph.shopping.facade.member.menum.MemberResultEnum;
import com.ph.shopping.facade.member.service.IMemberService;
import com.ph.shopping.facade.member.service.IMessageService;
import com.ph.shopping.facade.member.service.IPayPasswordService;
import com.ph.shopping.facade.score.service.IScoreService;
import com.ph.shopping.facade.spm.dto.MerchantDTO;
import com.ph.shopping.facade.spm.service.IMerchantService;
import com.ph.shopping.facade.spm.spmEnum.SPMEnum;
import com.ph.shopping.facade.spm.vo.MerchantVO;

import cm.ph.shopping.facade.order.constant.OrderResultEnum;
import cm.ph.shopping.facade.order.dto.AddMemberOrderOnlineDTO2;
import cm.ph.shopping.facade.order.dto.AddMemberOrderUnlineDTO;
import cm.ph.shopping.facade.order.dto.PhMemberOrderUnlineDTO;
import cm.ph.shopping.facade.order.dto.PhMemberOrderUnlineDTOS;
import cm.ph.shopping.facade.order.entity.PhMemberOrderOnlineSku;
import cm.ph.shopping.facade.order.entity.PhMemberOrderUnline;
import cm.ph.shopping.facade.order.service.IOnlineOrderService;
import cm.ph.shopping.facade.order.service.TradOrderService;
import cm.ph.shopping.facade.order.vo.PhMemberOrderUnlineVO;

/**
 * 
 * @ClassName:  OnlineOrderController   
 * @Description:交易订单相关接口   
 * @author:李治桦 
 * @date:   2017年08月25日 下午5:17:01     
 * @Copyright: 2017
 */
@RestController
@RequestMapping("api/merchant/")
public class TradOrderController {

	/**
	 * 日志对象
	 */
	private static final Logger logger = LoggerFactory.getLogger(TradOrderController.class);
	
	/**交易订单接口*/
    @Reference(version = "1.0.0")
    private TradOrderService tradOrderService;
    /**会员接口*/
    @Reference(version = "1.0.0")
	private IMemberService memberService;
    /**消息推送*/
    @Reference(version = "1.0.0", retries = 0, timeout = 30000)
	private IMessageService messageService;
    /**商户接口*/
    @Reference(version = "1.0.0")
    private IMerchantService iMerchantService;
    /**properties 操作服务*/
    @Autowired
	private WebProperties webConfig;
    
    /**
   	 * 
   	 * @Title: testAddTradOrder   
   	 * @Description: 验证接口 (创建交易订单)
   	 * @param: @return      
   	 * @return: Result
   	 * @author：李治桦
   	 * @throws
   	 */
    @RequestMapping(value="/testAddTradOrder",method = RequestMethod.POST)
    @ResponseBody
    public Result testAddTradOrder(HttpServletRequest request,String memberPhone,Long merchantId) {
		logger.info("验证会员是否存在：mermberPhone,merchantId = {} ", JSON.toJSONString(memberPhone),JSON.toJSONString(merchantId));
		Result result = ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR);
		//验证手机号
		if(memberPhone==null||"".equals(memberPhone)){
			return ResultUtil.getResult(RespCode.Code.ERROR_PARAM);
		}
		//发起验证
		result=tradOrderService.testAddTradOrder(memberPhone,merchantId);
		
		logger.info("验证会员是否存在result = {} ", JSON.toJSONString(result));
		
		return result;
	}
	/**
	 * 商户APP
	 * 验证绑定人是否为平台用户
	 *  @param: @return
	 *  @return: Result
	 *  @author：李治桦
	 */
	@RequestMapping(value="/testMemberPro",method = RequestMethod.POST)
	@ResponseBody
	public Result testMemberPro(HttpServletRequest request,String memberPhone) {
		logger.info("验证绑定人是否为平台用户：mermberPhone = {} ", JSON.toJSONString(memberPhone));
		Result result = ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR);
		//验证手机号
		if(memberPhone==null||"".equals(memberPhone)){
			return ResultUtil.getResult(RespCode.Code.ERROR_PARAM);
		}
		//发起验证
		result=tradOrderService.testMemberPro(memberPhone);

		logger.info("验证绑定人是否为平台用户result = {} ", JSON.toJSONString(result));

		return result;
	}
    /**
	 * 
	 * @Title: addTradOrder   
	 * @Description: 创建交易订单  
	 * @param: @return      
	 * @return: Result
	 * @author：李治桦
	 * @throws
	 */
	@RequestMapping(value = "/addTradOrder",method = RequestMethod.POST)
	@ResponseBody
	public Result addTradOrder(HttpServletRequest request,PhMemberOrderUnlineDTOS orderUnline) {
		logger.info("创建交易订单处理参数：PhMemberOrderUnline = {} ", JSON.toJSONString(orderUnline));
		Result result = ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR);
		byte payType=9;   //支付类型
		//发起交易
		result=tradOrderService.addTradOrder(orderUnline,payType);
		
		logger.info("创建交易订单返回数据 result = {} ", JSON.toJSONString(result));
		
		return result;
	}
	 /**
		 * 
		 * @Title: getMerchantTradOrder   
		 * @Description: 查看交易订单  
		 * @param: @return      
		 * @return: Result
		 * @author：李治桦
		 * @throws
		 */
		@RequestMapping(value = "/getMerchantTradOrder",method = RequestMethod.POST)
		@ResponseBody
		public Result getMerchantTradOrder(PhMemberOrderUnlineDTO orderUnline,int page,int pageSize) {
			logger.info("交易订单处理参数：PhMemberOrderUnlineDTO = {} ", JSON.toJSONString(orderUnline));
			Result result = ResultUtil.getResult(RespCode.Code.SUCCESS);
			//获取列表
			result=tradOrderService.getMerchantTradOrder(orderUnline,page,pageSize);
			
			logger.info("创建交易订单返回数据 result = {} ", JSON.toJSONString(result));
			
			return result;
		}
    
}
