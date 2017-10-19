/**  
 * @Title:  MemberShareRecordDTO.java   
 * @Package com.ph.shopping.facade.member.dto   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年7月27日 下午2:32:18   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.facade.member.dto;

import javax.validation.constraints.NotNull;

import com.ph.shopping.common.core.base.BaseValidate;
import com.ph.shopping.common.util.page.PageBean;

/**   
 * @ClassName:  MemberShareRecordDTO   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 李杰
 * @date:   2017年7月27日 下午2:32:18     
 * @Copyright: 2017
 */
public class MemberShareRecordDTO extends BaseValidate{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = 4771976146852858611L;

	@NotNull(message="会员ID不能为空")
	private Long memberId;
	
	@NotNull(message="查询类型不能为空")
	private Byte type;

	@NotNull(message="分页条件不能为空")
	private PageBean page;

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public PageBean getPage() {
		return page;
	}

	public void setPage(PageBean page) {
		this.page = page;
	}
}
