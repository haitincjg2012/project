package com.ph.shopping.facade.mapper;

import cm.ph.shopping.facade.order.entity.PhMemberSubOrderOnlineProducts;
import cm.ph.shopping.facade.order.vo.PhMemberSubOrderOnlineProductVO;
import com.ph.shopping.common.core.base.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @项目：phshopping-facade-order
 * @描述：线上订单中的商品信息
 * @作者： 张霞
 * @创建时间： 11:55 2017/6/1
 * @Copyright @2017 by zhangxia
 */
@Repository
public interface IMemberSubOrderOnlineProductsMapper extends BaseMapper<PhMemberSubOrderOnlineProducts>{
    /**
     * @author: 张霞
     * @description：通过线上子订单id获取订单的商品信息
     * @createDate: 14:07 2017/6/1
     * @param subOrderId
     * @return:
     * @version: 2.1
     */
    List<PhMemberSubOrderOnlineProducts> getMemberSubOrderProductBySubOrderId(Long subOrderId);

}
