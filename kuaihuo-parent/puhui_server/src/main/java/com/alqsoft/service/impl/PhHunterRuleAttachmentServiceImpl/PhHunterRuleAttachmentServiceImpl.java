package com.alqsoft.service.impl.PhHunterRuleAttachmentServiceImpl;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.alqframework.utils.UniqueUtils;
import org.alqframework.webmvc.springmvc.SpringMVCUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import com.alqsoft.dao.PhHunterRuleAttachment.PhHunterRuleAttachmentDAO;
import com.alqsoft.entity.attachment.Attachment;
import com.alqsoft.entity.phhunterruleattachment.PhHunterRuleAttachment;
import com.alqsoft.init.InitParamPc;
import com.alqsoft.rpc.mobile.RpcPhHunterRuleAttachmentService;
import com.alqsoft.service.impl.attachment.AttachmentServiceImpl;
import com.alqsoft.service.phHunterRuleAttachment.PhHunterRuleAttachmentService;
import com.alqsoft.utils.oss.UpLoadUtils;

@Service
@Transactional(readOnly = true)
public class PhHunterRuleAttachmentServiceImpl implements PhHunterRuleAttachmentService {
	private static Logger logger = LoggerFactory.getLogger(PhHunterRuleAttachmentServiceImpl.class);
	@Autowired
	private InitParamPc initParam;

	@Autowired
	private PhHunterRuleAttachmentDAO phHunterRuleAttachmentDAO;

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
				// 添加图片的类型
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

			logger.error("图片上传发生异常:" + e.getMessage());

			return ResultUtils.returnError("上传失败");
		}

	}

	@Override
	@Transactional
	public Result saveAttachment(PhHunterRuleAttachment phHunterRuleAttachment) {
		try {
			PhHunterRuleAttachment attachmentdb = saveAndModify(phHunterRuleAttachment);
			return ResultUtils.returnSuccess("上传成功", attachmentdb);
		} catch (Exception e) {
			logger.error("上传图片" + e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 回滚数据
			return ResultUtils.returnError("上传失败");
		}

	}

	@Override
	public boolean delete(Long arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PhHunterRuleAttachment get(Long arg0) {
		// TODO Auto-generated method stub
		return phHunterRuleAttachmentDAO.findOne(arg0);
	}

	@Override
	public PhHunterRuleAttachment saveAndModify(PhHunterRuleAttachment arg0) {
		// TODO Auto-generated method stub
		return phHunterRuleAttachmentDAO.save(arg0);
	}

	/*
	 * @Override public List<Long> ruleAttachmentId(Long hunterid, int type) {
	 * 
	 * return phHunterRuleAttachmentDAO.findAttachmentid(hunterid,type); }
	 */

	/*
	 * 更新图片
	 */
	@Override
	@Transactional
	public Result updateAttachment(Long saveimageid, Long updateimageid, Long hunterid, int type) {
		Result result = new Result();
		// 营业执照照片
		
		List<Long> attachemntid = ruleAttachmentId(hunterid, type);
		
		PhHunterRuleAttachment ph = phHunterRuleAttachmentDAO.findOne(saveimageid);
		
		PhHunterRuleAttachment ph2 = phHunterRuleAttachmentDAO.findOne(updateimageid);
		
		ph2.setAddress(ph.getAddress());
		ph2.setName(ph.getName());
		ph2.setHunterId(hunterid);
		ph2.setType(type);
		/*phHunterRuleAttachmentDAO.delete(ph2);
		delete(updateimageid);
		ph.setHunterId(hunterid);
		ph.setType(type);*/
		saveAndModify(ph2);
		
		result.setCode(1);
		result.setMsg("修改成功");
		return result;
	}

	@Override
	public List<Long> ruleAttachmentId(Long hunterid, int type) {
		// TODO Auto-generated method stub
		return null;
	}

	//将type=3的其他资料关联hunterid
	@Override
	public Result addAttachment(Long hunterid, Long imageid) {
		Result result = new Result();
		
		PhHunterRuleAttachment ph = phHunterRuleAttachmentDAO.findOne(imageid);
		ph.setHunterId(hunterid);
		ph.setType(3);
		saveAndModify(ph);
		
		result.setCode(1);
		result.setMsg("修改成功");
		return result;
	}

}
