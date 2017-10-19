package com.alqsoft.rpc.mobile;

import org.alqframework.result.Result;

/**
 * 
 * @Description: TODO
 * @author Wudi
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年3月10日 下午3:42:26
 * Copyright 
 */
public interface RpcUserSendService {
	
	Result sendMessageCode(String phone,String codeType);
	
	Result  checkMsg(String phone,String image,String  codeType);

}
