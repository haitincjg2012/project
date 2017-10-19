package com.ph.shopping.facade.pay.service.impl.order;

import cm.ph.shopping.facade.order.constant.OrderOnlineRoleTypeEnum;
import cm.ph.shopping.facade.order.constant.OrderOnlineStatusEnum;
import cm.ph.shopping.facade.order.constant.OrderOnlineUpdateContentEnum;
import cm.ph.shopping.facade.order.dto.UpdateOnlineOrderStatusDTO;
import cm.ph.shopping.facade.order.entity.PhMemberSubOrderOnline;
import cm.ph.shopping.facade.order.entity.PhMemberSubOrderOnlineRecord;
import com.ph.shopping.facade.mapper.IMemberSubOrderOnlineRecordMapper;
import com.ph.shopping.facade.mapper.MemberSubOrderOnlineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 线上积分service
 *
 * @author 郑朋
 * @create 2017/6/19
 **/
@Service
public class OnlineOrderService {

    @Autowired
    MemberSubOrderOnlineMapper memberSubOrderOnlineMapper;
    @Autowired
    IMemberSubOrderOnlineRecordMapper iMemberSubOrderOnlineRecordMapper;

    /**
     * @methodname updateOrder 的描述：修改线上订单状态（支付成功）
     * @param orderId
     * @param updaterId
     * @param payType
     * @return int
     * @author 郑朋
     * @create 2017/6/19
     */
    public int updateOrder(Long orderId, Long updaterId, Byte payType) {
        Date date = new Date();
        PhMemberSubOrderOnline memberSubOrderOnline = new PhMemberSubOrderOnline();
        memberSubOrderOnline.setId(orderId);
        memberSubOrderOnline.setPayTime(date);
        memberSubOrderOnline.setStatus(OrderOnlineStatusEnum.STATUS_TODO_SEND.getStatus());
        memberSubOrderOnline.setUpdateTime(date);
        memberSubOrderOnline.setUpdaterId(updaterId);
        memberSubOrderOnline.setUpdaterRoleType(OrderOnlineRoleTypeEnum.ROLE_TYPE_MEMBER.getRoleType());
        memberSubOrderOnline.setPayType(payType);
        //添加订单变更记录
        UpdateOnlineOrderStatusDTO updateOnlineOrderStatusDTO = new UpdateOnlineOrderStatusDTO();
        updateOnlineOrderStatusDTO.setRoleType( (byte)0 );//角色
        updateOnlineOrderStatusDTO.setCurrentOrderStatus( (byte)1 );//当前状态
        updateOnlineOrderStatusDTO.setUpdateContentType( OrderOnlineUpdateContentEnum.ONLINE_UPDATE_STATUS.getType() );//更新订单的具体内容的类型
        updateOnlineOrderStatusDTO.setOrderId( orderId );//订单号
        updateOnlineOrderStatusDTO.setUpdaterId( updaterId );//创建人

        PhMemberSubOrderOnlineRecord phMemberSubOrderOnlineRecord = new PhMemberSubOrderOnlineRecord();
        phMemberSubOrderOnlineRecord.setStatusBefore( String.valueOf( 0 ) );
        addMemberSubOrderOnlineRecord(updateOnlineOrderStatusDTO,phMemberSubOrderOnlineRecord);
        return memberSubOrderOnlineMapper.updateByPrimaryKeySelective(memberSubOrderOnline);
    }


    /**
     * @methodname updateOrderT503 的描述：修改线上订单状态 （处理T503的情况）
     * @param orderId
     * @param updaterId
     * @return int
     * @author 郑朋
     * @create 2017/6/19
     */
    public int updateOrderT503(Long orderId, Long updaterId) {
        Date date = new Date();
        PhMemberSubOrderOnline memberSubOrderOnline = new PhMemberSubOrderOnline();
        memberSubOrderOnline.setId(orderId);
        memberSubOrderOnline.setStatus(OrderOnlineStatusEnum.STATUS_CANCEL_ORDER.getStatus());
        memberSubOrderOnline.setCancelTime(new Date());
        memberSubOrderOnline.setUpdateTime(date);
        memberSubOrderOnline.setUpdaterId(updaterId);
        memberSubOrderOnline.setUpdaterRoleType(OrderOnlineRoleTypeEnum.ROLE_TYPE_BANK.getRoleType());
        return memberSubOrderOnlineMapper.updateByPrimaryKeySelective(memberSubOrderOnline);
    }
    /**
     * @author: 张霞
     * @description：添加更新订单状态记录
     * @createDate: 17:40 2017/6/1
     * @param updateOnlineOrderStatusDTO
     * @return: int
     * @version: 2.1
     */
    private int addMemberSubOrderOnlineRecord(UpdateOnlineOrderStatusDTO updateOnlineOrderStatusDTO,PhMemberSubOrderOnlineRecord phMemberSubOrderOnlineRecord){
        phMemberSubOrderOnlineRecord.setCreaterId( updateOnlineOrderStatusDTO.getUpdaterId() );
        phMemberSubOrderOnlineRecord.setCreaterRoleType( updateOnlineOrderStatusDTO.getRoleType() );
        phMemberSubOrderOnlineRecord.setCreateTime( new Date(  ) );
        phMemberSubOrderOnlineRecord.setStatusAfter(Byte.toString( updateOnlineOrderStatusDTO.getCurrentOrderStatus() ));
        phMemberSubOrderOnlineRecord.setSubOrderId( updateOnlineOrderStatusDTO.getOrderId() );
        return iMemberSubOrderOnlineRecordMapper.insertSelective( phMemberSubOrderOnlineRecord );//插入订单更新记录
    }

}
