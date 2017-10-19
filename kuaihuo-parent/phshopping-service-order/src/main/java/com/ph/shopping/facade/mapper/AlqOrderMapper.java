package com.ph.shopping.facade.mapper;

import cm.ph.shopping.facade.order.dto.AddRestaurantOrSeatDTO;
import cm.ph.shopping.facade.order.dto.DishDTO;
import cm.ph.shopping.facade.order.entity.Dish;
import cm.ph.shopping.facade.order.vo.AlqOrderVO;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AlqOrderMapper {
    /**
     * 批发订单列表
     * @return
     */
	List<AlqOrderVO> getAlqList(AlqOrderVO alqOrderVO);

    /**
     * 批发代理订单
     * @param alqOrderVO
     * @return
     */
    List<AlqOrderVO> getAlqPfList(AlqOrderVO alqOrderVO,RowBounds rowBounds);

    int getAlqPfListCount(AlqOrderVO alqOrderVO);

    Long getOrderTotalPrice(String orderNo);
    /**
     * 批发订单详情
     */
    AlqOrderVO getAlqDetail(Long id);

    Map getLevel(Long id);
}
