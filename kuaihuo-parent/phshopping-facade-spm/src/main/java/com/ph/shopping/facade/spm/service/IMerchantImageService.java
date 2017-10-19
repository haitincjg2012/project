package com.ph.shopping.facade.spm.service;

import java.util.List;

import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.spm.dto.MerchantImageDTO;
import com.ph.shopping.facade.spm.vo.MerchantImageVO;
/**
 * @项目  phshopping-facade-spm
 * @描述   商户图片业务层接口
 * @author 何文浪
 * @时间 2017-5-12
 * @version 2.1
 */
public interface IMerchantImageService {
	/**
	 * 查询商户图片
	 * @param merchantImageDTO
	 * @return Result
	 * @author 何文浪
	 * @时间 2017-5-12
	 * 
	 */
	Result getMerchantImagePage(PageBean pageBean,MerchantImageDTO merchantImageDTO) ;
	
	/**
	 * 查询商户图片
	 * @param merchantImageDTO
	 * @return List<MerchantImageVO>
	 * @author 何文浪
	 * @时间 2017-5-12
	 * 
	 */
	List<MerchantImageVO> getMerchantImageList(MerchantImageDTO merchantImageDTO) ;
}
