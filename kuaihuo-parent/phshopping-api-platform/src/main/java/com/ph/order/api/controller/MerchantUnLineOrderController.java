//package com.ph.order.api.controller;
//
//import java.util.UUID;
//
//import javax.servlet.http.HttpServletRequest;
//
//import com.ph.shopping.facade.spm.dto.AgentDTO;
//import com.ph.shopping.facade.spm.dto.MerchantDTO;
//import com.ph.shopping.facade.spm.vo.MerchantVO;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.alibaba.dubbo.config.annotation.Reference;
//import com.alibaba.fastjson.JSON;
//import com.ph.base.BaseController;
//import com.ph.shopping.common.core.customenum.RoleEnum;
//import com.ph.shopping.common.core.customenum.SmsCodeType;
//import OrderUtil;
//import OrderBizCode;
//import com.ph.shopping.common.util.core.RespCode;
//import com.ph.shopping.common.util.core.ResultUtil;
//import com.ph.shopping.common.util.page.PageBean;
//import com.ph.shopping.common.util.result.Result;
//import com.ph.shopping.common.util.rsa.MD5;
//import com.ph.shopping.common.util.transform.MoneyTransUtil;
//import com.ph.shopping.facade.member.dto.MemberCardInfoDTO;
//import com.ph.shopping.facade.member.dto.PayPasswordDTO;
//import com.ph.shopping.facade.member.entity.TradersPassword;
//import com.ph.shopping.facade.member.service.IMemberCardService;
//import com.ph.shopping.facade.member.service.IMemberService;
//import com.ph.shopping.facade.member.service.ISmsCodeSendService;
//import com.ph.shopping.facade.permission.vo.SessionRoleVo;
//import com.ph.shopping.facade.permission.vo.SessionUserVo;
//import com.ph.shopping.facade.score.service.IScoreService;
//import com.ph.shopping.facade.score.vo.MemberScoreVO2;
//import com.ph.shopping.facade.spm.entity.Agent;
//import com.ph.shopping.facade.spm.entity.Merchant;
//import com.ph.shopping.facade.spm.entity.UserBalance;
//import com.ph.shopping.facade.spm.service.IAgentService;
//import com.ph.shopping.facade.spm.service.IMerchantService;
//import com.ph.shopping.facade.spm.vo.AgentVO;
//import com.ph.shopping.facade.spm.vo.MerchantDetaileVO;
//
//import cm.ph.shopping.facade.order.service.IUnLineOrderService;
//import cm.ph.shopping.facade.order.dto.PhOrderUnlineDTO;
//import cm.ph.shopping.facade.order.dto.QueryMerchantOrderTakeDTO;
//import cm.ph.shopping.facade.order.entity.PhOrderUnline;
//import cm.ph.shopping.facade.order.exception.OrderExceptionEnum;
//
///**
// * @项目：phshopping-api-platform
// *
// * @描述：商户线下订单 和 商户的提货订单
// *
// * @作者： Mr.Dong
// *
// * @创建时间：2017年3月29日
// *
// * @Copyright @2017 by Mr.Dong
// */
//@Controller
//@RequestMapping("web/merchantUnLineOrder")
//public class MerchantUnLineOrderController extends BaseController {
//	
//	// 创建日志
//	protected Logger logger = LoggerFactory.getLogger(this.getClass());
//	
//	@Reference(version="1.0.0")
//	IUnLineOrderService iUnLineOrerService;
//	
//    @Reference(version = "1.0.0")
//    private ISmsCodeSendService iSmsCodeSendService;
//    
//	@Reference(version="1.0.0")
//	IMemberCardService  IMemberCardService;
//	
//	@Reference(version="1.0.0")
//	IMerchantService iMerchantService;
//	
//	@Reference(version="1.0.0")
//	IMemberService iMemberService;
//	
//	@Reference(version="1.0.0")
//	IScoreService iScoreService;
//	
//	//代理接口
//	@Reference(version="1.0.0")
//	IAgentService iAgentService;
//	 
//	/**
//	 * 跳转线下订单list页面
//	 * @author Mr.Dong
//	 * @param 
//	 * @createTime 2017年3月15日
//	 */
//	@RequestMapping(value="/unLineOrderPage",method=RequestMethod.GET)
//	public String getMerchantList(){
//		return "/order/unLineOrder/unLineOrderList";
//	}
//	
//	/**
//	 * 获取商户线下订单List(代理 管理员都要来调用此接口)
//	 * @author Mr.Dong
//	 * @param phOrderUnlineDTO
//	 * @createTime 2017年3月15日
//	 */
//	@SuppressWarnings("unused")
//	@RequestMapping(value="/unLineOrderList",method=RequestMethod.GET)
//	@ResponseBody
//	public Object getMerchantUnLineOrderVoList(@ModelAttribute PhOrderUnlineDTO phOrderUnlineDTO,PageBean pagebean,HttpServletRequest request)throws Exception{
//		Long agentId = null;
//		SessionUserVo sessionUserVo = getLoginUser(request);
//		if(sessionUserVo!=null&&sessionUserVo.getId()!=null
//				&&sessionUserVo.getSessionRoleVo()!=null&&sessionUserVo.getSessionRoleVo().get(0)!=null){
//			SessionRoleVo sessionRoleVo = sessionUserVo.getSessionRoleVo().get(0);
//			RoleEnum roleEnum = RoleEnum.getRoleEnumByCode(sessionRoleVo.getRoleCode());
//			if("ADMIN".equals(roleEnum.name())){
//				phOrderUnlineDTO.setMerchantId(null);
//			}else if("CITY_AGENT".equals(roleEnum.name()) || "COUNTY_AGENT".equals(roleEnum.name()) || "COMMUNITY_AGENT".equals(roleEnum.name())){
////				Agent agent = new Agent();
////				agent.setId(getLoginUser(request).getId());
//				AgentDTO agentDTO = new AgentDTO();
//				agentDTO.setId(getLoginUser(request).getId());
//				AgentVO agentDetail = iAgentService.getAgentVOListById(agentDTO);
//				if("CITY_AGENT".equals(roleEnum.name())){
//					phOrderUnlineDTO.setProvinceId(agentDetail.getProvinceId().toString());
//					phOrderUnlineDTO.setCityId(agentDetail.getCityId().toString());
//				}else if("COUNTY_AGENT".equals(roleEnum.name())){
//					phOrderUnlineDTO.setProvinceId(agentDetail.getProvinceId().toString());
//					phOrderUnlineDTO.setCityId(agentDetail.getCityId().toString());
//					phOrderUnlineDTO.setCountyId(agentDetail.getCountyId().toString());
//				}else{
//					phOrderUnlineDTO.setProvinceId(agentDetail.getProvinceId().toString());
//					phOrderUnlineDTO.setCityId(agentDetail.getCityId().toString());
//					phOrderUnlineDTO.setCountyId(agentDetail.getCountyId().toString());
//					phOrderUnlineDTO.setTownId(agentDetail.getTownId().toString());
//				}
//						
//			}else{
//				phOrderUnlineDTO.setMerchantId(getLoginUser(request).getId());//重session中获取id
//			}
//		}
//		return IUnLineOrderService.getUnLineOrderVoList(phOrderUnlineDTO,pagebean);
//		
//	}
//	
//	/**
//	 * 跳转创建线下订单页面
//	 * @author Mr.Dong
//	 * @param request
//	 * @createTime 2017年3月15日
//	 */
//	@RequestMapping(value="/establishUnLineOrderPage",method=RequestMethod.GET)
//	public String  examineMerchantList(HttpServletRequest request){
//		 UUID uuid = UUID.randomUUID();
//		request.getSession().setAttribute("token", uuid.toString());
//		return "/order/unLineOrder/establishUnLineOrder";
//	}
//	
//	
//	/**
//	 * 创建线下订单
//	 * @author Mr.Dong
//	 * @param phOrderUnline
//	 * @param verificationCode
//	 * @param payPassWord
//	 * @createTime 2017年3月15日
//	 */
//	@SuppressWarnings("unused")
//	@RequestMapping(value="/addUnLineOrder",method=RequestMethod.POST)
//	@ResponseBody
//	public Object addUnLineOrder(PhOrderUnline phOrderUnline,String verificationCode,String payPassWord,HttpServletRequest request,String token) throws Exception{
//		Result result = ResultUtil.getResult(RespCode.Code.SUCCESS);
//		
//		if(request.getSession().getAttribute("token") == null){
//			return ResultUtil.getResult(OrderExceptionEnum.ADD_MERCHANTUNLINEORDER_EXCEPTION);
//		}else if(!token.equals((String)(request.getSession().getAttribute("token")))){
//			return ResultUtil.getResult(OrderExceptionEnum.ADD_MERCHANTUNLINEORDER_EXCEPTION);
//		}
//		phOrderUnline.setOrderNo(OrderUtil.createOrderCode(OrderBizCode.MEMBER_UNLINEORDER));//生成订单号
//		phOrderUnline.setMerchantId(getLoginUser(request).getId());//当前商户的id
//		//判断会员卡号相关
//		Result memberCardInfoByCode = IMemberCardService.getMemberCardInfoByInnerCode(phOrderUnline.getMemberCard());
//		logger.info(" Result ={}",JSON.toJSON(memberCardInfoByCode));
//		if(!memberCardInfoByCode.getCode().equals("200")){
//			return  ResultUtil.getResult(OrderExceptionEnum.NO_MEMBERCARD_EMPTY);
//		}
//		MemberCardInfoDTO emberCardInfoDto = (MemberCardInfoDTO)memberCardInfoByCode.getData();
//		phOrderUnline.setMemberId(emberCardInfoDto.getMemberId());
//		
//		if(phOrderUnline.getPayTpe()==1){//现金支付
//			//判断该商户的余额和此账户是否存在
//			UserBalance userBalance = new UserBalance();
//			userBalance.setManageId(phOrderUnline.getMerchantId());
//			Result userBalance2 = iMerchantService.getUserBalance(userBalance);
//			if(!userBalance2.getCode().equals("200")){//无此账户
//				return userBalance2;
//			}
//			
//			//得到当前商户分润比率
//			MerchantDTO merchantDTO = new MerchantDTO();
//			merchantDTO.setId(phOrderUnline.getMerchantId());
//			MerchantVO merchantDetailForUpdate = iMerchantService.getMerchantDateilById(merchantDTO);
//			Double businessProfitRatio = merchantDetailForUpdate.getBusinessProfitRatio().doubleValue();//得到分润比率
//						
//			if((((UserBalance)userBalance2.getData()).getBalance()+10000000) < MoneyTransUtil.transMultiDouble((phOrderUnline.getOrderMoney()).doubleValue() * businessProfitRatio)){//余额不足
//				return  ResultUtil.getResult(OrderExceptionEnum.NOENOUGH_MONEY_EXCEPTION); 
//			}
//			
//			//判断商户密码
//			PayPasswordDTO dto = new PayPasswordDTO();
//			dto.setCustomerType(2);//平台支付密码
//			dto.setRelatedId(phOrderUnline.getMerchantId());//用户id
//			result = iMemberService.getPayPwdInfo(dto);
//			if(!result.getCode().equals("200")){
//				logger.error("获取商户支付密码异常"); 
//				return  ResultUtil.getResult(OrderExceptionEnum.NO_MEMBERPAYPASS_EXCEPTION);
//			}
//			TradersPassword p = (TradersPassword)result.getData();
//			if(!p.getPayPwd().equals(MD5.getMD5Str(payPassWord))){
//				logger.error("商户密码不对");
//				return  ResultUtil.getResult(OrderExceptionEnum.ERROR_PAYPASSWORD_EXCEPTION);
//			}
//		}else if (phOrderUnline.getPayTpe()==2){
//			//判断会员的积分是否充足
//		     MemberScoreVO2 memberScore = iScoreService.getMemberScore(phOrderUnline.getMemberId());
//		     if(memberScore == null){
//		    	 logger.error("没有查询到用户的积分");
//		    	 return  ResultUtil.getResult(OrderExceptionEnum.NO_MEMBERSCORCE_EXECEPTION);
//		     }
//		     if(memberScore.getEnablescore() < phOrderUnline.getOrderMoney()*10000){//积分不足
//		    	 	return  ResultUtil.getResult(OrderExceptionEnum.NOENOUGH_MEMBER_SCORE_EXCEPTION);
//				}
//			//验证短信 验证没问题了才发短信
//			 result = iSmsCodeSendService.verifySmsCode(((MemberCardInfoDTO)memberCardInfoByCode.getData()).getMemberPhone(), SmsCodeType.SCORE_PAY.getCodeType(), verificationCode);
//		        if(!result.getCode().equals("200")){
//		        	logger.error("短信验证异常");
//		        	return  ResultUtil.getResult(OrderExceptionEnum.CHECKBIND_EXCEPTION);
//		        }			    
//		}else {
//			logger.info("会员扫描支付");
//		}
//		request.getSession().setAttribute("token", null);
//		Result addUnLineOrder = IUnLineOrderService.addUnLineOrder(phOrderUnline);
//		return result;
//	}
//	
//	/**
//	 * 发验证码
//	 * @author Mr.Dong
//	 * @param phOrderUnline
//	 * @createTime 2017年3月15日
//	 */
//    @RequestMapping(value = "/sendValidCode", method = RequestMethod.POST)
//    @ResponseBody
//    public Object sendValidCode(@ModelAttribute PhOrderUnline phOrderUnline) {
//    	Result result = ResultUtil.getResult(RespCode.Code.SUCCESS);
//    	try{
//	    	//先通过会员卡内码获取会员手机号码
//	    	Result memberCardInfoByCode = IMemberCardService.getMemberCardInfoByInnerCode(phOrderUnline.getMemberCard());
//			if(!memberCardInfoByCode.getCode().equals("200")){
//				logger.error("根据卡号获取会员信息失败");
//				return memberCardInfoByCode;
//			}
//			MemberCardInfoDTO emberCardInfoDto = (MemberCardInfoDTO)memberCardInfoByCode.getData();
//	        return iSmsCodeSendService.sendSmsCodeByNoMsg(emberCardInfoDto.getMemberPhone(), SmsCodeType.SCORE_PAY.getCodeType());
//    	}catch (Exception e) {
//			e.printStackTrace();
//		}
//		return result;
//    }  
//
//    /**
//	 * 跳转商户提货订单页面
//	 * @author Mr.Dong
//	 * @param 
//	 * @createTime 2017年3月15日
//	 */
//    @RequestMapping(value="/getMerchantTakeOrderPage",method=RequestMethod.GET)
//    public String getMerchantTakeOrderPage(){
//    	return "/order/orderTake";
//    }  
//    
//    /**
//  	 * 提货订单VOlist
//  	 * @author Mr.Dong
//  	 * @param queryMerchantOrderTakeDTO
//  	 * @param pagebean
//  	 * @createTime 2017年3月15日
//  	 */
//	@RequestMapping(value="/getMerchantTakeOrderVoList",method=RequestMethod.GET)
//	@ResponseBody
//    public Object getMerchantTakeOrderVoList(QueryMerchantOrderTakeDTO queryMerchantOrderTakeDTO, PageBean pagebean,HttpServletRequest request){
//		queryMerchantOrderTakeDTO.setMerchantId(getLoginUser(request).getId());
//    	return IUnLineOrderService.getMerchantTakeOrderVoList(queryMerchantOrderTakeDTO,pagebean);
//    }
//}
