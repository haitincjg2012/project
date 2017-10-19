package com.alqsoft.service.hunterproductdetail;

import java.util.List;
import java.util.Map;

import com.alqsoft.vo.ProductDetailVO;
import org.alqframework.result.Result;

import org.springframework.ui.Model;

/**
 * 
 * @Description: TODO
 * @author Wudi
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年3月2日 上午9:24:27
 *商品详情
 */
public interface HunterProductDetailService {

	/**
	 * 商品详情文本
	 * */
	public Result getProductDetailText(Long productId,Long mId);
	
	/**
	 * 商品的评论
	 * */
	/*public Result getProductComment(Integer productId);*/
	
	/**
	 * 商品详情图片
	 * */
	public Result getProductDetailImage(Long productId);
	
	/**
	 * 批发商的基本信息，默认通过手机号
	 * */
	public Result getHunterBaseMessage(Long hId);
	
	  /**
     * 商品编辑时回显得商品详情
     * @param productid
     * @return
     */
    public List<Map<String,Object>> getProductDetailByProductId(Long productid);
     /***
      * 获取商品的规格
      */
    public  Result saleProductType(Long hId);
     /***
      * 文本详情链接
      * @param pId
      * @param model
      */
    public void textDetailsUrl(Long pId,Model model);

    List<ProductDetailVO> getProductDetailVoByProductId(Long id);
}
