package com.ph.shopping.facade.spm.service;

import java.util.List;

import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.spm.dto.AgentLevelDTO;
import com.ph.shopping.facade.spm.vo.AgentLevelVO;
/**
 * @项目  phshopping-service-spm
 * @描述   代理商类别业务层接口
 * @author 何文浪
 * @时间 2017-5-15
 * @version 2.1
 */
public interface AgentLevelService {

	/**
	 * 查询代理商等级列表
	 * @param AgentLevelDTO
	 * @return List<AgentLevelVO>
	 * @author 何文浪
	 * @时间 2017-5-15
	 */
	List<AgentLevelVO> getAgentLevelList(AgentLevelDTO agentLevelDTO) ;

	/**
	 * 分页查询代理商等级列表
	 * @param AgentLevelDTO
	 * @return List<AgentLevelVO>
	 * @author 何文浪
	 * @时间 2017-5-15
	 */
	Result getAgentLevelPage(PageBean pageBean, AgentLevelDTO agentLevelDTO) ;

	/**
	 * 查询代理商等级列表单条详情--传值为主键
	 * @param AgentLevelDTO:setId
	 * @return AgentLevelVO
	 * @author 何文浪
	 * @时间 2017-5-15
	 */
	AgentLevelVO getAgentLevelListById(AgentLevelDTO agentLevelDTO) ;

}