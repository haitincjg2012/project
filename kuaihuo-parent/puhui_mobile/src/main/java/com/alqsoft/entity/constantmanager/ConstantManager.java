package com.alqsoft.entity.constantmanager;

import org.alqframework.orm.hibernate.IdEntity;
import org.apache.ibatis.type.Alias;

/**
 * 
 * @Title: ConstantManager.java
 * @Description:常量管理实体类
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年3月14日 下午5:35:17
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 *
 */
@Alias("constantmanager")
public class ConstantManager extends IdEntity {

	private String chinaName;// 中文名称
	private String englishName;// 常量名称(大写、英文、唯一性)
	private String constantValue;// 常量值
	private String unit;// 单位 小时，分钟，秒、$等
	private Integer isMemory = 0;// 0：不放入内存，1代表常驻内存

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getIsMemory() {
		return isMemory;
	}

	public void setIsMemory(Integer isMemory) {
		this.isMemory = isMemory;
	}

	public String getChinaName() {
		return chinaName;
	}

	public void setChinaName(String chinaName) {
		this.chinaName = chinaName;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public String getConstantValue() {
		return constantValue;
	}

	public void setConstantValue(String constantValue) {
		this.constantValue = constantValue;
	}
}
