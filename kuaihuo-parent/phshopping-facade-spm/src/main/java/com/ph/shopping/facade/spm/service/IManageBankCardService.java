package com.ph.shopping.facade.spm.service;

import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.permission.vo.SessionUserVO;
import com.ph.shopping.facade.spm.dto.BankDTO;
import com.ph.shopping.facade.spm.entity.ManageBankCardInfo;
import com.ph.shopping.facade.spm.vo.BankVO;

/**
 * @项目：phshopping-facade-permission
 *
 * @描述： 绑定银行卡接口服务
 *
 * @作者： Mr.Shu
 *
 * @创建时间：2017-03-15
 *
 * @Copyright @2017 by Mr.Shu
 */
public interface IManageBankCardService {

    /**
     * @描述：绑定银行卡
     *
     * @param bankCardInfo
     * @param sessionUserVo
     *
     * @return:
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/4/25 17:56
     */
    Result bindCard(ManageBankCardInfo bankCardInfo, SessionUserVO sessionUserVo);

    /**
     * @描述：解绑银行卡
     * @param: bankCardInfo
     * @param: sessionUserVo
     * @return:
     * @作者： Mr.Shu
     * @创建时间：2017/4/25 17:55
     */
    Result unBindCard(ManageBankCardInfo bankCardInfo, SessionUserVO sessionUserVo);


    /**
     * @描述：通过用户获取绑定银行卡信息
     *
     * @param: bankCardInfo
     *
     * @return:
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/4/25 17:55
     */
    Result getBindCardInfo(ManageBankCardInfo bankCardInfo);

    /**
     * 
    * @Title: getRealNameInfo
    * @Description: 获取实名认证
    * @author 王强
    * @date  2017年7月13日 下午3:42:14
    * @return
     */
	Result getRealNameAndIdCardNoInfo(Long userId);
	
	/**
	 * 
	* @Title: insertManageBankCard
	* @Description: 新增认证信息 
	* @author 王强
	* @date  2017年7月13日 下午4:20:33
	* @param bankCardInfo
	* @return
	 */
	int insertRealNameAndIdCardNo(ManageBankCardInfo bankCardInfo) throws Exception;
	
	/**
	 * 
	* @Title: getBankInfo
	* @Description: 获取银卡信息
	* @author 王强
	* @date  2017年7月13日 下午5:58:58
	* @param userId
	* @return
	 */
	BankVO getBankInfo(Long userId);
	
	/**
	 * 
	* @Title: insertBindBank
	* @Description: 新增银行卡绑定
	* @author 王强
	* @date  2017年7月14日 上午10:08:40
	* @param bankDTO
	* @return
	 */
	Result insertBindBank(BankDTO bankDTO) throws Exception;

    Result updateBankCardInfo(Long userId,BankDTO bankDTO) throws Exception;
}
