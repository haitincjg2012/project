package com.ph.shopping.facade.mapper;

import cm.ph.shopping.facade.order.entity.SubOrderMdyHis;
import com.ph.shopping.common.core.base.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 子订单变更历史mapper
 *
 * @author 郑朋
 * @create 2017/5/16
 **/
@Repository
public interface SubOrderMdyHisMapper extends BaseMapper<SubOrderMdyHis> {

    /**
     * @methodname insertList 的描述：批量新增订单修改历史
     * @param list
     * @return int
     * @author 郑朋
     * @create 2017/5/19
     */
    int insertList(@Param("list") List<SubOrderMdyHis> list);
}
