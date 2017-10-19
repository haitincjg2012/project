/**  
 * @Title:  IndustryInfoMapper.java   
 * @Package com.ph.shopping.facade.mapper   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年5月8日 下午4:39:32   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.facade.mapper;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.system.dto.IndustryInfoDTO;
import com.ph.shopping.facade.system.entity.IndustryInfo;
import com.ph.shopping.facade.system.vo.IndustryInfoVO;

import java.util.List;

/**   
 * @ClassName:  IndustryInfoMapper   
 * @Description:行业数据操作dao   
 * @author: 李杰
 * @date:   2017年5月8日 下午4:39:32     
 * @Copyright: 2017
 */
public interface IndustryInfoMapper extends BaseMapper<IndustryInfo>{

	/**   
	 * @Title: selectIndustryInfoList   
	 * @Description: 查询行业数据列表  
	 * @param: @param dto
	 * @param: @return      
	 * @return: List<IndustryInfoVO>
	 * @author：李杰      
	 * @throws   
	 */ 
	List<IndustryInfoVO> selectIndustryInfoList(IndustryInfoDTO dto);

}
