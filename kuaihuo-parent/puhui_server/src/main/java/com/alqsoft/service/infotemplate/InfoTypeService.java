package com.alqsoft.service.infotemplate;

import java.util.List;
import java.util.Map;

import org.alqframework.easyui.EasyuiResult;
import org.alqframework.orm.BaseService;

import com.alqsoft.entity.infotemplate.InfoType;

/**
 * 模板类型服务接口
 * @Title: InfoTypeService.java
 * @Description: TODO
 * @author 俞旺
 * @e-mail 13606024548@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年4月21日 下午5:41:17
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
public interface InfoTypeService extends BaseService<InfoType> {
	/**
	 * 分页获取模板类型
	 * @Title: getInfoTypePage
	 * @Description: TODO
	 * @param: @param map
	 * @param: @param page
	 * @param: @param rows
	 * @param: @return
	 * @return: EasyuiResult<List<InfoType>>
	 * @throws
	 */
	public EasyuiResult<List<InfoType>> getInfoTypePage(Map<String, Object> map, Integer page, Integer rows);
	
	/**
	 * 根据类型英文名获取模板类型
	 * @Title: getInfoTypeByTypeName
	 * @Description: TODO
	 * @param: @param typeName
	 * @param: @return
	 * @return: InfoType
	 * @throws
	 */
	public InfoType getInfoTypeByTypeEnglishName(String typeEnglishName);
}
