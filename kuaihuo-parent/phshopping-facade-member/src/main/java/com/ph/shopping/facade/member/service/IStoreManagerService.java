package com.ph.shopping.facade.member.service;

import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.member.dto.StoreDTO;
import com.ph.shopping.facade.member.dto.StoreManagerDTO;
import com.ph.shopping.facade.member.vo.StoreManagerVO;



/**
 * Created by wudi on 2017/9/25.
 */
public interface IStoreManagerService {

    public Result getMerchantByPage(StoreManagerDTO dto,PageBean pageBean);

    public StoreManagerVO getStoreManagerVODateilById(StoreManagerDTO dto);

    public Result updateAgentAndImg(StoreDTO agentDTO);

    public Result updateWorkType(Long id,Integer type);
}
