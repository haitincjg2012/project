package com.ph.shopping.facade.mapper;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.spm.dto.AgentDTO;
import com.ph.shopping.facade.spm.entity.Agent;
import com.ph.shopping.facade.spm.vo.AgentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @项目  phshopping-service-spm
 * @描述   代理商持久层
 * @author 何文浪
 * @时间 2017-5-15
 * @version 2.1
 */
public interface AgentMapper extends BaseMapper<Agent>{
	/**
	 * 查询代理商列表详情
	 * @param AgentDTO
	 * @return List<AgentVO>
	 * @author 何文浪
	 * @时间 2017-5-15
	 */
	List<AgentVO> getAgentListDateil(AgentDTO agentDTO);
	/**
	 * 查询代理商列表
	 * @param AgentDTO
	 * @return List<AgentVO>
	 * @author 何文浪
	 * @时间 2017-5-15
	 */
	List<AgentVO> getAgentList(AgentDTO agentDTO);
	//获取供应商的列表展示
	List<Map> getAgentListMap(AgentDTO agentDTO);
	/**
	 * 通过区域查询代理上级和本级商列表
	 * @param AgentDTO
	 * @return List<AgentVO>
	 * @author 何文浪
	 * @时间 2017-6-13
	 */
	List<AgentVO> getPositionGetAgentList(AgentDTO agentDTO);
	/**
	 * 通过区域查询所有代理商列表
	 * @param AgentDTO
	 * @return List<AgentVO>
	 * @author 何文浪
	 * @时间 2017-6-13
	 */
	List<AgentVO> getPositionGetAllAgentList(AgentDTO agentDTO);
	/**
	 * 通过区域查询下级及本级代理商列表
	 * @param AgentDTO
	 * @return List<AgentVO>
	 * @author 何文浪
	 * @时间 2017-6-13
	 */
	List<AgentVO> getPositionGetNextAgentList(AgentDTO agentDTO);
	/**
	 * 查询父级代理商列表
	 * @param AgentDTO
	 * @return List<AgentVO>
	 * @author 何文浪
	 * @时间 2017-5-15
	 */
	List<AgentVO> getAgentParentList(AgentDTO agentDTO);
	/**
	 * 查询子级及当前级代理商列表
	 * @param AgentDTO
	 * @return List<AgentVO>
	 * @author 何文浪
	 * @时间 2017-5-15
	 */
	List<AgentVO> getAgentChildList(AgentDTO agentDTO);
	
	/**
	 * 根据商户来查找当前的市代理 
	 * @param AgentDTO
	 * @return List<AgentVO>
	 * @author 何文浪
	 * @时间 2017-5-27
	 */
	List<AgentVO> getAgentByMerchantList(AgentDTO agentDTO);
	
	/**
	 * 修改当前级子级的父id
	 * @param AgentDTO
	 * @return void
	 * @author 何文浪
	 * @时间 2017-6-1
	 */
	void updateAgentChildByParent(AgentDTO agentDTO);

	/**
	 * 根据市区县id 返回对应的代理商id 没有则返回oL
	 *
	 * @param agentDTO
	 * @return Result
	 * @author 熊克文
	 * @时间 2017-6-6
	 */

	List<AgentVO> getAgentVoByPosition(AgentDTO agentDTO);
	
	
	/**
	 * @param id
	 * 
	 * @return
	 */
	Agent getAgentById(@Param("id")Long id);
}