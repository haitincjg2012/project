package com.alqsoft.rpc.mobile.impl;

import com.alqsoft.entity.memberaddress.MemberAddress;
import com.alqsoft.rpc.mobile.RpcMemberAddressService;
import com.alqsoft.service.memberaddress.MemberAddressService;

import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 收货地址
 * @author Xuejizheng
 * @date 2017-03-01 14:32
 * @see
 * @since 1.8
 */
@Service
@Transactional
public class RpcMemberAddressServiceImpl implements RpcMemberAddressService {

    @Autowired
    private MemberAddressService memberAddressService;

    @Override
    public MemberAddress saveAndModify(MemberAddress memberAddress) {
        return memberAddressService.saveAndModify(memberAddress);
    }

    @Override
    public boolean delete(Long aLong) {
        memberAddressService.delete(aLong);
         return true;
    }

    @Override
    public MemberAddress get(Long aLong) {
        return memberAddressService.get(aLong);
    }

	@Override
	public Result saveAddress(MemberAddress memberAddress) {
		return this.memberAddressService.saveAddressAndMid(memberAddress);
	}

    @Override
    public boolean updateBatchNotDefault(Long mid) {
        return memberAddressService.updateBatchNotDefault(mid);
    }

	@Override
	public Result updateAddress(MemberAddress memberAddress) {
		return this.memberAddressService.updateAddressAndMid(memberAddress);
	}

	@Override
	public Result delAddressById(MemberAddress memberAddress) {
		return this.memberAddressService.delAddressById(memberAddress);
	}
}
