package com.alqsoft.service.shopcart;

import com.alqsoft.entity.member.Member;
import com.alqsoft.entity.shopcart.ShopCart;

import java.util.List;

import org.alqframework.orm.BaseService;
import org.alqframework.result.Result;

/**
 * @author Xuejizheng
 * @date 2017-03-01 11:31
 * @see
 * @since 1.8
 */
public interface ShopCartService extends BaseService<ShopCart> {

    @Override
    ShopCart saveAndModify(ShopCart shopCart);

    @Override
    boolean delete(Long aLong);

    Result deleteByMemberAndProductSpecification(Long uid,Long psid);

	public List<ShopCart> findByMidAndPsId(Long id, Long psId);

    Result deleteBatch(Member member,Long hid);
}
