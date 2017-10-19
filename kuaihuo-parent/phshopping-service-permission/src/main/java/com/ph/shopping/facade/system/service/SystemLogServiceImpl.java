package com.ph.shopping.facade.system.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ph.shopping.common.core.constant.PageConstant;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.mapper.SystemLogMapper;
import com.ph.shopping.facade.system.dto.QuerySystemLogDTO;
import com.ph.shopping.facade.system.dto.SystemLogDTO;
import com.ph.shopping.facade.system.entity.SystemLog;
import com.ph.shopping.facade.system.exception.SystemBizException;
import com.ph.shopping.facade.system.exception.SystemEnum;
import com.ph.shopping.facade.system.vo.SystemLogVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;

/**
 * 系统日志实现类
 *
 * @author 郑朋
 * @create 2017/5/8
 * @update 李杰
 * @updateTime 2017/6/13
 **/
@Component
@Service(version = "1.0.0")
public class SystemLogServiceImpl implements ISystemLogService {

    private static final Logger logger = LoggerFactory.getLogger(SystemLogServiceImpl.class);

    @Autowired
    SystemLogMapper systemLogMapper;

	/**
	 * @author: 郑朋
	 * @description：新增系统日志
	 * @createDate: 10:10 2017/6/15
	 * @param systemLogDTO
	 * @return: com.ph.shopping.common.util.result.Result
	 * @version: 2.1
	 */
	@Transactional
	@Override
	public Result addSystemLog(SystemLogDTO systemLogDTO) {
		try {
			logger.info("新增系统日志入参，systemLogDTO={}", JSON.toJSONString(systemLogDTO));
			Result result = ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR);
			if(null != systemLogDTO){
				String errorStr = systemLogDTO.validateForm();
				if (StringUtils.isNotBlank(errorStr)) {
					result.setMessage(errorStr);
					return result;
				} else {
					SystemLog systemLog = new SystemLog();
					BeanUtils.copyProperties(systemLogDTO, systemLog);
					systemLog.setCreateTime(new Date());
					systemLogMapper.insert(systemLog);
					result = ResultUtil.getResult(SystemEnum.Code.SUCCESS);
				}
			}
			logger.info("新增系统日志返回值，result={}", JSON.toJSONString(result));
			return result;
		} catch (Exception e) {
			logger.error("新增系统日志异常，e={}", e);
			throw new SystemBizException(SystemEnum.Code.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * @author: 郑朋
	 * @description：分页查询系统记录日志
	 * @createDate: 10:09 2017/6/15
	 * @param pageBean
	 * @param querySystemLogDTO
	 * @return: com.ph.shopping.common.util.result.Result
	 * @version: 2.1
	 */
    @Override
    public Result getSystemLogListByPage(PageBean pageBean, QuerySystemLogDTO querySystemLogDTO) {
		if (pageBean != null){
			pageBean.setPageSize(pageBean.getPageSize() == 0 ? PageConstant.pageSize : pageBean.getPageSize());
			pageBean.setPageNum(pageBean.getPageNum() == 0 ? PageConstant.pageNum : pageBean.getPageNum());
		}
        try {
            logger.info("查询系统日志入参，pageBean={}, systemLogDTO={}", JSON.toJSONString(pageBean),
                    JSON.toJSONString(querySystemLogDTO));
			PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
            List<SystemLogVO> list =  systemLogMapper.selectSystemLogListByPage(querySystemLogDTO);
            PageInfo<SystemLogVO> pageInfo = new PageInfo<>(list);
            Result result = ResultUtil.getResult(SystemEnum.Code.SUCCESS,pageInfo.getList(),pageInfo.getTotal());
            logger.info("查询系统日志返回值，result={}", JSON.toJSONString(result));
            return result;
        } catch (Exception e) {
            logger.error("查询系统日志异常，e={}", e);
            throw new SystemBizException(SystemEnum.Code.INTERNAL_SERVER_ERROR);
        }
    }
}
