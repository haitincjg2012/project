package com.alqsoft.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 批发商上传的图片
 * 
 * @author GAOGE
 *
 */
public class PhHunterRuleAttachmentVO implements Serializable {
	List<String> ruleAttachmentList;

	public List<String> getRuleAttachmentList() {
		return ruleAttachmentList;
	}

	public void setRuleAttachmentList(List<String> ruleAttachmentList) {
		this.ruleAttachmentList = ruleAttachmentList;
	}

}
