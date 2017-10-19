package com.ph.shopping.facade.spm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.mapper.SupplierImageMapper;
import com.ph.shopping.facade.spm.dto.SupplierImageDTO;
import com.ph.shopping.facade.spm.exception.SupplierExceptionEnum;
import com.ph.shopping.facade.spm.service.ISupplierImageService;
import com.ph.shopping.facade.spm.vo.SupplierImageVO;

/**
 * @项目  phshopping-service-spm
 * @描述   供应商图片业务层
 * @author 何文浪
 * @时间 2017-5-15
 * @version 2.1
 */
@Component
@Service(version = "1.0.0")
public class SupplierImageServiceImpl implements ISupplierImageService {
	//初始化供应商图片持久层
	@Autowired
	private SupplierImageMapper supplierImageMapper;
	
	@Override
	public List<SupplierImageVO> getSupplierImageList(SupplierImageDTO supplierImageDTO) {
		return supplierImageMapper.getSupplierImageList(supplierImageDTO);
	}
	
	@Override
	public Result getSupplierImagePage(PageBean pageBean,SupplierImageDTO supplierImageDTO) {
		//分页
		PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
		List<SupplierImageVO> list = supplierImageMapper.getSupplierImageList(supplierImageDTO);
		PageInfo<SupplierImageVO> pageInfo = new PageInfo<SupplierImageVO>(list);
		return new Result(true,SupplierExceptionEnum.SELECT_SUPPLIER_EXCEPTION.getCode(), "", pageInfo.getList(),
				pageInfo.getTotal());
	}
	
	@Override
	public SupplierImageVO getSupplierImageListById(SupplierImageDTO supplierImageDTO) {
		List<SupplierImageVO> list = supplierImageMapper.getSupplierImageList(supplierImageDTO);
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
}
