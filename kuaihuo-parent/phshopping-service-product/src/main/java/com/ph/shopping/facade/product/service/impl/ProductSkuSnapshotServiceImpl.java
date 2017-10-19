package com.ph.shopping.facade.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.ph.shopping.facade.mapper.ProductSkuSnapshotMapper;
import com.ph.shopping.facade.product.entity.ProductSkuSnapshot;
import com.ph.shopping.facade.product.service.IProductSkuSnapshotService;

/**
 * 
 * @项目：phshopping-service-product
 *
 * @描述： 商品sku快照service层
 *
 * @作者： 杨颜光
 *
 * @创建时间：2017年5月12日 下午3:53:42
 *
 * @Copyright by  杨颜光
 */
@Component
@Service(version = "1.0.0")
public class ProductSkuSnapshotServiceImpl implements IProductSkuSnapshotService {
    
	@Autowired
	private   ProductSkuSnapshotMapper  productSkuSnapshotMapper;
	
	@Override
	public  List<ProductSkuSnapshot>  getProductSkuSnapshotList(ProductSkuSnapshot skuSnapshot){
    	return  this.productSkuSnapshotMapper.select(skuSnapshot);
    }
    
	@Override
	public  ProductSkuSnapshot  getProductSkuSnapshot(ProductSkuSnapshot skuSnapshot){
    	return  this.productSkuSnapshotMapper.selectOne(skuSnapshot);
    }
}