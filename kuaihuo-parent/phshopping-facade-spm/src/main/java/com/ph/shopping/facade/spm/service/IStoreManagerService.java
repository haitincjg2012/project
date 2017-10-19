package com.ph.shopping.facade.spm.service;

import com.ph.shopping.common.util.result.Result;

/**
 * 
* @ClassName: IUserDrawCashService
* @Description: 提现记录接口
* @author 王强
* @date 2017年5月19日 上午9:57:37
 */
public interface IStoreManagerService {
	public Result getStoreMangerByUserId(Long merchantId);

	/**
	 * 解聘店面经理
	 * @param id
	 */
	public void updateStoreManager(Long id);


}
