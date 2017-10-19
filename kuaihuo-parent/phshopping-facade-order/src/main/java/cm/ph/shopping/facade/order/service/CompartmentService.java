package cm.ph.shopping.facade.order.service;


import java.util.List;

import com.ph.shopping.common.util.result.Result;

import cm.ph.shopping.facade.order.dto.AddRestaurantOrSeatDTO;
import cm.ph.shopping.facade.order.dto.DishDTO;
import cm.ph.shopping.facade.order.entity.Dish;

public interface CompartmentService {
	
	
	
	Result saveRestaurant(AddRestaurantOrSeatDTO addRestaurantOrSeatDTO);
	
	Result  delsRestaurant(String ids);
	
	Result updateRestaurant(AddRestaurantOrSeatDTO addRestaurantOrSeatDTO);
	
	Result findId(Long id);

	Result findDelId(String ids);

	Result findRestaurantList(DishDTO dishDto);

	Result findType(Long merchantId);

	Result findTypeDishName(Long merchantId,Long dishTypeId);

	Result findType2();
}
