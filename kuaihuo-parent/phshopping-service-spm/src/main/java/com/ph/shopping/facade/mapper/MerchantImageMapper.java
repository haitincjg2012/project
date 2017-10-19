package com.ph.shopping.facade.mapper;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.spm.dto.MerchantImageDTO;
import com.ph.shopping.facade.spm.entity.MerchantImage;
import com.ph.shopping.facade.spm.vo.MerchantImageVO;

import java.util.List;
/**
 * 
 * @项目 phshopping-service-spm
 * @描述 商户图片持久层
 * @author 何文浪
 * @时间 2017-5-12
 * @version 2.1
 * 
 */
public interface MerchantImageMapper extends BaseMapper<MerchantImage> {
	
	/**
	 * 查询商户图片
	 * @param merchantImageDTO
	 * @return List<MerchantImageVO>
	 * @author 何文浪
	 * @时间 2017-5-12
	 * 
	 */
	List<MerchantImageVO> getMerchantImageList(MerchantImageDTO merchantImageDTO);
}