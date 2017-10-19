/**  
 * @Title:  AppScoreQueryDTO.java   
 * @Package com.phshopping.api.controller.dto   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年7月26日 下午5:18:06   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.phshopping.api.controller.dto;

import java.io.Serializable;

/**   
 * @ClassName:  AppScoreQueryDTO   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 李杰
 * @date:   2017年7月26日 下午5:18:06     
 * @Copyright: 2017
 */
public class AppScoreQueryDTO implements Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = 7850310118993688783L;
	/**
	 * 请求token
	 */
	private String token;
	
	private Integer pageNum;
	
	private Integer pageSize;
	/**
	 * 是否查询列表
	 */
	private Byte isQueryList;
	/**
	 * 查询积分类型
	 */
	private Byte type;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Byte getIsQueryList() {
		return isQueryList;
	}

	public void setIsQueryList(Byte isQueryList) {
		this.isQueryList = isQueryList;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}
	
}
