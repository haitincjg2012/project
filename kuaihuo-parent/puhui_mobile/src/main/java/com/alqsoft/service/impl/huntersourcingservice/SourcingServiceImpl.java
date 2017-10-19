package com.alqsoft.service.impl.huntersourcingservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.dao.huntersourcingservice.SourcingServiceDao;
import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.hunterservice.HunterService;
import com.alqsoft.entity.member.Member;
import com.alqsoft.rpc.mobile.RpcHunterServiceRuleService;
import com.alqsoft.rpc.mobile.RpcHunterSourcingService;
import com.alqsoft.service.huntersourcingservice.SourcingService;

@Service
@Transactional(readOnly=true)
public class SourcingServiceImpl implements SourcingService{
	
	//查询使用dao
	@Autowired
	private SourcingServiceDao sourcingServiceDao;
	
	//添加需要走rpc通道
	@Autowired
	private RpcHunterSourcingService rpcHunterSourcingService;
	
	
	//查询
	@Override
	public Result getSourcingServiceList( Integer page, Integer rows, Member member) {
		HashMap<String, Object> param = new HashMap<String,Object>();
		
		Hunter hunter=member.getHunter();
		
		if(null==hunter){
			
			return ResultUtils.returnError("角色异常");
		}
		//判断当前登录的批发商ID和所传过来的ID是否一致
		Long hunter_id=member.getHunter().getId();//登录的批发商id
		param.put("hunter_id", hunter_id);
		param.put("startIndex", (page-1)*rows);
		param.put("endIndex",rows);
		
		List<Map<String,Object>> SourcingServiceList = sourcingServiceDao.getSourcingServiceList(param);
		
		if(SourcingServiceList.size()>0){
			
			return ResultUtils.returnSuccess("批发商货源服务列表获取成功", SourcingServiceList);
		}else{
			return ResultUtils.returnError("没有数据");
		}
	}

	//添加
	@Override
	public Result saveOrModifySourcingService(String detail, Member member) {
		Hunter hunter = member.getHunter();
		if(hunter==null){
			return ResultUtils.returnError("没有对应的批发商会员,信息错误");
		}
		return rpcHunterSourcingService.saveOrModifySourcingService(detail,member);
	}
	
	
	
		

}
