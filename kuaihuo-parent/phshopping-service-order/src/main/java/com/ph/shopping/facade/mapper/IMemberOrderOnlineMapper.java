package com.ph.shopping.facade.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ph.shopping.facade.member.dto.DishDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.member.entity.Member;
import com.ph.shopping.facade.spm.entity.Merchant;

import cm.ph.shopping.facade.order.dto.AddMemberOrderOnlineDTO2;
import cm.ph.shopping.facade.order.dto.DishDTO;
import cm.ph.shopping.facade.order.dto.DishOrder;
import cm.ph.shopping.facade.order.dto.QueryOrderDTO;
import cm.ph.shopping.facade.order.dto.ShopCartDTO;
import cm.ph.shopping.facade.order.entity.PhMemberOrderOnline;
import cm.ph.shopping.facade.order.entity.ShopCart;
import cm.ph.shopping.facade.order.vo.DishDetailsVO;
import cm.ph.shopping.facade.order.vo.OrderOnlineVO;

/**
 * @author chen
 * 
 * @description: 线上订单
 *
 */
@Repository
public interface IMemberOrderOnlineMapper extends BaseMapper<PhMemberOrderOnline> {

	List<Long> getOrderByStatus();

	PhMemberOrderOnline getLock(Long id);
	/**
	 * 添加到订单表
	 * @param addDto
	 * @author lzh
	 * @return 
	 */
	void addOnLineOrder(AddMemberOrderOnlineDTO2 addDto);
	/**
	 * 获取当前订单id
	 * @return
	 * @author lzh
	 */
	Long getMaxId();
	/**
	 * 添加到中间表
	 * @param dish
	 * @author lzh
	 */
	void addOrderSku(List<DishOrder> dish);
	/**
	 * 获取菜品集合
	 * @param dish1
	 * @author lzh
	 */
	List<DishOrder> getDishs(String[] dish1);
	/**
	 * 获取时间
	 * @param string
	 * @return
	 */
	List<PhMemberOrderOnline> getTimeByid(Long string);
	/**
	 * <pre>findOrderNoStatus(作用：查询订单状态)   
	 * 创建人：赵俊彪   
	 * @param
	 * @param
	 * @return</pre>
	 */
	List<PhMemberOrderOnline> findOrderNoStatus(@Param("merchantId") Long merchantId,@Param("dishId")Long dishId);
	/**
	 * <pre>updateOrderNoStatus(作用：商户接单)   
	 * 创建人：赵俊彪   
	 * @param orderNo</pre>
	 */
	void updateOrderNoStatus(String orderNo);

	void updateOrderNoStatusQu(String orderNo);

	void deleteShopCart(Long memberId, Long merchantId);

	void deleteShopCartTwo(Long memberId, Long merchantId,Long orderId);
	/**
	 * 获取用户预定的包间接口
	 */
	List<Long> getOnlineOrderDish(AddMemberOrderOnlineDTO2 info);
	
	/*订单列表*/
	List<OrderOnlineVO> selectOrdersList(QueryOrderDTO queryOrderDTO, RowBounds rowBounds);
	
	/*订单对应的菜品 或 餐位*/
	List<DishDetailsVO> selectDishesOrSeats(@Param("orderId") Long orderId, @Param("type") int type);
	
	/*订单详情*/
	OrderOnlineVO selectOrderByOrderNo(@Param("orderNo") String orderNo);
	
	/*根据订单ID 查询订单*/
	PhMemberOrderOnline selectOrderByOrderId(@Param("orderId") Long orderId);
	
	/*更新订单评论状态*/
	Integer updateOrderCommentStatus(@Param("id") Long id, @Param("status") Integer status);

	AddMemberOrderOnlineDTO2 queryOrderDate(String orderNo);
	//获取购物车信息
	List<DishDTO> getShopCart(ShopCart shopCart);
	/**
	 * 获取购物车包间的时间
	 * @param dishId
	 * @return
	 */
	List<PhMemberOrderOnline> getDateByDishId(Long dishId);
	//获取购物车其他信息
	List<ShopCartDTO> getShopCartOtherMessage(ShopCart shopCart);
	/**
	 * 获取商家营业时间
	 * @param merchantId
	 * @return
	 */
	Merchant getMerchantTime(Long merchantId);
	/**
	 * 获取商户userId
	 */
	Long getUserIdByMerchantId(Long merchantId);
	/**
	 * 商户门店照片
	 * @param merchantId
	 * @return
	 */
	List<String> getMerchantImg(Long merchantId);
	/**
	 * 查询sku表中的销量
	 */
	List<AddMemberOrderOnlineDTO2> queryOrderDcount(String orderNo);
	/**
	 * 修改dish表中的销量   
	 * @param dishId 
	 */
	void updateDishSaleNum(@Param("saleNum")Long saleNum, @Param("dishId")Long dishId);
	/**
	 * 商户重新选择时间删除购物车信息
	 * @param memberId
	 * @param merchantId
	 * @return
	 */
	int deleteShopCartForType(Long memberId, Long merchantId);
	/**
	 * 获取菜品图片
	 * @param id
	 * @return
	 */
	List<String> getDishImg(Long id);
	/**
	 * 查询商户名
	 */
	AddMemberOrderOnlineDTO2 findMerchantName(@Param("merchantId")Long merchantId);
	/**
	 * 发送消息 需要的信息
	 * @param merchantId
	 * @return
	 */
	Merchant getMerchantForSms(Long merchantId);

	Member getMemberForSms(Long merchantId);
	/**
	 * 查询购买菜品的数量
	 * @param map
	 * @return
	 */
	Long getDcount(Map<String, Object> map);

	/**
	 * 会员取消接单
	 * @return int
	 */
	int cancelOrder(@Param("orderId") Long orderId);

	/**
	 * 根据会员id  获取会员手机号
	 * @param memberId
	 * @return
	 */
	String getMemberTel(@Param("memberId") Long memberId);

	/**
	 * 根据商户id 获取商户手机号
	 * @param merchantId
	 * @return
	 */
	String getMerchantTel(@Param("merchantId") Long merchantId);
	/**
	 * 获取商户ID和会员ID
	 * @param orderId
	 * @return
	 */
	AddMemberOrderOnlineDTO2 getMemberIdAndmerchantId(Long orderId);
	/**
	 * 获取购物车信息用来插入到购物车中
	 * @param orderId
	 * @return 
	 */
	List<ShopCart> getOrderAllByorderId(Long orderId);
	/**
	 * 插入到购物车中
	 * @param shopCart
	 */
	void addShopCartOnceMore(ShopCart shopCart);

	/**
	 * 将订单id加入到购物车中
	 * @param shopCart
	 */
	int addShopCartOnceMoreFor(ShopCart shopCart);

	void insertMessageRecord(Map map);

	/**
	 * 获取餐位保留时间
	 * @param dishId
	 * @return
	 */
	Long getHopeTimeByDishId(Long dishId);

	/*===========================================快火二期==========================================================*/

	/**
	 * 获取菜品信息
	 * @param dishId
	 * @return
	 */
	DishOrder getDishsTwo(Long dishId);
	/**
	 * 添加订单表
	 */
	int addOnLineOrderTwo(AddMemberOrderOnlineDTO2 addDto);

	/**
	 * 添加中间表
	 * @param dishOrder
	 * @return
	 */
	int addOrderSkuTwo(DishOrder dishOrder);
	/**
	 * 将购物车中的菜品加入到订单中间表中
	 */
	int addOrderSkuForShopCartTwo(List<ShopCartDTO> shopCart);
	/**
	 * 判断订单中是否有该菜品
	 */
	Long getDishMessageForOrder(Map dishMap);
	/**
	 * 修改订单中的菜品数量
	 */
	int updateOrderDish(DishOrder dishOrder);
	/**
	 * 修改订单表中的订单totalPrice
	 */
	void updateOrderTotalPrice( Map<String,Object> priceMap);
	/**
	 * 查询购物车中没有关联OrderId的菜品
	 */
	List<ShopCartDTO> getShopCartForNull(AddMemberOrderOnlineDTO2 addDto);
	/**
	 * 删除购物车中已下单的商品
	 */
	void deleteShopCartForNull(AddMemberOrderOnlineDTO2 addDto);
}
