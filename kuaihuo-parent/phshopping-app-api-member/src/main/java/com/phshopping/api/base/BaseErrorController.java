package com.phshopping.api.base;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.ph.shopping.common.util.result.Result;
import com.phshopping.api.appenum.AppResultEnum;
import com.phshopping.api.exception.WebError;
/**
 * 
 * @ClassName:  BaseErrorController   
 * @Description:app 统一错误 处理  
 * @author: 李杰
 * @date:   2017年5月10日 上午11:26:05     
 * @Copyright: 2017
 */
@ControllerAdvice
public class BaseErrorController {
	
	private static final Logger logger = LoggerFactory.getLogger(BaseErrorController.class);
	
	/**
	 * 
	 * @Title: defaultErrorHandler   
	 * @Description: app 异常处理返回   
	 * @param: @param request
	 * @param: @param e
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	@ExceptionHandler(value = Throwable.class)
	public @ResponseBody Result defaultErrorHandler(HttpServletRequest request, Throwable e) {
		logfile(request, e, "app数据服务器未知异常");
		return new Result(false, AppResultEnum.SERVER_ERROR.getCode(), AppResultEnum.SERVER_ERROR.getMsg());
	}
	/**
	 * 
	 * @Title: pageNoFoundHandler   
	 * @Description: 资源不存在异常处理   
	 * @param: @param request
	 * @param: @param e
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	@ExceptionHandler(value = NoHandlerFoundException.class)
	@ResponseBody
	public Result pageNoFoundHandler(HttpServletRequest request, NoHandlerFoundException e) {
		logfile(request, e, "未找到相关资源");
		return new Result(false, AppResultEnum.NOHANDLER.getCode(), AppResultEnum.NOHANDLER.getMsg());
	}
	/**
	 * 
	 * @Title: toWebError   
	 * @Description: 加载页面失败   
	 * @param: @param request
	 * @param: @param e
	 * @param: @return      
	 * @return: String
	 * @author：李杰      
	 * @throws
	 */
	@ExceptionHandler(value = WebError.class)
	public String toWebError(HttpServletRequest request, WebError e) {
		logfile(request, e, "加载页面失败");
		return "error/500";
	}
	/**
	 * 
	 * @Title: logfile   
	 * @Description: 输出错误日志
	 * @param: @param request
	 * @param: @param e      
	 * @return: void
	 * @author：李杰      
	 * @throws
	 */
	private void logfile(HttpServletRequest request, Throwable e, String msg) {
		logger.error("BaseErrorController Throwable : ", e);
		logger.error("error message = " + msg + " <> " + e.getMessage());
		logger.error("request error url : " + request.getRequestURL());
	}
}
