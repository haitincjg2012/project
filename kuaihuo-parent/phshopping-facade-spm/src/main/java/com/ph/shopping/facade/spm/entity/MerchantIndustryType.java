
package com.ph.shopping.facade.spm.entity;

import javax.persistence.Column;
import javax.persistence.Table;

import com.ph.shopping.common.core.base.BaseEntity;

/**
 * 批发商与行业分类（标签）关联记录表
 * @author chen
 *
 * @date 2017年8月21日
 *
 */
@Table(name = "ph_merchant_industry_type")
public class MerchantIndustryType extends BaseEntity{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -7241761743041909103L;

	@Column(name="industryTypeId")
	private Long industryTypeId;//行业分类  方便查询
	@Column(name="merchantId")
	private Long merchantId;//关联批发商
	public Long getIndustryTypeId() {
		return industryTypeId;
	}
	public void setIndustryTypeId(Long industryTypeId) {
		this.industryTypeId = industryTypeId;
	}
	public Long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	
}

