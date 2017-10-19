package com.alqsoft.dao.order;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.LockModeType;

import org.alqframework.orm.hibernate.BaseDao;
import org.alqframework.result.Result;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alqsoft.entity.member.Member;
import com.alqsoft.entity.order.Order;

public interface OrderDao extends BaseDao<Order>{
	
	/**
	 * 查询会员是否已经在该批发商有存在的完成订单，用于app首页直接给批发商评价
	 * @param hunterid
	 * @param memberid
	 * @return
	 */
	@Query(value="SELECT COUNT(*) FROM alq_order o WHERE o.hunter_id=:hunterid AND o.member_id=:memberid AND o.status>3",nativeQuery=true)
	int getMemberHaveOrderForHunterCommentCount(@Param("hunterid") Long hunterid, @Param("memberid") Long memberid);
	
	
	@Lock(value = LockModeType.PESSIMISTIC_WRITE)
	@Query("from Order as o where o.id=:id")
	Order getRowLock(@Param("id") Long id);

	@Query("FROM Order AS o WHERE o.orderNo IN(:orderIds)")
	List<Order> getOrderByOrderNums(@Param("orderIds") Collection<String> orderIds);

	//设置已收货
	@Modifying
	@Query("UPDATE Order SET status=7, payTime=:date WHERE id IN(:payAll)")
	void updateOrderTypeByPayAll(@Param("payAll") Collection<Long> payAll, @Param("date") Date date);
	
	@Modifying
	@Query("UPDATE Order SET status=1, subscriptionTime=:date WHERE id IN(:payPart)")
	void updateOrderTypeByPayPart(@Param("payPart") Collection<Long> payPart, @Param("date") Date date);

	@Query("FROM Order AS o WHERE o.member.id=:id AND (o.status=4 OR o.status=7) ORDER BY o.createdTime")
	List<Order> getOrderByMId(@Param("id") Long id);

	@Query(value = " update alq_order set status = 7 , receive_time = now()  where status =3 and" +
			" (UNIX_TIMESTAMP(NOW()) - UNIX_TIMESTAMP(send_time))/(60*60*24)>15 ",nativeQuery = true)
	@Modifying
    void confirmReceive();

	@Query("FROM Order AS o WHERE o.status IN(4,7) AND o.fenRunStatus!=1")
	List<Order> getOrderByStatus();

	@Query(value="SELECT * FROM alq_order AS o WHERE o.`status` IN(4,7) AND o.fen_run_status=1 AND IFNULL(o.puhui_run_status,0)<5", nativeQuery=true)
	List<Order> getOrderByPuHuiFenRun();

	@Query("FROM Member AS m WHERE m.hunter.id=:id")
	Member getMemberByHunterId(@Param("id") Long id);

	@Query(value = "select * from alq_order where status =3 and" +
			" (UNIX_TIMESTAMP(NOW()) - UNIX_TIMESTAMP(send_time))/(60*60*24)>15 ",nativeQuery = true)
	List<Order> getWaitReceiveOrderList();

	@Modifying
	@Query("UPDATE Order SET negotiatePriceStatus=1, negotiatePrice=:negotiatePrice WHERE orderNo=:orderNo")
	int addNegotiatePriceForOrder(@Param("negotiatePrice") Long negotiatePrice, @Param("orderNo") String orderNo);

	@Modifying
	@Query("UPDATE Order SET negotiatePriceStatus=3 WHERE orderNo=:orderNo")
	int agreeNegotiatePriceForOrder(@Param("orderNo") String orderNo);

	@Modifying
	@Query("UPDATE Order SET negotiatePriceStatus=2 WHERE orderNo=:orderNo  AND status=3")
	int disagreeNegotiatePriceForOrder(@Param("orderNo") String orderNo);

	@Modifying
	@Query("UPDATE Order SET status=3, sendTime=:date WHERE status=2 and orderNo=:orderNo AND hunter.id=:hunterId")
	int updateStatusForOrder(@Param("orderNo") String orderNo, @Param("hunterId") Long hunterId, @Param("date") Date date);

	@Query(value="SELECT SUM(o.product_sale_price) FROM alq_order o WHERE o.order_no=:orderNo",nativeQuery=true)
	Long getProductSalePrice(@Param("orderNo") String orderNo);
	
	@Query(value="SELECT o.id FROM Order o WHERE orderNo=:orderNo AND o.status=3")
	List<Long> getOrderIdsByOrderNo(@Param("orderNo") String orderNo);
	
	@Query("FROM Order AS o WHERE o.id IN(:orderIds) AND o.status=3")
	List<Order> getOrderByOrderNum(@Param("orderIds") Collection<Long> orderIds);

	Order getOrderByOrderSubNoAndMember(String subOrderNo,Member member);

	List<Order> getOrdersByOrderNoAndMember(String orderNo,Member member);

	int deleteByOrderNoAndMember(String orderNo,Member member);

	@Query("FROM Order AS o WHERE o.orderNo=:orderNo")
	List<Order> getOrderListByOrderNum(@Param("orderNo") String orderNo);
}
