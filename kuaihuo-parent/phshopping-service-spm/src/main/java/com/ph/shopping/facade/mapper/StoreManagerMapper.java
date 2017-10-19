package com.ph.shopping.facade.mapper;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.member.dto.StoreManagerDTO;
import com.ph.shopping.facade.member.entity.StoreManagerRecord;
import com.ph.shopping.facade.member.vo.StoreManagerVO;
import com.ph.shopping.facade.member.vo.StoreMangerImageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 店面经理
 */
public interface StoreManagerMapper extends BaseMapper<StoreManagerRecord> {
    public Map getStoreManagerById(Long merchantId);

    public List<Map> getMerchantByPage(StoreManagerDTO dto);

    /**
     * 解聘店面经理
     * @param id
     */
    public void updateStoreManager(Long id);

    public List<StoreMangerImageVO> getAgentListDateil(StoreManagerDTO dto);

    public List<StoreManagerVO> getStoreMangerBase(StoreManagerDTO dto);

    public void deleteStoreMangerImage(Long id);

    public void saveStoreMangeImage(@Param("id") Long id,@Param("type") Integer type,@Param("url") String url);

    public void updateStoreManagerType(@Param("id") Long id,@Param("type") Integer type,@Param("name") String name,@Param("card") String card,@Param("phone") String phone);

    public void updateStoreManagerTypeById(@Param("id") Long id,@Param("type") Integer type);


}
