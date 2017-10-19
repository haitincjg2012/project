package com.ph.shopping.facade.mapper;

import cm.ph.shopping.facade.order.dto.QueryMemberSubOrderRefundDTO;
import cm.ph.shopping.facade.order.entity.PhMemberSubOrderRefund;
import cm.ph.shopping.facade.order.vo.PhMemberSubOrderRefundPageVO;
import com.ph.shopping.common.core.base.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @项目：phshopping-facade-order
 * @描述：线上订单申请退款
 * @作者： 张霞
 * @创建时间： 10:20 2017/6/5
 * @Copyright @2017 by zhangxia
 */
@Repository
public interface ImemberSubOrderRefundMapper extends BaseMapper<PhMemberSubOrderRefund> {
    List<PhMemberSubOrderRefundPageVO> getMemberSubOrderOnlineRrfundListByPage(QueryMemberSubOrderRefundDTO queryMemberSubOrderRefundDTO);
}
