package com.alqsoft.dao.productattachment;

import java.util.List;
import java.util.Map;

import com.alqsoft.vo.ProductPictureVo;
import org.alqframework.orm.mybatis.MyBatisRepository;

import com.alqsoft.entity.productattachment.ProductAttachment;

/**
 * 商品轮播图片
 * @author Administrator
 *
 */
@MyBatisRepository
public interface ProductAttachmentDao {
	
	/**
	 * 根据id查询商品图片
	 * @param pattid
	 * @return
	 */
	public ProductAttachment getProductAttachmentById(Long pattid);
	
	/**
	 * 根据商品id查询轮播图
	 * @param productid
	 * @return
	 */
	public List<Map<String,Object>> getProductAttachmentByProductId(Long productid);

    List<ProductPictureVo> getProductPictureVOByProductId(Long productId);
}
