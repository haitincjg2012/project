package com.alqsoft.service.order;

import com.alqsoft.dto.OrderDTO;
import com.alqsoft.entity.member.Member;
import com.alqsoft.entity.order.Order;
import com.alqsoft.vo.OrderListVO;
import org.alqframework.easyui.EasyuiResult;
import org.alqframework.orm.BaseService;
import org.alqframework.result.Result;

import java.util.List;
import java.util.Map;

public interface OrderService extends BaseService<Order>{

	/**
	 * 查询会员是否已经在该批发商有存在的完成订单，用于app首页直接给批发商评价
	 * @param hunterid
	 * @param memberid
	 * @return
	 */
	int getMemberHaveOrderForHunterCommentCount(Long hunterid, Long memberid);

	Result findOrderBySubscriptionToBack(Order order);

	Result findOrderBySubscriptionToPayMoney(Order order);

	Order getRowLock(Long id);

	Result toCreateOrder(Member m, OrderListVO list, Long aId);

	void updateOrderTypeByPay(List<String> orderIds);

	Order orderShare(Order order, Member memebr) throws Exception;

	//自动确认收货 task
	void autoConfirmReceive();
	//自动分润
	void orderFenRun();


	EasyuiResult getOrderList(Map<String, Object> map, int page, int rows);

	Result deleteBatch(String ids);

	Map<String,Object> detail(Long id);

	EasyuiResult getAccountList(Map<String, Object> map, int page, int rows);

	Result memberToPayBack(Order order);

	Result confirmReceive(Map<String,Object> map);

	Result updateOrderTypeByBack(Order o);

	void orderPuhuiFenRun();

	Result confirmSend(Map<String, Object> map);

	Result setRefundMsg(Map<String, Object> map);

	/*void updatePsCountByPay(List<Long> orderIds);*/
	/**
	 * 用户输入尾款价格
	 * @param ids
	 */
	Result addNegotiatePriceForOrder(Order order, String ids);
	/**
	 * 商家对尾款价格进行操作
	 */
	Result chooseNegotiatePriceForOrder(Order order, String type);

	/**
	 * 更新批发商信息
	 * @param ids
	 * @return
	 */
	Result updateHunterInfo(List<String> orderNos);

	Result createOrder(Member member, List<OrderDTO> orderDTOS);

	Result updateOrder(String time, String orderNo, Member member);

	Result deleteBySubOrderNo(Long id, Member member);

	Result updateOrderNum(Long id, Long num, Member member);

	Result recurOrder(String type,List<OrderDTO> orderDTOS, Member member);
	/**
	 * 根据主订单更新订单状态
	 */
	Result cancelOrderByOrderNo(String orderNo);
	/**
	 * 商户接单
	 */
	Integer agreeOrderBySupplyer(String orderNo);

	List<Order> getOrderListByOrderNum(String orderNo);

}
