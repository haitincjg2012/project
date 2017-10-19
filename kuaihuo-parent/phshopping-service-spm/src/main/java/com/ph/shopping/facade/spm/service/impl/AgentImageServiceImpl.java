package com.ph.shopping.facade.spm.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.mapper.AgentImageMapper;
import com.ph.shopping.facade.spm.dto.AgentImageDTO;
import com.ph.shopping.facade.spm.exception.AgentExceptionEnum;
import com.ph.shopping.facade.spm.service.AgentImageService;
import com.ph.shopping.facade.spm.vo.AgentImageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @项目  phshopping-service-spm
 * @描述   代理商图片业务层
 * @author 何文浪
 * @时间 2017-5-15
 * @version 2.1
 */
@Component
@Service(version = "1.0.0")
public class AgentImageServiceImpl implements AgentImageService {
	//实例化代理商图片持久层
	@Autowired
	private AgentImageMapper agentImageMapper;

    @Override
	public List<AgentImageVO> getAgentImageList(AgentImageDTO agentImageDTO) {
		return agentImageMapper.getAgentImageList(agentImageDTO);
	}
	
	@Override
	public Result getAgentImageList(PageBean pageBean,AgentImageDTO agentImageDTO) {
		//分页
		PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
		List<AgentImageVO>  list = agentImageMapper.getAgentImageList(agentImageDTO);
		PageInfo<AgentImageVO> pageInfo = new PageInfo<AgentImageVO>(list);
		return new Result(true,AgentExceptionEnum.SELECT_AGENT_EXCEPTION.getCode(), "", pageInfo.getList(),
				pageInfo.getTotal());
	}
	
	@Override
	public AgentImageVO getAgentImageListById(AgentImageDTO agentImageDTO) {
		List<AgentImageVO>  list = agentImageMapper.getAgentImageList(agentImageDTO);
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
}
