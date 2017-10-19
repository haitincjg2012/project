package com.ph.shopping.facade.mapper;

import java.util.List;
import java.util.Map;
import com.ph.shopping.facade.member.dto.DishDto;
import com.ph.shopping.facade.member.dto.MerchantDishDTO;
import com.ph.shopping.facade.member.dto.ShopCart;
import com.ph.shopping.facade.member.entity.Merchant;
import com.ph.shopping.facade.member.entity.PhMemberOrderOnlineSku;

/**
 * 进入商户  信息mapper类
 * @author lzh
 *
 */
public interface MemberIntoMerchantMapper {

	Merchant getMerchantInfo(Long merchantId);
	/**
	 * 获取商户评价条数
	 */
	Long getMerchantCount(Long userId);
	/**
	 * 获取商家门店照片
	 */
	List<String> getMerchantImg(Long merchantId);
	/**
	 * 获取商家菜品类型
	 * @param type
	 * @param merchantId
	 * @return
	 */
	List<MerchantDishDTO> getDishType0(MerchantDishDTO dish);
	/**
	 * 获取商家菜品信息
	 * @param type
	 * @param type
	 * @return
	 */
	List<MerchantDishDTO> getDishType(int type);
	/**
	 * 获取菜品
	 * @param typeId
	 * @param merchantId 
	 * @return
	 */
	List<DishDto> getDishByTypeId(Map<String,Long> map);
	/**
	 * 获取当前餐位预计到达时间、预计离开
	 * @param id
	 * @return
	 */
	List<DishDto> getTimeByid(Long id);

	/**
	 * 获取包间的所有的时间  用来判断用户本身是否预定该餐位
	 * @param map
	 * @return
	 */
	List<DishDto> getTimeForThreeByid(Map<String,Long> map);
	/**
	 * 获取购物车信息
	 * @param memberId
	 * @return
	 */
	List<ShopCart> getShopCart(Long memberId);
	/**
	 * 查询菜品图片
	 * @param id
	 * @return
	 */
	List<String> getDishImg(Long id);
	/**
	 * 获取商户是否已经预定该餐位
	 */
	List getOrderByMember(Map<String,Long> map);
	/**
	 * 单独查出来打包
	 * @param typeId
	 * @return
	 */
	DishDto getDabao(Long typeId);
	/**
	 * 获取用户预定的包间接口
	 */
	List<DishDto> getOnlineOrderDish(MerchantDishDTO order);
	/**
	 * 获取该包间中的菜品数量
	 */
	Long getOnlineOrderDishCount(Long id);
	/**
	 * 获取该包间绑定的购物车菜品数量
	 */
	Long getShopCartDishCount(Long id);
	/**
	 *
	 */
	Long getShopCartTotalPrice(Long orderId);
	/**
	 *
	 */
	Long getOrderTotalPrice(Long orderId);
	/**
	 * 包间菜品回显  购物车中的和订单中的  查询购物车中的信息
	 */
	List<DishDto> getDishForShopCart(Long orderId);
	/**
	 * 包间菜品回显  购物车中的和订单中的 查询订单中的信息
	 */
	List<PhMemberOrderOnlineSku> getDishForOrder(Long orderId);
	/**
	 *	查询订单中的餐位信息
	 */
	DishDto getDishByOrderId(Long orderId);
	/**
	 * 获取菜品基本信息
	 */
	DishDto getDishInfoByDishId(Long dishId);

}
