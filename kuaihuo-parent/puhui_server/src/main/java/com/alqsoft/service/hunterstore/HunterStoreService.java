package com.alqsoft.service.hunterstore;

import org.alqframework.orm.BaseService;
import org.alqframework.result.Result;

import com.alqsoft.entity.hunterstoreattachment.HunterStoreAttachment;

/**
 * 批发商店铺
 * @author Administrator
 *
 */
public interface HunterStoreService extends BaseService<HunterStoreAttachment>{

	/**
	 * 添加和保存
	 * @param id
	 * @param address
	 * @param content
	 * @return
	 */
	public Result saveHunterStore(Long id,Long hunterid,String address,String content); 
	
	public Result deleteHunterStore(Long id,Long hunterid);
}
