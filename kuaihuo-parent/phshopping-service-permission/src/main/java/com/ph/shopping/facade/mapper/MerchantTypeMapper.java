package com.ph.shopping.facade.mapper;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.system.dto.MerchantTypeDTO;
import com.ph.shopping.facade.system.entity.MerchantType;
import com.ph.shopping.facade.system.vo.MerchantTypeVO;

import java.util.List;

/**
 * @version 2.1
 * @项目：phshopping-parent
 * @描述： description
 * @作者： 熊克文
 * @创建时间： 2017/6/12
 * @Copyright by xkw
 */
public interface MerchantTypeMapper extends BaseMapper<MerchantType> {

    /**
     * 查询商户类型详情列表
     *
     * @param merchantTypeDTO
     * @return List<MerchantTypeVO>
     * @author 何文浪
     * @时间 2017-5-12
     */
    List<MerchantTypeVO> getMerchantTypeListDetail(MerchantTypeDTO merchantTypeDTO);

    /**
     * 查询商户类别列表
     *
     * @param merchantTypeDTO
     * @return List<MerchantTypeVO>
     * @author 何文浪
     * @时间 2017-5-12
     */
    List<MerchantTypeVO> getMerchantTypeList(MerchantTypeDTO merchantTypeDTO);

    List<MerchantTypeVO> getHunterTypeList(MerchantTypeDTO merchantTypeDTO);
    /**
     * 查询商户类别子级
     *
     * @param merchantTypeDTO
     * @return List<MerchantTypeVO>
     * @author 何文浪
     * @时间 2017-5-12
     */
    List<MerchantTypeVO> getMerchantTypeByChildList(MerchantTypeDTO merchantTypeDTO);
}
