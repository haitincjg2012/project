package com.alqsoft.service.impl.hunterrule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.dao.hunterrule.HunterRuleDao;
import com.alqsoft.entity.member.Member;
import com.alqsoft.service.hunterrule.HunterRuleService;

@Service
@Transactional(readOnly=true)
public class HunterRuleServiceImpl implements HunterRuleService {
	
	@Autowired
	private HunterRuleDao hunterRuleDao;

	@Override
	public Result getRoleList(Member member,Integer page, Integer rows) {
		if(member==null){
	 		return ResultUtils.returnError("没有批发商信息");
		}
		Long hunter_id = member.getHunter().getId();
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("startIndex", (page-1)*rows);
		params.put("endIndex",rows);
		params.put("hunter_id", hunter_id);
		
		List<Map<String,Object>> roleList = hunterRuleDao.getRoleList(params);
		
		return ResultUtils.returnSuccess("列表集合", roleList);
		
		
	}
	

}
