package com.ph.shopping.facade.member.exception;

import com.ph.shopping.common.core.exception.BizException;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.facade.member.menum.MemberResultEnum;
/**
 * 
 * @ClassName:  MemberException   
 * @Description:会员相关异常处理返回   
 * @author: 李杰
 * @date:   2017年5月2日 上午9:31:17     
 * @Copyright: 2017
 */
public class MemberException extends BizException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2215120646702088066L;

	public MemberException(MemberResultEnum result) {
		super(result.getMsg(), result.getCode());
	}

	public MemberException(String msg, String code) {
		super(msg, code);
	}
	public MemberException() {
	}

	public MemberException(String message) {
		super(message, RespCode.Code.INTERNAL_SERVER_ERROR.getCode());
	}

}
