package com.alqsoft.dao.productattachment;

import java.util.Collection;
import java.util.List;

import org.alqframework.orm.hibernate.BaseDao;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alqsoft.entity.productattachment.ProductAttachment;

public interface ProductAttachmentDao extends BaseDao<ProductAttachment>{

	/**
	 * 商品轮播图片和商品id关联
	 * @param procutAttachmentids
	 * @param productid
	 */
	@Modifying
	@Query(value="UPDATE alq_product_attachment patt SET patt.`product_id`=:productid WHERE patt.`id` IN(:procutAttachmentids)",nativeQuery=true)
	public void updateProcutAttachmentWithProduct(@Param("procutAttachmentids")Collection<Long> procutAttachmentids,@Param("productid")Long productid);

	/**
	 * 查询商品的轮播图片
	 * @param productid
	 * @return
	 */
	@Query(value="FROM ProductAttachment patt where patt.product.id=:productid")
	public List<ProductAttachment> findProductAttachmentByProductId(@Param("productid")Long productid);
	
	/**
	 * 删除轮播图片 
	 * @param id
	 */
	@Query(value="DELETE FROM alq_product_attachment WHERE id in(:ids)",nativeQuery=true)
	@Modifying
	public void deleteProductAttachmentById(@Param("ids")Collection<Long> ids);

}
