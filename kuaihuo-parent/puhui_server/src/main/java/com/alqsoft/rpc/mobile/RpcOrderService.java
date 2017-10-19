package com.alqsoft.rpc.mobile;

import com.alqsoft.dto.OrderDTO;
import org.alqframework.orm.BaseService;
import org.alqframework.result.Result;

import com.alqsoft.entity.member.Member;
import com.alqsoft.entity.order.Order;
import com.alqsoft.vo.OrderListVO;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 *  订单管理
 * @author Xuejizheng
 * @date 2017-03-03 16:06
 */
public interface RpcOrderService extends BaseService<Order> {

	public Result findOrderBySubscriptionToBack(Order order);

	public Result findOrderBySubscriptionToPayMoney(Order order);

	public Result toCreateOrder(Member m, OrderListVO list, Long aId);

	public Result memberToPayBack(Order order);

	Result confirmReceive(Map<String, Object> map);

	Result confirmSend(Map<String, Object> map);

	Result setRefundMsg(Map<String, Object> map);

	/**
	 * 用户输入尾款价格
	 */
	public Result addNegotiatePriceForOrder(Order order, String ids);
	/**
	 * 商家对尾款价格进行操作
	 */
	public Result chooseNegotiatePriceForOrder(Order order, String type);
	/**
	 * 买家取消订单操作  wang
	 */
	Result cancelOrderByOrderNo(String orderNo);
	/**
	 * 供应商接单
	 */
	Integer agreeOrderBySupplyer(String orderNo);

	Result createOrder(Member member, List<OrderDTO> orderDTO);

	Result updateOrder(String time, String orderNo, Member member);

	Result deleteBySubOrderNo(Long id, Member member);

	Result updateOrderNum(Long id, Long num, Member member);

	Result recurOrder(String type,List<OrderDTO> orderDTO, Member member);

	//Result recurOrder(List<OrderDTO> orderDTO, Member member);

}
