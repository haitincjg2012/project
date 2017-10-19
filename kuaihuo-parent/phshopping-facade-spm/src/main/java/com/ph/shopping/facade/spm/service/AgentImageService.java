package com.ph.shopping.facade.spm.service;

import java.util.List;

import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.spm.dto.AgentImageDTO;
import com.ph.shopping.facade.spm.vo.AgentImageVO;
/**
 * @项目  phshopping-service-spm
 * @描述   代理商图片业务层接口
 * @author 何文浪
 * @时间 2017-5-15
 * @version 2.1
 */
public interface AgentImageService {

	/**
	 * 查询代理商图片列表
	 * @param AgentImageDTO
	 * @return List<AgentImageVO> 
	 * @author 何文浪
	 * @时间 2017-5-15
	 */
	List<AgentImageVO> getAgentImageList(AgentImageDTO agentImageDTO) ;

	/**
	 * 分页查询代理商图片列表
	 * @param AgentImageDTO
	 * @return List<AgentImageVO> 
	 * @author 何文浪
	 * @时间 2017-5-15
	 */
	Result getAgentImageList(PageBean pageBean, AgentImageDTO agentImageDTO) ;

	/**
	 * 查询代理商图片列表单条查询--传值为主键
	 * @param AgentImageDTO:setId
	 * @return AgentImageVO
	 * @author 何文浪
	 * @时间 2017-5-15
	 */
	AgentImageVO getAgentImageListById(AgentImageDTO agentImageDTO) ;

}