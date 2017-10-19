package com.alqsoft.service.productspecification;

import java.util.Collection;
import java.util.List;

import org.alqframework.orm.BaseService;
import org.springframework.data.repository.query.Param;

import com.alqsoft.entity.order.Order;
import com.alqsoft.entity.productspecification.ProductSpecification;

public interface ProductSpecificationService extends BaseService<ProductSpecification> {

	public void updateNum(ProductSpecification ps, int count);
	
	/**
	 * 商品批量分类---更新此商品规格关联的商品分类id
	 * @param productId
	 * @param productTypeId
	 */
	public void updateProductSpecification(Collection<Long> productids,Long kindid);
	
	public ProductSpecification getLockRow(Long id);

	public void updateNumByBack(Order order);
	

	/**
	 * 查询商品的所有商品规格，未删除
	 * @param productid
	 * @return
	 */
	public  List<ProductSpecification> findProductSpecificationByProductId(Long productid);
	
	/**
	 * 逻辑删除商品规格
	 * @param ids
	 */
	public void deleteProductSpecificationByIds(Collection<Long> ids);

}
