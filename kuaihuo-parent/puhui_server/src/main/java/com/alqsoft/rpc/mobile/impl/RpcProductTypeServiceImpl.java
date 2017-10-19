package com.alqsoft.rpc.mobile.impl;

import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.entity.producttype.ProductType;
import com.alqsoft.rpc.mobile.RpcProductTypeService;
import com.alqsoft.service.producttype.ProductTypeService;

/**
 * 
 * @Description: TODO
 * @author Wudi
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年3月8日 下午11:17:37
 * Copyright 
 */
@Transactional(readOnly=true)
@Service
public class RpcProductTypeServiceImpl implements RpcProductTypeService{
    @Autowired
	private ProductTypeService productTypeService;
	@Override
	@Transactional
	public ProductType madeProductType(ProductType productType) {
		// TODO Auto-generated method stub
		return productTypeService.madeProductTypeFirst(productType);
	}
	@Override
	@Transactional
	public int updateProductType(Long pId, String newContent) {
		// TODO Auto-generated method stub
		return productTypeService.updateProductType(pId, newContent);
	}
	@Override
	@Transactional
	public int deleteById(Long pId,Long hId) {
		// TODO Auto-generated method stub
		return  productTypeService.deleteById(pId,hId);
	}
	@Override
	@Transactional
	public int updateProductTypeUp(Long cId,String split, Integer sortNum,Long hId) {
		// TODO Auto-generated method stub
		return productTypeService.updateProductTypeUp(cId,split,sortNum,hId);
	}
	@Transactional
	public Result compileAndMadeProductType(Long hId,String listMap){
		return productTypeService.compileAndMadeProductType(hId,listMap);
	}
	@Override
	@Transactional
	public Result madeProductType(Long hId, String firstType, String secondeType) {
		// TODO Auto-generated method stub
		return productTypeService.madeProductType(hId,firstType,secondeType);
	};
   
}
