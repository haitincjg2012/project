package com.ph.shopping.facade.spm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.mapper.AgentLevelMapper;
import com.ph.shopping.facade.spm.dto.AgentLevelDTO;
import com.ph.shopping.facade.spm.exception.AgentExceptionEnum;
import com.ph.shopping.facade.spm.service.AgentLevelService;
import com.ph.shopping.facade.spm.vo.AgentLevelVO;

/**
 * @项目  phshopping-service-spm
 * @描述   代理商类别业务层
 * @author 何文浪
 * @时间 2017-5-15
 * @version 2.1
 */
@Component
@Service(version = "1.0.0")
public class AgentLevelServiceImpl implements AgentLevelService {
	//初始化代理商类别持久层
	@Autowired
	private AgentLevelMapper agentLevelMapper;
	
	@Override
	public List<AgentLevelVO> getAgentLevelList(AgentLevelDTO agentLevelDTO) {
		return agentLevelMapper.getAgentLevelList(agentLevelDTO);
	}
	
	@Override
	public Result getAgentLevelPage(PageBean pageBean,AgentLevelDTO agentLevelDTO) {
		//分页
		PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
		List<AgentLevelVO> list = agentLevelMapper.getAgentLevelList(agentLevelDTO);
		PageInfo<AgentLevelVO> pageInfo = new PageInfo<AgentLevelVO>(list);
		return new Result(true,AgentExceptionEnum.SELECT_AGENT_EXCEPTION.getCode(), "", pageInfo.getList(),
				pageInfo.getTotal());
	}
	
	@Override
	public AgentLevelVO getAgentLevelListById(AgentLevelDTO agentLevelDTO) {
		List<AgentLevelVO> list =  agentLevelMapper.getAgentLevelList(agentLevelDTO);
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
}
