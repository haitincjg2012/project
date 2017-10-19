package com.alqsoft.controller.mobile.after.hunter;

import com.alqsoft.entity.hunter.Hunter;
import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.bouncycastle.crypto.modes.PaddedBlockCipher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alqsoft.anno.Explosionproof;
import com.alqsoft.anno.MemberAnno;
import com.alqsoft.entity.bankcard.BankCard;
import com.alqsoft.entity.member.Member;
import com.alqsoft.service.hunter.HunterService;
import com.alqsoft.service.phHunterRuleAttachment.PhHunterRuleAttachmentService;

/**
 * @ClassName IndustryAssociationController Date: 2017年3月1日 16:45:41 <br/>
 * @author dinglanlan
 * @version 批发商
 * @see
 */
@RequestMapping("mobile/after/hunter")
@Controller
public class HunterController {

	@Autowired
	private HunterService hunterService;
	

	/**
	 * 批发商个人信息编辑- 驻地区域接口
	 * 2017年7月18日18:23:20   wangzhen  增加参数   等级：positionLevel, 县名： countyName,经纬度定位地址:station
	 * @return
	 */
	@Explosionproof
	@RequestMapping(value = "updatehunterarea", method = RequestMethod.POST)
	@ResponseBody
	public Result updatehunterarea(@RequestParam(value = "id") Long id,
			@RequestParam(value = "provinceId",required=false) Long provinceId, 
			@RequestParam(value = "cityId",required=false) Long cityId,
			@RequestParam(value = "countyId",required=false) Long countyId,
			@RequestParam(value = "townId",required=false) Long townId,
			@RequestParam(value = "longitude") String longitude,
			@RequestParam(value = "latitude") String latitude,
			@RequestParam(value = "detail") String detail,
			@RequestParam(value="positionLevel") Integer positionLevel,
			@RequestParam(value = "countyName",required=false) String countyName,
			@RequestParam(value = "station") String station, 
			@MemberAnno Member member) {

		Result result = this.hunterService.updateHunterArea(id, provinceId, cityId, countyId, townId, longitude,
				latitude, detail, positionLevel,countyName,station,member);

		return result;
	}

	/**
	 * 安全设置-批发商身份认证-检验是否认证
	 * 
	 * @return
	 */
	@RequestMapping(value = "chackhuntercard", method = RequestMethod.POST)
	@ResponseBody
	public Result chackhuntercard(@MemberAnno Member member) {

		return this.hunterService.chackhuntercard(member);
	}

	/**
	 * 安全设置-批发商身份认证
	 * 
	 * @param name
	 * @param card
	 * @return
	 */
	@Explosionproof
	@RequestMapping(value = "checkhunter", method = RequestMethod.POST)
	@ResponseBody
	public Result checkhunter(@RequestParam(value = "name") String name, @RequestParam(value = "card") String card,
			@MemberAnno Member member) {

		return this.hunterService.checkHunterNameAndCard(name, card, member);
	}

	/**
	 * 安全设置-添加银行卡-检验是否绑卡
	 * 
	 * @return
	 */
	@RequestMapping(value = "chackhunterbankcard", method = RequestMethod.POST)
	@ResponseBody
	public Result chackhunterbankcard(@MemberAnno Member member) {

		return this.hunterService.chackHunterBankCard(member);
	}

	/**
	 * 安全设置-添加银行卡
	 * 
	 * @param bankNo
	 * @param bank
	 * @param name
	 * @param card
	 * @return
	 */
	@Explosionproof
	@RequestMapping(value = "addhunterbankcard", method = RequestMethod.POST)
	@ResponseBody
	public Result addhunterbankcard(@RequestParam(value = "bankNo") String bankNo,
			@RequestParam(value = "bank") String bank,
			@RequestParam(value = "bankAddr") String bankAddr,
			@RequestParam(value = "name") String name,
			@RequestParam(value = "card") String card, @MemberAnno Member member) {

		BankCard bankCard = new BankCard();
		bankCard.setBankNo(bankNo);
		bankCard.setBank(bank);
		bankCard.setName(name);
		bankCard.setCard(card);
		bankCard.setBankAddr(bankAddr);

		return this.hunterService.addHunterBankCard(bankCard, member);
	}

	/**
	 * 安全设置-修改银行卡
	 * 
	 * @param bankNo
	 * @param bank
	 * @param name
	 * @param card
	 * @return
	 */
	@Explosionproof
	@RequestMapping(value = "updatehunterbankcard", method = RequestMethod.POST)
	@ResponseBody
	public Result updatehunterbankcard(@RequestParam(value = "id") Long id,
			@RequestParam(value = "bankNo") String bankNo,
			@RequestParam(value = "bankAddr") String bankAddr,
			@RequestParam(value = "bank") String bank,
			@RequestParam(value = "name") String name,
			@RequestParam(value = "card") String card,
			@MemberAnno Member member) {

		BankCard bankCard = new BankCard();
		bankCard.setId(id);
		bankCard.setBankNo(bankNo);
		bankCard.setBank(bank);
		bankCard.setName(name);
		bankCard.setCard(card);
		bankCard.setBankAddr(bankAddr);

		return this.hunterService.updateHunterBankCard(bankCard, member);
	}

	/**
	 * 安全设置-删除银行卡
	 * 
	 * @param id
	 * @return
	 */
	@Explosionproof
	@RequestMapping(value = "deletehunterbankcard", method = RequestMethod.POST)
	@ResponseBody
	public Result deletehunterbankcard(@RequestParam(value = "id") Long id, @MemberAnno Member member) {

		return this.hunterService.deleteHunterBankCard(id, member);
	}

	/**
	 * 安全设置-修改手机号 原手机号码发送验证码 PHPF2017070301
	 * 
	 * @param oldphone
	 * @return
	 */
	@RequestMapping("sendmsgforoldphone")
	@ResponseBody
	public Result sendmsgforoldphone(@RequestParam(value = "oldphone") String oldphone, @MemberAnno Member member) {

		return hunterService.sendMsgForOldPhone(member, oldphone, "PHPF2017070301");
	}

	/**
	 * 安全设置-修改手机号 新手机号码发送验证码 PHPF2017070302
	 * 
	 * @param newphone
	 * @return
	 */
	@RequestMapping("sendmsgfornewphone")
	@ResponseBody
	public Result sendmsgfornewphone(@RequestParam(value = "newphone") String newphone, @MemberAnno Member member) {

		return hunterService.sendMsgForNewPhone(member, newphone, "PHPF2017070302");
	}

	/**
	 * 安全设置-修改手机号
	 * 
	 * @param oldphone
	 * @param oldcode
	 * @param newphone
	 * @param newcode
	 * @return
	 */
	@Explosionproof
	@RequestMapping("updatephone")
	@ResponseBody
	public Result updatephone(@RequestParam(value = "oldphone") String oldphone,
			@RequestParam(value = "oldcode") String oldcode, @RequestParam(value = "newphone") String newphone,
			@RequestParam(value = "newcode") String newcode, @MemberAnno Member member) {

		return hunterService.updatePhone(oldphone, oldcode, newphone, newcode, member);
	}

	/**
	 * 批发商个人中心
	 * 
	 * @return
	 */
	@RequestMapping(value = "huntercenter", method = RequestMethod.POST)
	@ResponseBody
	public Result memberCenter(@MemberAnno Member member) {
		if (member.getHunter() == null || member.getHunter().getId() == null) {
			return ResultUtils.returnError("您不是批发商身份");
		}
		return hunterService.getHunterCenter(member.getHunter().getId());

	}

	/**
	 * 批发商个人中心--所属协会编辑
	 * 
	 * @return
	 */
	@Explosionproof
	@RequestMapping(value = "updateindustryassociation", method = RequestMethod.POST)
	@ResponseBody
	public Result updateindustryassociation(@RequestParam(value = "id") Long id, @MemberAnno Member member) {

		Result result = this.hunterService.updateIndustryAssociation(id, member);

		return result;
	}

	/***
	 * 批发商个人中心--行业分类编辑，一级id，二级
	 */
	@RequestMapping(value = "classify", method = RequestMethod.POST)
	@ResponseBody
	public Result upateIndustryClassify(@RequestParam(value = "fId") Long fId,
			@RequestParam(value = "sId", defaultValue = "0") Long sId, @MemberAnno Member member) {
		if (member.getHunter() == null) {
			return ResultUtils.returnError("批发商有处理权限");
		}
		Result result = new Result();
		result = this.hunterService.upateIndustryClassify(fId, sId, member.getHunter().getId());
		return result;
	}

	/**
	 * 批发商个人中心--店铺管理--检测是否有背景图
	 * 
	 * @return
	 */
	@RequestMapping(value = "chackhunterbackgroundimage", method = RequestMethod.POST)
	@ResponseBody
	public Result chackhunterbackgroundimage(@MemberAnno Member member) {

		return this.hunterService.chackhunterbackgroundimage(member);
	}

	/**
	 * 批发商个人中心--店铺管理--添加背景图
	 * 
	 * @return
	 */
	@RequestMapping(value = "addhunterbackgroundimage", method = RequestMethod.POST)
	@ResponseBody
	public Result addhunterbackgroundimage(@MemberAnno Member member, @RequestParam(value = "hunterId") Long hunterId,
			@RequestParam(value = "imageId") Long imageId) {

		return this.hunterService.addhunterbackgroundimage(member, hunterId, imageId);
	}

    /**
     * 判断登录会员是与该批发商关注状态
     * @param member
     * @param hId
     * @return
     */
	@RequestMapping(value = "hunterfoucstype", method = RequestMethod.POST)
	@ResponseBody
	public Result getHunterFoucsType(@MemberAnno Member member, @RequestParam("hId") Long hId) {
		return this.hunterService.getHunterFoucsType(member, hId);
	}


	
	/**
	 * 批发商个人信息编辑- 编辑标签分类接口
	 * @return
	 */
	@Explosionproof
	@RequestMapping(value="updatehunterlabel",method=RequestMethod.POST)
	@ResponseBody
	public Result updateHunterlabel(@RequestParam(value="id") Long id, 
			@RequestParam(value="industryTypeIdList") String industryTypeIdList,
			@MemberAnno Member member){
		
		
		Result result =this.hunterService.updateHunterlabel(id,member,industryTypeIdList);
		
		return result;
	}
	
	/**
	 * 修改照片
	 */
	@RequestMapping(value="updatehunterruleattachment",method=RequestMethod.POST)
	@ResponseBody
	public Result updateHunterBusinessLicence(@RequestParam(value="saveimageid") Long saveimageid, 
			@RequestParam(value="updateimageid") Long updateimageid,@RequestParam(value="type") int type,
			@RequestParam(value="hunterid") Long hunterid
			){
		
		Result result =this.hunterService.updateHunterBusinessLicence(saveimageid,updateimageid,hunterid,type);
		
		return result;
	}
	
	
	
	
	
	/**
     * 根据批发商id获取二级分类标签
     */
    @RequestMapping(value = "findhunterlabel",method = RequestMethod.POST)
    @ResponseBody
    public Result findhHunterLabel(
    		@RequestParam(value = "hunterId")Long hunterId,
    		@MemberAnno Member member){
    		
    	Result result = hunterService.findhHunterLabel(hunterId,member);
    	return result;
    }

	/**
	 * 批发商配送范围回显
	 *
	 * @return
	 */
	@RequestMapping(value = "hunterarearang",method = RequestMethod.POST)
	@ResponseBody
	public Result hunterarearang(@MemberAnno Member member){

		if(member.getHunter()==null){
			Result result = new Result();
			result.setCode(0);
			result.setMsg("身份非法");
			return result;
		}
		return this.hunterService.hunterarearang(member.getHunter().getId());
	}

	/**
	 * 批发商配送范围保存
	 *
	 * @return
	 */
	@RequestMapping(value = "updatehunterarearang",method = RequestMethod.POST)
	@ResponseBody
	public Result updatehunterarearang(@RequestParam(value = "districtId") Long districtId,
									   @RequestParam(value = "districts") String districts, @MemberAnno Member member){

		if(member.getHunter()==null){
			Result result = new Result();
			result.setCode(0);
			result.setMsg("身份非法");

		}
		return hunterService.updatehunterarearang(districtId,districts,member);
	}


}
