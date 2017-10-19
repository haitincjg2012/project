/**  
 * @Title:  HttpConstants.java   
 * @Package com.ph.shopping.common.util.constants   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年5月14日 上午10:02:16   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.common.util.constants;

/**   
 * @ClassName:  HttpConstants   
 * @Description:http 相关常量   
 * @author: 李杰
 * @date:   2017年5月12日 上午10:02:16     
 * @Copyright: 2017
 */
public class HttpConstants {

	/**
	 * http 成功状态码
	 */
	public static final int SUCCEE_CODE = 200;
	/**
	 * 默认编码
	 */
	public static final String DEFAULT_ENCODING = "UTF-8";
	/**
	 * 设置连接超时时间，单位毫秒
	 */
	public static final int CONNECT_TIMEOUT = 10000;
	/**
	 * 设置从connect Manager获取Connection 超时时间，单位毫秒
	 */
	public static final int REQUEST_TIMEOUT = 10000;
	/**
	 * 请求获取数据的超时时间，单位毫秒
	 */
	public static final int SOCKET_TIMEOUT = 10000;
}
