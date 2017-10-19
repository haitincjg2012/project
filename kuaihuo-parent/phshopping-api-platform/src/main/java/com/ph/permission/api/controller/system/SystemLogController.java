package com.ph.permission.api.controller.system;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.base.BaseController;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.system.dto.QuerySystemLogDTO;
import com.ph.shopping.facade.system.service.ISystemLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @项目：phshopping-facade-order
 * @描述：系统日志控制层
 * @作者： 张霞
 * @创建时间： 15:52 2017/6/14
 * @Copyright @2017 by zhangxia
 */
@Controller
@RequestMapping(value = "web/systemLog")
public class SystemLogController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(SystemLogController.class);
    @Reference(version="1.0.0")
    ISystemLogService systemLogService;

    /**
     * @author: 张霞
     * @description：跳转到系统日志列表页面
     * @createDate: 10:05 2017/6/14
     * @param
     * @return: java.lang.String
     * @version: 2.1
     */
    @RequestMapping(value = "toListPage",method = {RequestMethod.GET})
    public String toListPage(){
        return "permission/system/systemLogList";
    }

    /**
     * @author: 张霞
     * @description：获取系统日志列表数据
     * @createDate: 10:05 2017/6/14
     * @param pageBean
     * @param querySystemLogDTO
     * @return: com.ph.shopping.common.util.result.Result
     * @version: 2.1
     */
    @RequestMapping(value = "list",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Result getSystemLogList(PageBean pageBean, QuerySystemLogDTO querySystemLogDTO){

        Result result;
        try {
            if (!(StringUtils.isEmpty( querySystemLogDTO.getEndTime() )&& StringUtils.isEmpty( querySystemLogDTO.getStarTime() ))){
                if (querySystemLogDTO.getStarTime().equals( querySystemLogDTO.getEndTime())){
                    querySystemLogDTO.setStarTime(querySystemLogDTO.getStarTime().toString()+" 00:00:00");
                    querySystemLogDTO.setEndTime(  querySystemLogDTO.getEndTime().toString()+" 23:59:59"  );
                }
            }
            result = systemLogService.getSystemLogListByPage( pageBean,querySystemLogDTO );
        }catch (Exception e){
            result = ResultUtil.getResult( RespCode.Code.FAIL );
        }
        return result;
    }
}
