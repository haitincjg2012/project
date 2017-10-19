package com.ph.shopping.facade.spm.service;

import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.spm.dto.UserDrawCashDTO;

import java.util.Map;

/**
 * 
* @ClassName: IUserDrawCashService
* @Description: 提现记录接口
* @author 王强
* @date 2017年5月19日 上午9:57:37
 */
public interface IUserDrawCashService {
	/**
	 * 
	* @Title: addDrawCashRecord
	* @Description: 新增提现记录
	* @author 王强
	* @date  2017年5月19日 上午9:58:17
	* @return
	 */
	Result addDrawCashRecord(UserDrawCashDTO userDrawcashDTO);

    //String verifySingNotifyForYPay(Map<String, String> );
}
