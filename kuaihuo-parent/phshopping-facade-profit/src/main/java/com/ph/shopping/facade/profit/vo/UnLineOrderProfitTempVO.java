package com.ph.shopping.facade.profit.vo;

import java.io.Serializable;
import java.util.List;

import com.ph.shopping.facade.profit.entity.UnlineOrderProfit;

/**
 * @ClassName: UnLineOrderProfitTempVO
 * @Description: 线下订单分润临时表
 * @author liuy
 * @date 2017年7月25日 上午10:39:26
 */
public class UnLineOrderProfitTempVO  implements Serializable{
	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 线下订单分润列表（数据库对应分润流水表）
	 */
	List<UnlineOrderProfit> unLineOrderProfitList;
	/**
	 * 代理商分润列表
	 */
	List<UnlineOrderProfit> agentProfitList;
	/**
	 * 会员分润列表，用于更新订单状态为2：会员分润
	 */
	List<UnlineOrderProfit> memberProfitList;
	/**
	 * 推广师分润列表，用于清空会员分润积分字段
	 */
	List<UnlineOrderProfit> promotionProfitList;
	public List<UnlineOrderProfit> getUnLineOrderProfitList() {
		return unLineOrderProfitList;
	}
	public void setUnLineOrderProfitList(List<UnlineOrderProfit> unLineOrderProfitList) {
		this.unLineOrderProfitList = unLineOrderProfitList;
	}
	public List<UnlineOrderProfit> getMemberProfitList() {
		return memberProfitList;
	}
	public void setMemberProfitList(List<UnlineOrderProfit> memberProfitList) {
		this.memberProfitList = memberProfitList;
	}
	public List<UnlineOrderProfit> getPromotionProfitList() {
		return promotionProfitList;
	}
	public void setPromotionProfitList(List<UnlineOrderProfit> promotionProfitList) {
		this.promotionProfitList = promotionProfitList;
	}
	public List<UnlineOrderProfit> getAgentProfitList() {
		return agentProfitList;
	}
	public void setAgentProfitList(List<UnlineOrderProfit> agentProfitList) {
		this.agentProfitList = agentProfitList;
	}
	
	
}
