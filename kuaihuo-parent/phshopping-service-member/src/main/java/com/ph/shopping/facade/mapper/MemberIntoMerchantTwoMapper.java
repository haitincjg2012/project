package com.ph.shopping.facade.mapper;

import com.ph.shopping.facade.member.dto.DishDto;
import com.ph.shopping.facade.member.dto.MerchantDishDTO;
import com.ph.shopping.facade.member.dto.ShopCart;
import com.ph.shopping.facade.member.entity.Merchant;
import com.ph.shopping.facade.member.vo.MerchantTimeSectionVO;

import java.util.List;
import java.util.Map;

/**
 * 进入商户  信息mapper类
 * @author lzh
 *
 */
public interface MemberIntoMerchantTwoMapper {

	/**
	 * 获取商家消费时间端
	 * @param merchantId
	 * @return
	 */
	List<MerchantTimeSectionVO> getMerchantTimeTwo(Long merchantId);

}
