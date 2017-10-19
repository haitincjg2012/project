package com.alqsoft.service.memberaddress;

import com.alqsoft.entity.member.Member;
import com.alqsoft.entity.memberaddress.MemberAddress;
import org.alqframework.orm.BaseService;
import org.alqframework.result.Result;

/**
 * 收货地址管理
 * @author Xuejizheng
 * @date 2017-03-01 17:08
 * @see
 * @since 1.8
 */
public interface MemberAddressService extends BaseService<MemberAddress> {

	public Result saveAddressAndMid(MemberAddress memberAddress);

    boolean updateBatchNotDefault(Long mid);

	public Result updateAddressAndMid(MemberAddress memberAddress);

	public Result delAddressById(MemberAddress memberAddress);

	public MemberAddress getmemAddById(Long aId);

	MemberAddress getDefaultMemberAddressByMember(Member member);

	MemberAddress getMemberAddressByMemberAndIsDefaultAndIsDelete(Member member);
}
