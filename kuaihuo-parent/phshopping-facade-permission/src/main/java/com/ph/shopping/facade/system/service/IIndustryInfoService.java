/**  
 * @Title:  IIndustryInfoService.java   
 * @Package com.ph.shopping.facade.system.service   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年5月8日 下午2:47:33   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.facade.system.service;

import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.system.dto.IndustryInfoDTO;

/**   
 * @ClassName:  IIndustryInfoService   
 * @Description:行业相关服务接口   
 * @author: 李杰
 * @date:   2017年5月8日 下午2:47:33     
 * @Copyright: 2017
 */
public interface IIndustryInfoService {
	/**
	 * 
	 * @Title: getIndustryInfos   
	 * @Description: 查询行业数据列表  
	 * @param: @param dto
	 * @param: @return      
	 * @return: List<IndustryInfoVO>
	 * @author：李杰      
	 * @throws
	 */
	Result getIndustryInfoList(IndustryInfoDTO dto);
	/**
	 * 
	 * @Title: addIndustryInfo   
	 * @Description: 添加行业信息   
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	Result addIndustryInfo(IndustryInfoDTO dto);
	/**
	 * 
	 * @Title: updateIndustryInfo   
	 * @Description: 修改行业信息  
	 * @param: @param dto
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	Result updateIndustryInfo(IndustryInfoDTO dto);
	/**
	 * 
	 * @Title: updateIndustryInfoByStart   
	 * @Description: 启用行业   
	 * @param: @param id
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	Result updateIndustryInfoByStart(Long id);
	/**
	 * 
	 * @Title: updateIndustryInfoByOutage   
	 * @Description: 停用行业  
	 * @param: @param id
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	Result updateIndustryInfoByOutage(Long id);
}
