package com.ph.shopping.facade.member.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.member.entity.Member;



/**
 * 
 * @ClassName: IShopCartService
 * @Description: 购物车操作接口
 * @author gaoge
 * @date 2017年05月16日
 */
public interface IShopCartService {
	Result getShopCartList(Map<String, Object> map);

    Result update(Long uid,Long cid,Long sum,Long orderId);

    Result delete(Long uid,Long cid,Long orderId);

    Result add(Long merchantid, Long spid, Long num, Long stid,String hopetime,Long type);


    Result batchUpdate(Long id, String cids, String nums);

    Result getShopCartCount(Long id);
    /*=====================================快火二期========================================*/

    /**
     * 加入购物车
     * @param merchantid
     * @param spid
     * @param num
     * @param stid
     * @return
     */
    Result addTwo(Long memberId, Long spid, Long num, Long stid,Long orderId);

}
