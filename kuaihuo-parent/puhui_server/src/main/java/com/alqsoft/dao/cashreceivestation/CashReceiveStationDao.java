package com.alqsoft.dao.cashreceivestation;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.LockModeType;

import org.alqframework.orm.hibernate.BaseDao;
import org.alqframework.orm.mybatis.MyBatisRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alqsoft.entity.cashreceivestation.CashReceiveStation;


public interface CashReceiveStationDao extends BaseDao<CashReceiveStation>{

	/***
	 * 根据当前日期查询批发商今日总提现金额 网银在线 京东代付专用
	 * @param supplierid
	 * @return
	 */
	@Query("SELECT SUM(c.money) FROM CashReceiveStation AS c WHERE c.cssType=1 AND c.cssId=:hunterid AND (c.status=1 OR c.status=2) AND c.createdTime >:createdTime AND c.money>0")
	public Long getTodayCashMoneyByDate(@Param("hunterid")Integer hunterid,@Param("createdTime")Date createdTime);

	/**
	 * 根据订单号获取记录ID
	 */
	@Query("select c.id from CashReceiveStation as c where c.merSeqId=:id")
	public Long getIDByMerSeqId(@Param("id")String merSeqId);
	
	/**
	 * 提现加锁
	 */
	@Lock(value = LockModeType.PESSIMISTIC_WRITE)
	@Query("from CashReceiveStation as o where o.id=:id")
	public CashReceiveStation getRowLock(@Param("id")Long id);
	
	/***
	 * 根据当前日期查询行业协会今日总提现金额 网银在线 京东代付专用
	 * @param supplierid
	 * @return
	 */
	@Query("SELECT SUM(c.money) FROM CashReceiveStation AS c WHERE c.cssType=2 AND c.cssId=:industryAssociationId AND (c.status=1 OR c.status=2)  AND c.createdTime >:createdTime AND c.money>0")
	public Long getTodayCashMoneyByDateIndustryAssociation(@Param("industryAssociationId")Integer industryAssociationId,@Param("createdTime")Date createdTime);

}
