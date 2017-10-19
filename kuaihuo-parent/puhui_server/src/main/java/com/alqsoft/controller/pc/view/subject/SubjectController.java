package com.alqsoft.controller.pc.view.subject;

import com.alqsoft.entity.attachment.Attachment;
import com.alqsoft.entity.subject.Subject;
import com.alqsoft.service.subject.SubjectService;
import org.alqframework.easyui.EasyuiResult;
import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RequestMapping("pc/view/subject")
@Controller
public class SubjectController {
	
	@Autowired
	private SubjectService subjectService;

	@RequestMapping(value = "toList",method = RequestMethod.GET)
	public String  toSubjectList(){
		return "subject/subject-list";
	}
	/** 运营专员后台&专题分类
	 * @return
	 */
	@RequestMapping(value = "subject-list-data",method = RequestMethod.POST)
    public @ResponseBody
	EasyuiResult getSubjectList(HttpServletRequest request,
								@RequestParam(value="page",defaultValue = "1",required = false)
										 int page,
								@RequestParam(value = "rows",defaultValue = "10",required = false)int rows){

		EasyuiResult subject = subjectService.getSubjectList(page,rows);

        return subject;
    }

	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete",method = RequestMethod.POST)
    public @ResponseBody Result  delete(@RequestParam("id")Long id){
		return subjectService.delete(id);
	}

	/**
	 * 获取行业分类
	 * @param pid
	 * @return
	 */
	@RequestMapping(value = "getIndustryType",method = RequestMethod.POST)
	public @ResponseBody
	List<Map<String,Object>> getIndustryType(@RequestParam(value = "pid",required = false)Long pid){
		return subjectService.getIndustryType(pid);
	}

	/**
	 * 获取二级行业分类下的批发商信息
	 * @param pid
	 * @return
	 */
	@RequestMapping(value = "getHunterByIndustryType",method = RequestMethod.POST)
	public @ResponseBody
	List<Map<String,Object>> getHunterByIndustryType(@RequestParam(value = "tid",required = false)Long pid){
		return subjectService.getHunterByIndustryType(pid);
	}

	/**
	 * 获取详情
	 * @param id  专题ID
	 * @return
	 */
	@RequestMapping(value = "detail",method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> getSubjectDetail(@RequestParam(value = "id")Long id){
		return subjectService.detail(id);
	}

	/**
	 * 获取专题下所有批发商
	 * @param sid  专题ID
	 * @return
	 */
	@RequestMapping(value = "getHunterIds",method = RequestMethod.POST)
	public @ResponseBody String getHunterIdsByIndustry(@RequestParam("sid")Long sid,
													   @RequestParam("tid")Long tid){
		return subjectService.getHunterIdsBySubjectId(sid,tid);
	}
	/**
	 * 保存修改
	 * @param id
	 * @param hids
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "save",method = RequestMethod.POST)
	public @ResponseBody Result submit(@RequestParam("id")Long id,
									   @RequestParam("hids")String hids,
									   @RequestParam("name")String name,
									   @RequestParam("aid")String aid){

		return subjectService.save(id,hids,name,aid);
	}

	/**
	 *
	 * @param inIds  行业分类ID
	 * @param name   批发商名称
	 * @return
	 */
	@RequestMapping(value = "getHunterOptions",method = RequestMethod.POST)
	public @ResponseBody String getProductInfo(
											   @RequestParam("inId")String inIds,
											   @RequestParam("name")String name){
       return subjectService.getHunterInfo(inIds,name);
	}

	/**
	 * 添加专题分类
	 * @param name  专题名称
	 * @param aid  附件ID
	 * @return
	 */
	@RequestMapping(value = "insertSubject",method = RequestMethod.POST)
	public @ResponseBody Result insertSubject(@RequestParam("name")String name,
											  @RequestParam("aid")String aid ){
		if (StringUtils.isBlank(name)){
			return ResultUtils.returnError("参数不能为空");
		}
        Subject subject= new Subject();
		subject.setName(name);
		if(StringUtils.isNotBlank(aid)){
			Attachment attachment = new Attachment();
			attachment.setId(Long.parseLong(aid));
			subject.setAttachment(attachment);
		}
		subject.setIsDel(0);
		try {
			subjectService.saveAndModify(subject);
			return ResultUtils.returnError("添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.returnError("添加失败");
		}
	}

	/**
	 * @param inIds  行业分类ID
	 * @return
	 */
	@RequestMapping(value = "getAllHunterOptions",method = RequestMethod.POST)
	public @ResponseBody String getAllHunterInfo(@RequestParam("sid")String inIds){
		return subjectService.getAllHunterInfoById(inIds);
	}

	/**
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "getIndustryInfo",method = RequestMethod.POST)
	public @ResponseBody Result  getIndustryTypeInfo(@RequestParam("hid")String id){

		return subjectService.getIndustryTypeByHunterId(id);
	}



}
