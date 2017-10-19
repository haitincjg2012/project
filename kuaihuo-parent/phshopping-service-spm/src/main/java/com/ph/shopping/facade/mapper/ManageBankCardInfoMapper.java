package com.ph.shopping.facade.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.spm.dto.BankDTO;
import com.ph.shopping.facade.spm.entity.ManageBankCardInfo;
import com.ph.shopping.facade.spm.vo.BankVO;
import com.ph.shopping.facade.spm.vo.ManageBankCardInfoVO;
import com.ph.shopping.facade.spm.vo.RealAndIdCardNoVO;

/**
 * 
 * @ClassName: ManageBankCardInfoMapper
 * @Description: 银卡基础信息mapper
 * @author 王强
 * @date 2017年5月25日 下午2:46:22
 */
public interface ManageBankCardInfoMapper extends BaseMapper<ManageBankCardInfo> {
	/**
	 * 
	 * @Title: getManagerBankCardInfo
	 * @Description: 查询银行卡
	 * @author 王强
	 * @date 2017年5月25日 下午4:47:28
	 * @param userId
	 * @return
	 */
	ManageBankCardInfoVO getManagerBankCardInfo(@Param("userId") Long userId);

	/**
	 * 
	 * @Title: getRealAndIdCardNo
	 * @Description: 获取商户名称和身份证
	 * @author 王强
	 * @date 2017年7月13日 下午3:46:03
	 * @param userId
	 * @return
	 */
	List<RealAndIdCardNoVO> getRealAndIdCardNo(@Param("userId") Long userId);
	
	/**
	 * 
	* @Title: getBankInfo
	* @Description:获取银卡信息
	* @author 王强
	* @date  2017年7月13日 下午5:58:07
	* @param userId
	* @return
	 */
	BankVO getBankInfo(@Param("userId") Long userId);
	
	/**
	 * 
	* @Title: validateAuth
	* @Description: 校验是否认证
	* @author 王强
	* @date  2017年7月13日 下午6:16:37
	* @param userId
	* @return
	 */
	int validateAuth(@Param("userId") Long userId);
	
	/**
	 * 
	* @Title: updateBankInfo
	* @Description: 更新认证信息
	* @author 王强
	* @date  2017年7月14日 上午9:55:00
	* @param bankDTO
	* @return
	 */
	int updateBankInfo(BankDTO bankDTO);
	
	/**
	 * 
	* @Title: countBindBank
	* @Description:查询是否有绑定关系
	* @author 王强
	* @date  2017年7月14日 上午11:15:55
	* @param userId
	* @return
	 */
	int countBindBank(@Param("userId") Long userId);
}
