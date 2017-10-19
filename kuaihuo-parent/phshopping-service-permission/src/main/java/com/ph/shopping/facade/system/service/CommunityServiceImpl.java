/**  
 * @Title:  CommunityServiceImpl.java   
 * @Package com.ph.shopping.facade.system.service   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年6月15日 下午2:23:41   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.facade.system.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.mapper.CommunityMapper;
import com.ph.shopping.facade.system.dto.CommunityAddDTO;
import com.ph.shopping.facade.system.dto.CommunityQueryDTO;
import com.ph.shopping.facade.system.dto.CommunityUpdateDTO;
import com.ph.shopping.facade.system.entity.CommunityInfo;
import com.ph.shopping.facade.system.exception.SystemBizException;
import com.ph.shopping.facade.system.vo.CommunityInfoVO;

/**   
 * @ClassName:  CommunityServiceImpl   
 * @Description:社区相关操作   
 * @author: 李杰
 * @date:   2017年6月15日 下午2:23:41     
 * @Copyright: 2017
 */
@Component
@Service(version = "1.0.0")
public class CommunityServiceImpl implements ICommunityService{

	private static final Logger logger = LoggerFactory.getLogger(CommunityServiceImpl.class);
			
	@Autowired
	private CommunityMapper communityMapper;
	
	@Override
	public Result getCommunityListByPage(CommunityQueryDTO dto, PageBean pageBean) {
		logger.info("查询社区列表入参，CommunityQueryDTO = {}", JSON.toJSONString(dto));
		PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
		List<CommunityInfoVO> list = communityMapper.selectCommunityListByPage(dto);
		PageInfo<CommunityInfoVO> pageInfo = new PageInfo<CommunityInfoVO>(list);
		return ResultUtil.getResult(RespCode.Code.SUCCESS, pageInfo.getList(), pageInfo.getTotal());
	}

	@Override
	@Transactional
	public Result addCommunity(CommunityAddDTO dto) {
		logger.info("添加社区列表入参，CommunityAddDTO = {}", JSON.toJSONString(dto));
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
			
			Long maxCountyId = getMaxTownIdByCountyId(dto.getCountyId());
			if(null == maxCountyId){
				maxCountyId = dto.getCountyId() + 1000;
			}
			CommunityInfo communityInfo = new CommunityInfo();
			BeanUtils.copyProperties(dto, communityInfo);
			communityInfo.setCreatedTime(new Date());
			communityInfo.setTownName(dto.getTownName());
			communityInfo.setTownId(maxCountyId);
			communityMapper.insertSelective(communityInfo);
			return ResultUtil.getResult(RespCode.Code.SUCCESS);
		} catch (Exception e) {
			logger.error("添加社区列表异常，e={}", e);
			throw new SystemBizException(RespCode.Code.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	@Transactional
	public Result updateCommunity(CommunityUpdateDTO dto) {
		logger.info("修改社区列表入参，CommunityAddDTO = {}", JSON.toJSONString(dto));
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
			CommunityInfo communityInfo = new CommunityInfo();
			communityInfo.setUpdateTime(new Date());
			communityInfo.setId(dto.getId());
			communityInfo.setTownName(dto.getTownName());
			communityMapper.updateByPrimaryKeySelective(communityInfo);
			return ResultUtil.getResult(RespCode.Code.SUCCESS);
		} catch (Exception e) {
			logger.error("修改社区列表异常，e={}", e);
			throw new SystemBizException(RespCode.Code.INTERNAL_SERVER_ERROR);
		}
	}
	/**
	 * @Title: getMaxTownIdByCountyId   
	 * @Description: 根据区ID 得到下面最大的社区ID   
	 * @param: @param countyId
	 * @param: @return      
	 * @return: Long
	 * @author：李杰      
	 * @throws
	 */
	private Long getMaxTownIdByCountyId(Long countyId){
		return communityMapper.selectMaxTownIdByCountyId(countyId);
	}

	@Override
	public Result getPositionById(Long id) {
		return ResultUtil.getResult(RespCode.Code.SUCCESS,communityMapper.selectByPrimaryKey(id));
	}
}
