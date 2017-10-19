package com.ph.spm.api.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.shopping.facade.spm.dto.AgentLevelDTO;
import com.ph.shopping.facade.spm.service.AgentLevelService;
import com.ph.shopping.facade.spm.vo.AgentLevelVO;

/**
 * @项目 phshopping-api-platform
 * @描述   代理商类别控制层
 * @author 何文浪
 * @时间 2017-5-26
 * @version 2.1
 */
@Controller
@RequestMapping("web/agentLevel")
public class AgentLevelController {
	@Reference(version = "1.0.0")
	private AgentLevelService agentLevelService;
	/**
	 * 查询代理商类别
	 * @param AgentLevelDTO
	 * @return List<AgentLevelVO>
	 * @author 何文浪
	 * @时间 2017-5-26
	 */
	@RequestMapping(value="/getAgentLevelList")
	@ResponseBody
	public List<AgentLevelVO> getAgentLevelList(AgentLevelDTO agentLevelDTO) {
		return agentLevelService.getAgentLevelList(agentLevelDTO);
	}
}
