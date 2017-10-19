package com.phshopping.api.controller;

import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ph.shopping.common.core.constant.ImgConstant;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.date.SyncDateUtil;
import com.ph.shopping.common.util.oss.OSSClientUtil;
import com.ph.shopping.common.util.result.Result;
/**
 * 
 * @ClassName:  ImageUploadController   
 * @Description:图片上传   
 * @author: 李杰
 * @date:   2017年5月25日 下午6:06:05     
 * @Copyright: 2017
 */
@RestController
@RequestMapping("api/merchant/image/")
public class ImageUploadController extends BaseMerchantController{

	private static final Logger logger = LoggerFactory.getLogger(ImageUploadController.class);
	
	@Autowired
	private OSSClientUtil ossClientUtil;
	/**
	 * 
	 * @Title: uploadHeadImage   
	 * @Description: 头像上传   
	 * @param: @param uploadImg
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public Result uploadHeadImage(@RequestParam("image") MultipartFile image) {
		Result result = ResultUtil.setResult(false, "图片上传失败");
		try {
			String module = "merchant/app/image";
			long size = image.getSize();
			if (size > ImgConstant.MAX_SISE) {
				return ResultUtil.setResult(result, false, "图片不能超过5M");
			}
			Date createTime = new Date();
			String fileName = image.getOriginalFilename();
			boolean isFile = StringUtils.endsWithAny(StringUtils.lowerCase(fileName),
					new String[] { ".png", ".jpg", ".jpeg", ".bmp", ".gif" });
			if (isFile) {
				StringBuilder sbud = new StringBuilder();
				sbud.append(module).append("/").append(SyncDateUtil.dateToString("yyyyMMdd", createTime)).append("/")
						.append(SyncDateUtil.dateToString("yyyyMMddHHmmssSSS", createTime))
						.append(RandomStringUtils.randomNumeric(6)).append(".")
						.append(fileName.substring(fileName.lastIndexOf(".") + ".".length()));
				String filePath = sbud.toString();
				logger.info("图片上传参数 upload data :" + filePath);
				String url = ossClientUtil.putObject(filePath, image);
				logger.info("图片上传返回路径 url = " + url);
				if (StringUtils.isNotBlank(url)) {
					result.setData(url);
					ResultUtil.setResult(result, true, "图片上传成功");
				}
			}
		} catch (Exception e) {
			logger.error("图片上传失败", e);
		}
		return result;
	}

}
