package com.alqsoft.rpc.mobile;

import com.alqsoft.entity.memberaddress.MemberAddress;
import org.alqframework.orm.BaseService;
import org.alqframework.result.Result;

/**
 * @author Xuejizheng
 * @date 2017-03-01 14:31
 * @see
 * @since 1.8
 */
public interface RpcMemberAddressService extends BaseService<MemberAddress> {
	
	public Result saveAddress(MemberAddress memberAddress);

    boolean updateBatchNotDefault(Long mid);
    
    public Result updateAddress(MemberAddress memberAddress);
    
    public Result delAddressById(MemberAddress memberAddress);
}
