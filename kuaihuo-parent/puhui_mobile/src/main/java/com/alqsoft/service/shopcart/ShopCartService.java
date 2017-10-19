package com.alqsoft.service.shopcart;

import com.alqsoft.entity.member.Member;
import org.alqframework.result.Result;

import java.util.Map;

/**
 * @author Xuejizheng
 * @date 2017-02-28 14:57
 * @see
 * @since 1.8
 */
public interface ShopCartService {

    Result getShopCartList(Map<String, Object> map);

    Result update(Long uid,Long cid,Long sum);

    Result delete(Long uid,Long cid);

    Result add(Member member, Long spid, Long num, Long stid);


    Result batchUpdate(Long id, String cids, String nums);

    Result getShopCartCount(Long id);

    Result batchAdd(Member member, String spid, String num, String stid);

    Result getHunterShopCartList(Map<String, Object> map);

    Result batchDelete(Member member,Long hid);
}
