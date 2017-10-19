package com.ph.shopping.common.util.core;

import com.ph.shopping.common.util.result.Result;

/**
 * ResultUtil
 * 
 * @author WQiang
 * 
 */
public class ResultUtil {

	/**
	 * @methodname getResult 的描述：分页使用
	 * @param respCode
	 * @param data
	 * @param count
	 * @return com.ph.shopping.common.util.result.Result
	 * @author 郑朋
	 * @create 2017/4/25
	 */
	public static Result getResult(RespCode respCode, Object data, long count) {

		Result result = new Result();
		result.setData(data);
		result.setMessage(respCode.getMsg());
		result.setCode(respCode.getCode());
		result.setCount(count);
		result.setSuccess("200".equals(respCode.getCode()));
		return result;
	}

	/**
	 * @methodname getResult 的描述：获取数据
	 * @param respCode
	 * @param data
	 * @return com.ph.shopping.common.util.result.Result
	 * @author 郑朋
	 * @create 2017/4/25
	 */
	public static Result getResult(RespCode respCode, Object data) {
		Result result = new Result();
		result.setData(data);
		result.setMessage(respCode.getMsg());
		result.setCode(respCode.getCode());
		result.setSuccess("200".equals(respCode.getCode()));
		return result;
	}

	/**
	 * @methodname getResult 的描述：获取数据
	 * @param respCode
	 * @return com.ph.shopping.common.util.result.Result
	 * @author 郑朋
	 * @create 2017/4/25
	 */
	public static Result getResult(RespCode respCode) {
		Result result = new Result();
		result.setMessage(respCode.getMsg());
		result.setCode(respCode.getCode());
		result.setSuccess("200".equals(respCode.getCode()));
		return result;
	}
	/**
	 * 
	 * @Title: setResult   
	 * @Description: 设置返回结果   
	 * @param: @param result
	 * @param: @param respCode
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	public static Result setResult(Result result, RespCode respCode) {
		result.setMessage(respCode.getMsg());
		result.setCode(respCode.getCode());
		result.setSuccess(RespCode.Code.SUCCESS.getCode().equals(respCode.getCode()));
		return result;
	}
	/**
	 * 
	 * @Title: setResult   
	 * @Description:设置返回结果   
	 * @param: @param result
	 * @param: @param flag
	 * @param: @param message
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	public static Result setResult(Result result, boolean flag, String message) {
		if (result == null) {
			result = new Result();
		}
		setResultValue(result, flag, message);
		return result;
	}

	/**
	 * 
	 * @Title: setResult   
	 * @Description: 设置返回结果  
	 * @param: @param flag
	 * @param: @param message
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	public static Result setResult(boolean flag, String message) {
		Result result = new Result();
		setResultValue(result, flag,message);
		return result;
	}
	/**
	 * 
	 * @Title: setResultValue   
	 * @Description: 赋值操作   
	 * @param: @param result
	 * @param: @param flag
	 * @param: @param message      
	 * @return: void
	 * @author：李杰      
	 * @throws
	 */
	private static void setResultValue(Result result, boolean flag, String message) {
		result.setCode(flag ? RespCode.Code.SUCCESS.getCode() : RespCode.Code.FAIL.getCode());
		result.setSuccess(flag);
		result.setMessage(message);
	}
}
