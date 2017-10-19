package com.alqsoft.dao.wxpayorder;

import java.util.Collection;
import java.util.List;

import javax.persistence.LockModeType;

import org.alqframework.orm.hibernate.BaseDao;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alqsoft.entity.wxpayorder.WxPayOrder;

public interface WxPayOrderDao extends BaseDao<WxPayOrder> {

	@Query("FROM WxPayOrder AS wx WHERE wx.wxOrderNum=:wxOrderNum")
	List<WxPayOrder> getListByWxOrderNum(@Param("wxOrderNum") String wxOrderNum);

	@Modifying
	@Query("UPDATE WxPayOrder SET wxSerialNumber=:wxSerialNumber, description=:description, status=1 WHERE id IN (:ids)")
	void updateWxPayOrderTypeByS(@Param("wxSerialNumber") String wxSerialNumber, @Param("description") String description, @Param("ids") Collection<Long> ids);

	@Modifying
	@Query("UPDATE WxPayOrder SET wxSerialNumber=:wxSerialNumber, description=:description, status=2 WHERE id IN (:ids)")
	void updateWxPayOrderTypeByF(@Param("wxSerialNumber") String wxSerialNumber, @Param("description") String description, @Param("ids") Collection<Long> ids);

	@Query("FROM WxPayOrder AS wx WHERE wx.orderInfo.id=:id AND wx.type IN(0,1,2) AND wx.status=1")
	List<WxPayOrder> findRecordByOrderId(@Param("id") Long id);

	@Query("FROM WxPayOrder AS wx WHERE wx.orderNum=:orderNo AND wx.type IN(0,1,2) AND wx.status=1")
	List<WxPayOrder> findRecordByOrderNo(@Param("orderNo") String orderNo);

	@Query("FROM WxPayOrder AS wx WHERE wx.orderInfo.id=:id AND wx.type=:type AND wx.status=2")
	List<WxPayOrder> findRecordByOrderIdAndStatus(@Param("id") Long id, @Param("type") Integer type);

	@Query("FROM WxPayOrder AS wx WHERE wx.orderInfo.id=:id AND wx.type IN(3,4,5) AND wx.status=1")
	List<WxPayOrder> findRecordByOrderIdAndStatusSuc(@Param("id") Long id);

	@Query("FROM WxPayOrder AS wx WHERE wx.orderInfo.id=:id AND wx.type IN(3,4,5) AND wx.status=0")
	List<WxPayOrder> getListByOrderIdAndStatus(@Param("id") Long id);
	@Query(value="SELECT SUM(money) FROM alq_wx_pay_order AS wx WHERE wx.wx_serial_number=:wxSerialNumber and wx.status=1",nativeQuery=true)
	Long getSumPriceBySerialNum(@Param("wxSerialNumber") String wxSerialNumber);

	@Query("FROM WxPayOrder AS w WHERE w.wxOrderNum=:wxOrderNum AND w.status=0 AND type IN(0,1,2)")
	List<WxPayOrder> findWxPayOrderByWxOrderNumAndStatus(@Param("wxOrderNum") String wxOrderNum);

	@Query("FROM WxPayOrder AS w WHERE w.wxOrderNum=:wxOrderNum AND w.status=0 AND type IN(3,4,5)")
	WxPayOrder findRecordByWxOrderNumAndStatus(@Param("wxOrderNum") String wxOrderNum);
	
	
	@Query("FROM WxPayOrder AS wx WHERE wx.orderNum=:orderNo AND wx.type IN(1,2) AND wx.status=1")
	List<WxPayOrder> checkOrderPay(@Param("orderNo") String orderNo);
	@Query("FROM WxPayOrder AS wx WHERE wx.orderNum=:orderNo AND wx.type=:type AND wx.status=1")
	List<WxPayOrder> checkOrderPayBySub(@Param("orderNo") String orderNo, @Param("type") Integer type);

	List<WxPayOrder> getAllByWxOrderNum(String orderNo);


}
