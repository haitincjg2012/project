package com.ph.shopping.facade.spm.service;

import java.util.List;

import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.spm.dto.SupplierImageDTO;
import com.ph.shopping.facade.spm.vo.SupplierImageVO;
/**
 * @项目  phshopping-service-spm
 * @描述   供应商图片业务层接口
 * @author 何文浪
 * @时间 2017-5-15
 * @version 2.1
 */
public interface ISupplierImageService {

	/**
	 * 查询供应商图片列表
	 * @param SupplierImageDTO
	 * @return List<SupplierImageVO>
	 * @author 何文浪
	 * @时间 2017-5-15
	 */
	List<SupplierImageVO> getSupplierImageList(SupplierImageDTO supplierImageDTO) throws Exception;

	/**
	 * 分页查询供应商图片列表
	 * @param SupplierImageDTO
	 * @return Result
	 * @author 何文浪
	 * @时间 2017-5-15
	 */
	Result getSupplierImagePage(PageBean pageBean, SupplierImageDTO supplierImageDTO) throws Exception;

	/**
	 * 查询供应商图片列表单条记录--传值为主键
	 * @param SupplierImageDTO:setId
	 * @return SupplierImageVO
	 * @author 何文浪
	 * @时间 2017-5-15
	 */
	SupplierImageVO getSupplierImageListById(SupplierImageDTO supplierImageDTO) throws Exception;

}