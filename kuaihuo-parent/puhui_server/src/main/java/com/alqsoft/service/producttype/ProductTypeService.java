package com.alqsoft.service.producttype;

import org.alqframework.orm.BaseService;
import org.alqframework.result.Result;

import com.alqsoft.entity.producttype.ProductType;

/*
 * 商品类别
 */
public interface ProductTypeService extends BaseService<ProductType>{

	ProductType	madeProductTypeFirst(ProductType productType);
	
	int updateProductType(Long pId,String newContent);
	
	int deleteById(Long pId,Long hId);
	
	public ProductType getRowLock(Long id);
	
	int updateProductTypeUp(Long cId,String split,Integer sortNum,Long hId);
	
	/**
	 * 根据商品分类id查询该分类下的上架商品数量
	 * @return
	 */
	public int getProductCountByTypeId(Long kindid);
	
	public Result compileAndMadeProductType(Long hId,String listMap);
	
	public Result  madeProductType(Long hId,String firstType,String secondeType);
	
}
