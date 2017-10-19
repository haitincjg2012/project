package com.ph.shopping.facade.mapper;

import cm.ph.shopping.facade.order.dto.PhOrderAddressDTO;
import cm.ph.shopping.facade.order.entity.PhOrderAddress;
import cm.ph.shopping.facade.order.vo.PhOrderAddressVO;
import com.ph.shopping.common.core.base.BaseMapper;

import java.util.List;

public interface OrderAddressMapper extends BaseMapper<PhOrderAddress> {

    List<PhOrderAddressVO> listOrderAddressDetail(PhOrderAddressDTO dto);
}