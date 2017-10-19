/**  
 * @Title:  UserException.java   
 * @Package com.ph.shopping.common.core.exception.user   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年6月8日 下午5:51:25   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.common.core.exception.user;

/**   
 * @ClassName:  UserException   
 * @Description:用户异常相关处理   
 * @author: 李杰
 * @date:   2017年6月8日 下午5:51:25     
 * @Copyright: 2017
 */
public class UserException extends RuntimeException{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = -8586811329749480826L;

	public UserException(Throwable e){
		super(e);
	}
	
	public UserException(String msg){
		super(msg);
	}
	
	public UserException(String msg,Throwable e){
		super(msg,e);
	}
}
