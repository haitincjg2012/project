package com.alqsoft.service.product;

import java.util.List;
import java.util.Map;

import org.alqframework.result.Result;
import org.springframework.web.multipart.MultipartFile;

import com.alqsoft.entity.attachment.Attachment;
import com.alqsoft.entity.product.Product;
import com.alqsoft.entity.productspecification.ProductSpecification;
import com.alqsoft.vo.ProductVo;

public interface ProductService {

	/**
	 * 商品出售中列表销售中或下架的   默认列表显示的是每个商品规格时间最早的  status : 0  下架 1出售中
	 * @param params
	 * @return
	 */
	public Result findProductSaleOrCancelList(Long hunterid,Integer status,Integer type,Integer page,Integer rows);
	

	/**
	 * 商品出售中列表销售中或下架的 默认列表显示的是每个商品规格时间最早的 status : 0 下架 1出售中
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> findProductSaleOrCancelList(Map<String,Object> params);
	
	/**
	 * 批量商品的上架和下架，多个商品id以逗号分隔符字符串拼接
	 * @param hunterid
	 * @param productid
	 * @return
	 */
	public Result updateBatchProductSaleOrCancel(Long hunterid,String productid,Integer type);
	
	/**
	 * 批量商品的分类
	 * @param hunterid
	 * @param productid
	 * @param kindid
	 * @return
	 */
	public Result updateBatchProductKind(Long hunterid,String productid,Long kindid);
	
	/**
	 * 根据商品id查询
	 * @param id
	 * @return
	 */
	public Product getProductById(Long id);
	
	/**
	 * 上传商品轮播图片
	 * @param urlfile
	 * @param backUrl
	 * @param module
	 * @param extendFile
	 * @return
	 */
	public Result mobileUploadProductAttachment(MultipartFile urlfile, Object[]backUrl,String module,String[]extendFile);
	
	/**
	 * 保存商品
	 * @param product
	 * @param memberid
	 * @return
	 */
	public Result saveProduct(ProductVo productVo,Long memberid);
	
	/**
	 * 上传商品详情图片
	 * @param urlfile
	 * @param backUrl
	 * @param module
	 * @param extendFile
	 * @return
	 */
	public Result mobileUploadProductDetailAttachment(MultipartFile urlfile, Object[]backUrl,String module,String[]extendFile);
	
	/**
	 * 上传商品轮播图片，最多不超过4张
	 * @param attachment
	 * @return
	 */
	public Result saveProductAttachment(Attachment attachment);
	
	/**
	 * 上传商品详情图片
	 * @param attachment
	 * @return
	 */
	public Result saveProductDetailAttachment(Attachment attachment);
	
	/**
	 * 根据商品id获取商品的所有信息，编辑时会用到
	 * @param member
	 * @return
	 */
	public Result getProductAllMsg(Long hunterid,Long productid);
	
	/**
	 * 商品编辑时用到，获取商品表基本的信息
	 * @param productid
	 * @return
	 */
	public Map<String,Object> getProductBaseMsgById(Long productid);
	
	/**
	 * 根据商品分类查询商品
	 * @param params
	 * @return
	 */
	public Result findProductByTypeList(Long productTypeId,Integer page,Integer rows);


    public Result getAllProduct(Long hunterId);

	public Result getProductByType(Long hunterId, Long productTypeId,String phone,String uuid,Integer currentPage, Integer numPage);


}
