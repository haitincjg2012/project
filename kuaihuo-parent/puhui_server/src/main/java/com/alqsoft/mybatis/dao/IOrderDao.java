package com.alqsoft.mybatis.dao;

import java.util.List;
import java.util.Map;

import com.alqsoft.entity.order.Order;
import com.alqsoft.entity.wxpayorder.WxPayOrder;
import org.alqframework.orm.mybatis.MyBatisRepository;
import org.apache.ibatis.annotations.Param;

@MyBatisRepository
public interface IOrderDao {

	public List<Map<String, Object>> getOrderFenByManager(Map<String, Object> map);

	public List<Map<String, Object>> getOrderFenByIndustryAssociation(Map<String, Object> map);

	List<Map<String,Object>> getOrderList(Map<String, Object> map);

	Map<String,Object> getOrderDetailById(Long id);

	List<Map<String,Object>> getAccountList(Map<String, Object> map);

	Long getOrderListTotal(Map<String, Object> map);

	Long getAccountListTotal(Map<String, Object> map);
	/**
	 * 更新订单状态
	 */
	int updateOrderStatusByOrderNo(@Param("orderNo") String orderNo,@Param("status") Integer status);
	/**
	 * 根据主订单 主订单状态查询交易流水表
	 */
	List<WxPayOrder> queryOrderTreadByOrderNo(@Param("orderNo") String orderNo, @Param("type") Integer type);
	List<WxPayOrder> getListByOrderNoAndStatus(@Param("orderNo") String orderNo,@Param("types") String types);
	List<String> getOrderListByOrderNo(@Param("orderNo") String orderNo);
	/**
	 * 供应商接单
	 */
	Integer agreeOrderBySupplyer(@Param("orderNo") String orderNo);
}
