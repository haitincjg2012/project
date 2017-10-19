/**  
 * @Title:  IndustryInfoServiceImpl.java   
 * @Package com.ph.shopping.facade.system.service   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年5月8日 下午3:04:48   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.facade.system.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.ph.shopping.common.util.container.ParamVerifyUtil;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.mapper.IndustryInfoMapper;
import com.ph.shopping.facade.system.constant.IndustryConstant;
import com.ph.shopping.facade.system.dto.IndustryInfoDTO;
import com.ph.shopping.facade.system.entity.IndustryInfo;
import com.ph.shopping.facade.system.exception.SystemBizException;
import com.ph.shopping.facade.system.exception.SystemEnum;
import com.ph.shopping.facade.system.vo.IndustryInfoVO;

/**   
 * @ClassName:  IndustryInfoServiceImpl   
 * @Description:行业相关操作实现类   
 * @author: 李杰
 * @date:   2017年5月8日 下午3:04:48     
 * @Copyright: 2017
 */
public class IndustryInfoServiceImpl implements IIndustryInfoService{

	private static final Logger logger = LoggerFactory.getLogger(IndustryInfoServiceImpl.class);

	@Autowired
	private IndustryInfoMapper industryInfoMapper;

	@Override
	public Result getIndustryInfoList(IndustryInfoDTO dto) {
		logger.info("查询行业列表数据入参，IndustryInfoDTO={}", JSON.toJSONString(dto));
		Result result = ResultUtil.getResult(SystemEnum.Code.SUCCESS);
		try {
			List<IndustryInfoVO> list = industryInfoMapper.selectIndustryInfoList(dto);
			result.setData(list);
		} catch (Exception e) {
			logger.error("查询行业列表数据错误", e);
			throw new SystemBizException(SystemEnum.Code.INTERNAL_SERVER_ERROR);
		}
		return result;
	}

	@Override
	@Transactional
	public Result addIndustryInfo(IndustryInfoDTO dto) {
		logger.info("添加行业数据入参，IndustryInfoDTO={}", JSON.toJSONString(dto));
		Result result = ResultUtil.getResult(SystemEnum.PARAM_INCOMPLETE);
		try {
			String[] fields = { "industryName" };
			if (!ParamVerifyUtil.entityIsNotNullByField(dto, fields)) {
				return result;
			}
			IndustryInfo info = new IndustryInfo();
			BeanUtils.copyProperties(dto, info);
			info.setId(null);
			info.setCreateTime(new Date());
			if (info.getLevel() == null) {
				info.setLevel(IndustryConstant.DEFAULT_NUM);
			}
			info.setStatus(IndustryConstant.INDUSTRY_NORMAL);
			industryInfoMapper.insert(info);
			result = ResultUtil.getResult(SystemEnum.Code.SUCCESS);
		} catch (Exception e) {
			logger.error("添加行业数据错误", e);
			throw new SystemBizException(SystemEnum.Code.INTERNAL_SERVER_ERROR);
		}
		return result;
	}

	@Override
	@Transactional
	public Result updateIndustryInfo(IndustryInfoDTO dto) {
		logger.info("修改行业数据入参，IndustryInfoDTO={}", JSON.toJSONString(dto));
		Result result = ResultUtil.getResult(SystemEnum.PARAM_INCOMPLETE);
		try {
			String[] fields = { "id" };
			if (!ParamVerifyUtil.entityIsNotNullByField(dto, fields)) {
				return result;
			}
			IndustryInfo info = new IndustryInfo();
			BeanUtils.copyProperties(dto, info);
			info.setUpdateTime(new Date());
			industryInfoMapper.updateByPrimaryKeySelective(info);
			result = ResultUtil.getResult(SystemEnum.Code.SUCCESS);
		} catch (Exception e) {
			logger.error("修改行业数据错误", e);
			throw new SystemBizException(SystemEnum.Code.INTERNAL_SERVER_ERROR);
		}
		return result;
	}

	@Override
	@Transactional
	public Result updateIndustryInfoByStart(Long id) {
		logger.info("启用行业数据入参，id : " + id);
		Result result = ResultUtil.getResult(SystemEnum.PARAM_INCOMPLETE);
		try {
			if (null == id) {
				return result;
			}
			IndustryInfo info = new IndustryInfo();
			info.setId(id);
			info.setUpdateTime(new Date());
			info.setStatus(IndustryConstant.INDUSTRY_NORMAL);
			industryInfoMapper.updateByPrimaryKeySelective(info);
			result = ResultUtil.getResult(SystemEnum.Code.SUCCESS);
		} catch (Exception e) {
			logger.error("启用行业数据错误", e);
			throw new SystemBizException(SystemEnum.Code.INTERNAL_SERVER_ERROR);
		}
		return result;
	}

	@Override
	@Transactional
	public Result updateIndustryInfoByOutage(Long id) {
		logger.info("停用行业数据入参，id : " + id);
		Result result = ResultUtil.getResult(SystemEnum.PARAM_INCOMPLETE);
		try {
			if (null == id) {
				return result;
			}
			IndustryInfo info = new IndustryInfo();
			info.setId(id);
			info.setUpdateTime(new Date());
			info.setStatus(IndustryConstant.INDUSTRY_OUTAGE);
			industryInfoMapper.updateByPrimaryKeySelective(info);
			result = ResultUtil.getResult(SystemEnum.Code.SUCCESS);
		} catch (Exception e) {
			logger.error("停用行业数据错误", e);
			throw new SystemBizException(SystemEnum.Code.INTERNAL_SERVER_ERROR);
		}
		return result;
	}

}
