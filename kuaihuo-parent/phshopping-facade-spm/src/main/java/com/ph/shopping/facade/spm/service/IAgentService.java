package com.ph.shopping.facade.spm.service;

import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.spm.dto.AgentDTO;
import com.ph.shopping.facade.spm.entity.Agent;
import com.ph.shopping.facade.spm.vo.AgentVO;

import java.util.List;
/**
 * @项目  phshopping-service-spm
 * @描述   代理商业务层接口
 * @author 何文浪
 * @时间 2017-5-15
 * @version 2.1
 */
public interface IAgentService {

	/**
	 * 查询代理商列表详情
     * @param agentDTO
     * @return List<AgentVO>
	 * @author 何文浪
	 * @时间 2017-5-15
	 */
	List<AgentVO> getAgentListDateil(AgentDTO agentDTO) ;
	
	/**
	 * 查询代理商列表
     * @param agentDTO
     * @return List<AgentVO>
	 * @author 何文浪
	 * @时间 2017-5-15
	 */
	List<AgentVO> getAgentList(AgentDTO agentDTO) ;
	
	
	/**
	 * 查询根据区域id查找上级和当级所有代理商列表详情
     * @param agentDTO:setPositionId
     * @return List<AgentVO>
	 * @author 何文浪
	 * @时间 2017-5-17
	 */
	List<AgentVO> getAgentPositionList(AgentDTO agentDTO) ;
	/**
	 * 查询根据区域id查找下级和当级所有代理商列表详情
     * @param agentDTO:setPositionId
     * @return List<AgentVO>
	 * @author 何文浪
	 * @时间 2017-5-17
	 */
	  List<AgentVO> getAgentChildList(AgentDTO agentDTO);
	  /**
	   * 查询根据区域查找下级和当级所有代理商
	   * @param agentDTO
	   * @return List<AgentVO>
	   * @author 何文浪
	   * @时间 2017-5-17
	   */
	  List<AgentVO> getPositionGetNextAgentList(AgentDTO agentDTO);
	/**
	 * 分页查询代理商列表
     * @param agentDTO
     * @return Result
	 * @author 何文浪
	 * @时间 2017-5-15
	 */
	Result getAgentPage(PageBean pageBean, AgentDTO agentDTO) ;
	
	/**
	 * 查询代理商列表详情单条详情--传值为主键
     * @param agentDTO:setId
     * @return AgentVO
	 * @author 何文浪
	 * @时间 2017-5-15
	 */
	AgentVO getAgentVODateilById(AgentDTO agentDTO) ;
	
	/**
	 * 查询代理商列表单条详情--传值为主键
     * @param agentDTO:setId
     * @return AgentVO
	 * @author 何文浪
	 * @时间 2017-5-15
	 */
	AgentVO getAgentVOListById(AgentDTO agentDTO) ;
	
	/**
	 * 根据商户来查找当前的市代理 
     * @param agentDTO:userId,agentLevelId
     * @return List<AgentVO>
	 * @author 何文浪
	 * @时间 2017-5-27
	 */
	 AgentVO getAgentByMerchant(AgentDTO agentDTO);
	 /**
	  * 添加代理商
	  * @param agentDTO
	  * @return Result
	  * @author 何文浪
	  * @时间 2017-6-2
	  */
	 Result addAgent(AgentDTO agentDTO);
	 /**
	  * 修改代理商
      * @param agentDTO
      * @return Result
	  * @author 何文浪
	  * @时间 2017-5-27
	  */
	 Result updateAgent(AgentDTO agentDTO);

    /**
     * 页面修改代理商 包括修改图片
     *
     * @param agentDTO
     * @return Result
     * @author 熊克文
     * @时间 2017-6-6
     */
    Result updateAgentAndImg(AgentDTO agentDTO);

	/**
     * 根据市区县id 返回对应的代理商
     *
	 * @param cityId   市id
	 * @param countyId 区id
	 * @param townId   社区id
	 * @return Result
	 * @author 熊克文
	 * @时间 2017-6-6
	 */
    List<AgentVO> getAgentVoByPosition(Long cityId, Long countyId, Long townId);

	Agent getAgentById(Long agentId);
}