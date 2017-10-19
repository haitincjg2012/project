package com.alqsoft.poi;


import java.util.Date;

import javax.validation.constraints.NotNull;

import org.alqframework.excel.poi.ExcelError;

/**
 * 测试实体类
 * 
 * @author 张灿
 * @e-mail 1023059764@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-1-7 下午2:20:29
 * 
 */
public class User {
	@NotNull(message="用户名不能为空")
	private String name;
	@NotNull(message="值不能为空")
	private Date value;

	@ExcelError
	private String errormsg;
	
	public String getErrormsg() {
		return errormsg;
	}

	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getValue() {
		return value;
	}

	public void setValue(Date value) {
		this.value = value;
	}

	
}
