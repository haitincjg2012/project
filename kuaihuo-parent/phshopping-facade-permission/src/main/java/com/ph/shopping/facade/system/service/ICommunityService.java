/**  
 * @Title:  ICommunityService.java   
 * @Package com.ph.shopping.facade.system.service   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年6月15日 下午1:40:45   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.facade.system.service;

import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.system.dto.CommunityAddDTO;
import com.ph.shopping.facade.system.dto.CommunityQueryDTO;
import com.ph.shopping.facade.system.dto.CommunityUpdateDTO;

/**   
 * @ClassName:  ICommunityService   
 * @Description:社区相关接口   
 * @author: 李杰
 * @date:   2017年6月15日 下午1:40:45     
 * @Copyright: 2017
 */
public interface ICommunityService {

	/**
	 * 
	 * @Title: getCommunityListByPage   
	 * @Description:根据分页信息查询社区列表   
	 * @param: @param dto
	 * @param: @param pageBean
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	Result getCommunityListByPage(CommunityQueryDTO dto,PageBean pageBean);
	/**
	 * 
	 * @Title: addCommunity   
	 * @Description: 添加社区   
	 * @param: @param dto
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	Result addCommunity(CommunityAddDTO dto);
	/**
	 * 
	 * @Title: updateCommunity   
	 * @Description: 修改社区   
	 * @param: @param dto
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	Result updateCommunity(CommunityUpdateDTO dto);
	
	/**
	 * 通过主键获取position
	* @Title: getCommunityById
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年6月16日 上午11:20:58
	* @param id
	* @return
	 */
	Result getPositionById(Long id);
}
