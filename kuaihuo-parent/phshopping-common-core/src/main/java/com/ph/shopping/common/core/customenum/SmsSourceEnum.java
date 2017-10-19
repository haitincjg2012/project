/**  
 * @Title:  SmsSourceEnum.java   
 * @Package com.ph.shopping.common.core.customenum   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年5月17日 下午4:54:03   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.common.core.customenum;

import com.alibaba.druid.util.StringUtils;

/**
 * 
 * @ClassName: SmsSourceEnum
 * @Description: 短信来源（类型）枚举   
 * @author liuy
 * @date 2017年6月1日 下午4:11:38
 */
public enum SmsSourceEnum {

	MEMBER("0","会员"),
	AGENT("1","代理商"),
	SUPPLIER("2","供应商"),
	MERCHANT("3","商户"),
	HUNTER_MEMBER("4","批发商会员"),
	STORE("5","店面经理代理");
	
	/**
	 * 类型码
	 */
    private String code;
    /**
	 * 描述
	 */
    private String msg;

	private SmsSourceEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	/**
	 * 
	 * @Title: getSmsSourceEnumByCode
	 * @Description: 根据code的值获取SmsSourceEnum枚举对象
	 * @author liuy
	 * @date  2017年6月1日 下午5:41:15
	 * @param code
	 * @return
	 */
	public static SmsSourceEnum getSmsSourceEnumByCode(String code){
		for(SmsSourceEnum smsSourceEnum : SmsSourceEnum.values()){
			if(StringUtils.equals(code, smsSourceEnum.getCode())){
				return smsSourceEnum;
			}
		}
		return null;
	}
}
