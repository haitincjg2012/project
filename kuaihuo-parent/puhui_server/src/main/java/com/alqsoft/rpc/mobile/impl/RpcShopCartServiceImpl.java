package com.alqsoft.rpc.mobile.impl;

import com.alqsoft.entity.member.Member;
import com.alqsoft.entity.shopcart.ShopCart;
import com.alqsoft.rpc.mobile.RpcShopCartService;
import com.alqsoft.service.shopcart.ShopCartService;
import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Xuejizheng
 * @date 2017-03-01 13:32
 * @see
 * @since 1.8
 */
@Service
@Transactional
public class RpcShopCartServiceImpl implements RpcShopCartService {

    @Autowired
    private ShopCartService shopCartService;

    @Override
    public ShopCart saveAndModify(ShopCart shopCart) {
        return shopCartService.saveAndModify(shopCart);
    }

    @Override
    public boolean delete(Long aLong) {
        shopCartService.delete(aLong);
        return false;
    }

    @Override
    public ShopCart get(Long aLong) {
        return shopCartService.get(aLong);
    }

    @Override
    public Result batchDelete(Member member,Long hid) {

        return shopCartService.deleteBatch(member,hid);
    }
}
