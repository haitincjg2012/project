package com.alqsoft.controller.pc.after;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.alqframework.easyui.EasyuiResult;
import org.alqframework.result.Result;
import org.alqframework.webmvc.servlet.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alqsoft.entity.bankcard.BankCard;
import com.alqsoft.entity.industryassociation.IndustryAssociation;
import com.alqsoft.entity.user.User;
import com.alqsoft.mybatis.service.industryassociation.MyIndustryAssociationService;
import com.alqsoft.service.industryassociation.IndustryAssociationService;

@Controller
@RequestMapping("pc/after/industryassociation")
public class IndustryAssociationController {

	@Autowired
	private IndustryAssociationService industryAssociationService;
	
	@Autowired
	private MyIndustryAssociationService myIndustryAssociationService;
	
	
	/**
	 * 行业协会
	 * @return
	 */
	@RequestMapping("industry-association")
	public String industrytype() {
		return "industry/industryassociation-list";
	}
	
	/**
	 * 行业协会列表
	 * @param page
	 * @param rows
	 * @param request
	 * @return
	 */
	@RequestMapping("industry-association-data")
	@ResponseBody
	public EasyuiResult<List<IndustryAssociation>> associationlist(@RequestParam("page") Integer page,
			@RequestParam("rows") Integer rows, HttpServletRequest request) {
		// 解析查询
		Map<String, Object> map = ServletUtils.getParametersStartingWith(request, "search_");
		return industryAssociationService.findIndustryAssociationList(map, page, rows);
	}

	
	
	/**
	 * 行业协会添加--弹框
	 * @return
	 */
	@RequestMapping("industry-association-input")
	public String associationInput( ) {
		return "industry/industryassociation-input";
	}
	/**
	 * 行业协会添加编辑
	 * @param industryVo
	 * @return
	 */
	@RequestMapping("industry-association-add")
	@ResponseBody
	public org.alqframework.webmvc.springmvc.Result associationAdd(IndustryAssociation industryAssociation,
			String onepassword,String twopassword){
		
		return industryAssociationService.addIndustryAssociation(industryAssociation,onepassword,twopassword);
		
		
	}

	@RequestMapping("industry-association-detail")
	public String associationDetail(Model model,@RequestParam(value="id",required=true)Long id){
		model.addAttribute("association", industryAssociationService.getAssociationById(id));
		return "industry/industryassociation-detail";
	}
	

	/**
	 * 跳转到行业协会后台查询身份信息
	 * @return
	 */
	@RequestMapping("card_industryassociation")
	public String cardindustryassociation(Model model) {
		
		return "industryassociation/card_industryassociation";
		
	}


	/**
	 * 行业协会后台--查询身份信息
	 * @return
	 */
	@RequestMapping("card_industryassociation_data")
	@ResponseBody
	public EasyuiResult<List<Map<String,Object>>> cardindustryassociationdata(@RequestParam("page") Integer page,
			@RequestParam("rows") Integer rows, HttpServletRequest request,HttpServletResponse response){

		//获取登录信息
				User user=(User)request.getSession().getAttribute("user");
				String phone=user.getUserName();
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("phone",phone);
				IndustryAssociation industryassociation=myIndustryAssociationService.getAssociationByPhone(param);
				
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", industryassociation.getId());
		EasyuiResult easyuiResult = new EasyuiResult();
		params.put("startIndex", (page-1)*rows);
		params.put("endIndex",rows);
		List<Map<String,Object>>  industryAssociation = myIndustryAssociationService.findIndustryAssociationById(params);
		
		int count = myIndustryAssociationService.findIndustryAssociationCountById(params);
		easyuiResult.setT(industryAssociation);
		
		easyuiResult.setTotal((long) count);
		
		return easyuiResult;
		
	}
	
	
	/**
	 * 跳转到行业协会后台查询手机号页面
	 * @return
	 */
	@RequestMapping("phone_industryassociation")
	public String phoneindustryassociation(Model model) {
		
		return "industryassociation/phone_industryassociation";
		
	}
	
	
	/**
	 * 行业协会后台--查询手机号
	 * @return
	 */
	@RequestMapping("phone_industryassociation_data")
	@ResponseBody
	public EasyuiResult<List<Map<String,Object>>> phoneindustryassociationdata(@RequestParam("page") Integer page,
			@RequestParam("rows") Integer rows, HttpServletRequest request,HttpServletResponse response){

		//获取登录信息
		User user=(User)request.getSession().getAttribute("user");
		String phone=user.getUserName();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("phone",phone);
		IndustryAssociation industryassociation=myIndustryAssociationService.getAssociationByPhone(param);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", industryassociation.getId());
		EasyuiResult easyuiResult = new EasyuiResult();
		params.put("startIndex", (page-1)*rows);
		params.put("endIndex",rows);
		List<Map<String,Object>> industryAssociation = myIndustryAssociationService.findIndustryAssociationById(params);
		int count = myIndustryAssociationService.findIndustryAssociationCountById(params);
		easyuiResult.setT(industryAssociation);
		
		easyuiResult.setTotal((long) count);
		
		return easyuiResult;
	}
	
	
	/**
	 * 行业协会后台--跳转到修改手机号页面
	 * @return
	 */
	@RequestMapping("update_phone_page")
	public String updatephonepage(Model model, HttpServletRequest request){
	
		//获取登录信息
				User user=(User)request.getSession().getAttribute("user");
				String phone=user.getUserName();
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("phone",phone);
				IndustryAssociation industryassociation=myIndustryAssociationService.getAssociationByPhone(param);
				
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", industryassociation.getId());
		IndustryAssociation  industryAssociation = myIndustryAssociationService.getIndustryAssociationById(params);
		
		model.addAttribute("industryAssociation", industryAssociation);
			
		return "industryassociation/update_phone";
		
	}
	
	
	/**
	 * 行业协会后台--安全设置-修改手机号  原手机号码发送验证码   PHPF2017070305
	 * @param oldphone
	 * @return
	 */
	@RequestMapping("sendMsgForOldPhone")
	@ResponseBody
	public Result sendMsgForOldPhone(@RequestParam(value="oldPhone") String oldPhone, HttpServletRequest request){
		
		return industryAssociationService.sendMsgForOldPhone(oldPhone,"PHPF2017070305",request);

	}
	
	/**
	 * 行业协会后台--安全设置-修改手机号  新手机号码发送验证码   PHPF2017070306
	 * @param oldphone
	 * @return
	 */
	@RequestMapping("sendMsgForNewPhone")
	@ResponseBody
	public Result sendMsgForNewPhone(@RequestParam(value="newPhone") String newPhone){
		
		return industryAssociationService.sendMsgForNewPhone(newPhone,"PHPF2017070306");

	}
	
	
	/**
	 * 行业协会后台--安全设置-修改手机号 
	 * @return
	 */
	@RequestMapping("update_phone")
	@ResponseBody
	public Result updatephone(@RequestParam("oldPhone") String oldPhone,
			@RequestParam("oldCode") String oldCode,
			@RequestParam("newPhone") String newPhone,
			@RequestParam("newCode") String newCode,HttpServletResponse response, HttpServletRequest request) {
		
		//获取登录信息
		User user=(User)request.getSession().getAttribute("user");
		String phone=user.getUserName();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("phone",phone);
		IndustryAssociation industryAssociation=myIndustryAssociationService.getAssociationByPhone(param);
		
		
		return industryAssociationService.updatephone(oldPhone,oldCode,newPhone,newCode,industryAssociation);
	}
	
	
	/**
	 * 跳转到行业协会后台检测银行卡页面
	 * @return
	 */
	@RequestMapping("check_bankcard")
	public String checkbankcard(Model model, HttpServletRequest request) {
		
		//获取登录信息
			User user=(User)request.getSession().getAttribute("user");
			String phone=user.getUserName();
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("phone",phone);
			IndustryAssociation industryAssociation=myIndustryAssociationService.getAssociationByPhone(param);
				
		Result result = new Result();
		 result=myIndustryAssociationService.checkBankCard(industryAssociation);//检测是否绑卡
		//判断返回状态（1 没进行身份认证  2 已绑定   3 没绑定   4 查询出现多条）
		 int code=result.getCode();
		 
		 Map<String,Object> bankCard=new HashMap<String,Object>();
		 
		 bankCard=(Map<String,Object>)result.getContent();
		 
		if(code==1){
			model.addAttribute("code", code);
		}else if(code==2){
			model.addAttribute("code", code);
			model.addAttribute("industryAssociation", industryAssociation);
			model.addAttribute("bankCard", bankCard);
		}else if(code==3){
			model.addAttribute("code", code);
			model.addAttribute("industryAssociation", industryAssociation);
			model.addAttribute("bankCard", bankCard);
		}else if(code==4){
			model.addAttribute("code", code);
		}
		
		return "industryassociation/check_bankcard";
		
	}
	
	/**
	 * 跳转到行业协会后台绑定银行卡
	 * @return
	 */
	@RequestMapping("to_bang_bankcard")
	public String tobangbankcard(Model model, HttpServletRequest request) {
		
		//获取登录信息
		User user=(User)request.getSession().getAttribute("user");
		String phone=user.getUserName();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("phone",phone);
		IndustryAssociation industryAssociation=myIndustryAssociationService.getAssociationByPhone(param);
		model.addAttribute("industryAssociation", industryAssociation);
		
		return "industryassociation/bangBankCard";
		
	}
	
	/**
	 * 行业协会后台--安全设置-绑定银行卡   手机号发送验证码   PHPF2017070307
	 * @param bangPhone
	 * @return
	 */
	@RequestMapping("sendMsgForBangPhone")
	@ResponseBody
	public Result sendMsgForBangPhone(@RequestParam(value="bangPhone") String bangPhone, HttpServletRequest request){
		
		return industryAssociationService.sendMsgForBangPhone(bangPhone,"PHPF2017070307",request);

	}
	
	/**
	 * 行业协会后台--安全设置-绑定银行卡
	 * @param bankNo
	 * @param bank
	 * @param name
	 * @param card
	 * @return
	 */
	@RequestMapping(value="addbankcard")
	@ResponseBody
	public Result addindustryassociationbankcard(
			@RequestParam(value="bankNo")String bankNo,
			@RequestParam(value="bankName")String bankName,
			@RequestParam(value="payname")String payname,
			@RequestParam(value="card")String card,
			@RequestParam(value="bangPhone")String bangPhone,
			@RequestParam(value="phoneCode")String phoneCode, HttpServletRequest request){
		
		//获取登录信息
			User user=(User)request.getSession().getAttribute("user");
			String phone=user.getUserName();
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("phone",phone);
			IndustryAssociation industryAssociation=myIndustryAssociationService.getAssociationByPhone(param);
		
			BankCard bankCard=new BankCard();
			bankCard.setBankNo(bankNo);
			bankCard.setBank(bankName);
			bankCard.setName(payname);
			bankCard.setCard(card);
		
		return this.industryAssociationService.addIndustryAssociationBankCard(bankCard,bangPhone,phoneCode,industryAssociation);
	}
	
	/**
	 * 跳转到行业协会后台修改银行卡
	 * @return
	 */
	@RequestMapping("to_update_bankcard")
	public String toupdatebankcard(Model model, HttpServletRequest request) {
		 
		//获取登录信息
		User user=(User)request.getSession().getAttribute("user");
		String phone=user.getUserName();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("phone",phone);
		IndustryAssociation industryAssociation=myIndustryAssociationService.getAssociationByPhone(param);
		
		Long industryAssociationId=industryAssociation.getId();
		Map<String,Object> bankCard=myIndustryAssociationService.getBankCardIndustryAssociationId(industryAssociationId);//根据会议协会ID查询银行卡信息
		int count=myIndustryAssociationService.getBankCardCountByIndustryAssociationId(industryAssociationId);//查询当月修改银行卡次数
		
		model.addAttribute("industryAssociation", industryAssociation);
		model.addAttribute("bankCard", bankCard);
		model.addAttribute("count", count);
		
		return "industryassociation/updateBankCard";
		
	}
	
	/**
	 * 行业协会后台--安全设置-修改银行卡   手机号发送验证码   PHPF2017070308
	 * @param updatePhone
	 * @return
	 */
	@RequestMapping("sendMsgForUpdatePhone")
	@ResponseBody
	public Result sendMsgForUpdatePhone(@RequestParam(value="updatePhone") String updatePhone, HttpServletRequest request){
		
		return industryAssociationService.sendMsgForUpdatePhone(updatePhone,"PHPF2017070308",request);

	}
	
	/**
	 * 行业协会后台--安全设置-修改银行卡
	 * @param bankNo
	 * @param bank
	 * @param name
	 * @param card
	 * @return
	 */
	@RequestMapping(value="updatebankcard")
	@ResponseBody
	public Result updateindustryassociationbankcard(
			@RequestParam(value="count")int count,
			@RequestParam(value="bankNo")String bankNo,
			@RequestParam(value="bankName")String bankName,
			@RequestParam(value="payname")String payname,
			@RequestParam(value="card")String card,
			@RequestParam(value="updatePhone")String updatePhone,
			@RequestParam(value="phoneCode")String phoneCode, HttpServletRequest request){
		
		//获取登录信息
			User user=(User)request.getSession().getAttribute("user");
			String phone=user.getUserName();
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("phone",phone);
			IndustryAssociation industryAssociation=myIndustryAssociationService.getAssociationByPhone(param);
		
			BankCard bankCard=new BankCard();
			bankCard.setBankNo(bankNo);
			bankCard.setBank(bankName);
			bankCard.setName(payname);
			bankCard.setCard(card);
		
			int updateCountSelect=myIndustryAssociationService.getBankCardCountByIndustryAssociationId(industryAssociation.getId());//查询当月修改银行卡次数
			
		return this.industryAssociationService.updateindustryassociationbankcard(bankCard,updatePhone,phoneCode,industryAssociation,count,updateCountSelect);
	}
	

}
