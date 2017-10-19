package com.alqsoft.vo;

import java.io.Serializable;
import java.util.List;

public class OrderListVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<CreateOrderVO> list;

	public List<CreateOrderVO> getList() {
		return list;
	}

	public void setList(List<CreateOrderVO> list) {
		this.list = list;
	}
	
}
