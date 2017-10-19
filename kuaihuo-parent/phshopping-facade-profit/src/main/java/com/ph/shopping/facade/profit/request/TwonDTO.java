package com.ph.shopping.facade.profit.request;

import java.io.Serializable;
/**
 * 
* @ClassName: TwonDTO
* @Description: 乡镇DTO
* @author 王强
* @date 2017年4月25日 下午6:07:22
 */
public class TwonDTO implements Serializable {

	private static final long serialVersionUID = 8588142665175945565L;
	
	private Long townId;//乡镇id

	public Long getTownId() {
		return townId;
	}

	public void setTownId(Long townId) {
		this.townId = townId;
	}

}
