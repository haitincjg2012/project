package com.alqsoft.service.impl.adshome;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.alqframework.utils.UniqueUtils;
import org.alqframework.webmvc.springmvc.SpringMVCUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.procedure.internal.Util.ResultClassesResolutionContext;
import org.hibernate.type.TrueFalseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import com.alqsoft.dao.adshome.AdsHomeDao;
import com.alqsoft.entity.adattachment.AdAttachment;
import com.alqsoft.entity.attachment.Attachment;
import com.alqsoft.init.InitParamPc;
import com.alqsoft.init.InitParams;
import com.alqsoft.service.adshome.AdsHomeService;
import com.alqsoft.service.attachment.AttachmentService;
import com.alqsoft.utils.oss.UpLoadUtils;
import com.alqsoft.vo.ProcudtTypeVO;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.google.common.util.concurrent.AbstractExecutionThreadService;

/**
 * 
 * @Description: TODO
 * @author Wudi
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年3月17日 下午1:29:39
 * Copyright 
 */
@Service
@Transactional(readOnly=true)
public class AdsHomeServiceImpl implements AdsHomeService {
	private static Logger logger = LoggerFactory.getLogger(AdsHomeServiceImpl.class);

	@Autowired
	private InitParamPc initParamPc;
	@Autowired
	private AdsHomeDao  adsHomeDao;
	@Autowired
	private AttachmentService attachmentService;
	@Override
	public boolean delete(Long arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public AdAttachment get(Long id) {
		// TODO Auto-generated method stub
		
		return adsHomeDao.findOne(id);
	}
    /***
     * 保存到数据库
     */
	@Override
	@Transactional
	public AdAttachment saveAndModify(AdAttachment adAttachment) {
		// TODO Auto-generated method stub
		return adsHomeDao.save(adAttachment);
	}

	@Override
	@Transactional
	public Result uploadAdsHomePicture(MultipartFile urlfile, Object[] backUrl, String module, String[] extendFile) {
		String fileName = null;
		AdAttachment adAttachment=null;
		
		try {
			if (urlfile.isEmpty()) {
				return ResultUtils.returnError("上传文件失败");
			} else {
				
					String basePath = SpringMVCUtils.getRequest().getRealPath("/upload/" + module);
					String path = null;
					fileName = urlfile.getOriginalFilename();
				
					boolean isFile = StringUtils.endsWithAny(StringUtils.lowerCase(fileName),extendFile );
					String sysFileName = UniqueUtils.getOrder() + "." + StringUtils.substringAfter(fileName, ".");
					if (isFile) {
						path = basePath + "/" + sysFileName;
					} else {
						return ResultUtils.returnError("文件格式不正确,上传文件失败");
					}
				
					urlfile.transferTo(new File(path));
					
					adAttachment = new AdAttachment();
					adAttachment.setName(fileName);
					adAttachment.setAddress("upload/" + module +"/"+ sysFileName);
					//上传到云服务器，module创建的文件夹，sysFileName文件的名，上传的路径path
					UpLoadUtils.alyUpload(module, sysFileName, path,initParamPc);//上传
					
					
					Object obj=backUrl[0];
					Class<? extends Object> clazz=obj.getClass();
					//通过文件的映射
					Method method=clazz.getDeclaredMethod(backUrl[1].toString(), adAttachment.getClass());
					
					Object returnObj=method.invoke(obj,adAttachment);
					
					if(returnObj==null){
						logger.error("上传图片回调方法返回数据为空");
						return ResultUtils.returnError("上传失败");
					}
						
					return (Result) returnObj;
					
				}
			
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
			
			logger.error("头像图片上传发生异常:"+e.getMessage());
			
			return ResultUtils.returnError("上传失败");
		}
	}

    /***
     * 保存数据到数据库
     */
	@Override
	@Transactional
	public Result saveAdAttachment(AdAttachment adAttachment) {
		// TODO Auto-generated method stub
		try {
			AdAttachment adAttachment2=saveAndModify(adAttachment);
			return ResultUtils.returnSuccess("保存图片成功",adAttachment2);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ResultUtils.returnError("图片未保存");
		}
	}
	/***
	 * 删除数据通过id
	 */
	@Override
	@Transactional
	public Result deleteAdsHomePicture(Long id){
		try {
			 adsHomeDao.delete(id);
			return ResultUtils.returnSuccess("删除成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ResultUtils.returnError("删除失败");
		}
	}

	@Override
	@Transactional
	public String uploadDetailPicture(MultipartFile urlfile, String module, String[] extendFile) {
		String fileName = null;
		AdAttachment adAttachment=null;
		String img_server="http://yst-images.img-cn-hangzhou.aliyuncs.com";
		try {
			if (urlfile.isEmpty()) {
				//return ResultUtils.returnError("上传文件失败");
			} else {
				
					String basePath = SpringMVCUtils.getRequest().getRealPath("/upload/" + module);
					String path = null;
					fileName = urlfile.getOriginalFilename();
				
					boolean isFile = StringUtils.endsWithAny(StringUtils.lowerCase(fileName),extendFile );
					String sysFileName = UniqueUtils.getOrder() + "." + StringUtils.substringAfter(fileName, ".");
					if (isFile) {
						path = basePath + "/" + sysFileName;
					} else {
						//return ResultUtils.returnError("文件格式不正确,上传文件失败");
					}
				
					urlfile.transferTo(new File(path));
					
					Attachment attachment= new Attachment();
					attachment.setName(fileName);
					attachment.setAddress("upload/" + module +"/"+ sysFileName);
					attachmentService.saveAndModify(attachment);
					//上传到云服务器，module创建的文件夹，sysFileName文件的名，上传的路径path
					UpLoadUtils.alyUpload(module, sysFileName, path,initParamPc);//上传
					
					// 设置headers参数
					String result = "";
					// 设置返回“图像”选项卡
					String callback = SpringMVCUtils.getRequest().getParameter(
							"CKEditorFuncNum");
					result = "<script type=\"text/javascript\">";
					String parentWindow = "var parentWindow = (window.parent == window)?window.opener : window.parent;";
					result = result + parentWindow;
					result = result + "parentWindow.CKEDITOR.tools.callFunction("
							+ callback + ",'" + img_server + "/upload/" + module + "/"
							+ sysFileName + "','')";
					result = result + "</script>";
					return result;
			}
		}catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return "上传失败";
			}
		return "上传失败";
	}

	@Override
	@Transactional
	public Result addAdsHome(AdAttachment adAttachment) {
		// TODO Auto-generated method stub
		try {
			AdAttachment adAttachmentdb = get(adAttachment.getId());
			if (adAttachmentdb!=null) {
				adAttachmentdb.setDetailContent(adAttachment.getDetailContent());
				this.saveAndModify(adAttachmentdb);
				return ResultUtils.returnSuccess("文本详请上传成功",adAttachmentdb);
			}
			return  ResultUtils.returnError("没有相应的数据");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ResultUtils.returnError("图片未保存");
		}
	}
	@Override
	@Transactional
	public org.alqframework.webmvc.springmvc.Result saveUpdatePictureHeaderName(ProcudtTypeVO vo){
		if (vo==null) {
			return SpringMVCUtils.returnError("参数不能为空");
		}
		/*if (vo.getName()==null || "".equals(vo.getName())) {
			return SpringMVCUtils.returnError("修改后的图片header不能为空");
		}*/
		if(vo.getAdPicAddress()==null||"".equals(vo.getAdPicAddress())){
			return SpringMVCUtils.returnError("图片不能为空");
		}
		try {
			Long id=vo.getId();
			String name=vo.getName();
			if(vo.getName()==null || "".equals(vo.getName())) {
				name=vo.getOldnameheader();
			}
			String address=vo.getAdPicAddress();
			adsHomeDao.saveUpdatePictureHeaderName(id,name,address);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return SpringMVCUtils.returnSuccess("修改失败");
		}
		     
		    return SpringMVCUtils.returnSuccess("修改成功");
	}

	@Override
	@Transactional
	public org.alqframework.webmvc.springmvc.Result saveUpdatePictureCode(ProcudtTypeVO vo) {
		if (vo==null) {
			return SpringMVCUtils.returnError("参数不能为空");
		}else if (vo.getName()==null || "".equals(vo.getName())) {
			return SpringMVCUtils.returnError("修改后的图片不能为空");
		}
		try {
			Long id=vo.getId();
			String name=vo.getName();
			adsHomeDao.saveUpdatePictureCode(id,name);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return SpringMVCUtils.returnSuccess("修改失败");
		}
		     
		    return SpringMVCUtils.returnSuccess("修改成功");
	}
	
}
