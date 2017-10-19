package com.alqsoft.rpc.mobile;

import com.alqsoft.entity.member.Member;
import com.alqsoft.entity.shopcart.ShopCart;
import org.alqframework.orm.BaseService;
import org.alqframework.result.Result;

/**
 * @author Xuejizheng
 * @date 2017-03-01 13:36
 * @see
 * @since 1.8
 */
public interface RpcShopCartService extends BaseService<ShopCart> {


    Result batchDelete(Member member,Long hid);
}
