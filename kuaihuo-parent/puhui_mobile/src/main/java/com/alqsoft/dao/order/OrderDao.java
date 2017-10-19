package com.alqsoft.dao.order;

import com.alqsoft.entity.order.Order;
import com.alqsoft.vo.OrderVO;
import org.alqframework.orm.mybatis.MyBatisRepository;
import org.alqframework.result.Result;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@MyBatisRepository
public interface OrderDao {

	/**
	 * 判断会员是否已经在该批发商有存在的完成订单，用于app首页直接给批发商评价
	 * @param params
	 * @return
	 */
	int getMemberHaveOrderForHunterCommentCount(Map<String, Object> params);

	OrderVO getOrder(Map<String,Object> map);

	List<Map<String, Object>> findOrderByWaitPay(Map<String, Object> params);

	int findOrderByWaitPayCount(Long id);

	List<Map<String, Object>> findOrderBySubscription(Map<String, Object> params);

	int findOrderBySubscriptionCount(Long id);

	Order getOrderByOrderIdAndMid(HashMap<String, Object> params);

	List<Map<String, Object>> findOrderByWaitForSend(Map<String, Object> params);

	int findOrderByWaitForSendCount(Long id);

	List<Map<String, Object>> findOrderBySend(Map<String, Object> params);

	int findOrderBySendCount(Long id);

	Map<String, Object> getOrderByIdAndWait(Long id);

	Order getOrderById(Long id);
	/**
	 * 批发商个人中心--收入明细
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> findOrderByHunterId(HashMap<String, Object> param);
	/**
	 * 批发商个人中心--收入订单明细
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> findOrderByorderNum(HashMap<String, Object> param);

	List<Map<String, Object>> getOrderListByStatus(Map<String, Object> params);

	int getOrderListByStatusCount(Map<String, Object> params);

	List<Map<String, Object>> getHunterOrderListByStatus(Map<String, Object> params);

	List<Map<String, Object>> getHunterOrderListByStatusCount(@Param("id") Long id);


	List<Map<String,Object>> getOrderByMemberAndOrderNo (Map<String,Object> map );

	List<Map<String,Object>> getAllMemberByMap(Map<String, Object> memberParam);

	List<Map<String,Object>> getOrderNoByMember(Map<String,Object> map);

	/**
	 * 根据订单号查询订单列表
	 */
	List<Map<String, Object>> getOrderListByOrderNo(Map<String, Object> params);


	List<Map<String, Object>> getHunterOrderCountByStatusCount(Long id);

	Order getOrderByOrderId(HashMap<String, Object> params);

	List<Map<String,Object>> getOrderListByCondition(Map<String, Object> query);

	List<Map<String,Object>> getOrderListByOrderNoForShopCart(@Param("orderNo") String orderNo, @Param("id") Long id);

	List<String> getDistinctOrderNos(Map<String, Object> query);
	/**
	 * 根据主订单号  查询当前订单所有的订单状态
	 */
	List<Integer> getOrderStatusByOrdeNo(@Param("orderNo") String orderNo);
	/**
	 * 买家（商户）查询主订单
	 */
	public List<Map<String,Object>> queryParentOrders(Map<String, Object> params);
	/**
	 * 查询订单总金额
	 */
	public Long countOrderMoney(@Param("orderNo") String orderNo);
	/**
	 * 根据主订单查询子订单
	 */
	public List<Map<String,Object>> querySonOrders(@Param("orderNo") String orderNo,@Param("status") String status);
	/**
	 * 查询订单详情  主订单
	 */
	public Map<String,Object> queryOrderDetailByOrderNo(@Param("orderNo") String orderNo);
	/**
	 * 查询订单详情  子订单（提取商品信息）
	 */
	public List<Map<String , Object>> queryOrderForProducts(@Param("orderNo") String orderNo);

}
