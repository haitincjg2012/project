package com.ph.shopping.facade.score.exception;

import com.ph.shopping.common.core.exception.BizException;
/**
 * 
* @ClassName: ScoreException
* @Description:积分异常类
* @author 王强
* @date 2017年4月26日 下午5:19:53
 */
public class ScoreException extends BizException {
private static final long serialVersionUID = 4790611300720211303L;
	
	public ScoreException(ScoreExceptionEnum exceptionEnum) {
		super(exceptionEnum.getMsg(),exceptionEnum.getCode());
	}

	public ScoreException() {
	}
}
