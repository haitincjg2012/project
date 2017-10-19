package com.ph.shopping.facade.mapper;

import org.apache.ibatis.annotations.Param;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.spm.entity.ManageBankCardBind;

/**
 * 
* @ClassName: ManageBankCardBindMapper
* @Description: 银卡绑定关系
* @author 王强
* @date 2017年5月25日 下午4:25:51
 */
public interface ManageBankCardBindMapper extends BaseMapper<ManageBankCardBind> {
	/**
	 * 
	 * @Title: updateBankCardBind
	 * @Description:更新绑定状态
	 * @author 王强
	 * @date 2017年5月25日 下午4:20:30
	 * @param userId
	 * @param bindStatus
	 * @return
	 */
	int updateBankCardBind(@Param("userId") Long userId);
	/**
	 * 
	* @Title: updateBankCardBindStatus
	* @Description: 更新绑定关系
	* @author 王强
	* @date  2017年7月14日 上午11:32:39
	* @param bindStatus
	* @param userId
	* @return
	 */
	int updateBankCardBindStatus(@Param("bindStatus") int bindStatus, @Param("userId") Long userId);
}
