package com.ph.shopping.common.util.result;

import java.io.Serializable;

/**
 * @项目：phshopping-common-util
 *
 * @描述：通用消息返回
 *
 * @作者： Mr.chang
 *
 * @创建时间：2017年3月8日
 *
 * @Copyright @2017 by Mr.chang
 */
public class Result implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2540464186250760929L;
	
	private boolean success;//是否成功
	private String code;//异常码
	private String message;//返回消息
	private Object data;//返回数据
	private long count;//总条数

	public Result(boolean success) {
		this.success = success;
	}

	public Result(boolean success, String code, String message) {
		this.success = success;
		this.code = code;
		this.message = message;

	}
	public Result(boolean success, String code, String message,Object data) {
		this.success = success;
		this.code = code;
		this.message = message;
		this.data=data;
	}

	public Result(boolean success, String message, Object data) {
		this.success = success;
		this.message = message;
		this.data = data;
	}

	public Result(boolean success, Object data, long count) {
		super();
		this.success = success;
		this.data = data;
		this.count = count;
	}

	public Result(boolean success,String code, String message, Object data, long count) {
		super();
		this.success = success;
		this.code = code;
		this.message = message;
		this.data = data;
		this.count = count;
	}

	public Result() {
		super();
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}

    public <T> T getData(Class<T> tClass) {
        return tClass.cast(this.getData());
    }

}
