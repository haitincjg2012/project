package com.alqsoft.service.industrymoney;

import org.alqframework.orm.BaseService;

import com.alqsoft.entity.industrymoney.IndustryMoney;

public interface IndustryMoneyService extends BaseService<IndustryMoney> {

	public IndustryMoney getRowLock(String orderNo);

}
