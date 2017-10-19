package com.alqsoft.service.hunter;

import java.util.Map;

import org.alqframework.orm.BaseService;
import org.alqframework.result.Result;

import com.alqsoft.entity.bankcard.BankCard;
import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.member.Member;
import com.alqsoft.entity.order.Order;
import com.alqsoft.vo.PhHunterRuleAttachmentVO;

public interface HunterService extends BaseService<Hunter>{

	/**
	 * 驻地区域接口
	 * @return
	 */
	public Result updateHunterArea(Hunter hunter);

	/**
	 * 注册-完善批发商信息接口
	 * @return
	 */
	public Result addHunter(Long merberId, Hunter hunter,PhHunterRuleAttachmentVO phVO);
	/**
	 * 安全设置-批发商身份认证
	 * @param name
	 * @param card
	 * @return
	 */
	public Result checkHunterNameAndCard(String name, String card, Member member);
	
	public Hunter getRowLock(Long id);

	/**
	 * 安全设置-添加银行卡
	 * @param bankCard
	 * @param member
	 * @return
	 */
	public Result addHunterBankCard(BankCard bankCard, Member member);

	/**
	 * 安全设置-修改手机号  原手机号码发送验证码   PHPF2017070301
	 * @param phone
	 * @return
	 */
	public Result sendMsgForOldPhone(Member member, String phone, String codeType);
	/**
	 * 安全设置-修改手机号  新手机号码发送验证码   PHPF2017070302
	 * @param newphone
	 * @return
	 */
	public Result sendMsgForNewPhone(Member member, String newphone, String codeType);
	/**
	 * 安全设置-修改手机号
	 * @param oldphone
	 * @param oldcode
	 * @param newphone
	 * @param newcode
	 * @param member 
	 * @param userSign 
	 * @param imPassword 
	 * @param identifilter 
	 * @return
	 */
	public Result updatePhone(String oldphone, String oldcode, String newphone, String newcode, Member member, String identifilter, String imPassword, String userSign);
	/**
	 * 安全设置-修改银行卡
	 */
	public Result updateHunterBankCard(BankCard bankCard, Member member);
	/**
	 * 安全设置-删除银行卡
	 * @param id
	 * @return
	 */
	public Result deleteHunterBankCard(Long id, Member member);
	/**
	 * 批发商个人中心--所属协会编辑
	 * @return
	 */
	public Result updateIndustryAssociation(Long id, Member member);
	
	
	public Map<String, Object> checkHunterLevel(Long orderMoney, Long goodCommentNum);
    /***
     * 批发商个人中心--行业分类
     * @param cId
     * @param hId
     * @return
     */
	public void upateIndustryClassify(Long cId,Long hId);

	public void updateOrderMoneyByOrderId(Order order);

	public void updateCommentNumOrder(Order order);
	/**
	 * 批发商个人中心--店铺管理--添加背景图
	 * @return
	 */
	public Result addhunterbackgroundimage(Long hunterId, Long imageId);
	/**
	 * 注册-完善批发商信息一期优化接口
	 * @return
	 */
	public Result addhunterLabel(Long merberId, Hunter hunter, String industryTypeIdList);
	/**
	 * 批发商个人信息编辑- 标签分类接口
	 * @param industryTypeIdList 
	 * @return
	 */
	public Result updateHunterlabel(Long id, Member member, String industryTypeIdList);
	
	public Result addhunterLabelAndAttachment(Long merberId, Hunter hunter, String industryTypeIdList,PhHunterRuleAttachmentVO phVO);

    public Result hunterarearang(Long hunterId);

	public void updatehunterarearang(Long districtId, String districts, Long hunterId);
}
