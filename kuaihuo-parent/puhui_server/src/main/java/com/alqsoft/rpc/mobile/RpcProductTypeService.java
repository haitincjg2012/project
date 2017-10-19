package com.alqsoft.rpc.mobile;

import org.alqframework.result.Result;

import com.alqsoft.entity.producttype.ProductType;

/**
 * 
 * @Description: TODO
 * @author Wudi
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年3月8日 下午11:14:23
 * Copyright 
 */
public interface RpcProductTypeService {
	ProductType madeProductType(ProductType productType);
	
	int updateProductType(Long pId,String newContent);
	
	int deleteById(Long pId,Long hId);
	
	int updateProductTypeUp(Long cId,String split,Integer sortNum,Long hId);
	/***
	 * 创建
	 * @param hId
	 * @param listMap
	 * @return
	 */
	Result compileAndMadeProductType(Long hId,String listMap);
	/***
	 * 创建商品的一级和二级类名
	 * @param hId
	 * @param firstType
	 * @param secondeType
	 * @return
	 */
	 Result  madeProductType(Long hId,String firstType,String secondeType);

}
