package com.ph.shopping.facade.product.service.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.transform.MoneyTransUtil;
import com.ph.shopping.facade.mapper.ProductImageMapper;
import com.ph.shopping.facade.mapper.ProductImageSnapshotMapper;
import com.ph.shopping.facade.mapper.ProductMapper;
import com.ph.shopping.facade.mapper.ProductPropertyValMapper;
import com.ph.shopping.facade.mapper.ProductPropertyValSnapshotMapper;
import com.ph.shopping.facade.mapper.ProductSkuMapper;
import com.ph.shopping.facade.mapper.ProductSkuSnapshotMapper;
import com.ph.shopping.facade.mapper.ProductSnapshotMapper;
import com.ph.shopping.facade.mapper.ProductSpecificationMapper;
import com.ph.shopping.facade.mapper.ProductSpecificationSnapshotMapper;
import com.ph.shopping.facade.mapper.ProductSpecificationValMapper;
import com.ph.shopping.facade.mapper.ProductSpecificationValSnapshotMapper;
import com.ph.shopping.facade.product.dto.ProductDTO;
import com.ph.shopping.facade.product.entity.Product;
import com.ph.shopping.facade.product.entity.ProductImage;
import com.ph.shopping.facade.product.entity.ProductImageSnapshot;
import com.ph.shopping.facade.product.entity.ProductPropertyVal;
import com.ph.shopping.facade.product.entity.ProductPropertyValSnapshot;
import com.ph.shopping.facade.product.entity.ProductSku;
import com.ph.shopping.facade.product.entity.ProductSkuSnapshot;
import com.ph.shopping.facade.product.entity.ProductSnapshot;
import com.ph.shopping.facade.product.entity.ProductSpecification;
import com.ph.shopping.facade.product.entity.ProductSpecificationSnapshot;
import com.ph.shopping.facade.product.entity.ProductSpecificationVal;
import com.ph.shopping.facade.product.entity.ProductSpecificationValSnapshot;
import com.ph.shopping.facade.product.exception.ProductException;
import com.ph.shopping.facade.product.exception.ProductExceptionEnum;
import com.ph.shopping.facade.product.service.IProductService;
import com.ph.shopping.facade.product.vo.ProductVO;
import com.ph.shopping.facade.spm.service.ISupplierService;

/**
 * 
 * @项目：phshopping-facade-
 *
 * @描述：商品 service层
 *
 * 		@作者： 杨颜光
 *
 * @创建时间：2017年5月16日 下午4:46:49
 *
 * @Copyright by 杨颜光
 */
@Component
@Service(version = "1.0.0")
public class ProductServiceImpl implements IProductService {

	private static Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	/** 商品Mapper */
	@Autowired
	private ProductMapper productMapper;
	

	/** 商品Mapper */
	@Autowired
	private ProductImageMapper productImageMapper;

	/** 商品属性值Mapper */
	@Autowired
	private ProductPropertyValMapper productPropertyValMapper;
	
	@Autowired
	private ProductSnapshotMapper productSnapshotMapper;


	/** 商品图片快照Mapper */
	@Autowired
	private ProductImageSnapshotMapper productImageSnapshotMapper;

	/** 商品属性值Mapper */
	@Autowired
	private ProductPropertyValSnapshotMapper productPropertyValSnapshotMapper;

	/** 商品规格类型Mapper */
	@Autowired
	private ProductSpecificationMapper productSpecificationMapper;

	/** 商品规格类型快照Mapper */
	@Autowired
	private ProductSpecificationSnapshotMapper productSpecificationSnapshotMapper;

	/** 商品规格值Mapper */
	@Autowired
	private ProductSpecificationValMapper productSpecificationValMapper;

	/** 商品规格值快照Mapper */
	@Autowired
	private ProductSpecificationValSnapshotMapper productSpecificationValSnapshotMapper;

	/** 商品skuMapper */
	@Autowired
	private ProductSkuMapper productSkuMapper;

	/** 商品sku快照Mapper */
	@Autowired
	private ProductSkuSnapshotMapper productSkuSnapshotMapper;

	@Reference(version = "1.0.0")
	ISupplierService supplierService;

	/**
	 * 商品添加方法
	 */
	@Transactional
	public Result addProduct(Product product, List<ProductImage> imageList, List<ProductPropertyVal> ppvList,
			List<ProductSku> skuList, String specificationAndValJson) {
		Result result = new Result();
		logger.info("调用商品添加接口实现类开始");
		try {
			// 后台数据验证开始
			List<String> errorList = product.validateForm();
			if (errorList != null) {
				logger.info("信息检索表[com.ph.shopping.facade.product.entity.Product]实体中出错验证错误：错误信息如下"
						+ JSON.toJSONString(errorList));
				result.setCode(ProductExceptionEnum.ENTITY_CHECK_EXCEPTION.getCode());
				result.setMessage(ProductExceptionEnum.ENTITY_CHECK_EXCEPTION.getMsg()+""+JSON.toJSONString(errorList));
				return result;
			}
			// 获取商品返回id
			this.productMapper.insertUseGeneratedKeys(product);
			//添加商品快照
			ProductSnapshot productSnapshot=new ProductSnapshot();
			BeanUtils.copyProperties(product, productSnapshot);
			productSnapshot.setId(null);
			productSnapshot.setProductId(product.getId());
			this.productSnapshotMapper.insertUseGeneratedKeys(productSnapshot);
			
			for (ProductImage image : imageList) {
				// 将商品id赋值给图片的ProductId
				image.setProductId(product.getId());
				// 添加图片
				this.productImageMapper.insertUseGeneratedKeys(image);
				// 添加图片快照
				ProductImageSnapshot imageSnapshot=new ProductImageSnapshot();
				BeanUtils.copyProperties(image, imageSnapshot);
				imageSnapshot.setId(null);
				imageSnapshot.setProductImageId(image.getId());
				//将商品快照表主键添加到图片快照表
				imageSnapshot.setProductSnapshotId(productSnapshot.getId());
				
				this.productImageSnapshotMapper.insertSelective(imageSnapshot);
			}
		
			// 添加属性值开始
			if (ppvList.size() > 0) {
				for (ProductPropertyVal val : ppvList) {
					// 将商品id赋值给属性的ProductId
					val.setProductId(product.getId());
					val.setCreaterId(product.getCreaterId());
					val.setCreateTime(new Date());
					// 添加属性值
					this.productPropertyValMapper.insertUseGeneratedKeys(val);
					// 添加属性值快照
					ProductPropertyValSnapshot  propertyValSnapshot=new ProductPropertyValSnapshot();
					BeanUtils.copyProperties(val, propertyValSnapshot);
					propertyValSnapshot.setId(null);
					propertyValSnapshot.setProductPropertyValId(val.getId());
					//将商品快照表主键添加到属性值快照表
					propertyValSnapshot.setProductSnapshotId(productSnapshot.getId());
					this.productPropertyValSnapshotMapper.insertSelective(propertyValSnapshot);
				}
			}
			// 定义sku 规格值组合id(为商城按规格查询使用) 使用的
			Map<String, Object> map = new HashMap<String, Object>();
			// String json =
			// "[{\"companyId\":\"111111111\",\"position\":\"a,b,c\"},{\"companyId\":\"9999999999999\",\"position\":\"a,b,c\"}]";
			// 将specificationAndValJson字符串（包括 规格类型和规格值）转换为Json数组
			JSONArray jsonArr = JSON.parseArray(specificationAndValJson);
			for (Iterator<Object> iterator = jsonArr.iterator(); iterator.hasNext();) {
				JSONObject job = (JSONObject) iterator.next();
				System.out.println(job.get("specificationName").toString());
				// 添加规格类型
				ProductSpecification specification = new ProductSpecification();
				specification.setProductId(product.getId());
				specification.setSpecificationName(job.get("specificationName").toString());
				specification.setCreaterId(product.getCreaterId());
				specification.setCreateTime(new Date());
				// 返回添加的主键
				this.productSpecificationMapper.insertUseGeneratedKeys(specification);
				//添加规格类型快照
				ProductSpecificationSnapshot productSpecificationSnapshot=new ProductSpecificationSnapshot();
				BeanUtils.copyProperties(specification, productSpecificationSnapshot);
				productSpecificationSnapshot.setId(null);
				productSpecificationSnapshot.setProductSpecificationId(specification.getId());
				
				productSpecificationSnapshot.setProductSnapshotId(productSnapshot.getId());
				this.productSpecificationSnapshotMapper.insertUseGeneratedKeys(productSpecificationSnapshot);
				String[] val = job.get("specificationValue").toString().split(",");
				for (String string : val) {
					ProductSpecificationVal specificationVal = new ProductSpecificationVal();
					specificationVal.setSpecificationId(specification.getId());
					specificationVal.setSpecificationValue(string);
					specificationVal.setCreaterId(product.getCreaterId());
					specificationVal.setCreateTime(new Date());
					this.productSpecificationValMapper.insertUseGeneratedKeys(specificationVal);
					// 将主键放在map里（key为规格值 value为id）
					map.put(specificationVal.getSpecificationValue(), specificationVal.getId());
					//添加规格值快照
					ProductSpecificationValSnapshot productSpecificationValSnapshot=new ProductSpecificationValSnapshot();
					BeanUtils.copyProperties(specificationVal, productSpecificationValSnapshot);
					productSpecificationValSnapshot.setId(null);
					productSpecificationValSnapshot.setProductSpecificationValId(specificationVal.getId());
					//将规格快照表主键添加到规格值快照表
					productSpecificationValSnapshot.setSpecificationSnapshotId(productSpecificationSnapshot.getId());
					this.productSpecificationValSnapshotMapper.insertSelective(productSpecificationValSnapshot);
				}
			}

			// 将商品id赋值给sku的ProductId
			for (ProductSku sku : skuList) {
				sku.setProductId(product.getId());
				sku.setCreaterId(product.getCreaterId());
				sku.setCreateTime(new Date());
				//将商品快照表主键添加到sku快照表

				String[] names = sku.getSkuName().split("-");
				// 定义LONG数数组，规格值排序使用
				Long[] ids = new Long[names.length];
				// 循环规格组合名称数组
				for (int i = 0; i < names.length; i++) {
					Long value = (Long) map.get(names[i]);
					// 将规格值id放进数组
					  ids[i]=value;
				}
				// 数组排序
				Arrays.sort(ids);
				// 给规格值组合id赋值
				String valIds = "";
				for (int i = 0; i < ids.length; i++) {
					valIds += ids[i].toString();
				}
				sku.setSpecificationValIds(valIds);
				//添加sku之前将所有价格乘以10000
				sku.setReferencePrice(MoneyTransUtil.transMulti(
						sku.getReferencePrice() == null ? new BigDecimal("0") : sku.getReferencePrice()));// 市场参考价
				sku.setRetailPrice(MoneyTransUtil
						.transMulti(sku.getRetailPrice() == null ? new BigDecimal("0") : sku.getRetailPrice()));// 零售价
				sku.setPurchasePrice(MoneyTransUtil
						.transMulti(sku.getPurchasePrice() == null ? new BigDecimal("0") : sku.getPurchasePrice()));// 进货价
				sku.setSettlementPrice(MoneyTransUtil.transMulti(
						sku.getSettlementPrice() == null ? new BigDecimal("0") : sku.getSettlementPrice()));// 结算价
				sku.setFreight(MoneyTransUtil
						.transMulti(sku.getFreight() == null ? new BigDecimal("0") : sku.getFreight()));// 物流费
				// 添加sku
				this.productSkuMapper.insertUseGeneratedKeys(sku);
				//添加规格值快照
				ProductSkuSnapshot  skuSnapshot=new ProductSkuSnapshot();
				BeanUtils.copyProperties(sku, skuSnapshot);
				skuSnapshot.setId(null);
				skuSnapshot.setProductsSkuId(sku.getId());
//				// 将规格组合名称截取,通过单个名字取得对应的规格值id
				skuSnapshot.setProductSnapshotId(productSnapshot.getId());
				this.productSkuSnapshotMapper.insertSelective(skuSnapshot);
			}
			map.clear();// 清空map
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("商品添加失败" + ProductExceptionEnum.ADD_PRODUCT_EXCEPTION.getCode());
			throw new ProductException(ProductExceptionEnum.ADD_PRODUCT_PROPERTY_EXCEPTION);
		}
		return result;
	}

	/**
	 * @description:
	 * 		1.修改商品主表
	 *  	2.图片,属性值,规格名称,规格值,SKU的修改方式是删除所有之前的记录, 新增当前的记录
	 *  	3.规格值所有金额转换操作
	 *  	4.同时新增所有快照
	 * @param product
	 *            实体
	 * @param imageList
	 *            图片集合
	 * @param ppvList
	 *            属性值集合
	 * @param skuList
	 *            规格值组合SKU集合
	 * @param specificationAndValJson
	 *            规格和值得json
	 * @return Result
	 *
	 * @author: 李超
	 * @date: 2017-05-18 14:51:14
	 */
	@Override
	@Transactional
	public Result updateProduct(Product product, List<ProductImage> imageList, List<ProductPropertyVal> ppvList,
								List<ProductSku> skuList, String specificationAndValJson) {
		Result result = new Result();
		logger.info("调用修改的商品实现类开始");
		logger.info("product: " + JSON.toJSONString(product));
		logger.info("imageList: " + JSON.toJSONString(imageList));
		logger.info("skuList: " + JSON.toJSONString(skuList));
		logger.info("specificationAndValJson: " + JSON.toJSONString(specificationAndValJson));
		try {
			// 后台数据验证开始
			List<String> errorList = product.validateForm();
			if (errorList != null) {
				logger.info("信息检索表[com.ph.shopping.facade.product.entity.Product]实体中出错验证错误：错误信息如下"
						+ JSON.toJSONString(errorList));
				result.setCode(ProductExceptionEnum.ENTITY_CHECK_EXCEPTION.getCode());
				result.setMessage(ProductExceptionEnum.ENTITY_CHECK_EXCEPTION.getMsg()+":"+JSON.toJSONString(errorList));
				return result;
			}
			//修改商品
			this.productMapper.updateByPrimaryKeySelective(product);
			product = this.productMapper.selectByPrimaryKey(product.getId());
			//删除所有之前的记录(图片,属性值,规格名称,规格值,SKU)
			//删除图片
			ProductImage productImageDel = new ProductImage();
			productImageDel.setProductId(product.getId());
			this.productImageMapper.delete(productImageDel);
			//删除属性值
			ProductPropertyVal productPropertyValDel = new ProductPropertyVal();
			productPropertyValDel.setProductId(product.getId());
			productPropertyValMapper.delete(productPropertyValDel);
			//删除规格名称和规格值
			productSpecificationMapper.deleteProductSpecificationAndValByProductId(product.getId());
			//删除SKU
			ProductSku productSkuDel = new ProductSku();
			productSkuDel.setProductId(product.getId());
			productSkuMapper.delete(productSkuDel);

			//---------------引用商品添加方法的部分代码----------------------
			//添加商品快照
			ProductSnapshot productSnapshot=new ProductSnapshot();
			BeanUtils.copyProperties(product, productSnapshot);
			productSnapshot.setId(null);
			productSnapshot.setProductId(product.getId());
			this.productSnapshotMapper.insertUseGeneratedKeys(productSnapshot);

			for (ProductImage image : imageList) {
				// 将商品id赋值给图片的ProductId
				image.setProductId(product.getId());
				// 添加图片
				this.productImageMapper.insertUseGeneratedKeys(image);
				// 添加图片快照
				ProductImageSnapshot imageSnapshot=new ProductImageSnapshot();
				BeanUtils.copyProperties(image, imageSnapshot);
				imageSnapshot.setId(null);
				imageSnapshot.setProductImageId(image.getId());
				//将商品快照表主键添加到图片快照表
				imageSnapshot.setProductSnapshotId(productSnapshot.getId());
				this.productImageSnapshotMapper.insertSelective(imageSnapshot);
			}

			// 添加属性值开始
			if (ppvList.size() > 0) {
				for (ProductPropertyVal val : ppvList) {
					// 将商品id赋值给属性的ProductId
					val.setProductId(product.getId());
					val.setCreaterId(product.getCreaterId());
					val.setCreateTime(new Date());
					// 添加属性值
					this.productPropertyValMapper.insertUseGeneratedKeys(val);
					// 添加属性值快照
					ProductPropertyValSnapshot  propertyValSnapshot=new ProductPropertyValSnapshot();
					BeanUtils.copyProperties(val, propertyValSnapshot);
					propertyValSnapshot.setId(null);
					propertyValSnapshot.setProductPropertyValId(val.getId());
					//将商品快照表主键添加到属性值快照表
					propertyValSnapshot.setProductSnapshotId(productSnapshot.getId());
					this.productPropertyValSnapshotMapper.insertSelective(propertyValSnapshot);
				}
			}
			// 定义sku 规格值组合id(为商城按规格查询使用) 使用的
			Map<String, Object> map = new HashMap<String, Object>();
			// String json =
			// "[{\"companyId\":\"111111111\",\"position\":\"a,b,c\"},{\"companyId\":\"9999999999999\",\"position\":\"a,b,c\"}]";
			// 将specificationAndValJson字符串（包括 规格类型和规格值）转换为Json数组
			JSONArray jsonArr = JSON.parseArray(specificationAndValJson);
			for (Iterator<Object> iterator = jsonArr.iterator(); iterator.hasNext();) {
				JSONObject job = (JSONObject) iterator.next();
				System.out.println(job.get("specificationName").toString());
				// 添加规格类型
				ProductSpecification specification = new ProductSpecification();
				specification.setProductId(product.getId());
				specification.setSpecificationName(job.get("specificationName").toString());
				specification.setCreaterId(product.getCreaterId());
				specification.setCreateTime(new Date());
				// 返回添加的主键
				this.productSpecificationMapper.insertUseGeneratedKeys(specification);
				//添加规格类型快照
				ProductSpecificationSnapshot productSpecificationSnapshot=new ProductSpecificationSnapshot();
				BeanUtils.copyProperties(specification, productSpecificationSnapshot);
				productSpecificationSnapshot.setId(null);
				productSpecificationSnapshot.setProductSpecificationId(specification.getId());
				productSpecificationSnapshot.setProductSnapshotId(productSnapshot.getId());
				this.productSpecificationSnapshotMapper.insertUseGeneratedKeys(productSpecificationSnapshot);
				String[] val = job.get("specificationValue").toString().split(",");
				for (String string : val) {
					ProductSpecificationVal specificationVal = new ProductSpecificationVal();
					specificationVal.setSpecificationId(specification.getId());
					specificationVal.setSpecificationValue(string);
					specificationVal.setCreaterId(product.getCreaterId());
					specificationVal.setCreateTime(new Date());
					this.productSpecificationValMapper.insertUseGeneratedKeys(specificationVal);
					// 将主键放在map里（key为规格值 value为id）
					map.put(specificationVal.getSpecificationValue(), specificationVal.getId());
					//添加规格值快照
					ProductSpecificationValSnapshot productSpecificationValSnapshot=new ProductSpecificationValSnapshot();
					BeanUtils.copyProperties(specificationVal, productSpecificationValSnapshot);
					productSpecificationValSnapshot.setId(null);
					productSpecificationValSnapshot.setProductSpecificationValId(specificationVal.getId());
					//将规格快照表主键添加到规格值快照表
					productSpecificationValSnapshot.setSpecificationSnapshotId(productSpecificationSnapshot.getId());
					this.productSpecificationValSnapshotMapper.insertSelective(productSpecificationValSnapshot);
				}
			}

			// 将商品id赋值给sku的ProductId
			for (ProductSku sku : skuList) {
				sku.setProductId(product.getId());
				sku.setCreaterId(product.getCreaterId());
				sku.setCreateTime(new Date());
				// 将规格组合名称截取,通过单个名字取得对应的规格值id
				String[] names = sku.getSkuName().split("-");
				// 定义LONG数数组，规格值排序使用
				Long[] ids = new Long[names.length];
				// 循环规格组合名称数组
				for (int i = 0; i < names.length; i++) {
					Long value = (Long) map.get(names[i]);
					// 将规格值id放进数组
					ids[i]=value;
				}
				// 数组排序
				Arrays.sort(ids);
				// 给规格值组合id赋值
				String valIds = "";
				for (int i = 0; i < ids.length; i++) {
					valIds += ids[i].toString();
				}
				sku.setSpecificationValIds(valIds);
				//添加sku之前将所有价格乘以10000
				sku.setReferencePrice(MoneyTransUtil.transMulti(
						sku.getReferencePrice() == null ? new BigDecimal("0") : sku.getReferencePrice()));// 市场参考价
				sku.setRetailPrice(MoneyTransUtil
						.transMulti(sku.getRetailPrice() == null ? new BigDecimal("0") : sku.getRetailPrice()));// 零售价
				sku.setPurchasePrice(MoneyTransUtil
						.transMulti(sku.getPurchasePrice() == null ? new BigDecimal("0") : sku.getPurchasePrice()));// 进货价
				sku.setSettlementPrice(MoneyTransUtil.transMulti(
						sku.getSettlementPrice() == null ? new BigDecimal("0") : sku.getSettlementPrice()));// 结算价
				sku.setFreight(MoneyTransUtil
						.transMulti(sku.getFreight() == null ? new BigDecimal("0") : sku.getFreight()));// 物流费
				// 添加sku
				this.productSkuMapper.insertUseGeneratedKeys(sku);
				//添加规格值快照
				ProductSkuSnapshot  skuSnapshot=new ProductSkuSnapshot();
				BeanUtils.copyProperties(sku, skuSnapshot);
				skuSnapshot.setId(null);
				skuSnapshot.setProductsSkuId(sku.getId());
				// 将规格组合名称截取, 通过单个名字取得对应的规格值id
				skuSnapshot.setProductSnapshotId(productSnapshot.getId());
				this.productSkuSnapshotMapper.insertSelective(skuSnapshot);
			}
			map.clear();// 清空map
			//---------------引用商品添加方法的部分代码结束\\----------------------
			logger.info("商品修改完成");
			result.setSuccess(true);
		} catch (Exception e) {
			logger.error("商品修改失败" + ProductExceptionEnum.UPDATE_PRODUCT_EXCEPTION.getCode());
			throw new ProductException(ProductExceptionEnum.UPDATE_PRODUCT_EXCEPTION);
		}
		return result;
	}

	public Result getProductByPage(PageBean pageBean, ProductDTO productDTo) {
		PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
		List<ProductVO> list = (Page<ProductVO>) productMapper.getPhProductVoPageList(productDTo);
		PageInfo<ProductVO> pageInfo = new PageInfo<ProductVO>(list);
		return new Result(true, ProductExceptionEnum.SELECT_PRODUCT_EXCEPTION.getCode(), "", pageInfo.getList(),
				pageInfo.getTotal());
	}

	@Transactional
	@Override
	public Result deleteLogicProduct(Product product) {
		Result result = new Result();
		try {
			//逻辑删除商品主表
			this.productMapper.updateByPrimaryKeySelective(product);
			//物理删除属性值
			ProductPropertyVal propertyVal=new ProductPropertyVal();
			propertyVal.setProductId(product.getId());
			this.productPropertyValMapper.delete(propertyVal);
		   //物理删除规格、规格值
			this.productSpecificationMapper.deleteProductSpecificationAndValByProductId(product.getId());
			//物理删除sku
			ProductSku productSku=new ProductSku();
			productSku.setProductId(product.getId());
			this.productSkuMapper.delete(productSku);
			//物理删除图片
			ProductImage productImageDel = new ProductImage();
			productImageDel.setProductId(product.getId());
			this.productImageMapper.delete(productImageDel);
			
			result.setSuccess(true);
		} catch (Exception e) {
			logger.error("商品删除失败" + ProductExceptionEnum.DELETE_PRODUCT_EXCEPTION.getCode());
			throw new ProductException(ProductExceptionEnum.DELETE_PRODUCT_EXCEPTION);
		}
		return result;
	} 

	@Transactional
	@Override
	public Result auditProduct(Product product) {
		Result result = new Result();
		try {
			productMapper.updateByPrimaryKeySelective(product);
			result.setSuccess(true);
		} catch (Exception e) {
			logger.error("商品审核失败" + ProductExceptionEnum.AUDIT_PRODUCT_EXCEPTION.getCode());
			throw new ProductException(ProductExceptionEnum.AUDIT_PRODUCT_EXCEPTION);
		}
		return result;
	}

	@Transactional
	@Override
	public Result onsalProduct(Product product) {
		Result result = new Result();
		try {
			productMapper.updateByPrimaryKeySelective(product);
			result.setSuccess(true);
		} catch (Exception e) {
			String status = "";
			if (product.getSaleNoSaleStatus() == 1) {
				status = "上架";
			} else if (product.getSaleNoSaleStatus() == 2) {
				status = "下架";
			} else {
				status = "上下架";
			}
			logger.error("商品" + status + "失败" + ProductExceptionEnum.ONSAL_PRODUCT_EXCEPTION.getCode());
			throw new ProductException(ProductExceptionEnum.ONSAL_PRODUCT_EXCEPTION);
		}

		return result;
	}

	@Override
	public Product getProduct(Product product) {
		return this.productMapper.selectOne(product);
	}

	@Override
	public List<Product> getProductList(Product product) {
		return this.productMapper.select(product);
	}

	/**
	 * 根据分类id获取商品数量
	 */
	@Override
	public Integer getProductCountByClassify(Long classifyId) {
		Product record = new Product();
		record.setProductClassifyId(classifyId);
		return productMapper.selectCount(record);
	}
	
	/**
	 * 通过id查询商品信息(供应链使用)
	 * 
	 * @param productIds
	 * @return
	 */
	@Override
	public
	List<ProductVO> getProductListForOder(@Param(value = "productIds") List<Long> productIds){
	            return  this.productMapper.getProductListForOder(productIds);
	}
	
	/**
	 * 
	 * 
	 * @param productDto
	 * @return List<ProductVO>
	 *
	 */
	@Override
	public  Result getProductsForMall(PageBean pageBean,ProductDTO productDto){
		Result result=new Result();
		//将查询价格乘以一万
		if(productDto.getMinPrice()!=null){
			int min=productDto.getMinPrice().compareTo(BigDecimal.ZERO); //和0，Zero比较
			if(min==1){
			productDto.setMinPrice(MoneyTransUtil
					.transMulti(productDto.getMinPrice() == null ? new BigDecimal("0") : productDto.getMinPrice()));// 零售价
			}
		}
		if(productDto.getMaxPrice()!=null){
			   int max=productDto.getMaxPrice().compareTo(BigDecimal.ZERO); //和0，Zero比较
			  if(max==1){
			productDto.setMaxPrice(MoneyTransUtil
					.transMulti(productDto.getMaxPrice() == null ? new BigDecimal("0") : productDto.getMaxPrice()));// 零售价
			  }
		}
		PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
		List<ProductVO> list= (Page<ProductVO>)this.productMapper.getProductsForMall(productDto);
		PageInfo<ProductVO> pageInfo = new PageInfo<ProductVO>(list);
		//将显示价格除以一万
		for (ProductVO productVO : list) {
			productVO.setRetailPrice(MoneyTransUtil
					.formatBigDecimalByDivisTwo(productVO.getRetailPrice() == null ? new BigDecimal("0") : productVO.getRetailPrice()));// 零售价
		}
		result.setCount(pageInfo.getTotal());
		result.setData(pageInfo.getList());
		result.setCode(String.valueOf(pageInfo.getPages()));//将result的code作为总页数
		result.setMessage(String.valueOf(pageInfo.getPageNum()));//将result的message作为当前页数
		 return   result;
	 }
	
	
	@Override
	public   List<ProductVO> getProductsForMallClass(ProductDTO productDto){
		//将查询价格乘以一万
		if(productDto.getMinPrice()!=null){
			int min=productDto.getMinPrice().compareTo(BigDecimal.ZERO); //和0，Zero比较
			if(min==1){
			productDto.setMinPrice(MoneyTransUtil
					.transMulti(productDto.getMinPrice() == null ? new BigDecimal("0") : productDto.getMinPrice()));// 零售价
			}
		}
		if(productDto.getMaxPrice()!=null){
			   int max=productDto.getMaxPrice().compareTo(BigDecimal.ZERO); //和0，Zero比较
			  if(max==1){
			productDto.setMaxPrice(MoneyTransUtil
					.transMulti(productDto.getMaxPrice() == null ? new BigDecimal("0") : productDto.getMaxPrice()));// 零售价
			  }
		}
		List<ProductVO> list=this.productMapper.getProductsForMall(productDto);
		//将显示价格除以一万
		for (ProductVO productVO : list) {
			productVO.setRetailPrice(MoneyTransUtil
					.formatBigDecimalByDivisTwo(productVO.getRetailPrice() == null ? new BigDecimal("0") : productVO.getRetailPrice()));// 零售价
		}
		  return   list;
	 }
	
	@Override
	public   Result getProductsAllForMall(PageBean pageBean,ProductDTO productDto){
		Result result=new Result();
		//将查询价格乘以一万
		if(productDto.getMinPrice()!=null){
			int min=productDto.getMinPrice().compareTo(BigDecimal.ZERO); //和0，Zero比较
			if(min==1){
			productDto.setMinPrice(MoneyTransUtil
					.transMulti(productDto.getMinPrice() == null ? new BigDecimal("0") : productDto.getMinPrice()));// 零售价
			}
		}
		if(productDto.getMaxPrice()!=null){
			   int max=productDto.getMaxPrice().compareTo(BigDecimal.ZERO); //和0，Zero比较
			  if(max==1){
			productDto.setMaxPrice(MoneyTransUtil
					.transMulti(productDto.getMaxPrice() == null ? new BigDecimal("0") : productDto.getMaxPrice()));// 零售价
			  }
		}
		PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
		List<ProductVO> list= (Page<ProductVO>)this.productMapper.getProductsAllForMall(productDto);
		PageInfo<ProductVO> pageInfo = new PageInfo<ProductVO>(list);
		//将显示价格除以一万
		for (ProductVO productVO : list) {
			productVO.setRetailPrice(MoneyTransUtil
					.formatBigDecimalByDivisTwo(productVO.getRetailPrice() == null ? new BigDecimal("0") : productVO.getRetailPrice()));// 零售价
		}
		result.setCount(pageInfo.getTotal());
		result.setData(pageInfo.getList());
		result.setCode(String.valueOf(pageInfo.getPages()));//将result的code作为总页数
		result.setMessage(String.valueOf(pageInfo.getPageNum()));//将result的message作为当前页数
		 return   result;
	}
	
	@Override
	public
	 List<ProductVO> getProductsForMallIndexPage(ProductDTO productDto){
		 List<ProductVO> list=this.productMapper.getProductsForMallIndexPage(productDto);
			//将显示价格除以一万
			for (ProductVO productVO : list) {
				productVO.setRetailPrice(MoneyTransUtil
						.formatBigDecimalByDivisTwo(productVO.getRetailPrice() == null ? new BigDecimal("0") : productVO.getRetailPrice()));// 零售价
			}
			return  list;
	 }
	@Override
	public  Integer  updateForSupplier(ProductDTO phProductDto){
				return   this.productMapper.updateForSupplier(phProductDto);
		}
}
