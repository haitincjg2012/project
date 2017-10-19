package com.alqsoft.service.order;

import com.alqsoft.entity.member.Member;
import com.alqsoft.entity.order.Order;
import com.alqsoft.vo.OrderListVO;
import org.alqframework.result.Result;

import java.util.Map;

public interface OrderService {

	Result findOrderByWaitPay(Member member, Integer page, Integer rows);

	Result setRefundMsg(Long uid,String orderNo,String msg,String type);

	/**
	 * 判断会员是否已经在该批发商有存在的完成订单，用于app首页直接给批发商评价
	 * @param params
	 * @return
	 */
	int getMemberHaveOrderForHunterCommentCount(Map<String, Object> params);

	Result findOrderBySubscription(Member m, Integer page, Integer rows);

	Result findOrderBySubscriptionToBack(Member m, Long id, String refundMsg);

	Result findOrderBySubscriptionToPayMoney(Member m, Order order, Double negotiatePrice);

	Result findOrderByWaitForSend(Member m, Integer page, Integer rows);

	Result findOrderBySend(Member m, Integer page, Integer rows);

	Result getOrderByIdAndWait(Member m, Order order);

	Result toPay(Member m, String sIds, String nums);

	Result detail(Member member, Long oid,String type);

	Result toCreateOrder(Member m, OrderListVO list, Long aId);

	Result toPayOrderByWait(Member m, Order order, String ip);

	Result toPayOrderByNegotiate(Member m, Order order, String ip);

	Result toPayBack(Member m, Order order);
	/**
	 * 批发商个人中心--收入明细
	 * @param hunterId
	 * @param page
	 * @param rows
	 * @return
	 */
	Result findOrderByHunterId(Long hunterId, Integer page, Integer rows, Member member);
	/**
	 * 批发商个人中心--收入订单明细
	 * @param hunterId
	 * @param orderNum
	 * @return
	 */
	Result findOrderByorderNum(Long hunterId, String orderNum, Member member);

	Result confirmReceive(Long id, String orderNo);

	Result getOrderList(Member m, String orderStatus, Integer page, Integer rows);

	Result getHunterOrderList(Member m, String orderStatus, Integer page, Integer rows);

	Result confirmSend(Member member, String orderNo);

	Result viewRefundMsg(Long id, Long oid);
	/**
	 * 用户输入尾款价格
	 * @param member
	 * @param id
	 * @return
	 */
	public Result addNegotiatePriceForOrder(Member member, Long orderId, String negotiatePrice);
	/**
	 * 商家对尾款价格进行操作
	 * @param member
	 * @param orderId
	 * @param type
	 * @return
	 */
	public Result chooseNegotiatePriceForOrder(Member member, Long orderId, String type);

	Result getMemberOrderList(Member m, String orderStatus, Integer page, Integer rows);

	Result getPuHuiHunterOrderList(Member m, String orderStatus, Integer page, Integer rows);

	Result createOrder(Member member, String json);

	Result getAccountOrderList(Member member, String time,int page,int size);

	Result updateOrder(String time, String orderNo, Member member);

	Result deleteOrder(Long id, Member member);

	Result udpateOrderNum(Long id, Long num, Member member);

	Result backShopCart(String orderNo, Member member);

	Result recurOrder(String json, Member member);
	/**
	 * 买家（商户）取消订单
	 */
	Result cancelOrderForMerchant(String orderNo);
	/**
	 * 卖家（供应商）取消订单
	 */
	Result cancelOrderForSuppler(String orderNo,Integer status);

	/**
	 * 买家（商户）查询订单列表
	 */
	Result queryOnllieOrders(Map<String,Object> map);
	/**
	 * 查询订单详情
	 */
	Result queryOnlineOrderDetails(String orderNo,Integer status);
    /**
     * 供应商接单
     */
    Result agreeOrderBySupplier(String orderNo);

	Result recurOrder(String type,String json, Member member);
}
