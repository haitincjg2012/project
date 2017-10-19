package com.ph.shopping.facade.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cm.ph.shopping.facade.order.entity.PhOrderOnline;
import cm.ph.shopping.facade.order.vo.ProductNumAndIdVO;
import cm.ph.shopping.facade.order.vo.OrderProVO;
import cm.ph.shopping.facade.order.vo.QueryOnLineOrderDetailVO;
import cm.ph.shopping.facade.order.vo.QueryOnLineOrderVO;
import cm.ph.shopping.facade.order.vo.QueryUserAddressVO;
import cm.ph.shopping.facade.order.vo.QueryWebOnLineOrderDetailVO;
import cm.ph.shopping.facade.order.vo.QueryWebOnLineOrderVO;

import com.ph.shopping.common.core.base.BaseMapper;

public interface IOrderOnlineMapper extends BaseMapper<PhOrderOnline> {
	/**
	 * 
	 * @Title: updateOnLineOrder
	 * @Description: 更新线上订单
	 * @author 王强
	 * @date 2017年4月26日 下午4:22:15
	 * @param orderNo
	 * @param status
	 * @return
	 */
	public int updateOnLineOrder(@Param("orderNo") String orderNo, @Param("status") int status);

	/**
	 * 
	 * @Title: updateUnLineOrder
	 * @Description: 更新线下订单状态
	 * @author 王强
	 * @date 2017年4月26日 下午4:22:27
	 * @param orderNo
	 * @param status
	 * @return
	 */
	public int updateUnLineOrder(@Param("orderNo") String orderNo, @Param("status") int status);

	/**
	 * 
	 * @Title: listOnlineOrders
	 * @Description: 查询后台订单列表
	 * @author 王强
	 * @date 2017年4月26日 下午4:22:36
	 * @param agentId
	 * @param supplierId
	 * @param orderNo
	 * @param roleCode
	 * @return
	 * @throws Exception
	 */
	public List<QueryOnLineOrderVO> listOnlineOrders(@Param("agentId") Long agentId,
			@Param("supplierId") Long supplierId, @Param("orderNo") String orderNo, @Param("roleCode") Integer roleCode)
					throws Exception;

	/**
	 * 
	 * @Title: listWebOnLineOrders
	 * @Description: 查询会员订单列表
	 * @author 王强
	 * @date 2017年4月26日 下午4:22:45
	 * @param memberId
	 * @param status
	 * @return
	 */
	public List<QueryWebOnLineOrderVO> listWebOnLineOrders(@Param("memberId") long memberId,
			@Param("status") int status);

	/**
	 * 
	 * @Title: getWebOnLineOrderDetail
	 * @Description: 会员订单详情
	 * @author 王强
	 * @date 2017年4月26日 下午4:22:51
	 * @param orderId
	 * @param deliveryType
	 * @return
	 * @throws Exception
	 */
	public QueryWebOnLineOrderDetailVO getWebOnLineOrderDetail(@Param("orderId") long orderId,
			@Param("deliveryType") int deliveryType) throws Exception;

	/**
	 * 
	 * @Title: getSupplyerName
	 * @Description:获取供应商名称
	 * @author 王强
	 * @date 2017年4月26日 下午4:22:59
	 * @param productId
	 * @return
	 */
	public String getSupplyerName(@Param("productId") long productId);

	/**
	 * 
	 * @Title: getOnLineOrderDetail
	 * @Description: 会员订单后台详情
	 * @author 王强
	 * @date 2017年4月26日 下午4:23:06
	 * @param orderId
	 * @return
	 */
	public QueryOnLineOrderDetailVO getOnLineOrderDetail(@Param("orderId") long orderId);

	/**
	 * 
	 * @Title: getSendAddress
	 * @Description: 查询发货地址
	 * @author 王强
	 * @date 2017年4月26日 下午4:23:12
	 * @param userId
	 * @return
	 */
	public List<QueryUserAddressVO> getSendAddress(@Param("userId") long userId);

	/**
	 * 
	 * @Title: updateOrderToSend
	 * @Description: 发货
	 * @author 王强
	 * @date 2017年4月26日 下午4:23:19
	 * @param status
	 * @param logisticName
	 * @param logisticNo
	 * @param orderId
	 * @return
	 */
	public int updateOrderToSend(@Param("status") int status, @Param("logisticName") String logisticName,
			@Param("logisticNo") String logisticNo, @Param("orderId") long orderId);

	/**
	 * 
	 * @Title: getProduct
	 * @Description:查询商品信息
	 * @author 王强
	 * @date 2017年4月26日 下午4:23:27
	 * @param productId
	 * @return
	 */
	public OrderProVO getProduct(@Param("productId") long productId);

	/**
	 * 
	 * @Title: getProductNum
	 * @Description: 获取商品数量和商品ID
	 * @author 王强
	 * @date 2017年4月26日 下午4:23:35
	 * @param orderNo
	 * @return
	 */
	public ProductNumAndIdVO getProductNum(@Param("orderNo") String orderNo);
}
