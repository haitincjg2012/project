/**  
 * @Title:  WebError.java   
 * @Package com.phshopping.api.exception   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年7月27日 上午10:13:19   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.phshopping.api.exception;

/**   
 * @ClassName:  WebError   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 李杰
 * @date:   2017年7月27日 上午10:13:19     
 * @Copyright: 2017
 */
public class WebError extends RuntimeException{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = 1105894626325872464L;

	public WebError(Throwable th,String msg){
		super(msg, th);
	}
	
	public WebError(String msg){
		super(msg);
	}
}
