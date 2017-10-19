package com.ph.shopping.facade.system.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.mapper.LogisticsMapper;
import com.ph.shopping.facade.system.dto.LogisticsDTO;
import com.ph.shopping.facade.system.entity.Logistics;
import com.ph.shopping.facade.system.exception.SystemBizException;
import com.ph.shopping.facade.system.exception.SystemEnum;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @ClassName:  LogisticsServiceImpl   
 * @Description:物流公司实现类   
 * @author: 李杰
 * @date:   2017年6月13日 下午11:21:57     
 * @Copyright: 2017
 */
@Component
@Service(version = "1.0.0")
public class LogisticsServiceImpl implements ILogisticsService {

	private static final Logger logger = LoggerFactory.getLogger(LogisticsServiceImpl.class);
	
    @Autowired
    LogisticsMapper logisticsMapper;

	@Override
	public Result getLogisticsList() {
		return ResultUtil.getResult(SystemEnum.Code.SUCCESS,logisticsMapper.selectLogisticsList());
	}
	
    @Override
    public Result getLogisticsList(PageBean pageBean) {
    	PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
    	List<Logistics> selectLogisticsList = logisticsMapper.selectLogisticsList();
    	PageInfo<Logistics> pageInfo = new PageInfo<Logistics>(selectLogisticsList);
        return ResultUtil.getResult(SystemEnum.Code.SUCCESS,pageInfo.getList(),pageInfo.getTotal());
    }

	@Override
	@Transactional
	public Result addLogistics(LogisticsDTO dto) {
		logger.info("新增物流公司入参，LogisticsDTO = {}", JSON.toJSONString(dto));
		Result result = ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR);
		try {
			if (null == dto) {
				return result;
			}
			String errorStr = dto.validateForm();
			if (StringUtils.isNotBlank(errorStr)) {
				result.setMessage(errorStr);
				return result;
			}
			Logistics logi = new Logistics();
			BeanUtils.copyProperties(dto, logi);
			logi.setCreateTime(new Date());
			logi.setDeleteFlag((byte) 1);// TODO:修改成枚举
			logisticsMapper.insert(logi);
			return ResultUtil.getResult(SystemEnum.Code.SUCCESS);
		} catch (Exception e) {
			logger.error("新增物流公司异常，e={}", e);
			throw new SystemBizException(SystemEnum.Code.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	@Transactional
	public Result updateLogistics(LogisticsDTO dto) {
		logger.info("修改物流公司入参，LogisticsDTO = {}", JSON.toJSONString(dto));
		Result result = ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR);
		try {
			if (null == dto) {
				return result;
			}
			String errorStr = dto.validateForm();
			if (StringUtils.isNotBlank(errorStr)) {
				result.setMessage(errorStr);
				return result;
			}
			if (null == dto.getId()) {
				result.setMessage("主键ID不能为空");
				return result;
			}
			Logistics logi = new Logistics();
			BeanUtils.copyProperties(dto, logi);
			logisticsMapper.updateByPrimaryKeySelective(logi);
			return ResultUtil.getResult(SystemEnum.Code.SUCCESS);
		} catch (Exception e) {
			logger.error("修改物流公司异常，e={}", e);
			throw new SystemBizException(SystemEnum.Code.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	@Transactional
	public Result deleteLogistics(Long id) {
		logger.info("删除物流公司入参，ID = " + id);
		Result result = ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR);
		try {
			if (null == id) {
				result.setMessage("ID不能为空");
				return result;
			}
			logisticsMapper.deleteLogistics(id);
			return ResultUtil.getResult(SystemEnum.Code.SUCCESS);
		} catch (Exception e) {
			logger.error("根据ID 删除物流公司异常，e={}", e);
			throw new SystemBizException(SystemEnum.Code.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 通过id获取物流公司
	* Title: getLogisticById
	* Description:
	* @author Mr.Dong
	* @date 2017年6月15日 上午11:55:46
	* @param id
	* @return
	* @see com.ph.shopping.facade.system.service.ILogisticsService#getLogisticById(java.lang.Long)
	 */
	@Override
	public Result getLogisticById(Long id) { 
		return ResultUtil.getResult(RespCode.Code.SUCCESS,logisticsMapper.selectByPrimaryKey(id));
	}

}
