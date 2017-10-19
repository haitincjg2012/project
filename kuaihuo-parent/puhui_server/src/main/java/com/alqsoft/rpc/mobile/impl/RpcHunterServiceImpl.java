package com.alqsoft.rpc.mobile.impl;

import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.alqsoft.entity.bankcard.BankCard;
import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.member.Member;
import com.alqsoft.rpc.mobile.RpcHunterService;
import com.alqsoft.service.hunter.HunterService;
import com.alqsoft.vo.PhHunterRuleAttachmentVO;
/**
 * Date:     2017年3月1日  16:50:41 <br/>
 * @author   dinglanlan
 * @version  批发商
 * @see 	 
 */

@Controller
public class RpcHunterServiceImpl implements RpcHunterService {


	@Autowired
	private HunterService hunterService;
	
	/**
	 * 驻地区域接口
	 * @return
	 */
	@Override
	public Result updateHunterArea(Hunter hunter) {
		// TODO Auto-generated method stub
		return hunterService.updateHunterArea(hunter);
	}
	
	/**
	 * 注册-完善批发商信息接口
	 * @return
	 */
	@Override
	public Result addHunter(Long merberId, Hunter hunter,PhHunterRuleAttachmentVO phVO) {
		// TODO Auto-generated method stub
		
		return hunterService.addHunter(merberId,hunter,phVO);
	}

	/**
	 * 安全设置-批发商身份认证
	 * @param name
	 * @param card
	 * @return
	 */
	@Override
	public Result checkHunterNameAndCard(String name, String card, Member member) {
		
		return hunterService.checkHunterNameAndCard(name,card,member);
	}
	/**
	 * 安全设置-添加银行卡
	 * @param bankCard
	 * @param member
	 * @return
	 */
	@Override
	public Result addHunterBankCard(BankCard bankCard, Member member) {
		// TODO Auto-generated method stub
		
		return hunterService.addHunterBankCard(bankCard,member);
	}

	/**
	 * 安全设置-修改手机号  原手机号码发送验证码   PHPF2017070301
	 * @param phone
	 * @return
	 */
	@Override
	public Result sendMsgForOldPhone(Member member, String oldphone, String codeType) {
		// TODO Auto-generated method stub
		return hunterService.sendMsgForOldPhone(member,oldphone,codeType);
	}
	/**
	 * 安全设置-修改手机号  新手机号码发送验证码   PHPF2017070302
	 * @param newphone
	 * @return
	 */
	@Override
	public Result sendMsgForNewPhone(Member member, String newphone, String codeType) {
		// TODO Auto-generated method stub
		return hunterService.sendMsgForNewPhone(member,newphone,codeType);
	}
	/**
	 * 安全设置-修改手机号
	 * @param oldphone
	 * @param oldcode
	 * @param newphone
	 * @param newcode
	 * @return
	 */
	@Override
	public Result updatePhone(String oldphone, String oldcode, String newphone, String newcode, Member member, String identifilter, String imPassword, String userSign) {
		// TODO Auto-generated method stub
		return hunterService.updatePhone(oldphone,oldcode,newphone,newcode,member,identifilter,imPassword,userSign);
	}

	/**
	 * 安全设置-修改银行卡
	 */
	@Override
	public Result updateHunterBankCard(BankCard bankCard, Member member) {
		
		return hunterService.updateHunterBankCard(bankCard,member);
	}
	/**
	 * 安全设置-删除银行卡
	 * @param id
	 * @return
	 */
	@Override
	public Result deleteHunterBankCard(Long id, Member member) {
		
		return hunterService.deleteHunterBankCard(id,member);
	}
	/**
	 * 批发商个人中心--所属协会编辑
	 * @return
	 */
	@Override
	public Result updateIndustryAssociation(Long id, Member member) {
		// TODO Auto-generated method stub
		return hunterService.updateIndustryAssociation(id,member);
	}
	/***
	 * 批发商个人中心--行业分类
	 */
	public  void upateIndustryClassify(Long cId,Long hId){
		
		 hunterService.upateIndustryClassify(cId,hId);
	}
	/**
	 * 批发商个人中心--店铺管理--添加背景图
	 * @return
	 */
	@Override
	public Result addhunterbackgroundimage(Long hunterId, Long imageId) {
	
		return hunterService.addhunterbackgroundimage(hunterId,imageId);
	}

	/**
	 * 注册-完善批发商信息一期优化接口
	 * @return
	 */
	@Override
	public Result addhunterLabel(Long merberId, Hunter hunter, String industryTypeIdList) {
		
		return hunterService.addhunterLabel(merberId,hunter,industryTypeIdList);
	}

	/**
	 * 批发商个人信息编辑- 标签分类接口
	 * @return
	 */
	@Override
	public Result updateHunterlabel(Long id, Member member, String industryTypeIdList) {
		
		return hunterService.updateHunterlabel(id,member,industryTypeIdList);
	}

	@Override
	public Result addhunterLabelAndAttachment(Long merberId, Hunter hunter, String industryTypeIdList,
			PhHunterRuleAttachmentVO phVO) {
		return hunterService.addhunterLabelAndAttachment(merberId, hunter, industryTypeIdList, phVO);
	}

	@Override
	public Result hunterarearang(Long hunterId) {
		return hunterService.hunterarearang(hunterId);
	}

	@Override
	public void updatehunterarearang(Long districtId, String districts, Long hunterId) {
		hunterService.updatehunterarearang(districtId,districts,hunterId);
	}

	
}
