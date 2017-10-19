//package com.ph.member.api.controller;
//
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.alibaba.dubbo.config.annotation.Reference;
//import com.alibaba.fastjson.JSON;
//import com.ph.base.BaseController;
//import com.ph.member.api.service.MemberIntegralDataService;
//import com.ph.shopping.common.core.constant.PageConstant;
//import com.ph.shopping.common.util.core.ResultUtil;
//import com.ph.shopping.common.util.excel.ExcelBean;
//import com.ph.shopping.common.util.excel.ExcelUtil;
//import com.ph.shopping.common.util.result.Result;
//import com.ph.shopping.common.util.transform.MoneyTransUtil;
//import com.ph.shopping.facade.member.dto.MemberIntegralDTO;
//import com.ph.shopping.facade.member.service.IMemberIntegralService;
//import com.ph.shopping.facade.member.vo.MemberScoreVO;
//import com.ph.shopping.facade.score.service.IScoreService;
//import com.ph.shopping.facade.score.vo.MemberScoreVO2;
///**
// * 
// * @ClassName:  MemberIntegralController   
// * @Description:会员积分   
// * @author: 李杰
// * @date:   2017年4月27日 下午3:21:32     
// * @Copyright: 2017
// */
//@Controller
//@RequestMapping("web/memberintegral")
//public class MemberIntegralController extends BaseController{
//
//	private static final Logger log = LoggerFactory.getLogger(MemberIntegralController.class);
//	
//	@Reference(version = "1.0.0", retries = 1, timeout = 10000)
//	private IMemberIntegralService memberIntegralService;
//	
//	@Reference(version = "1.0.0", retries = 1, timeout = 10000)
//	private IScoreService scoreService;
//	
//	@Autowired
//	private MemberIntegralDataService memberIntegralDataService;
//	/**
//	 * 
//	* @Title: list  
//	* @Description: 加载会员积分列表
//	* @param @return    参数  
//	* @return String    返回类型  
//	* @throws
//	 */
//	@RequestMapping(value = "/memberintegralList", method = RequestMethod.GET)
//	public String list(HttpServletRequest request, Model model) {
//		Result result = memberIntegralService.getMemberIntegralSource();
//		if (result != null && result.isSuccess()) {
//			model.addAttribute("data", result.getData());
//		}
//		model.addAttribute("memberId", request.getParameter("memberId"));
//		return "/member/memberIntrgralList";
//	}
//	/**
//	 * 
//	* @Title: listPage  
//	* @Description: 加载会员积分数据列表 
//	* @param @param request
//	* @param @param qo
//	* @param @return    参数  
//	* @return Result    返回类型  
//	* @throws
//	 */
//	@ResponseBody
//	@RequestMapping("/listPage")
//	public Result listPage(HttpServletRequest request, MemberIntegralDTO dto) {
//		log.info("加载会员积分列表参数 MemberIntegralQo = {} ", JSON.toJSONString(dto));
//		Result result = ResultUtil.setResult(false, "未查询到相关数据");
//		try {
//			if (dto.getPageNum() == null) {
//				dto.setPageNum(PageConstant.pageNum);
//			}
//			if (dto.getPageSize() == null) {
//				dto.setPageSize(PageConstant.pageSize);
//			}
//			result = memberIntegralService.getMemberIntegralInfoLsitByPage(dto);
//			log.info("加载会员积分列表返回数据 Result = {}",JSON.toJSONString(result));
//		} catch (Exception e) {
//			log.error("加载会员积分列表错误", e);
//		}
//		return result;
//	}
//	/**
//	 * 
//	* @Title: getScoreByMemberId  
//	* @Description:根据会员ID 获取积分数据
//	* @param @param request
//	* @param @param memberId
//	* @param @return    参数  
//	* @return Map<String,Object>    返回类型  
//	* @throws
//	 */
//	@ResponseBody
//	@RequestMapping("/getScoreByMemberId")
//	public Result getScoreByMemberId(HttpServletRequest request, Long memberId) {
//		log.info("加载会员积分总收支信息 参数 >> ：" + memberId);
//		Result result = ResultUtil.setResult(false, "操作失败");
//		if (memberId != null) {
//			MemberScoreVO ms = updateMemberScoreVo(scoreService.getMemberScore(memberId));
//			log.info("根据会员ID 查询积分返回数据 MemberScoreVo={}", ms);
//			if (ms != null) {
//				result.setData(ms);
//				ResultUtil.setResult(result, true, "");
//			}
//		}
//		return result;
//	}
//	/**
//	 * 
//	 * @Title: updateMemberScoreVo   
//	 * @Description: 处理返回的 积分值 统一除以 1000   
//	 * @param: @param vo
//	 * @param: @return      
//	 * @return: MemberScoreVo2      
//	 * @throws
//	 */
//	private MemberScoreVO updateMemberScoreVo(MemberScoreVO2 vo){
//		MemberScoreVO result = null;
//		if(vo != null){
//			result = new MemberScoreVO();
//			long drawcashScore = vo.getDrawcashScore();
//			result.setDrawcashScore(MoneyTransUtil.transDivisDouble(drawcashScore));
//			long enablescore = vo.getEnablescore();
//			result.setEnablescore(MoneyTransUtil.transDivisDouble(enablescore));
//			long standbyscore = vo.getStandbyscore();
//			result.setStandbyscore(MoneyTransUtil.transDivisDouble(standbyscore));
//		}
//		return result;
//	} 
//	/**
//	 * @throws Exception 
//	 * 
//	* @Title: exportExcel  
//	* @Description: 会员积分数据导出
//	* @param @param request
//	* @param @param response
//	* @param @param qo    参数  
//	* @return void    返回类型  
//	* @throws
//	 */
//	@RequestMapping("/memberExportExcel")
//	public void exportExcel(HttpServletRequest request, HttpServletResponse response, MemberIntegralDTO dto)
//			throws Exception {
//		log.info("导出会员积分列表参数 MemberPageDto = {} ", JSON.toJSONString(dto));
//		Result result = memberIntegralService.getMemberIntegralInfoList(dto);
//		log.info("导出会员积分列表返回数据 Result = {} ", JSON.toJSONString(result));
//		if (result != null && result.isSuccess()) {
//			Object obj = result.getData();
//			if (obj instanceof List) {
//				@SuppressWarnings("unchecked")
//				List<MemberIntegralDTO> list = (List<MemberIntegralDTO>) obj;
//				ExcelBean exelBean = memberIntegralDataService.getMemberIntegralExcelBean(list);
//				ExcelUtil.download(request, response, exelBean);
//			}
//		}
//	}
//}
