package com.ph.shopping.facade.order.service.impl;

import cm.ph.shopping.facade.order.constant.OrderAddressIsDefaultEnum;
import cm.ph.shopping.facade.order.constant.OrderAddressStatusEnum;
import cm.ph.shopping.facade.order.dto.PhOrderAddressDTO;
import cm.ph.shopping.facade.order.entity.PhOrderAddress;
import cm.ph.shopping.facade.order.service.IOrderAddressService;
import cm.ph.shopping.facade.order.vo.PhOrderAddressVO;
import com.alibaba.dubbo.config.annotation.Service;
import com.ph.shopping.common.util.bean.VoDtoEntityUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.mapper.OrderAddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Component
@Service(version = "1.0.0")
public class OrderAddressServiceImpl implements IOrderAddressService {

    @Autowired
    private OrderAddressMapper orderAddressMapper;

    @Transactional
    @Override
    public Result addOrderAddress(PhOrderAddressDTO dto) {
        Result result = new Result(false);
        String validateMessage = dto.validateForm();
        if (validateMessage == null) {
            if (dto.getCreaterId() == null) {
                result.setMessage("创建人不能为空");
            } else {
                PhOrderAddress add = VoDtoEntityUtil.convert(dto, PhOrderAddress.class);
                add.basic(dto.getCreaterId());
                add.setIsDelete(OrderAddressStatusEnum.NORMAL.getCode());
                PhOrderAddress phOrderAddress = new PhOrderAddress();
                phOrderAddress.setMemberId(dto.getMemberId());
                phOrderAddress.setIsDelete(OrderAddressStatusEnum.NORMAL.getCode());
                phOrderAddress.setIsDefault(OrderAddressIsDefaultEnum.DEFAULT.getCode());
                PhOrderAddress defaultAddress = this.orderAddressMapper.selectOne(phOrderAddress);
                add.setIsDefault(defaultAddress == null ? OrderAddressIsDefaultEnum.DEFAULT.getCode() : OrderAddressIsDefaultEnum.NOT_DEFAULT.getCode());
                this.orderAddressMapper.insertUseGeneratedKeys(add);
                result.setMessage("会员地址保存成功");
                result.setSuccess(true);
            }
        } else {
            result.setMessage(validateMessage);
        }
        return result;
    }

    @Override
    public Result updateOrderAddress(PhOrderAddressDTO dto) {
        Result result = new Result(false);
        if (dto.getUpdaterId() == null) {
            result.setMessage("修改人不能为空");
        } else {
            if (dto.getId() == null) {
                result.setMessage("修改id不能为空");
            } else {
                PhOrderAddress old = this.orderAddressMapper.selectByPrimaryKey(dto.getId());
                if (old == null) {
                    result.setMessage("不存在的会员地址id");
                } else {
                    PhOrderAddress update = VoDtoEntityUtil.convert(dto, PhOrderAddress.class);
                    VoDtoEntityUtil.copyPropertiesNotNull(update, old);
                    update.basic(dto.getUpdaterId());
                    this.orderAddressMapper.updateByPrimaryKey(old);
                    result.setMessage("会员地址修改成功");
                    result.setSuccess(true);
                }
            }
        }
        return result;
    }

    @Override
    public Result listOrderAddressByMemberId(Long memberId) {
        Result result = new Result(false);
        if (memberId == null) {
            result.setMessage("会员id不能为空");
        } else {
            PhOrderAddressDTO phOrderAddress = new PhOrderAddressDTO();
            phOrderAddress.setMemberId(memberId);
            List<PhOrderAddressVO> addressList = this.orderAddressMapper.listOrderAddressDetail(phOrderAddress);

            //如果townId为空则详情不显示TownName
            for(PhOrderAddressVO address :addressList){
                if(address.getTownId()==null){
                    address.setTownName(null);
                }
            }

            addressList.sort(Comparator.comparingInt(PhOrderAddressVO::getIsDefault));
            result.setData(addressList);
            result.setCount(addressList.size());
            result.setMessage(CollectionUtils.isEmpty(addressList) ? "暂无数据" : "查询会员收货地址列表成功");
            result.setSuccess(!CollectionUtils.isEmpty(addressList));
        }
        return result;
    }

    @Override
    public Result getAddressDetailById(Long addressId) {
        Result result = new Result(false);
        if (addressId == null) {
            result.setMessage("地址不能为空");
        } else {
            PhOrderAddressDTO phOrderAddressDTO = new PhOrderAddressDTO();
            phOrderAddressDTO.setId(addressId);
            phOrderAddressDTO.setIsDelete(OrderAddressStatusEnum.NORMAL.getCode());
            PhOrderAddressVO phOrderAddressVO = this.orderAddressMapper.listOrderAddressDetail(phOrderAddressDTO).get(0);
            result.setData(phOrderAddressVO);
            result.setMessage("查询会员收货地址详情成功");
            result.setSuccess(true);
        }
        return result;
    }

    @Transactional
    @Override
    public Result setDefaultAddressByMemberId(Long addressId, Long memberId) {
        Result result = new Result(false);
        if (addressId == null) {
            result.setMessage("地址不能为空");
        } else {
            if (memberId == null) {
                result.setMessage("会员id不能为空");
            } else {
                PhOrderAddress phOrderAddress = new PhOrderAddress();
                phOrderAddress.setMemberId(memberId);
                phOrderAddress.setIsDelete(OrderAddressStatusEnum.NORMAL.getCode());
                phOrderAddress.setIsDefault(OrderAddressIsDefaultEnum.DEFAULT.getCode());
                PhOrderAddress defaultOldAddress = this.orderAddressMapper.selectOne(phOrderAddress);
                //修改之前的默认地址
                if (defaultOldAddress != null) {
                    defaultOldAddress.setIsDefault(OrderAddressIsDefaultEnum.NOT_DEFAULT.getCode());
                    this.orderAddressMapper.updateByPrimaryKey(defaultOldAddress);
                }
                PhOrderAddress update = orderAddressMapper.selectByPrimaryKey(addressId);
                update.setIsDefault(OrderAddressIsDefaultEnum.DEFAULT.getCode());
                update.setUpdateTime(new Date());
                this.orderAddressMapper.updateByPrimaryKey(update);
                result.setMessage("会员设置默认地址成功");
                result.setSuccess(true);

            }
        }
        return result;
    }
}
