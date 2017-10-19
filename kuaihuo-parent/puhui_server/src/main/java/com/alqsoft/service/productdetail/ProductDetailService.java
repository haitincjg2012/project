package com.alqsoft.service.productdetail;

import java.util.Collection;
import java.util.List;

import org.alqframework.orm.BaseService;
import org.springframework.data.repository.query.Param;

import com.alqsoft.entity.productdetail.ProductDetail;

public interface ProductDetailService extends BaseService<ProductDetail>{

	/**
	 * 查询该商品的商品详情信息只查图片type为1类型的
	 * @param productid
	 * @return
	 */
	public List<ProductDetail> findProductDetailByProductIdToPicture(Long productid);
	
	
	/**
	 * 删除商品详情，商品编辑用
	 * @param id
	 */
	public void deleteProductDetailByIds(Collection<Long> ids,Long productids);
}
