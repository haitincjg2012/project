package com.ph.shopping.facade.mapper;

import cm.ph.shopping.facade.order.entity.PurchaseSubOrder;
import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.pay.entity.PurchaseChargeRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 进货订单银行流水mapper
 *
 * @author 郑朋
 * @create 2017/5/15
 **/
@Repository
public interface PurchaseChargeRecordMapper extends BaseMapper<PurchaseChargeRecord>{
}
