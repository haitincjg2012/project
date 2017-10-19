package com.ph.shopping.facade.mapper;

import java.util.List;

import com.ph.shopping.facade.spm.dto.AgentLevelDTO;
import com.ph.shopping.facade.spm.vo.AgentLevelVO;

/**
 * @项目  phshopping-service-spm
 * @描述   代理商等级持久层
 * @author 何文浪
 * @时间 2017-5-15
 * @version 2.1
 */
public interface AgentLevelMapper {
	/**
	 * 查询代理商等级列表
	 * @param AgentLevelDTO
	 * @return List<AgentLevelVO>
	 * @author 何文浪
	 * @时间 2017-5-15
	 */
	List<AgentLevelVO> getAgentLevelList(AgentLevelDTO agentLevelDTO);
}