package com.ph.shopping.facade.system.service;

import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.system.dto.QuerySystemLogDTO;
import com.ph.shopping.facade.system.dto.SystemLogDTO;

/**
 * 系统日志接口
 *
 * @author 郑朋
 * @create 2017/5/8
 **/
public interface ISystemLogService {

    /**
     * @methodname addSystemLog 的描述：新增系统日志
     * @param systemLogDTO
     * @return com.ph.shopping.common.util.result.Result
     * @author 郑朋
     * @create 2017/5/8
     */
    Result addSystemLog(SystemLogDTO systemLogDTO);

    /**
     * @methodname getSystemLogListByPage 的描述：分页查询系统记录日志
     * @param pageBean
     * @param querySystemLogDTO
     * @return com.ph.shopping.common.util.result.Result
     * @author 郑朋
     * @create 2017/5/8
     */
    Result getSystemLogListByPage(PageBean pageBean, QuerySystemLogDTO querySystemLogDTO);

}
