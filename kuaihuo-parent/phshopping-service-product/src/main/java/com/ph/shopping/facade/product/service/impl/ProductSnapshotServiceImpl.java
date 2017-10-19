package com.ph.shopping.facade.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.ph.shopping.facade.mapper.ProductSnapshotMapper;
import com.ph.shopping.facade.product.dto.ProductSnapshotDTO;
import com.ph.shopping.facade.product.entity.ProductSnapshot;
import com.ph.shopping.facade.product.service.IProductSnapshotService;
import com.ph.shopping.facade.product.vo.ProductSnapshotVO;

/**
 * 
 * @项目：phshopping-facade-
 *
 * @描述：商品快照service
 *
 * @作者： 杨颜光
 *
 * @创建时间：2017年5月16日 上午11:09:33
 *
 * @Copyright by  杨颜光
 */

@Component
@Service(version = "1.0.0")
public class ProductSnapshotServiceImpl implements IProductSnapshotService  {
	
	@Autowired
	private   ProductSnapshotMapper  productSnapshotMapper;
	
	@Override
	public  List<ProductSnapshot>  getProductSnapshotList(ProductSnapshot productSnapshot){
    	return  this.productSnapshotMapper.select(productSnapshot);
    }
    
	@Override
	public  ProductSnapshot  getProducSnapshot(ProductSnapshot productSnapshot){
    	return  this.productSnapshotMapper.selectOne(productSnapshot);
    }
	
	@Override
	 public  ProductSnapshotVO  selectProductSnapshotByproductId(ProductSnapshotDTO dtos){
			List<ProductSnapshotVO> list=this.productSnapshotMapper.selectProductSnapshotByproductId(dtos);
		return list.get(0);
	}
	
}