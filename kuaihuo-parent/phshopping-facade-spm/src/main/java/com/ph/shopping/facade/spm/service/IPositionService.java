package com.ph.shopping.facade.spm.service;

import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.spm.dto.PositionDTO;
import com.ph.shopping.facade.spm.vo.PositionVO;

import java.util.List;

public interface IPositionService {

	/**
	 * 查询区域列表
	 * @param positionDTO
	 * @return List<PositionVO>
	 * @author 何文浪
	 * @时间 2017-5-22
	 */
	List<PositionVO> getPositionList(PositionDTO positionDTO);
	/**
	 * 查询子级区域列表
	 * @param positionDTO
	 * @return List<PositionVO>
	 * @author 何文浪
	 * @时间 2017-5-22
	 */
	List<PositionVO> getPositionChildList(PositionDTO positionDTO);

	/**
	 * 分页查询区域列表
	 * @param positionDTO
	 * @return Result
	 * @author 何文浪
	 * @时间 2017-5-22
	 */
	Result getPositionList(PageBean pageBean, PositionDTO positionDTO);

	/**
	 * 查询单条区域
	 * @param positionDTO:setId
	 * @return PositionVO
	 * @author 何文浪
	 * @时间 2017-5-22
	 */
	PositionVO getPositionVo(PositionDTO positionDTO);
	
	/**
	 * 查询单条区域
	 * @param id
	 * @return PositionVO
	 * @author 何文浪
	 * @时间 2017-5-22
	 */
	PositionVO getPositionVoById(Long id);
	/**
	 * 
	 * @Title: getPositionVoByIds   
	 * @Description: 根据 乡镇ID 得到乡镇名称
	 * @param: @param ids
	 * @param: @return      
	 * @return: List<PositionVO>
	 * @author：李杰      
	 * @throws
	 */
	List<PositionVO> getPositionVoByTownIds(List<Long> ids);


    /**
     * 查询区域前三级 按照app端要求数据返回
     */
    Result getMerchantAppCounty();
}