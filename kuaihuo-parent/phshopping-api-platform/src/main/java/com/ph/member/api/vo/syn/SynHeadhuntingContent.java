/**  
 * @Title:  SynHeadhuntingContent.java   
 * @Package com.ph.member.api.vo.syn   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年7月12日 上午10:22:11   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.member.api.vo.syn;

import java.io.Serializable;
import java.util.List;

/**   
 * @ClassName:  SynHeadhuntingContent   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 李杰
 * @date:   2017年7月12日 上午10:22:11     
 * @Copyright: 2017
 */
public class SynHeadhuntingContent implements Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = 4303886628386840823L;
	/**
	 * 总条数
	 */
	private Integer count;
	
	/**
	 * 详细内容
	 */
	private List<SynHeadhuntingDetailVO> hunterList;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public List<SynHeadhuntingDetailVO> getHunterList() {
		return hunterList;
	}

	public void setHunterList(List<SynHeadhuntingDetailVO> hunterList) {
		this.hunterList = hunterList;
	}
}
