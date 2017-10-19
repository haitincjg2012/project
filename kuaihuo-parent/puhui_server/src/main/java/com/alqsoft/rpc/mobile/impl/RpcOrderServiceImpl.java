package com.alqsoft.rpc.mobile.impl;

import com.alqsoft.dto.OrderDTO;
import com.alqsoft.entity.member.Member;
import com.alqsoft.entity.order.Order;
import com.alqsoft.rpc.mobile.RpcOrderService;
import com.alqsoft.service.order.OrderService;
import com.alqsoft.vo.OrderListVO;
import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/** 订单管理
 * @author Xuejizheng
 * @date 2017-03-03 17:51
 */
@Service
@Transactional
public class RpcOrderServiceImpl implements RpcOrderService {

    @Autowired
    private OrderService orderService;

    @Override
    public Order saveAndModify(Order order) {
        return orderService.saveAndModify(order);
    }

    @Override
    public boolean delete(Long aLong) {
        return orderService.delete(aLong);
    }

    @Override
    public Order get(Long aLong) {
        return orderService.get(aLong);
    }

	@Override
	public Result findOrderBySubscriptionToBack(Order order) {
		return this.orderService.findOrderBySubscriptionToBack(order);
	}

	@Override
	public Result findOrderBySubscriptionToPayMoney(Order order) {
		return this.orderService.findOrderBySubscriptionToPayMoney(order);
	}

	@Override
	public Result toCreateOrder(Member m, OrderListVO list, Long aId) {
		return this.orderService.toCreateOrder(m, list, aId);
	}

	@Override
	@Transactional
	public Result memberToPayBack(Order order) {
		return this.orderService.memberToPayBack(order);
	}

    @Override
    public Result confirmReceive(Map<String, Object> map) {
        return orderService.confirmReceive(map);
    }

    @Override
    public Result confirmSend(Map<String, Object> map) {
        return orderService.confirmSend(map);
    }

    @Override
    public Result setRefundMsg(Map<String, Object> map) {
        return orderService.setRefundMsg(map);
    }
    /**
	 * 用户输入尾款价格
	 */
	@Override
	public Result addNegotiatePriceForOrder(Order order, String ids) {
		
		return orderService.addNegotiatePriceForOrder(order,ids);
	}
	/**
	 * 商家对尾款价格进行操作
	 */
	@Override
	public Result chooseNegotiatePriceForOrder(Order order, String type) {
		
		return orderService.chooseNegotiatePriceForOrder(order,type);
	}

	@Override
	public Result createOrder(Member member, List<OrderDTO> orderDTOS) {
		return orderService.createOrder(member,orderDTOS);
	}

	@Override
	public Result updateOrder(String time,String orderNo, Member member) {
		return orderService.updateOrder(time,orderNo,member);
	}

	@Override
	public Result deleteBySubOrderNo(Long id, Member member) {
		return orderService.deleteBySubOrderNo(id,member);
	}

	@Override
	public Result updateOrderNum(Long id, Long num, Member member) {
		return orderService.updateOrderNum(id,num,member);
	}

	@Override
	public Result recurOrder(String type,List<OrderDTO> orderDTO, Member member) {
		return orderService.recurOrder(type,orderDTO,member);
	}
	@Override
	public Result cancelOrderByOrderNo(String orderNo) {
		return orderService.cancelOrderByOrderNo(orderNo);
	}

	/**
	 * 供应商接单
	 * @param orderNo
	 * @return
	 */
	@Override
	public Integer agreeOrderBySupplyer(String orderNo) {
		return orderService.agreeOrderBySupplyer(orderNo);
	}

}
