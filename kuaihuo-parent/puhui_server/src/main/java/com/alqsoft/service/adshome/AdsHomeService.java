package com.alqsoft.service.adshome;

import java.util.List;
import java.util.Map;

import org.alqframework.orm.BaseService;
import org.alqframework.result.Result;
import org.springframework.web.multipart.MultipartFile;

import com.alqsoft.entity.adattachment.AdAttachment;
import com.alqsoft.entity.attachment.Attachment;
import com.alqsoft.vo.ProcudtTypeVO;

/**
 * 
 * @Description: TODO
 * @author Wudi
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年3月15日 下午3:44:25
 * Copyright 
 */
public interface AdsHomeService extends BaseService<AdAttachment> {
	/***
	 * 上传图片
	 * @param urlFile
	 * @param backUrl
	 * @param module
	 * @param extendFile
	 * @return
	 */
	public Result uploadAdsHomePicture(MultipartFile urlFile,Object[] backUrl,String module,String[] extendFile);
    /***
     * 保存到数据库
     * @param adAttachment 
     * @return
     */
	public Result saveAdAttachment(AdAttachment adAttachment);
	/***
	 * 删除数据
	 * @param id
	 * @return
	 */
	public Result deleteAdsHomePicture(Long id);
	
	public String uploadDetailPicture(MultipartFile urlfile, String module, String[] extendFile);
	/***
	 * 保存详情
	 */
	public Result addAdsHome(AdAttachment adAttachment);
	/**
	 * 保存修改的header
	 * @param vo
	 * @return
	 */
	public org.alqframework.webmvc.springmvc.Result saveUpdatePictureHeaderName(ProcudtTypeVO vo);
	/***
	 * 保存修改后code
	 * @param vo
	 * @return
	 */
	public org.alqframework.webmvc.springmvc.Result saveUpdatePictureCode(ProcudtTypeVO vo);

}
