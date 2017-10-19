package com.ph.shopping.common.util.page;

import java.io.Serializable;

/**
 * @项目：phshopping-facade-permission
 *
 * @描述：分页基础Bean
 *
 * @作者： Mr.chang
 *
 * @创建时间：2017年3月17日
 * @Copyright @2017 by Mr.chang
 */
public class PageBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8440299431844004877L;
	
	private int pageNum;
	private int pageSize;
	
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	
}
