package com.ph.shopping.facade.mapper;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.spm.dto.SupplierDTO;
import com.ph.shopping.facade.spm.entity.Supplier;
import com.ph.shopping.facade.spm.vo.SupplierVO;

import java.util.List;
import java.util.Map;

/**
 * @项目  phshopping-service-spm
 * @描述   供应商持久层
 * @author 何文浪
 * @时间 2017-5-15
 * @version 2.1
 */
public interface SupplierMapper extends BaseMapper<Supplier>{
	/**
	 * 查询供应商列表详情
	 * @param SupplierDTO
	 * @return List<SupplierVO>
	 * @author 何文浪
	 * @时间 2017-5-15
	 */
	List<SupplierVO> getSupplierListDateil(SupplierDTO supplierDTO);


	List<SupplierVO> getHunterListDateil(SupplierDTO supplierDTO);
	/**
	 * 查询供应商列表
	 * @param SupplierDTO
	 * @return List<SupplierVO>
	 * @author 何文浪
	 * @时间 2017-5-15
	 */
	List<SupplierVO> getSupplierList(SupplierDTO supplierDTO);

	/**
	 * 查询供应商列表‘
	 * @author wudi
	 * @param supplierDTO
	 * @return
	 */
 List<Map> getSupplierListMap(SupplierDTO supplierDTO);
}