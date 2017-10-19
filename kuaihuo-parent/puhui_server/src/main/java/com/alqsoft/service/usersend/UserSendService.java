package com.alqsoft.service.usersend;

import org.alqframework.result.Result;

/**
 * 
 * @Description: TODO
 * @author Wudi
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年3月10日 下午3:54:50
 * Copyright 
 */
public interface UserSendService {
	
	Result sendMessageCode(String phone,String codeType);
	
	Result checkMsg(String phone,String image,String codeType);

}
