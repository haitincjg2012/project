package com.alqsoft.service.feedback;

import java.util.List;
import java.util.Map;

import org.alqframework.easyui.EasyuiResult;
import org.alqframework.orm.BaseService;

import com.alqsoft.entity.feedback.FeedBackType;

/**
 * 
 * @Title: FeedBackServiceImpl.java
 * @Description: TODO
 * @author 俞旺
 * @e-mail 13606024548@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年1月7日 下午6:09:10
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
public interface FeedBackTypeService extends BaseService<FeedBackType> {
	public EasyuiResult<List<FeedBackType>> getFeedBackTypePage(Map<String, Object> map,Integer page,Integer rows);
	public  FeedBackType  getFeedBackTypeByTypeName(String typeName);
	
	public List<FeedBackType> findFeedBackTypeAll();
}
