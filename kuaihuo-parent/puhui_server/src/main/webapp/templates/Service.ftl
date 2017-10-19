package com.alqsoft.service.${lower};

import java.util.List;
import java.util.Map;

import org.alqframework.easyui.EasyuiResult;
import org.alqframework.orm.BaseService;

import com.alqsoft.entity.${lower}.${bean};

/**
 * 
 * @Title: ${bean}Service.java
 * @Description: TODO
 * @author chenzhenbing
 * @e-mail chenzb@jinledou.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年1月7日 下午6:09:10
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
public interface ${bean}Service extends BaseService<${bean}>{
	public EasyuiResult<List<${bean}>> get${bean}Page(Map<String, Object> map,Integer page,Integer rows);
}
