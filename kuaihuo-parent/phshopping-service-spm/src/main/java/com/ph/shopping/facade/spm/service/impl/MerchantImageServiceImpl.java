package com.ph.shopping.facade.spm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.mapper.MerchantImageMapper;
import com.ph.shopping.facade.spm.dto.MerchantImageDTO;
import com.ph.shopping.facade.spm.exception.MerchantExceptionEnum;
import com.ph.shopping.facade.spm.service.IMerchantImageService;
import com.ph.shopping.facade.spm.vo.MerchantImageVO;

/**
 * @项目  phshopping-service-spm
 * @描述   商户图片业务层
 * @author 何文浪
 * @时间 2017-5-12
 * @version 2.1
 */
@Component
@Service(version = "1.0.0")
public class MerchantImageServiceImpl implements IMerchantImageService{
	//调用商户图片持久层
	@Autowired
	private MerchantImageMapper merchantImageMapper;

	@Override
	public Result getMerchantImagePage(PageBean pageBean,MerchantImageDTO merchantImageDTO) {
		//分页
		PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
		List<MerchantImageVO> list = merchantImageMapper.getMerchantImageList(merchantImageDTO);
		PageInfo<MerchantImageVO> pageInfo = new PageInfo<MerchantImageVO>(list);
		return new Result(true,MerchantExceptionEnum.SELECT_MRRCHANT_EXCEPTION.getCode(), "", pageInfo.getList(),
				pageInfo.getTotal());
	}

	@Override
	public List<MerchantImageVO> getMerchantImageList(MerchantImageDTO merchantImageDTO) {
		return merchantImageMapper.getMerchantImageList(merchantImageDTO);
	}
}
