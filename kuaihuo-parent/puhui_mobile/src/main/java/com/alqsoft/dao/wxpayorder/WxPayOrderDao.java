package com.alqsoft.dao.wxpayorder;

import java.util.List;
import java.util.Map;

import org.alqframework.orm.mybatis.MyBatisRepository;
import org.apache.ibatis.annotations.Param;

import com.alqsoft.entity.wxpayorder.WxPayOrder;

@MyBatisRepository
public interface WxPayOrderDao {

	public List<WxPayOrder> findWxPayOrderByWxOrderNum(Map<String, Object> params);

	public List<WxPayOrder> getPayOrderByOrderId(Long id);

	public List<WxPayOrder> findWxPayOrderByWxOrderNumAndStatus(@Param("wxOrderNum")String wxOrderNum);

	public List<WxPayOrder> getPayBackOrderByOrderId(Long id);

}
