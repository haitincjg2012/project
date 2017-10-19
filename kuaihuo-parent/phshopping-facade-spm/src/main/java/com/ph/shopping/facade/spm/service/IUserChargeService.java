package com.ph.shopping.facade.spm.service;

import java.math.BigDecimal;

import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.spm.dto.UserChargeBackDTO;
import com.ph.shopping.facade.spm.entity.UserCharge;
import com.ph.shopping.facade.spm.vo.CheckPayVO;

/**
 * 
 * @ClassName: IUserChargeRecordService
 * @Description: 后台用户充值记录生成接口
 * @author 王强
 * @date 2017年5月16日 下午4:34:45
 */
public interface IUserChargeService {
	/**
	 * 
	 * @Title: addUserChargeRecord
	 * @Description: 新增后台用户充值记录
	 * @author 王强
	 * @date 2017年5月17日 上午10:06:19
	 * @param userCharge
	 * @return
	 * @throws Exception
	 */
	public Result addUserChargeRecord(UserCharge userCharge);
	/**
	 * 
	* @Title: updateUSerChargeRecord
	* @Description:更新充值记录状态及相关信息
	* @author 王强
	 * @param score 
	* @date  2017年6月15日 上午11:36:59
	* @return
	 */
	public Result updateUSerChargeRecord(UserChargeBackDTO userChargeBackDTO);
	
	/**
	 * 
	* @Title: getUserCharegMd5Str
	* @Description: 获取MD5串
	* @author 王强
	* @date  2017年6月15日 下午4:15:33
	* @param orderNum
	* @return
	 */
	public CheckPayVO getUserCharegMd5Str(String orderNum);
}
