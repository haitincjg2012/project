package com.ph.base;

import com.ph.shopping.common.core.exception.BizException;
import com.ph.shopping.common.util.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * @项目：phshopping-api-platform
 *
 * @描述：通用异常处理
 *
 * @作者： Mr.chang
 *
 * @创建时间：2017年3月27日
 *
 * @Copyright @2017 by Mr.chang
 */
@ControllerAdvice
public class BaseErrorController {
	
	private static Logger logger = LoggerFactory.getLogger(BaseErrorController.class);
	
	/**
	 * 500异常处理
	 * @param request
	 * @param e
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(value=Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest request, Exception e) {
		logger.error("异常信息,e={}",e);
		ModelAndView view = new ModelAndView();
		view.addObject("code", 500);
		view.addObject("msg", e.getMessage());
		view.addObject("url", request.getRequestURL());
		view.setViewName("error/500");
		return view;
    }
	
	/**
	 * 404异常处理
	 * @param request
	 * @param e
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(value=NoHandlerFoundException.class)
    public ModelAndView pageNoFoundHandler(HttpServletRequest request, Exception e) {
		logger.error("异常信息,e={}",e);
		ModelAndView view = new ModelAndView();
		view.addObject("code", 404);
		view.addObject("msg", "页面未找到");
		view.addObject("url", request.getRequestURL());
		view.setViewName("error/404");
		return view;
    }
	
	/**
	 * biz业务异常处理
	 * @param e
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(value=BizException.class)
	@ResponseBody
	public Result bizExceptionHandler(BizException e) {
		logger.error("异常信息, msg={},code={},e={}",e.getMsg(),e.getCode(),e.getStackTrace());
		return new Result(false, e.getCode(), e.getMsg());
	}
	
//	/**
//	 * dubbox调用异常处理
//	 * @param request
//	 * @param e
//	 * @return
//	 * @throws Exception
//	 */
//	@ExceptionHandler(value=RpcException.class)
//	@ResponseBody
//	public Result bizExceptionHandler(RpcException e) throws Exception {
//		return new Result(false, String.valueOf(e.getCode()), "dubbo接口调用异常");
//	}
	

}
