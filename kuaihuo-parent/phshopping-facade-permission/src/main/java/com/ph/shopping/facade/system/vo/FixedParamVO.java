/**  
 * @Title:  FixedParamVO.java   
 * @Package com.ph.shopping.facade.system.vo   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年6月27日 下午5:41:19   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.facade.system.vo;

import java.io.Serializable;

/**   
 * @ClassName:  FixedParamVO   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 李杰
 * @date:   2017年6月27日 下午5:41:19     
 * @Copyright: 2017
 */
public class FixedParamVO implements Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = 1084735717817765727L;
	/**
	 * id
	 */
	private Long id;
	/**
	 * 参数值
	 */
	private Double value;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
}
