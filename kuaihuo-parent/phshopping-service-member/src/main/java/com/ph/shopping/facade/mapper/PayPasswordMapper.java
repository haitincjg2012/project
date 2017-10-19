package com.ph.shopping.facade.mapper;

import org.apache.ibatis.annotations.Param;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.member.dto.PayPasswordQueryDTO;
import com.ph.shopping.facade.member.entity.TradersPassword;

/**
 * 
 * phshopping-service-member
 *
 * @description：支付密码Mapper
 *
 * @author：liuy
 *
 * @createTime：2017年5月22日
 *
 * @Copyright @2017 by liuy
 */
public interface PayPasswordMapper extends BaseMapper<TradersPassword>{
	
	/**
     * 根据手机号查询支付密码 
     * @param dto
	 * @return TradersPassword 返回类型  
	 * @author 李杰
	 * @createTime 2017年3月14日
	 * @updateTime liuy重构 2017年05月23日
     */
	TradersPassword selectUserPayPwdByTelPhone(@Param("telPhone") String telPhone, @Param("customerType") Byte customerType);
	
	/**
     * 根据密码使用者和密码类型 修改支付密码   
     * @param tradersPassword
	 * @author 李杰
	 * @createTime 2017年3月14日
	 * @updateTime liuy重构 2017年05月23日
     */
	void updadtePayPwd(TradersPassword tradersPassword);

	/**
	 * @Description: 验证支付密码是否存在
	 * @author liuy
	 * @date  2017年6月8日 上午9:18:47
	 * @param dto
	 * @return
	 */
	int selectPayPwdIsExist(PayPasswordQueryDTO dto);
}
