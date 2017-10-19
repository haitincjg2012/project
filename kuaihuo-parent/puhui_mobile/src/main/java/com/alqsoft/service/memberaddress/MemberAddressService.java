package com.alqsoft.service.memberaddress;

import java.util.Map;

import org.alqframework.result.Result;

import com.alqsoft.entity.memberaddress.MemberAddress;

/**
 * @author Xuejizheng
 * @date 2017-02-28 18:19
 * @see
 * @since 1.8
 */
public interface MemberAddressService {

    Result  getAddressList(Map<String,Object> map);

    Result getDefaultAddress(Map<String, Object> map);

	public Result saveAddress(MemberAddress memberAddress, Long mId, Long pId, Long cId);

	public Result updateAddress(MemberAddress memberAddress, Long mId, Long pId, Long cId);

	public Result delAddressById(MemberAddress memberAddress, Long mId);

	public Result findAddressById(MemberAddress memberAddress, Long mId);

    Result update(Long uid, Long aid);

	public Map<String, Object> getDefAddByMid(Long id);
}
