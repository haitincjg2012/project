package com.alqsoft.controller.pc.after;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.alqframework.easyui.EasyuiResult;
import org.alqframework.webmvc.servlet.ServletUtils;
import org.alqframework.webmvc.springmvc.Result;
import org.bouncycastle.asn1.crmf.PKIPublicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alqsoft.entity.industrytype.IndustryType;
import com.alqsoft.model.IndustryTypeVo;
import com.alqsoft.service.industrytype.IndustryTypeService;
import com.alqsoft.service.industrytype.IndustryTypesService;

/**
 * 行业分类
 * @author Administrator
 *
 */
@Controller
@RequestMapping("pc/after/industry")
public class IndustryTypeController {
	
	@Autowired
	private IndustryTypeService industryTypeService;
	@Autowired
	private IndustryTypesService industryTypesService;
	
	/**
	 * 行业分类
	 * @return
	 */
	@RequestMapping("industry-type")
	public String industrytype() {
		return "industry/industrytype-list";
	}
	
	/**
	 * 行业分类分页查询
	 * 
	 * @param page
	 * @param rows
	 * @param request
	 * @return
	 */
/*	@RequestMapping("industry-typelist-data")
	@ResponseBody
	@AlqReplace
	public EasyuiResult<List<IndustryType>> typelist(@RequestParam("page") Integer page,
			@RequestParam("rows") Integer rows, HttpServletRequest request) {
		// 解析查询
		Map<String, Object> map = ServletUtils.getParametersStartingWith(request, "search_");
		map.put("NNULL_parentId.id", "null");
		return industryTypeService.findIndustryTypeList(map, page, rows);
	}*/
	
	@RequestMapping("industry-typelist-data")
	@ResponseBody
	public EasyuiResult<List<Map<String,Object>>> typelist(@RequestParam("page") Integer page,
			@RequestParam("rows") Integer rows, HttpServletRequest request) {
		Map<String, Object> params = ServletUtils.getParametersStartingWith(request, "search_");
		params.put("startIndex", (page-1)*rows);
		params.put("endIndex",rows);
		List<Map<String,Object>> typelist= industryTypesService.findIndustryTypeListMybatis(params);
		int count = industryTypesService.getIndustryTypeListCount(params);
		EasyuiResult easyuiResult = new EasyuiResult();
		easyuiResult.setT(typelist);
		easyuiResult.setTotal((long) count);
		return easyuiResult;
	}
	
	/**
	 * 行业分类添加弹框
	 * @param page
	 * @param rows
	 * @param request
	 * @return
	 */
	@RequestMapping("industrytype-input")
	public String userInput( ) {
		return "industry/industrytype-input";
	}
	
	@RequestMapping("industrytype-add")
	@ResponseBody
	public Result industryTypeAdd(IndustryTypeVo industryVo){
		
		return industryTypeService.addIndustryType(industryVo);
		
		
	}
	/**
	 * 编辑行业分类
	 * @param model
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("industrytype-edit")
	public String industryTypeEdit(Model model, @RequestParam(value = "id") Long id,
			HttpServletRequest request) {
		IndustryType firsttype = industryTypeService.get(id);//一级
		//List<IndustryType> secondtype = industryTypeService.findSecondIndustryTypeByFirstId(id);//二级
		List<Map<String,Object>> secondtype = industryTypesService.findSecondIndustryTypeByFirstId(id);
		model.addAttribute("firsttype", firsttype);
		model.addAttribute("secondtype", secondtype);
		model.addAttribute("num", secondtype.size());
		return "industry/industrytype-edit";
	}
	
	/**
	 * 删除行业分类
	 * @param industryVo
	 * @return
	 */
	@RequestMapping("industrytype-delete")
	@ResponseBody
	public Result industryTypeDelete(@RequestParam(value ="id",required=true) Long id){
		
		return industryTypeService.industryTypeDelete(id);
		
	}
	/**
	 * 将行页置于首页
	 * @param id 选中的行业分类id
	 * @author wudi
	 * @return
	 */
	@RequestMapping("industrytype-top")
	@ResponseBody
	public Result firstpAppPageTop(@RequestParam(value ="id",required=true) Long id,@RequestParam(value ="top",required=true) Integer top){
		return industryTypeService.firstpAppPageTop(id,top);
	}
}
