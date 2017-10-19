package com.ph.shopping.facade.member.service;

import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.member.dto.PayPasswordAddDTO;
import com.ph.shopping.facade.member.dto.PayPasswordQueryDTO;
import com.ph.shopping.facade.member.dto.PayPasswordUpdateDTO;

/**
 * 
 * phshopping-facade-member
 *
 * @description：支付密码接口
 *
 * @author：liuy
 *
 * @createTime：2017年5月22日
 *
 * @Copyright @2017 by liuy
 */
public interface IPayPasswordService {

	/**
	 * @Title: getPayPwdInfo
	 * @Description: 根据条件查询会员支付密码 
	 * @author liuy
	 * @date  2017年6月8日 上午9:31:24
	 * @param dto
	 * @return
	 */
	Result getPayPwdInfo(PayPasswordQueryDTO dto);
	
	/**
	 * @Title: getPayPwdInfoByTelphone
	 * @Description: 根据手机号查询会员支付密码 
	 * @author liuy
	 * @date  2017年6月8日 上午9:31:24
	 * @param dto
	 * @return
	 */
	Result getPayPwdInfoByTelphone(String telPhone, Byte customerType);
	
	/**
     * 新增支付密码
     * @param tradersPassword
	 * @author 李杰
	 * @createTime 2017年3月14日
	 * @updateTime liuy重构 2017年05月16日
     */
	Result addPayPassword(PayPasswordAddDTO dto);
	
	/**
     * 修改支付密码  
     * @param dto
	 * @author 李杰
	 * @createTime 2017年3月14日
	 * @updateTime liuy重构 2017年05月16日
     */
	Result updatePayPassword(PayPasswordUpdateDTO dto);
	
	/**
	 * @Title: verifyPayPwdIsExists
	 * @Description: 验证支付密码是否存在
	 * @author liuy
	 * @date  2017年6月8日 上午9:16:28
	 * @param dto
	 * @return
	 */
	Result verifyPayPwdIsExists(PayPasswordQueryDTO dto);
}
