package com.ph.shopping.facade.profit.dto;

import java.io.Serializable;

/**
 * 
* @ClassName: FrozenDTO
* @Description: 冻结/解冻实体
* @author 王强
* @date 2017年6月9日 下午10:34:55
 */
public class FrozenDTO implements Serializable {

	private static final long serialVersionUID = 5721016482321185163L;
	
	private Long balanceId;//余额id
	
	private Integer flag;//1代表冻结 对应数据库1，2解冻 对应数据库0

	public Long getBalanceId() {
		return balanceId;
	}

	public void setBalanceId(Long balanceId) {
		this.balanceId = balanceId;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}
}
