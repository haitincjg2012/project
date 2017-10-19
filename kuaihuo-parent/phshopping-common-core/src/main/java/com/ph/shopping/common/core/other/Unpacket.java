package com.ph.shopping.common.core.other;

import java.util.Map;

import com.google.gson.Gson;
/**
 * 
* @ClassName: GsonUtil
* @Description: Gson转换类
* @author 王强
* @date 2017年6月21日 下午4:21:01
 */
public class Unpacket {
	/**
	 * 
	* @Title: mapToJson
	* @Description: Map转json
	* @author 王强
	* @date  2017年6月21日 下午4:21:28
	* @param map
	* @return
	 */
	public static <T> String mapToJson(Map<String, Object> map) {
		 Gson gson = new Gson();
		 String jsonStr = gson.toJson(map);
		 return jsonStr;
		}
	
	/**
	 * 
	* @Title: unpackt
	* @Description: map转bean
	* @author 王强
	* @date  2017年6月21日 下午4:21:44
	* @param map
	* @param clazz
	* @return
	 */
	public static <T> T unpacket(Map<String, Object> map,Class<T> clazz) {
		return new Gson().fromJson(mapToJson(map),  clazz);
	}
}
