package com.alqsoft.service.memberconnect;

import com.alqsoft.entity.memberconnect.MemberConnect;

/**
 * 
*@Description: 
* 
* @author : zhuangll
* @ProjectName : haitunwu-interface
* @version : v4.0 - 2014
* @createTime : 2014-8-18 下午3:42:06
*
 */
public interface MemberConnectService {
	public MemberConnect getMemberConnByAccount(String thirdAccount,String thirdType);
}
