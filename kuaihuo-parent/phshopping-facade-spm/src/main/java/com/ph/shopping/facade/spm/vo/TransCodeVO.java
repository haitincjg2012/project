package com.ph.shopping.facade.spm.vo;

import java.io.Serializable;

/**
 * 
 * @ClassName: TransCodeVO
 * @Description: 交易码VO
 * @author 王强
 * @date 2017年6月2日 下午3:57:34
 */
public class TransCodeVO implements Serializable {

	private static final long serialVersionUID = 213722430654823086L;

	private Integer transCode;//交易码
	private String source;// 来源

	public Integer getTransCode() {
		return transCode;
	}

	public void setTransCode(Integer transCode) {
		this.transCode = transCode;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
}
