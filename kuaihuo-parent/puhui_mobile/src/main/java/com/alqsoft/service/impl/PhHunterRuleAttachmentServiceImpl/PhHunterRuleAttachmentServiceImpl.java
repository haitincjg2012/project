package com.alqsoft.service.impl.PhHunterRuleAttachmentServiceImpl;

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

import com.alqsoft.entity.phhunterruleattachment.PhHunterRuleAttachment;
import com.alqsoft.init.InitParamPc;
import com.alqsoft.rpc.mobile.RpcPhHunterRuleAttachmentService;
import com.alqsoft.service.phHunterRuleAttachment.PhHunterRuleAttachmentService;
import com.alqsoft.utils.oss.UpLoadUtils;

@Service
@Transactional(readOnly = true)
public class PhHunterRuleAttachmentServiceImpl implements PhHunterRuleAttachmentService {
	private static Logger logger = LoggerFactory.getLogger(PhHunterRuleAttachmentServiceImpl.class);
	@Autowired
	private InitParamPc initParam;
	
	@Autowired
	private RpcPhHunterRuleAttachmentService rpcPhHunterRuleAttachmentService;
	/**
	 * 上传图片
	 * 
	 * @param urlfile
	 *            图片
	 * @param backUrl
	 *            图片上传完成后 需要执行的类的方法 该方法默认参数都是Attachment 返回类型都是Result
	 * @param module
	 *            上传文件夹名字
	 * @param extendFile
	 *            上传文件格式后缀
	 * @return
	 */
	@Override
	@Transactional
	public Result mobileUploadAttachment(MultipartFile urlfile, int type, Object[] backUrl, String module,
			String[] extendFile) {
		String fileName = null;
		PhHunterRuleAttachment attachment = null;

		try {
			if (urlfile.isEmpty()) {
				return ResultUtils.returnError("上传文件失败");
			} else {

				String basePath = SpringMVCUtils.getRequest().getRealPath("/upload/" + module);
				String path = null;
				fileName = urlfile.getOriginalFilename();

				boolean isFile = StringUtils.endsWithAny(StringUtils.lowerCase(fileName), extendFile);
				String sysFileName = UniqueUtils.getOrder() + "." + StringUtils.substringAfter(fileName, ".");
				if (isFile) {
					path = basePath + "/" + sysFileName;
				} else {
					return ResultUtils.returnError("文件格式不正确,上传文件失败");
				}

				urlfile.transferTo(new File(path));

				attachment = new PhHunterRuleAttachment();
				attachment.setName(fileName);
				//添加图片的类型
				attachment.setType(type);
				attachment.setAddress("upload/" + module + "/" + sysFileName);
				UpLoadUtils.alyUpload(module, sysFileName, path, initParam);// 上传

				Object obj = backUrl[0];
				Class<? extends Object> clazz = obj.getClass();
				Method method = clazz.getDeclaredMethod(backUrl[1].toString(), attachment.getClass());

				Object returnObj = method.invoke(obj, attachment);

				if (returnObj == null) {
					logger.error("上传图片回调方法返回数据为空");
					return ResultUtils.returnError("上传失败");
				}

				return (Result) returnObj;

			}

		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 回滚数据

			logger.error("头像图片上传发生异常:" + e.getMessage());

			return ResultUtils.returnError("上传失败");
		}

	}
	
	@Override
	@Transactional
	public Result saveAttachment(PhHunterRuleAttachment phHunterRuleAttachment) {
		
		phHunterRuleAttachment =rpcPhHunterRuleAttachmentService.mobileUploadAttachment(phHunterRuleAttachment);//保存图片信息
		
		return ResultUtils.returnSuccess("上传成功", phHunterRuleAttachment);
	}

	@Override
	public Result addAttachment(Long hunterid, Long imageid) {
		
		return rpcPhHunterRuleAttachmentService.addAttachment(hunterid, imageid);
	}

	
	
}
