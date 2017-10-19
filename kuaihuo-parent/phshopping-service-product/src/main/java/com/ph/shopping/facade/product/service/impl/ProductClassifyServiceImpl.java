package com.ph.shopping.facade.product.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.mapper.ProductClassifyMapper;
import com.ph.shopping.facade.product.dto.ProductClassifyDTO;
import com.ph.shopping.facade.product.entity.ProductClassify;
import com.ph.shopping.facade.product.exception.ProductException;
import com.ph.shopping.facade.product.exception.ProductExceptionEnum;
import com.ph.shopping.facade.product.service.IProductClassifyService;
import com.ph.shopping.facade.product.status.ProductStatusEnum;
import com.ph.shopping.facade.product.vo.ProductClassifyVO;


/**
 * ProductClassifyServiceImpl 商品分类实现类
 * @version: 2.1
 * @author: 李超
 * @date: 2017-05-15 11:48:15
 */
@Component
@Service(version = "1.0.0")
public class ProductClassifyServiceImpl implements IProductClassifyService {
	
	private static Logger logger = LoggerFactory.getLogger(ProductClassifyServiceImpl.class);

	@Autowired
	private ProductClassifyMapper productClassifyMapper;

	/**
	 * 新增商品类型
	 */
	@Override
	@Transactional
	public Result addProductClassify(ProductClassify productClassify) {
		logger.info("进入添加商品类别方法:" + JSON.toJSONString(productClassify));
		Result result = new Result();
		// 后台数据验证开始
		List<String> errorList = productClassify.validateForm();
		if (errorList != null) {
			logger.info("信息检索表[com.ph.shopping.facade.product.entity.productClassify]实体中出错验证错误：错误信息如下"
					+ JSON.toJSONString(errorList));
			result.setCode(ProductExceptionEnum.ENTITY_CHECK_EXCEPTION.getCode());
			result.setMessage(ProductExceptionEnum.ENTITY_CHECK_EXCEPTION.getMsg() + ":" + JSON.toJSONString(errorList));
			return result;
		}
		try {
			productClassifyMapper.insertSelective(productClassify);
			result.setSuccess(true);
		} catch (Exception e) {
			logger.error("商品分类添加失败"+ProductExceptionEnum.ADD_PRODUCT_CLASSIFY_EXCEPTION.getCode());
			throw new ProductException(ProductExceptionEnum.ADD_PRODUCT_CLASSIFY_EXCEPTION);
		}
		logger.info("添加商品类别结果:" + JSON.toJSONString(result));
		return result;	
	}

	/**
	 * 修改商品类型排序
	 */
	@Override
	@Transactional
	public Result updateProductClassifySort(ProductClassify productClassify) {
		logger.info("进入修改商品类别排序方法:" + JSON.toJSONString(productClassify));
		Result result = new Result();
		try {
			productClassifyMapper.updateByPrimaryKeySelective(productClassify);
			result.setSuccess(true);
		} catch (Exception e) {
			logger.error("商品分类排序修改失败"+ProductExceptionEnum.UPDATE_PRODUCT_CLASSIFY_EXCEPTION.getCode());
			throw new ProductException(ProductExceptionEnum.UPDATE_PRODUCT_CLASSIFY_EXCEPTION);
		}
		logger.info("修改商品类别排序结果:" + JSON.toJSONString(result));
		return result;
	}

	/**
	 * 修改商品类型
	 */
	@Override
	@Transactional
	public Result updateProductClassify(ProductClassify productClassify) {
		logger.info("进入修改商品类别方法:" + JSON.toJSONString(productClassify));
		Result result = new Result();
		// 后台数据验证开始
		List<String> errorList = productClassify.validateForm();
		if (errorList != null) {
			logger.info("信息检索表[com.ph.shopping.facade.product.entity.productClassify]实体中出错验证错误：错误信息如下"
					+ JSON.toJSONString(errorList));
			result.setCode(ProductExceptionEnum.ENTITY_CHECK_EXCEPTION.getCode());
			result.setMessage(ProductExceptionEnum.ENTITY_CHECK_EXCEPTION.getMsg() + ":" + JSON.toJSONString(errorList));
			return result;
		}
		try {
			productClassifyMapper.updateByPrimaryKeySelective(productClassify);
			if(null != productClassify.getStatus()) {
				if (productClassify.getStatus() == ProductStatusEnum.ENABLED.getCode()) {
					//设为已启用时, 启用分类同时启用该类型的所有上级类分类(如果有上级)
					productClassifyMapper.updateEnableProductClassifyById(productClassify.getId());
				} else {
					// 停用分类同时停用该类型的所有子类分类(如果有子类),如果有商品引用则将商品下架
					productClassifyMapper.updateDisableProductClassifyById(productClassify.getId());
				}
			}
			result.setSuccess(true);
		} catch (Exception e) {
			logger.error("商品分类修改失败"+ProductExceptionEnum.UPDATE_PRODUCT_CLASSIFY_EXCEPTION.getCode());
			throw new ProductException(ProductExceptionEnum.UPDATE_PRODUCT_CLASSIFY_EXCEPTION);
		}
		logger.info("修改商品类别结果:" + JSON.toJSONString(result));
		return result;
	}

	/**
	 * 根据条件获取商品类别集合(单表查询)
	 */
	public List<ProductClassify> getProductClassifyList(ProductClassify productClassify){
		logger.info("进入查询商品类别集合(单表查询)方法:" + JSON.toJSONString(productClassify));
		productClassify.setIsDelete(ProductStatusEnum.Not_DELETE.getCode());
		List<ProductClassify> list = productClassifyMapper.select(productClassify);
		logger.info("查询商品类别集合(单表查询)结果: " + JSON.toJSONString(list));
		return list;
	}
	/**
	 * 根据条件获取商品类别VO列表
	 */
	public List<ProductClassifyVO> getProductClassifyVOList(ProductClassifyDTO productClassifyDTO){
		logger.info("进入根据条件获取商品类别VO列表方法:" + JSON.toJSONString(productClassifyDTO));
		List<ProductClassifyVO> ProductClassifyVOList = productClassifyMapper.getProductClassifyVOList(productClassifyDTO);
		Map<Long, ProductClassifyVO> all = new HashMap<>();
		ProductClassifyVOList.forEach(key -> all.put(key.getId(), key));
		List<ProductClassifyVO> list = new ArrayList<ProductClassifyVO>(ProductClassifyVOList.size());
		ProductClassifyVOList.stream().map(
				vo -> new ProductClassifyVO(
						vo.getId(),
						vo.getClassifyName(),
						vo.getClassifyLevel(),
						vo.getDescription(),
						vo.getUrl(),
						vo.getParentId(),
						vo.getStatus(),
						vo.getIsDelete(),
						vo.getSort(),
						vo.getCreateTime(),
						vo.getUpdateTime(),
						vo.getCreaterId(),
						vo.getUpdaterId(),
						vo.getProductCount(),
						this.generateProductClassifySortList(vo.getId(), all)
				)).forEach(list::add);
		Collections.sort(list);
		//list.stream().map(vo->JSON.toJSONString(vo.getSortList())).forEach(System.out::println);
		logger.info("查询商品类别VO列表结果: " + JSON.toJSONString(list));
		return list;
	}


	/**
	 * sort反转 重载 私有方法
	 *
	 * @param Long, Map<Long, ProductClassifyVO>, ArrayList<Integer>
	 * @return ArrayList<Integer>
	 *
	 * @author: 李超
	 * @date: 2017-05-22 17:00:41
	 */
	private ArrayList<Integer> generateProductClassifySortList(Long id, Map<Long, ProductClassifyVO> all) {
		return this.generateProductClassifySortList(id, all, null);
	}

	/**
	 * sort反转 私有方法
	 *
	 * @param Long, Map<Long, ProductClassifyVO>, ArrayList<Integer>
	 * @return ArrayList<Integer>
	 * 
	 * @author: 李超
	 * @date: 2017-05-22 17:00:41
	 */
	private ArrayList<Integer> generateProductClassifySortList(Long id, Map<Long, ProductClassifyVO> all, ArrayList<Integer> sortList) {
		if (sortList == null) {
			sortList = new ArrayList<>();
		}

		ProductClassifyVO current = all.get(id);

		if (current == null) {
			Collections.reverse(sortList);
			return sortList;
		}

		sortList.add(current.getSort());

		if (all.containsKey(id)) {
			return this.generateProductClassifySortList(current.getParentId(), all, sortList);
		} else {
			//因为加入排序的时候是正序 反转返回
			Collections.reverse(sortList);
			return sortList;
		}
	}

	/**
	 * 根据商品类型id获取下面所有被引用的商品数量
	 */
	@Override
	public Integer getExistProductCountByClassify(Long id) {
		logger.info("进入根据商品类型id获取下面所有被引用的商品数量方法: " + id);
		Integer count = productClassifyMapper.getExistProductCountByClassify(id);
		logger.info("被引用的商品数量: " + count);
		return count;
	}

	/**
	 * 根据当前分类获取所有子类别(结果包含自己)
	 */
	@Override
	public List<ProductClassifyVO> getProductClassifyChildren(ProductClassifyDTO productClassifyDTO) {
		logger.info("进入根据当前分类获取所有子类别方法:" + JSON.toJSONString(productClassifyDTO));
		List<ProductClassifyVO> list = productClassifyMapper.getProductClassifyChildren(productClassifyDTO);
		logger.info("根据当前分类获取所有子类别结果: " + JSON.toJSONString(list));
		return list;
	}

	/**
	 * 根据当前分类获取所有上级类别(结果包含自己)
	 */
	@Override
	public List<ProductClassifyVO> getProductClassifyParents(ProductClassifyDTO productClassifyDTO) {
		logger.info("进入根据当前分类获取所有上级类别方法:" + JSON.toJSONString(productClassifyDTO));
		List<ProductClassifyVO> list = productClassifyMapper.getProductClassifyParents(productClassifyDTO);
		logger.info("根据当前分类获取所有上级类别结果: " + JSON.toJSONString(list));
		return list;
	}

	/**
	 * 根据主键id返回实体
	 */
	@Override
	public ProductClassify getProductClassifyById(long id) {
		return productClassifyMapper.selectByPrimaryKey(id);
	}

	/**
	 * 根据单表条件返回一个商品对象
	 * 注意!!! 保证查询结果只会返回一条记录才能用此方法
	 * 实现类直接调用selectOne()
	 */
	@Override
	public ProductClassify getOneProductClassify(ProductClassify productClassify) {
		return productClassifyMapper.selectOne(productClassify);
	}

	/**
	 * 删除商品分类
	 */
	@Override
	@Transactional
	public Result deleteProductClassifyById(long id) {
		logger.info("进入删除商品分类方法: " + id);
		Result result = new Result();
		try {
			//获得引用该分类以及所有子类的商品(未删除的商品)数量
			int count = productClassifyMapper.getExistProductCountByClassify(id);
			//引用数量大于零则不能删除
			if(count > 0){
				result.setSuccess(false);
				result.setCode(ProductExceptionEnum.REFERENCED.getCode());
				result.setMessage(ProductExceptionEnum.REFERENCED.getMsg());
			}else{
				//逻辑删除商品类别(包含删除所有子类别)
				Integer rs = productClassifyMapper.deleteProductClassifyById(id);
				//根据商品类别[逻辑删除]该类别以及子类别中包含的[属性]
				productClassifyMapper.deleteProductPropertyByProductClassifyId(id);
				//根据商品类别[物理删除]该类别以及子类别中包含的[属性值]
				productClassifyMapper.deleteProductPropertyValByProductClassifyId(id);
				result.setSuccess(true);
				result.setCount(rs);
			}
		} catch (Exception e) {
			logger.error("删除商品分类异常"+ProductExceptionEnum.DETELE_PRODUCT_CLASSIFY_EXCEPTION .getCode());
			throw new ProductException(ProductExceptionEnum.DETELE_PRODUCT_CLASSIFY_EXCEPTION);
		}
		logger.info("删除商品分类结果:" + JSON.toJSONString(result));
		return result;
	}

	/**
	 * 停用商品分类
	 * 停用分类同时停用该类型的所有子类分类(如果有子类)
	 */
	@Override
	@Transactional
	public Result updateDisableProductClassifyById(long id) {
		logger.info("进入停用商品分类方法: " + id);
		Result result = new Result();
		try {
			//获得引用该分类以及所有子类的商品(未删除的商品)数量
			int count = productClassifyMapper.getExistProductCountByClassify(id);
			//引用数量大于零则不能停用
			if(count > 0){
				result.setSuccess(false);
				result.setCode(ProductExceptionEnum.REFERENCED.getCode());
				result.setMessage(ProductExceptionEnum.REFERENCED.getMsg());
			}else {
				Integer rs = productClassifyMapper.updateDisableProductClassifyById(id);
				result.setSuccess(true);
				result.setCount(rs);
			}
		} catch (Exception e) {
			logger.error("停用商品分类异常"+ProductExceptionEnum.DISABLE_PRODUCT_CLASSIFY_EXCEPTION .getCode());
			throw new ProductException(ProductExceptionEnum.DISABLE_PRODUCT_CLASSIFY_EXCEPTION);
		}
		logger.info("停用商品分类结果:" + JSON.toJSONString(result));
		return result;
	}

	/**
	 * 启用商品分类
	 * 启用分类同时启用该类型的所有上级类分类(如果有上级)
	 */
	@Override
	@Transactional
	public Result updateEnableProductClassifyById(long id) {
		logger.info("进入启用商品分类方法: " + id);
		Result result = new Result();
		try {
			Integer rs = productClassifyMapper.updateEnableProductClassifyById(id);
			result.setSuccess(true);
			result.setCount(rs);
		} catch (Exception e) {
			logger.error("启用商品分类异常"+ProductExceptionEnum.ENABLE_PRODUCT_CLASSIFY_EXCEPTION .getCode());
			throw new ProductException(ProductExceptionEnum.ENABLE_PRODUCT_CLASSIFY_EXCEPTION);
		}
		logger.info("启用商品分类结果:" + JSON.toJSONString(result));
		return result;
	}

	/**
	 * 商城首页商品分类展示 导航
	 *  根据sort排序默认获取前8个一级分类一级其二级子类
	 *
	 * @param productClassifyDTO
	 * @return
	 * @author: 李超
	 * @date: 2017-06-12 11:09:01
	 */
	@Override
	public List<ProductClassifyVO> getProductClassifyShowIndex(Integer number) {
		logger.info("进入查询商城头部商品分类方法:" + JSON.toJSONString(number));
		List<ProductClassifyVO> list = productClassifyMapper.getProductClassifyShowIndex(number == null ? 8 : number);
		logger.info("查询商城头部商品分类结果: " + JSON.toJSONString(list));
		return list;
	}

	/**
	 * 商城首页商品分类展示 导航
	 * 根据sort排序默认获取前8个一级分类一级其二级子类
	 *
	 * @return
	 * @author: 李超
	 * @date: 2017-06-12 11:09:01
	 */
	@Override
	public List<ProductClassifyVO> getProductClassifyShowIndex() {
		return getProductClassifyShowIndex(null);
	}

	/**
	 * 商城首页商品分类模块
	 *
	 * @return
	 * @author: 李超
	 * @date: 2017-06-12 11:09:01
	 */
	@Override
	public List<ProductClassifyVO> getIndexClassify() {
		logger.info("进入查询商城头部商品分类方法");
		List<ProductClassifyVO> list = productClassifyMapper.getIndexClassify();
		logger.info("查询商城头部商品分类结果: " + JSON.toJSONString(list));
		return list;
	}
}
