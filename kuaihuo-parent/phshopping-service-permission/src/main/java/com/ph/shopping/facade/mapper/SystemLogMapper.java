package com.ph.shopping.facade.mapper;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.system.dto.QuerySystemLogDTO;
import com.ph.shopping.facade.system.entity.SystemLog;
import com.ph.shopping.facade.system.vo.SystemLogVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 系统日志mapper
 *
 * @author 郑朋
 * @create 2017/5/8
 **/
@Repository
public interface SystemLogMapper extends BaseMapper<SystemLog> {

    /**
     * @methodname selectSystemLogListByPage 的描述：分页查询系统记录日志
     * @param querySystemLogDTO
     * @return java.util.List<com.ph.shopping.facade.system.vo.SystemLogVO>
     * @author 郑朋
     * @create 2017/5/8
     */
    List<SystemLogVO> selectSystemLogListByPage(QuerySystemLogDTO querySystemLogDTO);
}
