package com.alqsoft.rpc.mobile.impl;

import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.alqsoft.rpc.mobile.RpcHunterStoreService;
import com.alqsoft.service.hunterstore.HunterStoreService;

@Controller
public class RpcHunterStoreServiceImpl implements RpcHunterStoreService{
	
	@Autowired
	private HunterStoreService hunterStoreService;

	@Override
	public Result saveHunterStore(Long id,Long hunterid, String address, String content) {
		// TODO Auto-generated method stub
		return hunterStoreService.saveHunterStore(id, hunterid, address, content);
	}

	@Override
	public Result deleteHunterStore(Long id,Long hunterid) {
		// TODO Auto-generated method stub
		return hunterStoreService.deleteHunterStore(id,hunterid);
	}

}
