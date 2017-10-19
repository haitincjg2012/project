package com.alqsoft.service.indexadattach;

import org.alqframework.result.Result;
import org.springframework.ui.Model;


/**
 * @Description: 获取首页数据。广告滚动展示
 * @author Wudi
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年2月28日 下午4:05:12
 * Copyright 
 */

public interface AdAttachmentService {
/***
 * 轮播图
 * @param startInt 
 * @param endInt
 * @return
 */
public Result findAll(Integer startInt,Integer endInt);
/***
 * 通过对应的轮播图id获取详情图片
 * @param id
 * @return
 */
public void getTextDetail(Long id,Model model);
	
}
