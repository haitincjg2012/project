//package com.ph.order.api.controller;
//
//import java.util.List;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.alibaba.dubbo.config.annotation.Reference;
//import com.ph.base.BaseController;
//import com.ph.shopping.common.util.core.RespCode;
//import com.ph.shopping.common.util.core.ResultUtil;
//import com.ph.shopping.common.util.page.PageBean;
//import com.ph.shopping.common.util.result.Result;
//import com.ph.shopping.common.util.transform.MoneyTransUtil;
//import com.ph.shopping.facade.permission.vo.SessionRoleVo;
//import com.ph.shopping.facade.permission.vo.SessionUserVo;
//
//import cm.ph.shopping.facade.order.entity.PhOrderAddress;
//import cm.ph.shopping.facade.order.exception.OrderException;
//import cm.ph.shopping.facade.order.exception.OrderExceptionEnum;
//import cm.ph.shopping.facade.order.request.MemberDTO;
//import cm.ph.shopping.facade.order.request.OnLineOrderDTO;
//import cm.ph.shopping.facade.order.request.OnLineOrderSendDTO;
//import cm.ph.shopping.facade.order.request.OrderAddressDefDTO;
//import cm.ph.shopping.facade.order.request.PhOrderOnlineDTO;
//import cm.ph.shopping.facade.order.request.SupplyerNameRequest;
//import cm.ph.shopping.facade.order.request.UpdateOrderDTO;
//import cm.ph.shopping.facade.order.request.UserDTO;
//import cm.ph.shopping.facade.order.request.WebOnLineDTO;
//import cm.ph.shopping.facade.order.request.WebOnLineOrderDTO;
//import cm.ph.shopping.facade.order.service.IOrderService;
//import cm.ph.shopping.facade.order.vo.QueryOnLineOrderDetailVO;
//
//@Controller
//@RequestMapping("web/order")
//public class OrderController extends BaseController {
//
//	@Reference(version = "1.0.0")
//	private IOrderService iOrderService;
//
//	/**
//	 * @Title: addOnLineOrder
//	 * @Description: 生成线上订单
//	 * @author 王强
//	 * @date 2017年3月15日 上午9:26:07
//	 * @param orderOnline
//	 * @return
//	 */
//	@RequestMapping("addonlineorder")
//	@ResponseBody
//	public Result addOnLineOrder(PhOrderOnlineDTO request) {
//		return iOrderService.insertPhOrderOnline(request);
//	}
//
//	/**
//	 * 
//	 * @Title: addOrderAddress
//	 * @Description:新增收货地址
//	 * @author 王强
//	 * @date 2017年3月18日 下午2:12:04
//	 * @param orderAddress
//	 * @return
//	 */
//	@RequestMapping("addorderaddress")
//	@ResponseBody
//	public Result addOrderAddress(PhOrderAddress orderAddress) {
//		return iOrderService.insertOrderAddress(orderAddress);
//	}
//
//	/**
//	 * 
//	 * @Title: delOrderAddress
//	 * @Description: 删除收货地址
//	 * @author 王强
//	 * @date 2017年3月18日 下午2:16:20
//	 * @return
//	 */
//	@RequestMapping("delorderaddress")
//	@ResponseBody
//	public Result delOrderAddress(long addrId) {
//		return iOrderService.deleteOrderAddress(addrId);
//	}
//
//	/**
//	 * 
//	 * @Title: updOrderAddress
//	 * @Description: 更新收货地址
//	 * @author 王强
//	 * @date 2017年3月18日 下午2:20:13
//	 * @param orderAddress
//	 * @return
//	 */
//	@RequestMapping("updorderaddress")
//	@ResponseBody
//	public Result updOrderAddress(PhOrderAddress orderAddress) {
//		return iOrderService.updateOrderAddress(orderAddress);
//	}
//
//	/**
//	 * 
//	 * @Title: updOrderAddressDef
//	 * @Description: 设置默认地址
//	 * @author 王强
//	 * @date 2017年3月18日 下午2:24:52
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping("updorderaddressdef")
//	@ResponseBody
//	public Result updOrderAddressDef(OrderAddressDefDTO request) {
//		return iOrderService.updateOrderAddrDef(request.getMemberId(), request.getAddrId());
//	}
//
//	/**
//	 * 
//	 * @Title: getOrderAddressList
//	 * @Description: 收货地址列表
//	 * @author 王强
//	 * @date 2017年3月18日 下午2:26:09
//	 * @param memberId
//	 * @return
//	 */
//	@RequestMapping("getorderaddresslist")
//	@ResponseBody
//	public Result getOrderAddressList(MemberDTO request) {
//		return iOrderService.queryOrderAddress(request.getMemberId());
//	}
//
//	/**
//	 * 
//	 * @Title: updateOnLineOrder
//	 * @Description: 更新订单状态
//	 * @author 王强
//	 * @date 2017年3月15日 上午9:35:42
//	 * @param orderId
//	 * @return
//	 */
//	@RequestMapping("updateonlineorder")
//	@ResponseBody
//	public Result updateOnLineOrder(UpdateOrderDTO request) {
//		if (iOrderService.updatePhOrderOnline(request.getOrderNo(), request.getStatus()) != 1) {
//			throw new OrderException(OrderExceptionEnum.UPDATE_ORDER_EXCEPTION);
//		}
//		return ResultUtil.getResult(RespCode.Code.SUCCESS);
//	}
//
//	/**
//	 * 
//	 * @Title: toOrderList
//	 * @Description: 跳转到订单列表界面
//	 * @author 王强
//	 * @date 2017年3月21日 上午10:34:38
//	 * @return
//	 */
//	@RequestMapping(value = "/tolistorders", method = RequestMethod.GET)
//	public String toOrderList() {
//		return "order/orderlist";
//	}
//
//	/**
//	 * 
//	 * @Title: selectOnLineOrder
//	 * @Description: 查询后台订单
//	 * @author 王强
//	 * @date 2017年3月15日 下午1:53:36
//	 * @return
//	 */
//	@RequestMapping("/listonlineorder")
//	@ResponseBody
//	public Result listOnLineOrder(UserDTO request, PageBean pageBean) {
//		SessionUserVo userVo = getLoginUser();
//		List<SessionRoleVo> roles = userVo.getSessionRoleVo();
//		for (SessionRoleVo roleVo : roles) {
//			// 管理员
//			if (roleVo.getRoleCode() == 1) {
//				request.setRoleCode((int) roleVo.getRoleCode());
//				break;
//			}
//
//			if (roleVo.getRoleCode() == 2) {// 供应商呢
//				request.setRoleCode((int) roleVo.getRoleCode());
//				request.setSupplierId(userVo.getId());
//			}
//
//			// 代理商
//			if (roleVo.getRoleCode() == 3 || roleVo.getRoleCode() == 4 || roleVo.getRoleCode() == 5) {
//				request.setRoleCode((int) roleVo.getRoleCode());
//				request.setSupplierId(userVo.getId());
//			}
//		}
//
//		return iOrderService.findOnlineOrders(request, pageBean);
//	}
//
//	/**
//	 * 
//	 * @Title: getOnLineOrderDetail
//	 * @Description: 获取订单详情
//	 * @author 王强
//	 * @date 2017年3月31日 上午10:17:47
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping("getorderdetail")
//	public ModelAndView getOnLineOrderDetail(OnLineOrderDTO request) {
//
//		ModelAndView mv = new ModelAndView("/order/orderDetail");
//		QueryOnLineOrderDetailVO detailVo = iOrderService.getOrderDetail(request.getId());
//		detailVo.setPrice(MoneyTransUtil.transDivisDouble(detailVo.getPrice1()));
//		detailVo.setTotalMoney(MoneyTransUtil.transDivisDouble(detailVo.getPrice1()) * detailVo.getNum());
//		detailVo.setPostage(MoneyTransUtil.transDivisDouble(detailVo.getPostage1()));
//		detailVo.setPayMoney(MoneyTransUtil.transDivisDouble(detailVo.getPayMoney1()));
//
//		mv.addObject("orderDetail", detailVo);
//		return mv;
//	}
//
//	/**
//	 * 
//	 * @Title: listWebOnLineOrders
//	 * @Description: 查询会员订单
//	 * @author 王强
//	 * @date 2017年3月28日 下午4:39:03
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping("listwebonlineorders")
//	@ResponseBody
//	public Result listWebOnLineOrders(WebOnLineOrderDTO request) {
//		return iOrderService.queryWebOnLineOrders(request.getMemberId(), request.getStatus(), request.getPageNum(),request.getPageSize());
//	}
//
//	/**
//	 * 
//	 * @Title: getWebOnLineOrderDetail
//	 * @Description: 获取会员订单详情
//	 * @author 王强
//	 * @date 2017年3月28日 下午6:42:40
//	 * @param orderId
//	 * @return
//	 */
//	@RequestMapping("getwebonlineorderdetail")
//	@ResponseBody
//	public Result getWebOnLineOrderDetail(WebOnLineDTO request) throws Exception {
//		return iOrderService.getWebOnLineOrderDetail(request.getOrderId(), request.getDeliveryType());
//	}
//
//	/**
//	 * 
//	 * @Title: getSupplyerName
//	 * @Description: 获取供应商名称
//	 * @author 王强
//	 * @date 2017年3月29日 下午2:36:38
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping("getsupplyername")
//	@ResponseBody
//	public Result getSupplyerName(SupplyerNameRequest request) {
//		return iOrderService.querySupplyerName(request.getProductId());
//	}
//
//	/**
//	 * 
//	 * @Title: getUserAddress
//	 * @Description: 获取发货地址
//	 * @author 王强
//	 * @date 2017年3月31日 下午4:42:22
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping("getuseraddress")
//	@ResponseBody
//	public Result getUserAddress() {
//		SessionUserVo userVo = getLoginUser();
//		return iOrderService.getUserAddress(userVo.getId());
//	}
//
//	/**
//	 * 
//	 * @Title: updateOrderSend
//	 * @Description: 会员订单发货
//	 * @author 王强
//	 * @date 2017年3月31日 下午5:44:17
//	 * @return
//	 */
//	@RequestMapping("tosend")
//	@ResponseBody
//	public Result updateOrderSend(OnLineOrderSendDTO request) {
//		return iOrderService.updateOrderToSend(request.getStatus(), request.getLogisticName(), request.getLogisticNo(),
//				request.getOrderId());
//	}
//	/**
//	 * 
//	* @Title: validateOrderPay
//	* @Description: 校验订单是否超过规定时间内支付
//	* @author 王强
//	* @date  2017年4月27日 下午4:29:48
//	* @param orderNo
//	* @return
//	 */
//	public boolean validateOrderPay(String orderNo) {
//		return iOrderService.validateOrderIsPay(orderNo);
//	}
//}
