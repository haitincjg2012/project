package com.ph.shopping.util;

import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.member.menum.MemberResultEnum;

public final class DubboResult {
	/**
	 * 
	* @Title: getResultByEnum  
	* @Description: 根据枚举得到返回值  
	* @param @param dubboReturn
	* @param @return    参数  
	* @return Result    返回类型  
	* @throws
	 */
	public static Result getResultByEnum(RespCode dubboReturn) {
		Result result = new Result();
		setResultByEnum(dubboReturn, result);
		return result;
	}
	/**
	 * 
	* @Title: getResultByEnumOrRe  
	* @Description: 设置返回值 
	* @param @param dubboReturn
	* @param @param result
	* @param @return    参数  
	* @return Result    返回类型  
	* @throws
	 */
	public static Result setResultByEnum(RespCode dubboReturn, Result result) {
		result.setCode(dubboReturn.getCode());
		result.setMessage(dubboReturn.getMsg());
		if (dubboReturn.getCode().equals(RespCode.Code.SUCCESS.getCode())) {
			result.setSuccess(true);
		} else {
			result.setSuccess(false);
		}
		return result;
	}
	/**
	 * 
	* @Title: getResultBySuccess  
	* @Description: 返回成功值 
	* @param @param result
	* @param @return    参数  
	* @return Result    返回类型  
	* @throws
	 */
	public static Result getResultBySuccess(Result result) {
		
		return setResultByEnum(MemberResultEnum.Code.SUCCESS, result);
	}
	/**
	 * 
	* @Title: getResultBySuccess  
	* @Description: 返回成功  
	* @param @return    参数  
	* @return Result    返回类型  
	* @throws
	 */
	public static Result getResultBySuccess() {
		
		return setResultByEnum(MemberResultEnum.Code.SUCCESS, new Result());
	}
	/**
	 * 
	* @Title: getDefResult  
	* @Description: service 默认返回值  
	* @param @return    参数  
	* @return Result    返回类型  
	* @throws
	 */
	public static Result getDefResult(){
		
		return DubboResult.getResultByEnum(RespCode.Code.REQUEST_DATA_ERROR);
	}
}
