package com.alqsoft.service.impl.attachment;

import java.io.File;
import java.lang.reflect.Method;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.alqframework.utils.UniqueUtils;
import org.alqframework.webmvc.springmvc.SpringMVCUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import com.alqsoft.dao.attachment.AttachmentDao;
import com.alqsoft.entity.attachment.Attachment;
import com.alqsoft.init.InitParamPc;
import com.alqsoft.service.attachment.AttachmentService;
import com.alqsoft.utils.oss.UpLoadUtils;


@Service
@Transactional(readOnly=true)
public class AttachmentServiceImpl implements AttachmentService {
	
	private static Logger logger = LoggerFactory.getLogger(AttachmentServiceImpl.class);

	@Autowired
	private AttachmentDao attachmentDao;
	@Autowired
	private InitParamPc initParamPc;
	

	

	@Override
	public boolean delete(Long arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Attachment get(Long arg0) {
		// TODO Auto-generated method stub
		return attachmentDao.findOne(arg0);
	}

	@Override
	@Transactional
	public Attachment saveAndModify(Attachment arg0) {
		// TODO Auto-generated method stub
		return attachmentDao.save(arg0);
	}

	@Override
	@Transactional
	public Result uploadAttachment(MultipartFile urlfile, Object[] backUrl, String module, String[] extendFile) {
		String fileName = null;
		Attachment attachment=null;
		
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
					
					attachment = new Attachment();
					attachment.setName(fileName);
					attachment.setAddress("upload/" + module +"/"+ sysFileName);
					UpLoadUtils.alyUpload(module, sysFileName, path,initParamPc);//上传
					
					
					Object obj=backUrl[0];
					Class<? extends Object> clazz=obj.getClass();
					Method method=clazz.getDeclaredMethod(backUrl[1].toString(), attachment.getClass());
					
					Object returnObj=method.invoke(obj,attachment);
					
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

	@Override
	@Transactional
	public Result saveAttachment(Attachment attachment) {
		try {
			Attachment attachmentdb = saveAndModify(attachment);
			return ResultUtils.returnSuccess("上传成功",attachmentdb);
		} catch (Exception e) {
			logger.error("行业分类上传图片"+e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
			return ResultUtils.returnError("上传失败");
		}
		
	}

}
