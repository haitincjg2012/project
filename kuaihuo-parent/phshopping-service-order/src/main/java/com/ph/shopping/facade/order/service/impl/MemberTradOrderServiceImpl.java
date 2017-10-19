package com.ph.shopping.facade.order.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.date.DoubleUtils;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.mapper.IMemberMainOrderOnlineMapper;
import com.ph.shopping.facade.mapper.MemberTradOrderMapper;

import cm.ph.shopping.facade.order.dto.PhMemberOrderUnlineDTO;
import cm.ph.shopping.facade.order.entity.PhMemberOrderUnline;
import cm.ph.shopping.facade.order.service.MemberTradOrderService;
/**
 * @date 2017-08-26 17：498
 * @author lzh
 * 会员交易列表实现层
 */
@Component
@Service(version="1.0.0")
public class MemberTradOrderServiceImpl implements MemberTradOrderService {
	 // 创建日志
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MemberTradOrderMapper memberTradOrderMapper;
	/**
	 * 查看交易订单列表
	 */
	@Override
	public Result getMemberTradOrder(PhMemberOrderUnlineDTO member,int page,int pageSize) {
			Result result =ResultUtil.getResult( RespCode.Code.SUCCESS ) ;
			if(page==0){
				page=1;
			}
			int index=(page-1)*pageSize;
			RowBounds rowBounds=new RowBounds(index,pageSize);
			List<PhMemberOrderUnlineDTO> memberTradOrder=memberTradOrderMapper.getMemberTradOrder(member,rowBounds);
			//判断是否为空
			if(memberTradOrder==null||memberTradOrder.isEmpty()){
				return ResultUtil.getResult( RespCode.Code.NO_ORDER);
			}
			//将type空值转换成数值
			for(int i=0;i<memberTradOrder.size();i++){
				//订单金额处理
				memberTradOrder.get(i).setOrderMoney(Double.parseDouble(DoubleUtils.formatRound(memberTradOrder.get(i).getOrderMoney()/10000.00, 2)));
				if(memberTradOrder.get(i).getPayType()!=null){
					//转换支付类型
					if(memberTradOrder.get(i).getPayType()==8){
						memberTradOrder.get(i).setPayTypeString("快捷支付");
					}else if(memberTradOrder.get(i).getPayType()==9){
						memberTradOrder.get(i).setPayTypeString("远程支付");
					}else if(memberTradOrder.get(i).getPayType()==0){
						memberTradOrder.get(i).setPayTypeString("现金支付");
					}
				}
			
				//
				if(memberTradOrder.get(i).getType()==null){
					memberTradOrder.get(i).setType(2);//未领取
				}
				//  0、天天返  1、刮刮乐
				else if(memberTradOrder.get(i).getType()!=0&&memberTradOrder.get(i).getType()!=1){
					memberTradOrder.get(i).setType(2);//未领取
				}
			}
			
				result=ResultUtil.getResult(RespCode.Code.SUCCESS, memberTradOrder);
		
		return result;
	}

}
