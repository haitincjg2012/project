package com.alqsoft.controller.pc.after.cashreceivestation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.alqframework.easyui.EasyuiResult;
import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.alqframework.webmvc.servlet.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alqsoft.entity.industryassociation.IndustryAssociation;
import com.alqsoft.entity.user.User;
import com.alqsoft.mybatis.service.cashreceivestation.MyCashReceiveStationService;
import com.alqsoft.mybatis.service.industryassociation.MyIndustryAssociationService;
import com.alqsoft.service.cashreceivestation.CashReceiveStationService;
import com.alqsoft.service.industryassociation.IndustryAssociationService;

/**
 * @ClassName  CashReceiveStationController
 * Date:     2017年3月15日  17:45:41 <br/>
 * @author   dinglanlan
 * @version  提现记录
 * @see 	 
 */
@RequestMapping("pc/after/cashreceivestation")
@Controller
public class CashReceiveStationController {

	@Autowired
	private MyCashReceiveStationService myCashReceiveStationService;
	
	@Autowired
	private MyIndustryAssociationService myIndustryAssociationService;
	
	@Autowired
	private CashReceiveStationService cashReceiveStationService;
	
	
	/**
	 * 跳转到批发商提现明细列表页面
	 * @return
	 */
	@RequestMapping("hunter_cashreceivestation_list")
	public String userList(Model model) {
		
		return "cashreceivestation/hunter_cashreceivestation_list";
		
	}
	
	/**
	 * 管理员后台——提现明细列表
	 * @param page
	 * @param rows
	 * @param request
	 * @return
	 */
	@RequestMapping("hunter_cashreceivestation_list_data")
	@ResponseBody
	public EasyuiResult<List<Map<String,Object>>> cashreceivestationlistdata(@RequestParam("page") Integer page,
			@RequestParam("rows") Integer rows, HttpServletRequest request,HttpServletResponse response){
		// 解析查询
		Map<String, Object> params = ServletUtils.getParametersStartingWith(request, "search_");
		EasyuiResult easyuiResult = new EasyuiResult();
		
		params.put("startIndex", (page-1)*rows);
		params.put("endIndex",rows);
		List<Map<String,Object>> cashReceiveStationList = myCashReceiveStationService.getCashReceiveStationList(params);
		int count = myCashReceiveStationService.getCashReceiveStationCount(params);
		easyuiResult.setT(cashReceiveStationList);
		
		easyuiResult.setTotal((long) count);
		
		return easyuiResult;
	}
	
	/**
	 * 跳转到行业协会提现明细列表页面
	 * @return
	 */
	@RequestMapping("industryassociation_cashreceivestation_list")
	public String industryassociationCashreceivestationList(Model model) {
		
		return "cashreceivestation/industryassociation_cashreceivestation_list";
		
	}
	
	/**
	 * 行业协会后台——提现明细列表
	 * @param page
	 * @param rows
	 * @param request
	 * @return
	 */
	@RequestMapping("industryassociation_cashreceivestation_list_data")
	@ResponseBody
	public EasyuiResult<List<Map<String,Object>>> industryassociationcashreceivestationlistdata(@RequestParam("page") Integer page,
			@RequestParam("rows") Integer rows, HttpServletRequest request,HttpServletResponse response){
		
		//获取登录信息
		User user=(User)request.getSession().getAttribute("user");
		String phone=user.getUserName();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("phone",phone);
		IndustryAssociation industryassociation=myIndustryAssociationService.getAssociationByPhone(param);
		// 解析查询
		Map<String, Object> params = ServletUtils.getParametersStartingWith(request, "search_");
		EasyuiResult easyuiResult = new EasyuiResult();
		params.put("startIndex", (page-1)*rows);
		params.put("endIndex",rows);
		params.put("industryassociationId",industryassociation.getId());//行业协会id
		List<Map<String,Object>> cashReceiveStationList = myCashReceiveStationService.getIndustryAssociationCashReceiveStationList(params);
		int count = myCashReceiveStationService.getIndustryAssociationCashReceiveStationCount(params);
		easyuiResult.setT(cashReceiveStationList);
		
		easyuiResult.setTotal((long) count);
		
		return easyuiResult;
	}
	
	
	/**
	 * 跳转到行业协会后台--提现页面
	 * @return
	 */
	@RequestMapping("to_cash_money")
	public String toupdatebankcard(Model model, HttpServletRequest request) {
		 
		User user=(User)request.getSession().getAttribute("user");
		String phone=user.getUserName();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("phone",phone);
		IndustryAssociation industryAssociation=myIndustryAssociationService.getAssociationByPhone(param);
		Long industryAssociationId=industryAssociation.getId();
		List<Map<String,Object>> bankCardList=myIndustryAssociationService.getIndustryAssociationBankCardMsg(industryAssociationId);//根据会议协会ID查询银行卡信息
		if(bankCardList.size()>0){
			Map<String,Object> bankCard=bankCardList.get(0);
			model.addAttribute("bankCard", bankCard);
			model.addAttribute("isBangCard", true);
		}else{//没有绑卡
			model.addAttribute("isBangCard", false);
		}
		model.addAttribute("industryAssociation", industryAssociation);
		
		
		return "cashreceivestation/cashmoney";
		
	}
	
	/**
	 * 行业协会后台--提现 手机号发送验证码   PHPF2017070304
	 * @param tixianPhone
	 * @return
	 */
	@RequestMapping("sendMsgForTixianPhone")
	@ResponseBody
	public Result sendMsgForUpdatePhone(@RequestParam(value="tixianPhone") String tixianPhone,HttpServletRequest request){
		
		return cashReceiveStationService.sendMsgForUpdatePhone(tixianPhone,"PHPF2017070304",request);

	}
	
	
	/**
	 *  行业协会后台--提现
	 * @param phone
	 * @param code
	 * @param cardNo
	 * @param hunterId
	 * @param money
	 * @return
	 */
	@RequestMapping("cashmoney")
	@ResponseBody
	public Result cashmoney(@RequestParam(value="tixianPhone")String tixianPhone,
			@RequestParam(value="feephonecode")String feephonecode,
			@RequestParam(value="bankCardId")Long bankCardId,
			@RequestParam(value="industryAssociationId")Long industryAssociationId,
			@RequestParam(value="money")Double money,
			@RequestParam(value="bankNo")String bankNo,
			@RequestParam(value="name")String name,
			@RequestParam(value="card")String card,
			@RequestParam(value="bank")String bank,
			HttpServletRequest request){
		
		Result result =this.cashReceiveStationService.industryAssociationCashmoney(tixianPhone,feephonecode,bankCardId,industryAssociationId,money,bankNo,name,card,bank,request);
		
		return result;		
		
	}
	
}
