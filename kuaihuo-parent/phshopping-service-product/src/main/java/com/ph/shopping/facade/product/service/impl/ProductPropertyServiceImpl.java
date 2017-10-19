package com.ph.shopping.facade.product.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.mapper.ProductPropertyMapper;
import com.ph.shopping.facade.mapper.ProductPropertyValMapper;
import com.ph.shopping.facade.product.dto.ProductDTO;
import com.ph.shopping.facade.product.dto.ProductPropertyDTO;
import com.ph.shopping.facade.product.entity.ProductProperty;
import com.ph.shopping.facade.product.entity.ProductPropertyVal;
import com.ph.shopping.facade.product.exception.ProductException;
import com.ph.shopping.facade.product.exception.ProductExceptionEnum;
import com.ph.shopping.facade.product.service.IProductPropertyService;
import com.ph.shopping.facade.product.vo.ProductPropertyVO;

/**
 * 
 * @项目：phshopping-facade-
 *
 * @描述：
 *
 * @作者： 杨颜光
 *
 * @创建时间：2017年5月15日 下午2:20:35
 *
 * @Copyright by 杨颜光
 */
@Component
@Service(version = "1.0.0")
public class ProductPropertyServiceImpl  implements IProductPropertyService {

	private static Logger logger = LoggerFactory.getLogger(ProductPropertyServiceImpl.class);

	@Autowired
	private ProductPropertyMapper productPropertyMapper;
	
	@Autowired
	private ProductPropertyValMapper productPropertyValMapper;
	
	
	
	@Override
	@Transactional
	public Result addProductPeoperty(ProductProperty property)  {
		 Result result = new Result();
		 try {
			this.productPropertyMapper.insertSelective(property);
			 result.setSuccess(true);
			 return result;
		} catch (Exception e) {
			logger.error("商品属性添加失败"+ProductExceptionEnum.ADD_PRODUCT_PROPERTY_EXCEPTION.getCode());
			throw new ProductException(ProductExceptionEnum.ADD_PRODUCT_PROPERTY_EXCEPTION);
		}
		
	}
	
	@Override
	@Transactional
	public Result updateProductPeoperty(ProductProperty property)  {
		Result result = new Result();
		try {
			this.productPropertyMapper.updateByPrimaryKeySelective(property);
			result.setSuccess(true);
			return result;
		} catch (Exception e) {
			logger.error("商品属性修改失败"+ProductExceptionEnum.UPDATE_PRODUCT_PROPERTY_EXCEPTION.getCode());
			throw new ProductException(ProductExceptionEnum.UPDATE_PRODUCT_PROPERTY_EXCEPTION);
		}
	
	}
	
	@Override
	public Result getProductPropertyByPage(PageBean pageBean, ProductPropertyDTO ProductPropertyDto)
			 {
		PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
		List<ProductPropertyVO> list = (Page<ProductPropertyVO>) productPropertyMapper
				.getPhProductPropertyVoList(ProductPropertyDto);
		PageInfo<ProductPropertyVO> pageInfo = new PageInfo<ProductPropertyVO>(list);
		return new Result(true,ProductExceptionEnum.SELECT_PRODUCT_PROPERTY_EXCEPTION.getCode(), "", pageInfo.getList(),
				productPropertyMapper.getPhProductPropertyVoCount(ProductPropertyDto));
	}
	
	@Override
	public  ProductProperty getProductProperty(ProductProperty productProperty)  {
			ProductProperty productPropertyResult=this.productPropertyMapper.selectOne(productProperty);
	   return productPropertyResult;
	}
	
	@Override
	@Transactional
	public Result deleteProductProperty(ProductProperty productProperty)  {
		Result result = new Result();
		try {
			productPropertyMapper.updateByPrimaryKeySelective(productProperty);
			//删除属性值（如何属性下没有属性值不做）
			ProductPropertyVal propertyVal=new ProductPropertyVal();
			propertyVal.setPropertyId(productProperty.getId());
			//查询属性是否有属性值
			int count =productPropertyValMapper.selectCount(propertyVal);
			if(count>0){
				this.productPropertyValMapper.delete(propertyVal);
			}
		} catch (Exception e) {
			logger.error("商品属性删除失败"+ProductExceptionEnum.DETELE_PRODUCT_PROPERTY_EXCEPTION.getCode());
			throw new ProductException(ProductExceptionEnum.DETELE_PRODUCT_PROPERTY_EXCEPTION);
		}
	    result.setSuccess(true);
		return result;
	}
	
	@Override
	public 	List<ProductProperty>  getProductProperties(ProductProperty productProperty){
			return this.productPropertyMapper.select(productProperty);
	}
	
	  /***
		 * 
		 * 通过(商品所属的三级分类id)查询对应的属性（后台和商城都可使用）
		 * @param phProductPropertyDto
		 * @return
		 * @author 杨颜光
		 */
	  @Override
	   public List<ProductPropertyVO>  getProductPropertyVoByThreeCassifyId(ProductDTO productDto){
		  return  this.productPropertyMapper.getProductPropertyVoByThreeCassifyId(productDto);
	   }

	@Override
	public Result updateProductPeopertyForSort(List<ProductProperty> propertiess) {
		Result result=new Result();
			try {
				for (ProductProperty productProperty : propertiess) {
					this.productPropertyMapper.updateByPrimaryKeySelective(productProperty);
				}
			} catch (Exception e) {
				logger.error("商品属性列表排序失败"+ProductExceptionEnum.SORT_PRODUCT_PROPERTY_EXCEPTION.getCode());
				throw new ProductException(ProductExceptionEnum.SORT_PRODUCT_PROPERTY_EXCEPTION);
			}
		    result.setSuccess(true);
			return result;
	}
}
