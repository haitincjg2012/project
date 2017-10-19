package com.alqsoft.dao.productspecification;

import java.util.Collection;
import java.util.List;

import javax.persistence.LockModeType;

import org.alqframework.orm.hibernate.BaseDao;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alqsoft.entity.productspecification.ProductSpecification;

public interface ProductSpecificationDao extends BaseDao<ProductSpecification> {

	/**
	 * 商品批量分类---更新此商品规格关联的商品分类id
	 * @param productId
	 * @param productTypeId
	 */
	@Query(value="UPDATE alq_product_specification ps SET ps.`product_type_id`=:kindid WHERE ps.`product_id` IN(:productids) ",nativeQuery=true)
	@Modifying
	public void updateProductSpecification(@Param("productids")Collection<Long> productids,@Param("kindid")Long kindid);

	@Lock(value = LockModeType.PESSIMISTIC_WRITE)
	@Query("FROM ProductSpecification WHERE id=:id")
	public ProductSpecification getLockRow(@Param("id") Long id);
	
	
	/**
	 * 查询商品的所有商品规格，未删除
	 * @param productid
	 * @return
	 */
	@Query(value="FROM ProductSpecification ps WHERE ps.product.id=:productid and ps.isDelete=0")
	public  List<ProductSpecification> findProductSpecificationByProductId(@Param("productid")Long productid);
	
	/**
	 * 逻辑删除商品规格
	 * @param ids
	 */
	@Query(value="UPDATE alq_product_specification p SET p.`is_delete`=1 WHERE p.`id` IN(:ids)",nativeQuery=true)
	@Modifying
	public void deleteProductSpecificationByIds(@Param("ids")Collection<Long> ids);
	
}
