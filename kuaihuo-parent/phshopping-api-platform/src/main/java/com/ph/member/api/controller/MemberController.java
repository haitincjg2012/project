package com.ph.member.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.util.StringUtil;
import com.ph.base.BaseController;
import com.ph.log.SystemService;
import com.ph.member.api.service.MemberDataService;
import com.ph.shopping.common.core.constant.PageConstant;
import com.ph.shopping.common.core.customenum.SystemOperateEnum;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.excel.ExcelBean;
import com.ph.shopping.common.util.excel.ExcelUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.member.dto.MemberAddDTO;
import com.ph.shopping.facade.member.dto.MemberDTO;
import com.ph.shopping.facade.member.dto.MemberPromotionDTO;
import com.ph.shopping.facade.member.entity.Member;
import com.ph.shopping.facade.member.menum.MemberResultEnum;
import com.ph.shopping.facade.member.menum.member.MemberIsMarketingEnum;
import com.ph.shopping.facade.member.service.IMemberService;
import com.ph.shopping.facade.permission.vo.SessionUserVO;

/**
 * 
 * @ClassName:  MemberController   
 * @Description:会员相关操作   
 * @author: liuy
 * @date:   2017年05月15日 下午3:20:58     
 * @Copyright: 2017
 */
@Controller
@RequestMapping("web/member")
public class MemberController extends BaseController{

	//日志
	private static final Logger log = LoggerFactory.getLogger(MemberController.class);

	//会员接口
	@Reference(version = "1.0.0")
	private IMemberService memberService;

	//会员列表导出
	@Autowired
	private MemberDataService memberDataService;

	//日志接口
	@Autowired
	private  SystemService systemService;
	/**======================================页面跳转================================================*/
	
	/**
	 * 跳转会员列表页面 
	 * @return 会员列表页面 
	 * @author liuy
	 * @createTime  2017年05月15日
	 */
	@RequestMapping(value = "/listPage", method = RequestMethod.GET)
	public String toListPage() {
		return "member/memberList";
	}
	
	/**======================================数据操作================================================*/
	
	/**
	 * 分页获取会员列表
	 * @param page 分页对象
     * @param qo 查询条件
	 * @return 会员列表
	 * @author liuy
	 * @createTime  2017年05月15日
	 */
	@ResponseBody
	@RequestMapping("/list")
	public Result getMemberListByPage(MemberDTO dto) {
		log.info("加载会员列表参数 MemberPageDto = {} ", JSON.toJSONString(dto));
		if (dto.getPageNum() == null) {
			dto.setPageNum(PageConstant.pageNum);
		}
		if (dto.getPageSize() == null) {
			dto.setPageSize(PageConstant.pageSize);
		}
		return memberService.getMemberListByPage(dto);
	}

	/**
	 * 会员新增 
	 * @param request 
     * @param qo 会员对象
	 * @return 新增Result
	 * @author liuy
	 * @createTime  2017年05月15日
	 */
	@ResponseBody
	@RequestMapping("/addMember")
	public Result addMemberSave(HttpServletRequest request, MemberAddDTO dto) {
		log.info("新增会员参数 MemberInfoQo = {} ", JSON.toJSONString(dto));
		//获取session，通过Session获取用户Id
		SessionUserVO sessionUserVO = getLoginUser();
		dto.setCreaterId(sessionUserVO.getId());
		
		Result result = systemService.addSysLog(sessionUserVO, SystemOperateEnum.INSERT.getType(), "会员新增");
		if(result.isSuccess())
			result = memberService.addMember(dto);
		return result;
	}
	
	/**
	 * 会员冻结
	 * @param request 
     * @param qo 会员对象
	 * @return 冻结Result
	 * @author liuy
	 * @createTime  2017年05月15日
	 */
	@ResponseBody
	@RequestMapping("/frozenMember")
	public Result memberFrozen(HttpServletRequest request, MemberDTO dto) {
		log.info("冻结会员参数 MemberPageDto = {} ", JSON.toJSONString(dto));
		//获取session，通过Session获取用户Id
		SessionUserVO sessionUserVO = getLoginUser();
		dto.setUpdaterId(sessionUserVO.getId());

		Result result = systemService.addSysLog(sessionUserVO, SystemOperateEnum.UPDATE.getType(), "会员冻结");
		if(result.isSuccess())
			result = memberService.memberFrozen(dto);
		return  result;
	}
	
	/**
	 * 会员解冻
	 * @param request 
     * @param qo 会员对象
	 * @return 解冻Result
	 * @author liuy
	 * @createTime  2017年05月15日
	 */
	@ResponseBody
	@RequestMapping("/relievefrozenMember")
	public Result relievefrozen(HttpServletRequest request, MemberDTO dto) {
		log.info("解除冻结会员参数 MemberPageDto = {} ", JSON.toJSONString(dto));
		//获取session，通过Session获取用户Id
		SessionUserVO sessionUserVO = getLoginUser();
		dto.setUpdaterId(sessionUserVO.getId());

		Result result = systemService.addSysLog(sessionUserVO, SystemOperateEnum.UPDATE.getType(), "会员解冻");
		if(result.isSuccess())
			result = memberService.memberRelievefrozen(dto);
		return result;
	}

	/**
	 * 会员升级为推广师
	 * @param request 
     * @param qo 会员对象
	 * @return 解冻Result
	 * @author liuy
	 * @createTime  2017年05月15日
	 */
	@ResponseBody
	@RequestMapping("/memberUpgradePromotion")
	public Result memberUpgradePromotion(HttpServletRequest request,MemberPromotionDTO dto) {
		log.info("会员升级为推广师参数 MemberPageDto = {} ", JSON.toJSONString(dto));
		//获取session，通过Session获取用户Id
		SessionUserVO sessionUserVO = getLoginUser();
		dto.setCreaterId(sessionUserVO.getId());

		Result result = systemService.addSysLog(sessionUserVO, SystemOperateEnum.UPDATE.getType(), "会员升级为推广师 = {}"+JSON.toJSONString(dto));
		if(result.isSuccess())
			result = memberService.memberUpgradePromotion(dto);
		return result;
	}
	
	/**
	 * 根据会员/推广师电话自动获取会员/推广师姓名
	 * @param merchant  
	 * @param isAgent 是否代理商 0：是代理，1：否（是商户）
	 * @return
	 * @备注  商户可以由会员或者推广师推广，代理商只能推广师推广
	 * @createTime 2017年05月15日
	 */
	@RequestMapping(value="/getPromoterNameByTel",method=RequestMethod.POST)
	@ResponseBody
	public Result getPromoterNameByTel(String telPhone,String isAgent){
		Result result = memberService.getMemberInfoByMobile(telPhone);
		if(result.getData()!=null){
			Member member = (Member) result.getData();
			if("0".equals(isAgent)){
				//会员是否推广师状态为推广师，并且会员名称不为空才返回true
				if(member!=null&& member.getIsMarketing().equals(MemberIsMarketingEnum.IS_MARKETING_BYYES.getCode())){
					if(StringUtil.isEmpty(member.getMemberName())){//推广师存在，但没有名称
						return ResultUtil.getResult(MemberResultEnum.PROMOTER_CERTIFICATE_NON);
					}else{
						return ResultUtil.getResult(RespCode.Code.SUCCESS,member);
					}
				}
			}else{
				if(StringUtil.isEmpty(member.getMemberName())){//会员或推广师存在，但没有名称
					return ResultUtil.getResult(MemberResultEnum.MEMBER_CERTIFICATE_NON);
				}else{
					return ResultUtil.getResult(RespCode.Code.SUCCESS,member);
				}
			}
		}
		return ResultUtil.getResult(MemberResultEnum.PROMOTER_NON_EXISTENT);
	}
	
	/**
	 * 校验会员（手机号）是否存在 
     * @param telPhone 手机号
	 * @return 新增Result
	 * @author liuy
	 * @createTime  2017年05月15日
	 */
	@ResponseBody
	@RequestMapping("/verifyPhoneIsExists")
	public Result verifyPhoneIsExists(String telPhone) {
		log.info("校验会员（手机号）是否存在 参数 telPhone： ", JSON.toJSONString(telPhone));
		return memberService.verifyPhoneIsExists(telPhone);
	}
	
	/**
	 * 会员导出（目前原型中无导出按钮）
	 * @param request 
	 * @param response 
     * @param qo 查询条件
	 * @author liuy
	 * @createTime  2017年05月15日
	 */
	@RequestMapping("/exportmember")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response, MemberDTO dto){
		log.info("导出会员列表参数 MemberPageDto = {} ", JSON.toJSONString(dto));
		Result result = memberService.getMemberList(dto);
		log.info("导出会员列表返回数据 Result = {} ", JSON.toJSONString(result));
		if (result != null && result.isSuccess()) {
			Object obj = result.getData();
			if (obj instanceof List) {
				@SuppressWarnings("unchecked")
				List<Member> list = (List<Member>) obj;
				ExcelBean exelBean = memberDataService.getMemberExcelBean(list);
				try {
					ExcelUtil.download(request, response, exelBean);
				} catch (Exception e) {
					e.printStackTrace();
					log.info("导出会员列表出错： ", e.getMessage());
				}
			}
		}
	}
	
}
