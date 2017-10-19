/**  
 * @Title:  MemberShareRecordMapper.java   
 * @Package com.ph.shopping.facade.mapper   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年7月24日 下午5:24:59   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.shopping.facade.mapper;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.member.entity.MemberShareRecord;
import com.ph.shopping.facade.member.vo.MemberShareRecordVO;

/**   
 * @ClassName:  MemberShareRecordMapper   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 李杰
 * @date:   2017年7月24日 下午5:24:59     
 * @Copyright: 2017
 */
public interface MemberShareRecordMapper extends BaseMapper<MemberShareRecord>{

	/**
	 * 
	 * @Title: getShareRecordInfoByTelphone   
	 * @Description: 根据手机号得到分享记录  
	 * @param: @param telPhone
	 * @param: @return      
	 * @return: MemberShareRecordVO
	 * @author：李杰      
	 * @throws
	 */
	MemberShareRecordVO getShareRecordInfoByTelphone(MemberShareRecord record);
}
