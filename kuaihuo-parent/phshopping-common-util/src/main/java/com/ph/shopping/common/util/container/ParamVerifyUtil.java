package com.ph.shopping.common.util.container;

import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;

/**
 * 
 * @ClassName: ParamVerifyUtil
 * @Description: TODO(参数非空验证工具类 )
 * @author lijie
 * @date 2017年3月13日
 *
 */
public class ParamVerifyUtil {
	
	/**
	 * 
	* @Title: entityIsNotNullByField  
	* @Description: 验证指定字段 是否为空  
	* @param @param obj
	* @param @param fields
	* @param @return    参数  
	* @return boolean    返回类型  
	* @throws
	 */
	public static boolean entityIsNotNullByField(Object obj, String[] fields) {
		boolean flag = false;
		if (obj != null) {
			flag = (fields == null || fields.length == 0);
			if (!flag) {
				Object fieldValue;
				for (String str : fields) {
					fieldValue = ClassUtil.getPropertiesValue(str, obj);
					flag = objIsNotNull(fieldValue);
					if (!flag) {
						break;
					}
				}
			}
		}
		return flag;
	}

	/**
	 * 
	* @Title: objIsNotNull  
	* @Description: 验证常用的 类型是否为空 
     * @param  obj
     * @return boolean    返回类型
     */
    public static <T> boolean objIsNotNull(T obj) {
		if (obj == null) {
            return false;
        } else if (obj instanceof String) {
            if (StringUtils.isBlank(obj.toString()))
                return false;
        } else if (obj instanceof Collection) {
            if (((Collection<?>) obj).isEmpty())
                return false;
        } else if (obj instanceof Map) {
            if (((Map<?, ?>) obj).isEmpty())
                return false;
        } else if (obj instanceof Object[]) {
            if (((Object[]) obj).length < 1)
                return false;
        }
        return true;
	}
}
