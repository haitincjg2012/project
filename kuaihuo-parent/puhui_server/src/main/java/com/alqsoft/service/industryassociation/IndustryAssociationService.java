package com.alqsoft.service.industryassociation;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.alqframework.easyui.EasyuiResult;
import org.alqframework.orm.BaseService;
import org.alqframework.result.Result;
import org.springframework.data.repository.query.Param;

import com.alqsoft.entity.bankcard.BankCard;
import com.alqsoft.entity.industryassociation.IndustryAssociation;
import com.alqsoft.entity.order.Order;

public interface IndustryAssociationService extends BaseService<IndustryAssociation>{

	public void industryAssociationFenRun(Order o);

	/**
	 * 行业协会后台--安全设置-修改手机号  原手机号码发送验证码   PHPF2017070305
	 * @param oldphone
	 * @param request 
	 * @return
	 */
	public Result sendMsgForOldPhone(String oldphone, String codeType, HttpServletRequest request);
	/**
	 * 行业协会后台--安全设置-修改手机号  新手机号码发送验证码   PHPF2017070306
	 * @param newPhone
	 * @return
	 */
	public Result sendMsgForNewPhone(String newPhone, String codeType);
	
	/**
	 * 行业协会列表
	 * @param map
	 * @param page
	 * @param rows
	 * @return
	 */
	/**
	 * 行业协会后台--安全设置-修改手机号 
	 * @return
	 */
	public Result updatephone(String oldPhone, String oldCode, String newPhone, String newCode,
			IndustryAssociation industryAssociation);

	/**
	 * 行业协会后台--安全设置-绑定银行卡   手机号发送验证码   PHPF2017070307
	 * @param bangPhone
	 * @param request 
	 * @return
	 */
	public Result sendMsgForBangPhone(String bangPhone, String codeType, HttpServletRequest request);
	/**
	 * 行业协会后台--安全设置-绑定银行卡
	 * @param phoneCode 
	 */
	public Result addIndustryAssociationBankCard(BankCard bankCard, String bangPhone, String phoneCode, IndustryAssociation industryAssociation);
	
	
	public EasyuiResult<List<IndustryAssociation>> findIndustryAssociationList(Map<String, Object> map, Integer page, Integer rows);

	/**
	 * 行业协会后台--安全设置-修改银行卡   手机号发送验证码   PHPF2017070308
	 * @param updatePhone
	 * @param request 
	 * @return
	 */
	public Result sendMsgForUpdatePhone(String updatePhone, String codeType, HttpServletRequest request);
	/**
	 * 行业协会后台--安全设置-修改银行卡
	 * @param updateCount 
	 * @param updateCountSelect 
	 * @return
	 */
	public Result updateindustryassociationbankcard(BankCard bankCard, String updatePhone, String phoneCode,
			IndustryAssociation industryAssociation, int updateCount, int updateCountSelect);
	
	/**
	 * 添加编辑行业协会
	 * @param industryAssociation
	 * @return
	 */
	public org.alqframework.webmvc.springmvc.Result addIndustryAssociation(IndustryAssociation industryAssociation,String onepassword,String twopassword);
	
	/**
	 * 查看是否存在该手机号
	 * @param phone
	 * @return
	 */
	public IndustryAssociation checkHasPhone(String phone);
	
	
	/**
	 * 查看行业协会详情
	 * @param id
	 * @return
	 */
	public IndustryAssociation getAssociationById(Long id);

	public IndustryAssociation getRowLock(Long valueOf);

	public IndustryAssociation getIndustryAssociationByPhone(String phone);
	
}
