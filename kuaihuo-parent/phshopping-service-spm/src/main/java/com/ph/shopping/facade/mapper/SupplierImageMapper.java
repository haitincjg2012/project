package com.ph.shopping.facade.mapper;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.spm.dto.SupplierImageDTO;
import com.ph.shopping.facade.spm.entity.SupplierImage;
import com.ph.shopping.facade.spm.vo.SupplierImageVO;

import java.util.List;

/**
 * @项目  phshopping-service-spm
 * @描述   供应商图片持久层
 * @author 何文浪
 * @时间 2017-5-15
 * @version 2.1
 */
public interface SupplierImageMapper extends BaseMapper<SupplierImage> {
	
	/**
	 * 查询供应商图片列表
	 * @param SupplierImageDTO
	 * @return List<SupplierImageVO>
	 * @author 何文浪
	 * @时间 2017-5-15
	 */
	List<SupplierImageVO> getSupplierImageList(SupplierImageDTO supplierImageDTO);
}