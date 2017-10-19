package com.alqsoft.dao.productdetail;

import java.util.Collection;
import java.util.List;

import org.alqframework.orm.hibernate.BaseDao;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alqsoft.entity.productdetail.ProductDetail;

public interface ProductDetailDao extends BaseDao<ProductDetail>{

	/**
	 * 查询该商品的商品详情信息只查图片type为1类型的
	 * @param productid
	 * @return
	 */
	@Query(value="FROM ProductDetail ps WHERE ps.product.id=:productid and ps.type=1")
	public List<ProductDetail> findProductDetailByProductIdToPicture(@Param("productid")Long productid);
	
	
	/**
	 * 删除商品详情，商品编辑用
	 * @param id
	 */
	@Modifying
	@Query(value="DELETE FROM alq_product_detail WHERE product_id=:productids AND id NOT IN (:ids)",nativeQuery=true)
	public void deleteProductDetailByIds(@Param("ids")Collection<Long> ids,@Param("productids")Long productids);

}
