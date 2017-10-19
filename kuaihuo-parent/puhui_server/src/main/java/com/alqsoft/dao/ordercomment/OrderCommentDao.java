package com.alqsoft.dao.ordercomment;

import javax.persistence.LockModeType;

import org.alqframework.orm.hibernate.BaseDao;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alqsoft.entity.ordercomment.OrderComment;

public interface OrderCommentDao extends BaseDao<OrderComment>{
	
	/**
	 * 判断该会员是否已有过评价此订单商品数量
	 * @param orderno
	 * @param productid
	 * @param memberid
	 * @return
	 */
	@Query(value="SELECT COUNT(*) FROM alq_order_comment oc WHERE oc.`order_id`=:orderid  AND oc.`member_id`=:memberid",nativeQuery=true)
	public int getDirectOrderCommentCount(@Param("orderid")Long orderid,@Param("memberid")Long memberid);

	@Lock(value = LockModeType.PESSIMISTIC_WRITE)
	@Query("from OrderComment as oc where oc.id=:id")
	public OrderComment getRowLock(@Param("id")Long id);

	@Query(value="SELECT * FROM alq_order_comment WHERE order_id=:id AND ISNULL(parent_id)",nativeQuery=true)
	public OrderComment getCommentByOrderId(@Param("id") Long id);
}
