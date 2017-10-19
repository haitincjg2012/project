package com.ph.shopping.facade.mapper;

import cm.ph.shopping.facade.order.dto.QueryMemberOrderOnlineDTO;
import cm.ph.shopping.facade.order.entity.PhMemberSubOrderOnline;
import cm.ph.shopping.facade.order.vo.PhMemberSubOrderOnlinePageVO;
import com.ph.shopping.common.core.base.BaseMapper;
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
public interface IMemberSubOrderOnlineMapper extends BaseMapper<PhMemberSubOrderOnline>{

    /**
     * @author: 张霞
     * @description：分页查询线上订单(子订单)列表
     * @createDate: 14:38 2017/6/1
     * @param queryMemberOrderOnlineDTO
     * @return:
     * @version: 2.1
     */
    List<PhMemberSubOrderOnlinePageVO> getMemberSubOrderOnlineListByPage(QueryMemberOrderOnlineDTO queryMemberOrderOnlineDTO);

}
