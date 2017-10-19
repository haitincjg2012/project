package com.ph.shopping.facade.system.service;

import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.system.dto.LogisticsDTO;

/**
 * 物流公司service
 *
 * @author 郑朋
 * @create 2017/6/8
 **/
public interface ILogisticsService {

    /**
     * @methodname getLogisticsList 的描述：查询所有的物流公司
     * @param
     * @return com.ph.shopping.common.util.result.Result
     * @author 郑朋
     * @create 2017/6/8
     */
	Result getLogisticsList();
	
	/**
	 * 分页物流公司
	* @Title: getLogisticsList
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年6月15日 下午1:48:45
	* @param pageBean
	* @return
	 */
    Result getLogisticsList(PageBean pageBean);
    /**
     * 
     * @Title: addLogistics   
     * @Description: 添加物流公司  
     * @param: @param dto
     * @param: @return      
     * @return: Result
     * @author：李杰      
     * @throws
     */
    Result addLogistics(LogisticsDTO dto);
    /**
     * 
     * @Title: updateLogistics   
     * @Description: 修改物流公司   
     * @param: @param dto
     * @param: @return      
     * @return: Result
     * @author：李杰      
     * @throws
     */
    Result updateLogistics(LogisticsDTO dto);
    /**
     * 
     * @Title: deleteLogistics   
     * @Description: 根据ID 删除物流公司   
     * @param: @param id
     * @param: @return      
     * @return: Result
     * @author：李杰      
     * @throws
     */
    Result deleteLogistics(Long id);
    
    /**
     * 通过主键获取物流公司
    * @Title: getLogisticById
    * @Description: TODO(这里用一句话描述这个方法的作用)
    * @author Mr.Dong
    * @date  2017年6月15日 上午11:55:12
    * @param id
    * @return
     */
    Result getLogisticById(Long id);
}
