package com.phshopping.api.controller.personal.upload;

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

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.ph.shopping.common.core.constant.ImgConstant;
import com.ph.shopping.common.core.customenum.CacheKeyTypeEnum;
import com.ph.shopping.common.core.other.annotation.Token;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.date.SyncDateUtil;
import com.ph.shopping.common.util.oss.OSSClientUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.member.dto.MemberUpdateDTO;
import com.ph.shopping.facade.member.service.IMemberService;
import com.phshopping.api.entity.MemberInfo;
import com.phshopping.api.uitl.UserCacheUtil;
/**
 * 
 * @ClassName:  ImageUploadController   
 * @Description:图片上传   
 * @author: 李杰
 * @date:   2017年5月25日 下午6:06:05     
 * @Copyright: 2017
 */
@RestController
@RequestMapping("api/image/")
public class ImageUploadController {

	private static final Logger logger = LoggerFactory.getLogger(ImageUploadController.class);
	
	@Autowired
	private OSSClientUtil ossClientUtil;
	
	@Reference(version = "1.0.0", retries = 0, timeout = 30000)
	private IMemberService memberService;
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
	@Token(key = "token", isCache = true, cacheType = CacheKeyTypeEnum.HASH)
	@RequestMapping(value = "uploadHeadImage", method = RequestMethod.POST)
	public Result uploadHeadImage(@RequestParam("headImg") MultipartFile headImg, @RequestParam("token") String token) {
		Result result = UserCacheUtil.getUser(token);
		if (result.isSuccess()) {
			MemberInfo memberInfo = (MemberInfo) result.getData();
			result = ResultUtil.setResult(false, "图片上传失败");
			String module = "member/head";
			long size = headImg.getSize();
			if (size > ImgConstant.MAX_SISE) {
				return ResultUtil.setResult(result, false, "图片不能超过5M");
			}
			Date createTime = new Date();
			String fileName = headImg.getOriginalFilename();
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
				String url = ossClientUtil.putObject(filePath, headImg);
				logger.info("图片上传返回路径 url = " + url);
				if (StringUtils.isNotBlank(url)) {
					result.setData(url);
					saveHeadImage(token, memberInfo, url);
					ResultUtil.setResult(result, true, "图片上传成功");
				}
			}
		}
		return result;
	}

	/**
	 * 
	 * @Title: saveHeadImage   
	 * @Description: TODO:保存图片  
	 * @param: @param token
	 * @param: @param memberInfo
	 * @param: @param headImgUrl      
	 * @return: void
	 * @author：李杰      
	 * @throws
	 */
	private void saveHeadImage(String token, MemberInfo memberInfo, String headImgUrl) {
		if (null != memberInfo) {
			MemberUpdateDTO rdto = new MemberUpdateDTO();
			rdto.setId(memberInfo.getId());
			rdto.setHeadImage(headImgUrl);
			rdto.setUpdaterId(memberInfo.getId());
			Result result = memberService.updateMember(rdto);
			logger.info("保存头像地址返回参数：result = {} ", JSON.toJSONString(result));
			if (result.isSuccess()) {
				UserCacheUtil.updateUser(token);
			}
		}
	}
}
