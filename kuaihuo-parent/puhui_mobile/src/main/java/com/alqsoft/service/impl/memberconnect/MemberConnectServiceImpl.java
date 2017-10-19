package com.alqsoft.service.impl.memberconnect;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.dao.memberconnect.MemberConnectDao;
import com.alqsoft.entity.memberconnect.MemberConnect;
import com.alqsoft.service.memberconnect.MemberConnectService;

/***
 * 
*@Description: 
* 
* @author : zhuangll
* @ProjectName : haitunwu-interface
* @version : v4.0 - 2014
* @createTime : 2014-8-18 下午3:43:54
*
 */
@Service
@Transactional(readOnly = true)
public class MemberConnectServiceImpl implements MemberConnectService {
	@Autowired
	private MemberConnectDao dao;

	@Override
	public MemberConnect getMemberConnByAccount(String thirdAccount, String thirdType) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("thirdAccount", thirdAccount);
		map.put("thirdType", thirdType);
		return dao.getMemberConnByAccount(map);
	}
}
