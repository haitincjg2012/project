package com.ph.shopping.facade.mapper;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.system.entity.SystemParameter;
import com.ph.shopping.facade.system.vo.SystemParameterVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 系统参数设置mapper
 *
 * @author 郑朋
 * @create 2017/5/8
 **/
@Repository
public interface SystemParameterMapper extends BaseMapper<SystemParameter> {

    /**
     * @methodname selectSystemParameterList 的描述：查询所有的参数配置
     * @param
     * @return java.util.List<com.ph.shopping.facade.system.vo.SystemParameterVO>
     * @author 郑朋
     * @create 2017/5/8
     */
    List<SystemParameterVO> selectSystemParameterList();


    /**
     * @methodname selectSystemParameterBySelective 的描述：条件查询系统参数
     * @param systemParameter
     * @return java.util.List<com.ph.shopping.facade.system.vo.SystemParameterVO>
     * @author 郑朋
     * @create 2017/5/9
     */
    List<SystemParameterVO> selectSystemParameterBySelective(SystemParameter systemParameter);

    /**
     * @methodname selectSystemParameterByIds 的描述：通过id查询系统参数
     * @param ids
     * @return java.util.List<com.ph.shopping.facade.system.vo.SystemParameterVO>
     * @author 郑朋
     * @create 2017/5/9
     */
    List<SystemParameterVO> selectSystemParameterByIds(@Param(value = "ids") List<Long> ids);
}
