package com.alqsoft.dao.productdetail;

import java.util.List;
import java.util.Map;

import com.alqsoft.vo.ProductDetailVO;
import org.alqframework.orm.mybatis.MyBatisRepository;
import org.apache.ibatis.annotations.Param;

import com.alqsoft.entity.product.Product;

/**
 * 
 * @Description: TODO
 * @author Wudi
 * @version v1.0
 * @copyright 2 010-2015
 * @create-time 2017年3月2日 上午9:35:15
 * 获取商品详情
 */

@MyBatisRepository
public interface ProductDetailDao {
	
	/**
	 * 获取详情商品文本
	 * */
	public List<Map> getProductDetailText(Long productId);
	
	/**
	 * 总的评价
	 * */
	
	public Long getCountComment();
	
	/**
	 *好的评价数
	 * */
	
	public Long getNiceCommentNum();
	
	/**
	 * 一般评价数
	 * */
	public Long getCommonCommentNum();
	
	/**
	 * 差的评价
	 * */
	public Long getBadCommentNum();
	
	/**
	 * 所有评价的内容
	 * 
	 * */
	
	public List<Map> getProductComment(Long productId);
	
	
	
	/**
	 * 获取详情商品图片
	 * */
    public List<Map> getProductDetailImage(Long productId);
    
    /**
     * 批发商的基本信息
     * */
    public Map getHunterBaseMessage(Long hId);
    
    /**
     * 商品编辑时回显得商品详情
     * @param productid
     * @return
     */
    public List<Map<String,Object>> getProductDetailByProductId(Long productid);

    /***
     * 获取销售商品的中种类 ，规格
     */
    public List<Map> saleProductType(Long pId);
    /***
     * 销售商品的分类
     * @param pId 商品Id
     * @return
     */
    public List<Map> saleProductStandard(Long pId);
    /***
     * 获取商品的销售的价格，图片，库存，起批量
     * @return
     */
    public List<Map> getProuctSaleMessage(Long pId);
    /***
     * 获取商品的收藏状态 
     * @param pId
     * @param mId
     * @return
     */
    public Long findCollectionType(@Param("pId")Long pId,@Param("mId") Long mId);
    
    /***
     * 获取商品的所有的评价
     * @param pId
     * @return
     */
    public Map getProductNumCommentAll(@Param("pId") Long pId);
    /***
     * 获取销售商品的轮播图片
     * @return
     */
    public List<Map> saleProductImage(@Param("pId") Long pId);


    List<ProductDetailVO> getProductDetailVoByProductId(Long productId);
}
