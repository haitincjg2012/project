/**  
 * @Title:  HeadhuntingPageVO.java   
 * @Package com.ph.member.api.vo   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年7月12日 上午10:27:10   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.member.api.vo;

import java.io.Serializable;
import java.util.List;

/**   
 * @ClassName:  HeadhuntingPageVO   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 李杰
 * @date:   2017年7月12日 上午10:27:10     
 * @Copyright: 2017
 */
public class HeadhuntingPageVO implements Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = 8949947367103400944L;
	/**
	 * 总数
	 */
	private Integer total;
	/**
	 * 列表数据
	 */
	private List<HeadhuntingVO> data;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<HeadhuntingVO> getData() {
		return data;
	}

	public void setData(List<HeadhuntingVO> data) {
		this.data = data;
	}
}
