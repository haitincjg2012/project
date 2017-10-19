package com.ph.shopping.facade.system.service;

import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.system.dto.SystemParameterDTO;
import com.ph.shopping.facade.system.dto.UpdateSystemParameterDTO;

import java.util.List;

/**
 * 系统参数设置接口
 *
 * @author 郑朋
 * @create 2017/5/8
 **/
public interface ISystemParameterService {

    /**
     * @methodname updateSystemParameter 的描述：修改参数值
     * @param updateSystemParameterDTO
     * @return com.ph.shopping.common.util.result.Result
     * @author 郑朋
     * @create 2017/5/8
     */
    Result updateSystemParameter(UpdateSystemParameterDTO updateSystemParameterDTO);

    /**
     * @methodname getSystemParameterList 的描述：查询所有的系统参数
     * @param
     * @return com.ph.shopping.common.util.result.Result
     * @author 郑朋
     * @create 2017/5/8
     */
    Result getSystemParameterList(PageBean pageBean);

    /**
     * @methodname getSystemParameterBySelective 的描述：条件查询系统参数
     * @param systemParameterDTO
     * @return com.ph.shopping.common.util.result.Result
     * @author 郑朋
     * @create 2017/5/8
     */
    Result getSystemParameterBySelective(SystemParameterDTO systemParameterDTO);

    /**
     * @methodname getSystemParameterListByIds 的描述：通过id查询系统参数
     * @param ids
     * @return com.ph.shopping.common.util.result.Result
     * @author 郑朋
     * @create 2017/5/17
     */
    Result getSystemParameterListByIds(List<Long> ids);


}
