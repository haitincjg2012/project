//package com.ph.order.api.controller;
//
//import java.util.Date;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.alibaba.dubbo.config.annotation.Reference;
//import com.ph.base.BaseController;
//import com.ph.shopping.common.core.customenum.TransCodeEnum;
//import com.ph.shopping.common.util.result.Result;
//import com.ph.shopping.common.util.transform.MoneyTransUtil;
//import com.ph.shopping.facade.permission.vo.SessionRoleVo;
//import com.ph.shopping.facade.permission.vo.SessionUserVo;
//import com.ph.shopping.facade.spm.entity.UserBalance;
//import com.ph.shopping.facade.spm.service.IMerchantService;
//import com.ph.shopping.facade.spm.service.IUserBalance;
//
//import cm.ph.shopping.facade.order.dto.PhTradersPasswordForOrderDTO;
//import cm.ph.shopping.facade.order.entity.PhAgentOrder;
//import cm.ph.shopping.facade.order.entity.PhMerchantOrder;
//import cm.ph.shopping.facade.order.exception.OrderExceptionEnum;
//import cm.ph.shopping.facade.order.service.IAgentOrderService;
//import cm.ph.shopping.facade.order.service.IMerchantOrderService;
//import cm.ph.shopping.facade.order.vo.PhTradersPasswordForOrderVO;
//
///**
// * 后台 商户、代理商进货支付 控制层
// * 
// * @项目：phshopping-facade-
// *
// * @描述：l
// *
// * 		@作者： 杨颜光
// *
// * @创建时间：2017年3月28日 下午1:42:47
// *
// * @Copyright by 杨颜光
// */
//@Controller
//@RequestMapping("web/forOrderPayment")
//public class PurchasePaymentForOderController extends BaseController {
//
//	@Reference(version = "1.0.0")
//	private IMerchantService iMerchantService;
//
//	@Reference(version = "1.0.0")
//	private IMerchantOrderService iMerchantOrderService;
//
//	@Reference(version = "1.0.0")
//	private IAgentOrderService iAgentOrderService;
//
//	@Reference(version = "1.0.0")
//	private IUserBalance iUserBalance;
//
//	/**
//	 * 商户支付跳转方法
//	 * 
//	 * @param orderId
//	 * @param model
//	 * @param request
//	 * @return String
//	 * @author  杨颜光
//	 */
//	@RequestMapping("/merchatPay")
//	public String merchatPay(Long orderId, Model model, HttpServletRequest request) {
//		// 获取session，判断是商户还是代理商
//		SessionUserVo info = getLoginUser(request);
//		SessionRoleVo role = info.getSessionRoleVo().get(0);
//		// 查询商户在平台的余额
//		UserBalance balance = new UserBalance();
//		balance.setManageId(info.getId());
//		Result result = this.iMerchantService.getUserBalance(balance);
//		double d;
//		if (result.getData() == null) {
//			d = 0;
//		} else {
//			UserBalance balance2 = (UserBalance) result.getData();
//			d = MoneyTransUtil.transDivisDouble(balance2.getBalance());
//		}
//
//		// 查询要支付订单的信息
//		PhMerchantOrder merchantOrders = new PhMerchantOrder();
//		merchantOrders.setId(orderId);
//		PhMerchantOrder merchantOrder = this.iMerchantOrderService.findMerchantOrderByTarget(merchantOrders);
//		merchantOrder.setTotalCost(MoneyTransUtil.transMulti1(merchantOrder.getTotalCost()));
//		model.addAttribute("oder", merchantOrder);
//		model.addAttribute("userBalance", result.getData());
//		model.addAttribute("roleCode", role.getRoleCode());// 商户
//		model.addAttribute("balance", d);
//		return "/payment/payment";
//	}
//
//	/**
//	 * 代理商支付方法
//	 * 
//	 * @param orderId
//	 * @param model
//	 * @param request
//	 * @return String
//	 * @author  杨颜光
//	 */
//	@RequestMapping("/agentPay")
//	public String agentPay(Long orderId, Model model, HttpServletRequest request) {
//		// 获取session，判断是商户还是代理商
//		SessionUserVo info = getLoginUser(request);
//		SessionRoleVo role = info.getSessionRoleVo().get(0);
//		// 查询代理商在平台的余额
//		UserBalance balance = new UserBalance();
//		balance.setManageId(info.getId());
//		Result result = this.iMerchantService.getUserBalance(balance);
//		double d;
//		if (result.getData() == null) {
//			d = 0;
//		} else {
//			UserBalance balance2 = (UserBalance) result.getData();
//			d = MoneyTransUtil.transDivisDouble(balance2.getBalance());
//		}
//		// 查询要支付订单的信息
//		PhAgentOrder agentOrder = new PhAgentOrder();
//		agentOrder.setId(orderId);
//		PhAgentOrder oder = this.iAgentOrderService.findAgentOrderByTarget(agentOrder);
//		oder.setTotalCost(MoneyTransUtil.transMulti1(oder.getTotalCost()));
//		model.addAttribute("oder", oder);
//		model.addAttribute("userBalance", result.getData());
//		model.addAttribute("roleCode", role.getRoleCode());// 代理商
//		model.addAttribute("balance", d);
//		return "/payment/payment";
//	}
//
//	/***
//	 * 代理商余额支付方法
//	 * 
//	 * @param orderId
//	 * @param passWord
//	 * @param request
//	 * @return Result
//	 * @author 杨颜光
//	 * @ 
//	 */
//	@RequestMapping("/agentBalancePay")
//	@ResponseBody
//	public Result agentBalancePay(Long orderId, String passWord, HttpServletRequest request)  {
//		SessionUserVo info = getLoginUser(request);
//		SessionRoleVo role = info.getSessionRoleVo().get(0);
//			// 查询账户余额
//			Result result=new Result();
//			UserBalance balance = new UserBalance();
//			balance.setManageId(info.getId());
//		     result = this.iMerchantService.getUserBalance(balance);
//			UserBalance balance2 = new UserBalance();
//			if (result.getData() == null) {
//				balance2.setBalance(0L);
//			} else {
//				balance2 = (UserBalance) result.getData();
//			}
//
//			// 查询订单支付金额
//			PhAgentOrder agentOrder = new PhAgentOrder();
//			agentOrder.setId(orderId);
//			PhAgentOrder oder = this.iAgentOrderService.findAgentOrderByTarget(agentOrder);
//			// 如果订单金额大于余额提示余额不足
//			if (oder.getTotalCost().longValue() > balance2.getBalance()) {
//				result.setCode(OrderExceptionEnum.PAY__ORDER_BALANCE_IS_NOT_ENOUGH.getCode());
//				result.setMessage(OrderExceptionEnum.PAY__ORDER_BALANCE_IS_NOT_ENOUGH.getMsg());
//				return result;
//			}
//			// 验证支付密码是否正确
//			PhTradersPasswordForOrderDTO passwordForOrderDtoQuery = new PhTradersPasswordForOrderDTO();
//			passwordForOrderDtoQuery.setRelatedId(info.getId());
//			passwordForOrderDtoQuery.setCustomerType(2);
//			PhTradersPasswordForOrderVO passwordForOrderVo = this.iMerchantOrderService
//					.checkTradersPassword(passwordForOrderDtoQuery);
//			logger.info("输入的密码" + passWord);
//			logger.info("库里的密码" + passwordForOrderVo.getPayPwd());
//			logger.info("ps:" + passWord.equals(passwordForOrderVo.getPayPwd()));
//			if (passwordForOrderVo != null && passWord.equals(passwordForOrderVo.getPayPwd())) {
//				// 调用 余额支付接口
//				int msg = this.iUserBalance.userBalanceTrade(balance2.getManageId(), TransCodeEnum.AGENT_STOCK,
//						role.getRoleCode(), oder.getTotalCost().longValue(), agentOrder.getId(), null,null);
//				if (msg == 1) {
//					result.setSuccess(true);
//					return result;// 支付成功
//				}
//				if (msg == -1) {
//					result.setCode(OrderExceptionEnum.PAY__ORDER_FAIL.getCode());
//					result.setMessage(OrderExceptionEnum.PAY__ORDER_FAIL.getMsg());
//					return result;// 接口支付失败
//				}
//			} else {
//				result.setCode(OrderExceptionEnum.PAY__PWD_ERROR.getCode());
//				result.setMessage(OrderExceptionEnum.PAY__PWD_ERROR.getMsg());
//				return result; // 支付密码输入错误
//			}
//		
//		return result;
//	}
//
//	/***
//	 * 商户余额支付方法
//	 * 
//	 * @param orderId
//	 * @param passWord
//	 * @param request
//	 * @return Result
//	 * @author 杨颜光
//	 * @ 
//	 */
//	@RequestMapping("/merchatBalancePay")
//	@ResponseBody
//	public Result merchatBalancePay(Long orderId, String passWord, HttpServletRequest request)  {
//		SessionUserVo info = getLoginUser(request);
//		SessionRoleVo role = info.getSessionRoleVo().get(0);
//		Result result = new Result();
//			// 查询账户余额
//		UserBalance balance = new UserBalance();
//		balance.setManageId(info.getId());
//		result = this.iMerchantService.getUserBalance(balance);
//		UserBalance balance2 = new UserBalance();
//		if (result.getData() == null) {
//			balance2.setBalance(0L);
//		} else {
//			balance2 = (UserBalance) result.getData();
//		}
//		// 查询订单支付金额
//		PhMerchantOrder merchantOrder = new PhMerchantOrder();
//		merchantOrder.setId(orderId);
//		PhMerchantOrder oder = this.iMerchantOrderService.findMerchantOrderByTarget(merchantOrder);
//		// 如果订单金额大于余额提示余额不足
//		if (oder.getTotalCost().longValue() > balance2.getBalance()) {
//			result.setCode(OrderExceptionEnum.PAY__ORDER_BALANCE_IS_NOT_ENOUGH.getCode());
//			result.setMessage(OrderExceptionEnum.PAY__ORDER_BALANCE_IS_NOT_ENOUGH.getMsg());
//			return result;
//		}
//		// 验证支付密码是否正确
//		PhTradersPasswordForOrderDTO passwordForOrderDtoQuery = new PhTradersPasswordForOrderDTO();
//		passwordForOrderDtoQuery.setRelatedId(info.getId());
//		passwordForOrderDtoQuery.setCustomerType(2);
//		PhTradersPasswordForOrderVO passwordForOrderVo = this.iMerchantOrderService
//				.checkTradersPassword(passwordForOrderDtoQuery);
//
//		if (passwordForOrderVo != null && passWord.equals(passwordForOrderVo.getPayPwd())) {
//			// 调用 余额支付接口
//			int msg = this.iUserBalance.userBalanceTrade(balance2.getManageId(), TransCodeEnum.MERCHANT_STOCK,
//					role.getRoleCode(), oder.getTotalCost().longValue(), oder.getId(), null,null);
//			if (msg == 1) {
//				result.setSuccess(true);
//				return result;// 支付成功
//			}
//			if (msg == -1) {
//				result.setCode(OrderExceptionEnum.PAY__ORDER_FAIL.getCode());
//				result.setMessage(OrderExceptionEnum.PAY__ORDER_FAIL.getMsg());
//				return result;// 接口支付失败
//			}
//		} else {
//			result.setCode(OrderExceptionEnum.PAY__PWD_ERROR.getCode());
//			result.setMessage(OrderExceptionEnum.PAY__PWD_ERROR.getMsg());
//			return result; // 支付密码输入错误
//		}
//		return result;
//	}
//
//	/**
//	 * 代理商确认收货
//	 * 
//	 * @param orderId
//	 * @param request
//	 * @return Result
//	 * @author 杨颜光
//	 */
//	@RequestMapping("/agentConfirmReceipt")
//	@ResponseBody
//	public Result agentConfirmReceipt(Long orderId, HttpServletRequest request)  {
//		// 获取session，判断是商户取消订单还是代理商
//		Result result=new Result();
//		SessionUserVo info = getLoginUser(request);
//			PhAgentOrder order = new PhAgentOrder();
//			order.setId(orderId);
//			order.setCancelUserId(info.getId());
//			order.setUpdateTime(new Date());
//			order.setStatus(4); // 交易完成
//			result=this.iAgentOrderService.updateForShipping(order);
//		return result;
//	}
//
//	/**
//	 * 商户确认收货
//	 * 
//	 * @param orderId
//	 * @param request
//	 * @return 	Result
//	 * @author 杨颜光
//	 */
//	@RequestMapping("/merchantConfirmReceipt")
//	@ResponseBody
//	public Result merchantConfirmReceipt(Long orderId, HttpServletRequest request) {
//		// 获取session，判断是商户取消订单还是代理商
//		SessionUserVo info = getLoginUser(request);
//			PhMerchantOrder order = new PhMerchantOrder();
//			order.setId(orderId);
//			order.setCancelUserId(info.getId());
//			order.setUpdateTime(new Date());
//			order.setStatus(4); // 交易完成
//			Result result=this.iMerchantOrderService.updateForShipping(order);
//			return result;
//	}
//
//}
