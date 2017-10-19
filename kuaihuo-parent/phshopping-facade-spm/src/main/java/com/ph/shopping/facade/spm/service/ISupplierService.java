package com.ph.shopping.facade.spm.service;

import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.spm.dto.SupplierDTO;
import com.ph.shopping.facade.spm.vo.SupplierVO;

import java.util.List;
/**
 * @项目  phshopping-service-spm
 * @描述   供应商业务层接口
 * @author 何文浪
 * @时间 2017-5-15
 * @version 2.1
 */
public interface ISupplierService {

	/**
	 * 查询供应商列表详情
	 * @param SupplierDTO
	 * @return List<SupplierVO>
	 * @author 何文浪
	 * @时间 2017-5-15
	 */
	List<SupplierVO> getSupplierListDateil(SupplierDTO supplierDTO) ;

	/**
	 * 查询供应商列表
	 * @param SupplierDTO
	 * @return List<SupplierVO>
	 * @author 何文浪
	 * @时间 2017-5-15
	 */
	List<SupplierVO> getSupplierList(SupplierDTO supplierDTO) ;

	/**
	 * 查询分页供应商列表
	 * @param SupplierDTO
	 * @return Result
	 * @author 何文浪
	 * @时间 2017-5-15
	 */
	Result getSupplierPage(PageBean pageBean, SupplierDTO supplierDTO) ;

	/**
	 * 查询供应商列表详情单条详情记录--传值为主键
	 * @param SupplierDTO：setId
	 * @return SupplierVO
	 * @author 何文浪
	 * @时间 2017-5-15
	 */
	SupplierVO getSupplierListDateilById(SupplierDTO supplierDTO) ;


	SupplierVO getHunterListDateilById(SupplierDTO supplierDTO) ;
	/**
	 * 查询供应商列表单条记录
	 * @param SupplierDTO:setId
	 * @return SupplierVO
	 * @author 何文浪
	 * @时间 2017-5-15
	 */
	SupplierVO getSupplierListById(SupplierDTO supplierDTO) ;
	/**
	 * 修改供应商
	 * @param SupplierDTO
	 * @return Result
	 * @author 何文浪
	 * @时间 2017-5-15
	 */
	Result updateSupplier(SupplierDTO supplierDTO);
	/**
	 * 修改供应商状态
	 * @param SupplierDTO
	 * @return Result
	 * @author 何文浪
	 * @时间 2017-5-15
	 */
	 Result updateSupplierByStatus(SupplierDTO supplierDTO);

	/**
	 * 增加供应商
	 *
	 * @param merchantDTO 增加dto
	 * @return Result
	 * @author 熊克文
	 * @时间 2017-5-26
	 */
	Result addSupplier(SupplierDTO supplierDTO);

}