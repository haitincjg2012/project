/**  
 * @Title:  LogisticsVO.java   
 * @Package com.ph.shopping.facade.system.vo   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年6月13日 下午10:59:27   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.facade.system.vo;

import java.io.Serializable;
import java.util.Date;

/**   
 * @ClassName:  LogisticsVO   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 李杰
 * @date:   2017年6月13日 下午10:59:27     
 * @Copyright: 2017
 */
public class LogisticsVO implements Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	/**
     * 物流公司名称
     */
    private String logisticsName;

    /**
     * 物流公司图标
     */
    private String image;

    /**
     * 电话
     */
    private String telphone;

    /**
     * 地址
     */
    private String dnfAddress;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogisticsName() {
		return logisticsName;
	}

	public void setLogisticsName(String logisticsName) {
		this.logisticsName = logisticsName;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
