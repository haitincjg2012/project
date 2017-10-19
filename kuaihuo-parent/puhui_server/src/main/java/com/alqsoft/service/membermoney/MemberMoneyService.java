package com.alqsoft.service.membermoney;

import org.alqframework.orm.BaseService;

import com.alqsoft.entity.membermoney.MemberMoney;

public interface MemberMoneyService extends BaseService<MemberMoney> {

	public MemberMoney getRowLock(String custOrderId);

}
