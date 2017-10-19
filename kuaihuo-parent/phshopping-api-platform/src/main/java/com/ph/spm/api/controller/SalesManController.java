package com.ph.spm.api.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.ph.base.BaseController;
import com.ph.shopping.common.core.constant.PageConstant;
import com.ph.shopping.common.core.customenum.RoleEnum;
import com.ph.shopping.common.core.customenum.SmsCodeType;
import com.ph.shopping.common.core.customenum.SmsSourceEnum;
import com.ph.shopping.common.core.dto.FindPasswordDTO;
import com.ph.shopping.common.core.other.sms.data.SmsSendData;
import com.ph.shopping.common.core.other.sms.handle.ISmsDataService;
import com.ph.shopping.common.core.util.SmsUtil;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.member.dto.AdAtachmentDTO;
import com.ph.shopping.facade.member.dto.MemberDTO;
import com.ph.shopping.facade.permission.vo.AdAttachmentVo;
import com.ph.shopping.facade.permission.vo.SessionUserVO;
import com.ph.shopping.facade.spm.service.ISalesmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("web/salesman")
public class SalesManController extends BaseController {

	@Reference(version = "1.0.0")
	private ISalesmanService salesmanService;

	@Autowired
	ISmsDataService smsDataService;

	@Autowired
	private SmsUtil util ;


	@RequestMapping(value="/tosalesmanlist")
	public String tosalesmanlist(){
		return "spm/salesman/salesmanList";
	}

	/**
	 *
	 * 添加业务员
	 * wz
	 *
	 * */
	@RequestMapping(value="/addsalesman",method=RequestMethod.POST)
	public Result addSalesMan(String telPhone, String memberName, String idCardNo, String smsCode){

		SessionUserVO userBean = getLoginUser();

		SmsSendData smsSendData = new SmsSendData();
		smsSendData.setMobile(telPhone);
		smsSendData.setType(SmsCodeType.ADD_SALESMAN_VC);
		smsSendData.setSourceEnum(SmsSourceEnum.AGENT);
		smsSendData.setSmsCode(smsCode);
		Result checkResult = smsDataService.checkSmsCodeByMobile(smsSendData);
		if(checkResult.isSuccess()){

			return salesmanService.addSaleman(telPhone,memberName,idCardNo,userBean.getId());
		}

		return ResultUtil.setResult(false, "验证码错误");

	}


	/**
	 * 根据代理查询相应的业务员
	 */
	@RequestMapping(value="/salesmanlist")
	@ResponseBody
	public Result salesmanList(MemberDTO memberDto){
		SessionUserVO userBean = getLoginUser();
		return salesmanService.getSalesmanByAgentId(memberDto.getPageNum(),memberDto.getPageSize(),userBean.getId());
	}

	/**
	 * 查询业务员推广企业
	 */
	@RequestMapping("/getShareCompanyBySalesman")
	@ResponseBody
	public Result getShareCompanyBySalesman(MemberDTO member){

		return salesmanService.getShareCompanyBySalesman(member.getId());
	}


	/**
	 * 冻结
	 */
	@RequestMapping("/frozenSalesman")
	public Result frozenSalesman(MemberDTO member){

		return salesmanService.frozenSalesman(member.getId(),member.getIsFrozen());
	}

	/**
	 * 业务展示列表
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="saleDataList",method = RequestMethod.POST)
	@ResponseBody
	public Result saleManDataList(AdAtachmentDTO dto){
		logger.info("加载会员列表参数 MemberPageDto = {} ", JSON.toJSONString(dto));
		// 获取当前登录用户
		SessionUserVO userBean = getLoginUser();
		String telPhone = userBean.getTelphone();

		if(userBean.getSessionRoleVo().get(0).getRoleCode() == RoleEnum.ADMIN.getCode()){
			telPhone = "";
		}

		if (dto.getPageNum() == null) {
			dto.setPageNum(PageConstant.pageNum);
		}
		if (dto.getPageSize() == null) {
			dto.setPageSize(PageConstant.pageSize);
		}
	    return 	salesmanService.saleManDataList(dto, telPhone);
	}

    /**
     * 添加业务员通过手机号发送验证码
     * @param phone
     * @return
     */
    @RequestMapping(value="saleManSendCode",method = RequestMethod.POST)
    @ResponseBody
	public Result salManSendCode(@RequestParam("phone") String phone, HttpSession session){



        if (phone==null){
            return new Result(false, "1", "请输入手机号");
        }//Fr170001
        FindPasswordDTO dto = new FindPasswordDTO();
        dto.setYTime("3");
        dto.setPhone(phone);
        dto.setKfPhone("400–8586–315");
        dto.setOperation("快火业务添加获取验证码");
        //调用手机号发送验证码
        Result b = util.sendRegisterOrFindPasswordMsg(dto);
        if ("1".equals(b.getCode())){
            return new Result(true, "1", "发送验证码正确");
        }else{
            return new Result(false, "0", b.getMessage());
        }

    }

    /**
     * 保存数据到数据库
     * @param ato
     * @return
     */
    @RequestMapping(value="saveSaleManData",method = RequestMethod.POST)
    @ResponseBody
    public Result saveSaleManData(@ModelAttribute AdAttachmentVo ato,HttpSession session){
        if (ato.getCode() ==null){
            return  new Result(false, "1", "验证码不能为空");
        }
        if(ato.getName() == null){
            return  new Result(false, "1", "用户名不能为空");
        }
        if(ato.getPhone() == null){
            return  new Result(false, "1", "手机号不能为空");
        }
        if(ato.getType() == null ){
            return  new Result(false, "1", "省份证不能为空");
        }
        String userName =(String)session.getAttribute("userName");
        return salesmanService.saveSaleManData(ato.getCode(),ato.getName(),ato.getPhone(),ato.getType(),userName);
    }

	@RequestMapping(value="updateFrozen",method = RequestMethod.POST)
	@ResponseBody
    public Result udpateFrozenType( Long id){
		if (id ==null){
			return  new Result(false, "1", "请选择选择的数据");
		}
		return salesmanService.udpateFrozenType(id);
    }
}
