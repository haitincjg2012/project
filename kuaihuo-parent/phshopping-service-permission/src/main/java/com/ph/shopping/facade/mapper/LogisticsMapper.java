package com.ph.shopping.facade.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.system.entity.Logistics;


/**
 * 物流公司mapper
 *
 * @author 郑朋
 * @create 2017/5/8
 **/
@Repository
public interface LogisticsMapper extends BaseMapper<Logistics> {

    /**
     * @methodname selectLogisticsList 的描述：查询所有的物流公司
     * @param
     * @return java.util.List<com.ph.shopping.facade.system.entity.Logistics>
     * @author 郑朋
     * @create 2017/6/8
     */
    List<Logistics> selectLogisticsList();
    
    /**
     * 逻辑删除物流公司
    * @Title: deleteLogistics
    * @Description: TODO(这里用一句话描述这个方法的作用)
    * @author Mr.Dong
    * @date  2017年6月15日 上午10:15:28
    * @param id
    * @return
     */
    int deleteLogistics(@Param("id") Long id);
}
