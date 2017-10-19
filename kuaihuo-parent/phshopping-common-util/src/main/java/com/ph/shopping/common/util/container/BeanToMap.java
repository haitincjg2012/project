/**  
 * @Title:  BeanToMap.java   
 * @Package com.ph.shopping.common.util.container   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年5月18日 上午10:00:49   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.common.util.container;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ph.shopping.common.util.date.SyncDateUtil;

/**   
 * @ClassName:  BeanToMap   
 * @Description:bean 转 map   
 * @author: 李杰
 * @date:   2017年5月18日 上午10:00:49     
 * @Copyright: 2017
 */
public class BeanToMap {

	private static final Logger log = LoggerFactory.getLogger(BeanToMap.class);
	
	/**
	 * 
	 * @Title: beanToMap   
	 * @Description:bean 转 map   
	 * @param: @param obj
	 * @param: @return      
	 * @return: Map<String,Object>
	 * @author：李杰      
	 * @throws
	 */
	public static Map<String, Object> beanToMap(Object obj) {
		Map<String, Object> map = ContainerUtil.map();
		if (obj != null) {
			String[] fields = ClassUtil.getFiledStrByClassBySuper(obj.getClass());
			if (fields != null) {
				for (String field : fields) {
					Object value = ClassUtil.getPropertiesValue(field, obj);
					if (value != null) {
						map.put(field, value);
					}
				}
			}
		}
		return map;
	}
	/**
	 * 
	 * @Title: beanToMapByDate   
	 * @Description:bean 转 map by date
	 * @param: @param obj
	 * @param: @param dateMap
	 * @param: @return
	 * @param: @throws Exception      
	 * @return: Map<String,Object>
	 * @author：李杰      
	 * @throws
	 */
	public static Map<String, Object> beanToMapByDate(Object obj, Map<String, String> dateMap){
		Map<String, Object> map = ContainerUtil.map();
		if (obj != null) {
			String[] fields = ClassUtil.getFiledStrByClassBySuper(obj.getClass());
			if (fields != null) {
				for (String field : fields) {
					Object value = ClassUtil.getPropertiesValue(field, obj);
					if (value != null) {
						map.put(field, getValueByDateMap(field, value, dateMap));
					}
				}
			}
		}
		return map;
	}
	/**
	 * 
	 * @Title: getValueByDateMap   
	 * @Description: 处理时间格式   
	 * @param: @param filed
	 * @param: @param value
	 * @param: @param dateMap
	 * @param: @return      
	 * @return: Object
	 * @author：李杰      
	 * @throws
	 */
	private static Object getValueByDateMap(String filed, Object value, Map<String, String> dateMap) {
		if (dateMap != null) {
			String format = dateMap.get(filed);
			if (StringUtils.isNotBlank(format)) {
				if (value instanceof String) {
					value = SyncDateUtil.strToDate(format, value.toString());
				}
			}
		}
		return value;
	}
	/**
	 * 
	 * @Title: getMapByStr   
	 * @Description: 将 bean 转换成 map String   
	 * @param: @param bean
	 * @param: @return      
	 * @return: Map<String,String>
	 * @author：李杰      
	 * @throws
	 */
	public static Map<String, String> getMapByStr(Object bean) {
		Map<String, String> map = null;
		try {
			if (bean != null) {
				map = BeanUtils.describe(bean);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return map;
	}
}
