package com.ph.shopping.facade.mapper;

import com.ph.shopping.facade.spm.dto.PositionDTO;
import com.ph.shopping.facade.spm.vo.PositionVO;

import java.util.List;
/**
 * @项目  phshopping-service-spm
 * @描述   区域数据层
 * @author 何文浪
 * @时间 2017-5-12
 * @version 2.1
 */
public interface PositionMapper {

	/**
	 * 查询区域列表
	 * @param PositionDTO
	 * @return List<PositionVO>
	 * @author 何文浪
	 * @时间 2017-5-12
	 */
	List<PositionVO> getPositionList(PositionDTO positionDTO);
	/**
	 * 查询子级区域列表
	 * @param PositionDTO
	 * @return List<PositionVO>
	 * @author 何文浪
	 * @时间 2017-5-12
	 */
	List<PositionVO> getPositionChildList(PositionDTO positionDTO);
	/**
	 * 查询区域
	 * @param Long id
	 * @return PositionVO
	 * @author 何文浪
	 * @时间 2017-5-12
	 */
	PositionVO getPositionById(Long id);

    /**
     * @Title: getPositionVoByTownIds
     * @Description: 根据townID 得到名称
     * @param: @param ids
     * @param: @return
     * @return: List<PositionVO>
     * @author：李杰
     * @throws
     */
    List<PositionVO> getPositionVoByTownIds(List<Long> list);

    /**
     * @Title: getPositionVoByGroup
     * @Description: 查询所有省、市、区、社区  根据传入字段进行分组
     * @return: List<PositionVO>
     * @author：熊克文
     */
    List<PositionVO> getPositionVoByGroup(String groupByCol);
}