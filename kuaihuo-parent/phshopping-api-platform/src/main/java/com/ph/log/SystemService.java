package com.ph.log;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.permission.vo.SessionUserVO;
import com.ph.shopping.facade.system.dto.SystemLogDTO;
import com.ph.shopping.facade.system.service.ISystemLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 系统日志记录
 *
 * @author 郑朋
 * @create 2017/6/27
 **/
@Service
public class SystemService {

    private static final Logger logger = LoggerFactory.getLogger(SystemService.class);

    @Reference(version = "1.0.0",retries = 0,timeout = 30000)
    ISystemLogService systemLogService;


    /**
     * 统一记录日志
     * @param userBean
     * @param enumType
     * @param content
     * @return
     */
    public Result addSysLog(SessionUserVO userBean, byte enumType, String content){
        SystemLogDTO systemLogDTO = new SystemLogDTO();
        systemLogDTO.setCreaterId(userBean.getId());
        systemLogDTO.setOperateAccount(userBean.getTelphone());
        systemLogDTO.setCreaterName(userBean.getUserName());
        systemLogDTO.setOperateType(enumType);
        systemLogDTO.setOperateContent(content);
        logger.info("新增系统日志接口调用入参，systemLogDTO={}", JSON.toJSONString(systemLogDTO));
        Result result = systemLogService.addSystemLog(systemLogDTO);
        logger.info("新增系统日志接口调用返回值，result={}", JSON.toJSONString(result));
        return result;
    }

}
