package com.alqsoft.entity.producttraceattachment;

import org.alqframework.orm.hibernate.IdEntity;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.alqsoft.entity.producttrace.ProductTrace;

/**
 * 系统附件
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-10-19 上午12:27:45
 * 
 */

public class ProductTraceAttachment extends IdEntity{
	
	@NotBlank(message = "附件名称不能为空")
	@Length(min = 2, max = 50, message = "附件名称必须在2到50之间，请重新输入")
	private String name;//附件名称
	
	@NotBlank(message = "附件地址不能为空")
	@Length(min = 2, max = 200, message = "附件名称必须在2到200之间，请重新输入")
	private String address;//附件地址
	
	private ProductTrace productTrace;//订单跟踪
	
	
	public ProductTrace getProductTrace() {
		return productTrace;
	}
	public void setProductTrace(ProductTrace productTrace) {
		this.productTrace = productTrace;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
}
