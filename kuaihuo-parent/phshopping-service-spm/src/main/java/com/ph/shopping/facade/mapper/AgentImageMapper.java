package com.ph.shopping.facade.mapper;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.spm.dto.AgentImageDTO;
import com.ph.shopping.facade.spm.entity.AgentImage;
import com.ph.shopping.facade.spm.vo.AgentImageVO;

import java.util.List;
/**
 * @项目  phshopping-service-spm
 * @描述   代理商图片持久层
 * @author 何文浪
 * @时间 2017-5-15
 * @version 2.1
 */
public interface AgentImageMapper extends BaseMapper<AgentImage> {

    /**
	 * 查询代理商图片列表
	 * @param AgentImageDTO
	 * @return List<AgentImageVO> 
	 * @author 何文浪
	 * @时间 2017-5-15
	 */
	List<AgentImageVO> getAgentImageList(AgentImageDTO agentImageDTO);
}