package com.ph.shopping.facade.profit.vo;

import java.io.Serializable;
/**
 * 
* @ClassName: AgentVo
* @Description: 代理商VO
* @author 王强
* @date 2017年4月25日 下午4:58:45
 */
public class AgentVO implements Serializable {

	private static final long serialVersionUID = -1198434726324834328L;
	
	private String agentName;//代理商名称
	private String promoterName;//代理商推荐人名称
	private Long   promoterId;//推广师id
	private Long   agentId;//代理id
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public String getPromoterName() {
		return promoterName;
	}
	public void setPromoterName(String promoterName) {
		this.promoterName = promoterName;
	}
	public Long getPromoterId() {
		return promoterId;
	}
	public void setPromoterId(Long promoterId) {
		this.promoterId = promoterId;
	}
	public Long getAgentId() {
		return agentId;
	}
	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}

}
