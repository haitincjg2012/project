package com.alqsoft.rpc.mobile;

import org.alqframework.result.Result;

import com.alqsoft.entity.producttype.ProductType;

/**
 * 
 * @Description: TODO
 * @author Wudi
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年3月8日 下午10:42:15
 * Copyright 
 */
public interface RpcProductTypeService {
	
	ProductType madeProductType(ProductType productType);
	
	int updateProductType(Long pId,String newContent);
	
	int deleteById(Long pId,Long hId);
	
	int updateProductTypeUp(Long id,String split,Integer num,Long hId);
	
   Result compileAndMadeProductType(Long hId,String listMap);
   
    Result  madeProductType(Long hId,String firstType,String secondeType);
	
	

}
