package com.ph.shopping.facade.profit.dto;

import java.io.Serializable;

import com.ph.shopping.common.util.page.PageBean;

/**
 * 推广师分润记录 pc商城用
* @ClassName: PromoterProfitRecordDTO
* @Description: TODO(这里用一句话描述这个类的作用)
* @author Mr.Dong
* @date 2017年6月22日 下午9:14:42
 */
public class PromoterProfitRecordDTO extends PageBean implements Serializable   {

	private static final long serialVersionUID = 8541910400465842565L;

	private Byte enterpriseType;//企业类型  3市级代理，4县级代理，5社区代理 6商户，默认为1
	
	private String enterpriseName;//企业名称
	
	private Long promoterId;//推广师id

	
	public Byte getEnterpriseType() {
		return enterpriseType;
	}

	public void setEnterpriseType(Byte enterpriseType) {
		this.enterpriseType = enterpriseType;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public Long getPromoterId() {
		return promoterId;
	}

	public void setPromoterId(Long promoterId) {
		this.promoterId = promoterId;
	}
	
	
}
