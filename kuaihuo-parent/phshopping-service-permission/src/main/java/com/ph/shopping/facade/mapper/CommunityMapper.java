/**  
 * @Title:  CommunityMapper.java   
 * @Package com.ph.shopping.facade.mapper   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年6月15日 下午2:25:01   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.facade.mapper;

import java.util.List;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.system.dto.CommunityQueryDTO;
import com.ph.shopping.facade.system.entity.CommunityInfo;
import com.ph.shopping.facade.system.vo.CommunityInfoVO;

/**   
 * @ClassName:  CommunityMapper   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 李杰
 * @date:   2017年6月15日 下午2:25:01     
 * @Copyright: 2017
 */
public interface CommunityMapper extends BaseMapper<CommunityInfo>{

	/**   
	 * @Title: CommunityListByPage   
	 * @Description: 查询社区列表参数   
	 * @param: @param dto
	 * @param: @return      
	 * @return: List<CommunityInfoVO>
	 * @author：李杰      
	 * @throws   
	 */ 
	List<CommunityInfoVO> selectCommunityListByPage(CommunityQueryDTO dto);

	/**   
	 * @Title: selectMaxTownIdByCountyId   
	 * @Description: 根据区ID得到最大的社区ID   
	 * @param: @param countyId
	 * @param: @return      
	 * @return: Long
	 * @author：李杰      
	 * @throws   
	 */ 
	Long selectMaxTownIdByCountyId(Long countyId);
	
	

}
