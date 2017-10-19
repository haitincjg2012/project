/**  
 * @Title:  LogisticsDTO.java   
 * @Package com.ph.shopping.facade.system.dto   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年6月13日 下午10:53:27   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.facade.system.dto;

import com.ph.shopping.common.core.base.BaseValidate;
import org.hibernate.validator.constraints.NotBlank;

/**   
 * @ClassName:  LogisticsDTO   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 李杰
 * @date:   2017年6月13日 下午10:53:27     
 * @Copyright: 2017
 */
public class LogisticsDTO extends BaseValidate{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = -1764883642297620533L;
	/**
	 * 主键ID
	 */
	private Long id;
	/**
	 * 物流公司名字
	 */
	@NotBlank(message = "物流公司名字不能为空")
	private String logisticsName;
	/**
	 * 物流公司电话
	 */
	@NotBlank(message = "联系电话不能为空")
	private String telphone;
	/**
	 * 物流公司官网地址
	 */
	private String dnfAddress;
	/**
	 * 物流公司名称拼音简写
	 */
	@NotBlank(message = "物流公司名称拼音简写不能为空")
	private String logisticsSpell;
	
	public String getLogisticsName() {
		return logisticsName;
	}
	public void setLogisticsName(String logisticsName) {
		this.logisticsName = logisticsName;
	}
	public String getTelphone() {
		return telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	public String getDnfAddress() {
		return dnfAddress;
	}
	public void setDnfAddress(String dnfAddress) {
		this.dnfAddress = dnfAddress;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getLogisticsSpell() {
		return logisticsSpell;
	}

	public void setLogisticsSpell(String logisticsSpell) {
		this.logisticsSpell = logisticsSpell;
	}
}
