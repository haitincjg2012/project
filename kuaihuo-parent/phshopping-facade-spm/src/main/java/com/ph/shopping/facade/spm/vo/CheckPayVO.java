package com.ph.shopping.facade.spm.vo;

import java.io.Serializable;

/**
 * 
 * @ClassName: CheckPayVO
 * @Description: 易联支付回调检查VO
 * @author 王强
 * @date 2017年6月29日 上午10:00:07
 */
public class CheckPayVO implements Serializable {
	private static final long serialVersionUID = -4959719357222462365L;
	private String md5Str;// MD5
	private Long score;// 充值金额

	public String getMd5Str() {
		return md5Str;
	}

	public void setMd5Str(String md5Str) {
		this.md5Str = md5Str;
	}

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}
}
