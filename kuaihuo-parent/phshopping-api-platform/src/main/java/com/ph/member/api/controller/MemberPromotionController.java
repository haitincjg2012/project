package com.ph.member.api.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.ph.base.BaseController;
import com.ph.log.SystemService;
import com.ph.member.api.service.MemberPromotionDataService;
import com.ph.shopping.common.core.customenum.SystemOperateEnum;
import com.ph.shopping.common.util.excel.ExcelBean;
import com.ph.shopping.common.util.excel.ExcelUtil;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.member.dto.CheckMemberPromotionDTO;
import com.ph.shopping.facade.member.dto.MemberPromotionDTO;
import com.ph.shopping.facade.member.menum.promotion.PromotionStatusEnum;
import com.ph.shopping.facade.member.service.IMemberPromotionService;
import com.ph.shopping.facade.member.vo.MemberPromotionVO;
import com.ph.shopping.facade.permission.vo.SessionUserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 
 * phshopping-api-platform
 *
 * @description：推广师Controller
 *
 * @author：liuy
 *
 * @createTime：2017年5月24日
 *
 * @Copyright @2017 by liuy
 */
@Controller
@RequestMapping("web/memberPromotion")
public class MemberPromotionController extends BaseController{
	
	//日志
	private static Logger logger = LoggerFactory.getLogger(MemberPromotionController.class);
	
	//推广师接口
	@Reference(version = "1.0.0", retries = 0, timeout = 30000)
	IMemberPromotionService memberPromotionService;
	
	//推广师导出接口
	@Autowired
	MemberPromotionDataService memberPromotionDataService;

	//日志接口
	@Autowired
	private  SystemService systemService;
	/**
	 * 跳转推广师列表页面
	 * @return 推广师列表页面
	 * @author 郑朋
	 * @createTime  2017年4月27日
	 * @updateTime liuy重构 2017年05月24日
	 */
	@RequestMapping(value="/listPage",method=RequestMethod.GET)
	public String listPage(){
		return "memberPromotion/promotionList";
	}

	/**
	 * 分页获取推广师列表
	 * @return result 
	 * @author 郑朋
	 * @createTime  2017年4月27日
	 * @updateTime liuy重构 2017年05月24日
	 */
    @RequestMapping(value="/list",method= RequestMethod.GET)
	@ResponseBody
    public Object getMemberAuthListByPage(@ModelAttribute PageBean page, @ModelAttribute MemberPromotionDTO memberAuthDTO) {
        logger.info("分页获取推广师列表入参，page={},memberAuthDTO={}", JSON.toJSONString(page), JSON.toJSONString(memberAuthDTO));
        Result result = memberPromotionService.getMemberAuthListByPage(page, memberAuthDTO);
        logger.info("分页获取推广师列表返回值，result={}", JSON.toJSONString(result));
        return result;
	}
	
	/**
	 * 推广师审核
	 * @return result
	 * @author 郑朋
	 * @createTime  2017年4月27日
	 * @updateTime liuy重构 2017年05月24日
	 */
    @RequestMapping(value="/check",method= RequestMethod.POST)
	@ResponseBody
    public Object check(CheckMemberPromotionDTO checkMemberAuthDTO) {
        logger.info("推广师审核接口入参，checkMemberAuthDTO={}", JSON.toJSONString(checkMemberAuthDTO));
      //获取session，通过Session获取用户Id
      	SessionUserVO sessionUserVO = getLoginUser();
      	checkMemberAuthDTO.setUpdaterId(sessionUserVO.getId());
      	
      	String str = "";
      	if(checkMemberAuthDTO.getStatus().equals(PromotionStatusEnum.PROMOTION_ADOPT.getCode())){
      		str = "推广师审核通过";
      	}else{
      		str = "推广师审核不通过";
      	}
		Result result = systemService.addSysLog(sessionUserVO, SystemOperateEnum.INSERT.getType(), str);
		if(result.isSuccess()){
			result = memberPromotionService.check(checkMemberAuthDTO);
        	logger.info("推广师审核返回值，result={}", JSON.toJSONString(result));
		}
        return result;
	}
	
	/**
	 * 推广师导出
	 * @author 郑朋
	 * @createTime  2017年4月27日
	 * @updateTime liuy重构 2017年05月24日
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/memberAuthExport")
	public void exportExcel(HttpServletRequest request,HttpServletResponse response,@ModelAttribute MemberPromotionDTO dto) throws Exception {
		logger.info("导出推广师导出参数 MemberPageDto = {} " ,JSON.toJSONString(dto));
		Result result = memberPromotionService.getMemberAuthList(dto);
		logger.info("导出推广师导出返回数据 Result = {} " ,JSON.toJSONString(result));
		if(result != null && result.isSuccess()){
			Object obj = result.getData();
			ExcelBean excelBean = memberPromotionDataService.getMemberAuthExcelBean((List<MemberPromotionVO>)obj);
			ExcelUtil.download(request, response, excelBean);
		}
	}
}
