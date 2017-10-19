package com.alqsoft.rpc.mobile;

import org.alqframework.result.Result;

/**
 * 批发商店铺
 * @author Administrator
 *
 */
public interface RpcHunterStoreService {

	public Result saveHunterStore(Long id,Long hunterid,String address,String content); 
	
	public Result deleteHunterStore(Long id,Long hunterid);
	
}
