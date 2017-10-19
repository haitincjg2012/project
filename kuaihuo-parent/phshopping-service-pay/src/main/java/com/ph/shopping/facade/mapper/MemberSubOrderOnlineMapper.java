package com.ph.shopping.facade.mapper;

import cm.ph.shopping.facade.order.dto.QueryMemberOrderOnlineDTO;
import cm.ph.shopping.facade.order.entity.PhMemberSubOrderOnline;
import cm.ph.shopping.facade.order.vo.PhMemberSubOrderOnlinePageVO;
import com.ph.shopping.common.core.base.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @项目：phshopping-facade-order
 * @描述：线上订单（子订单）
 * @作者： 张霞
 * @创建时间： 10:38 2017/6/1
 * @Copyright @2017 by zhangxia
 */
@Repository
public interface MemberSubOrderOnlineMapper extends BaseMapper<PhMemberSubOrderOnline>{

    /**
     * @methodname getMemberOrderByOrderNo 的描述：根据会员订单号查询订单信息
     * @param orderNo
     * @return cm.ph.shopping.facade.order.entity.PhMemberSubOrderOnline
     * @author 郑朋
     * @create 2017/6/19
     */
    PhMemberSubOrderOnline getMemberOrderByOrderNo(@Param("orderNo") String orderNo);

}
