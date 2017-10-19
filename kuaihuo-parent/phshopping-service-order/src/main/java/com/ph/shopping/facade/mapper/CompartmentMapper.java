package com.ph.shopping.facade.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import cm.ph.shopping.facade.order.dto.AddRestaurantOrSeatDTO;
import cm.ph.shopping.facade.order.dto.DishDTO;
import cm.ph.shopping.facade.order.entity.Dish;

@Repository
public interface CompartmentMapper {
	
	/**
	 * <pre>saveRestaurant(作用：添加餐位)   
	 * @param insertList 
	 */
	public Long saveRestaurant(AddRestaurantOrSeatDTO addRestaurantOrSeatDTO);
	
	public Integer saveRestaurantType(AddRestaurantOrSeatDTO addRestaurantOrSeatDTO);

	public Integer delsRestaurant(String dishId);
	
	public void updateRestaurant(AddRestaurantOrSeatDTO addRestaurantOrSeatDTO);
	
	public Dish findId(Long id);
	
	public List<AddRestaurantOrSeatDTO> findDishDeleteId(String ids);

	public AddRestaurantOrSeatDTO addImg(String imgAddress);
	
	public List<Dish>  findRestaurantListDel(Long id);
	
	public List<DishDTO> findRestaurantList(DishDTO dishDto,RowBounds rowBounds);


	public Long insertList(@Param("trim")String trim, @Param("id")Long id);

	public List findType(@Param("merchantId")Long merchantId);

	public List<Map<String,Object>> findTypeDishName(@Param("merchantId")Long merchantId, @Param("dishTypeId")Long dishTypeId);

	public List findType2();

	
}
