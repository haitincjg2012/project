package com.alqsoft.controller.pc.after;

import javax.servlet.http.HttpServletRequest;

import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alqsoft.entity.adattachment.AdAttachment;
import com.alqsoft.service.adshome.AdsHomeService;
import com.alqsoft.utils.UploadFileName;
import com.alqsoft.vo.ProcudtTypeVO;

/*import com.alqsoft.service.adshome.AdsHomeService;*/

/**
 * 
 * @Description: TODO
 * @author Wudi
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年3月15日 下午2:12:00
 * Copyright 
 */
@RequestMapping("pc/after/adshome")
@Controller
public class AdsHomeController {
	
	@Autowired
	private AdsHomeService adsHomeService;
	
	/***
	 * 
	 * 跳转到jsp页面
	 * 
	 */
	@RequestMapping("adshome")
	public String adsHome(){
		return "adshome/adshome-list";
	}
	/***
	 * 图片上传页面
	 */
	@RequestMapping("uploadurl")
	public String industryUrl(){
		return "adshome/adshome-input";
	}
	/***
	 * 文本详情
	 */
	@RequestMapping("textdetail")
	public String  textDetailUrl(){
		return "adshome/adshome-textdetail";
	}
	/***
	 * 图片上传，以及保存到数据库
	 * @return
	 * MultipartFile 上传的文件
	 * field
	 */
	@RequestMapping("picture")
	@ResponseBody
	public Result uploadAdsHomePicture(@RequestParam("urlfile") MultipartFile urlfile,
			@RequestParam(required = false, value = "field") Integer field,HttpServletRequest request){
		String[] extendFile=new String[]{".png", ".jpg", ".jpeg", ".bmp", ".gif"};
		//文件夹名
		String module=UploadFileName.HUNTER_INDUSTRY.getName();
		//文件urlfile，图片格式extendFile，
		return adsHomeService.uploadAdsHomePicture(urlfile,new Object[]{adsHomeService,"saveAdAttachment"},module,extendFile);
	}
	@RequestMapping("delete")
	@ResponseBody
	public Result deleteAdsHomePicture(@RequestParam(value ="id",required=true) Long id){
		return adsHomeService.deleteAdsHomePicture(id);
	}
	/***
	 * 图片上传，以及保存到数据库
	 * @return
	 * MultipartFile 上传的文件
	 * field
	 */
	@RequestMapping("detailpicture")
	public String uploadDetailPicture(Model model,@RequestParam("upload") MultipartFile upload,
			@RequestParam(required = false, value = "field") Integer field,HttpServletRequest request){
		String[] extendFile=new String[]{".png", ".jpg", ".jpeg", ".bmp", ".gif"};
		//文件夹名
		String module=UploadFileName.HUNTER_INDUSTRY.getName();
		//文件urlfile，图片格式extendFile，
		String result = adsHomeService.uploadDetailPicture(upload,module,extendFile);
		model.addAttribute("result", result);
		return "adshome/ckeditor-fileupload";
	}
	
	@RequestMapping("addpicture")
	@ResponseBody
	public Result  addAdsHome(AdAttachment adAttachment){
		return adsHomeService.addAdsHome(adAttachment);
	}
	/***
	 *返回header将要修改的数据
	 * @param model
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("update")
	public String updatePictureHeaderById(Model model, @RequestParam(value = "id") Long id,
			HttpServletRequest request){
		AdAttachment adAttachment = adsHomeService.get(id);
		model.addAttribute("udpatePicture", adAttachment);
		return "adshome/adshome-edit";
	}
	/***
	 * 修改后的header
	 * @return
	 */
	@RequestMapping("saveupdateheadername")
	@ResponseBody
	public org.alqframework.webmvc.springmvc.Result saveUpdatePictureHeaderName(ProcudtTypeVO vo){
		
		return adsHomeService.saveUpdatePictureHeaderName(vo);
	}
	/***
	 * 修改图片源码
	 * @param model
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("code")
	public ModelAndView updatePictureCodeById(@RequestParam(value = "id") Long id,
			HttpServletRequest request){
		ModelAndView view = new ModelAndView();
		AdAttachment adAttachment = adsHomeService.get(id);
		view.setViewName("adshome/adshome-code");
		view.addObject("udpateCode",adAttachment);
		return view;
	}
	
	@RequestMapping("code-ajax")
	@ResponseBody
	public AdAttachment updateCodeById(Model model, @RequestParam(value = "id") Long id,
			HttpServletRequest request){
		AdAttachment adAttachment = adsHomeService.get(id);
		return adAttachment;
	}
	/***
	 * 保存修改后的源码
	 * @return
	 */
	@RequestMapping("updatecode")
	@ResponseBody
	public org.alqframework.webmvc.springmvc.Result  saveUpdatePictureCode(ProcudtTypeVO vo){
		return adsHomeService.saveUpdatePictureCode(vo);
	}
	
}
