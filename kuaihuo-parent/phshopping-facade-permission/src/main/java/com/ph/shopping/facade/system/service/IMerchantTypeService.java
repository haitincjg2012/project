package com.ph.shopping.facade.system.service;

import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.system.dto.MerchantTypeDTO;
import com.ph.shopping.facade.system.vo.MerchantTypeAppVO;
import com.ph.shopping.facade.system.vo.MerchantTypeVO;

import java.util.List;

/**
 * @version 1.0.0
 * @项目：phshopping-parent
 * @描述： 商户类别接口
 * @作者： 熊克文
 * @创建时间： 2017/5/12
 * @Copyright by xkw
 */
public interface IMerchantTypeService {

    /**
     * 查询商户类别app树形 默认根id为0
     *
     * @param merchantTypeDTO: id为0查询所有类别树
     * @return List<MerchantTypeVO>
     * @author 熊克文
     * @时间 2017-5-12
     */
    List<MerchantTypeVO> getMerchantTypeTree(MerchantTypeDTO merchantTypeDTO);


    /**
     * 查询商户类别子级列表
     *
     * @param merchantTypeDTO 查询条件dto
     * @return List<MerchantTypeVO>
     * @author 熊克文
     * @时间 2017-5-12
     */
    List<MerchantTypeVO> getMerchantTypeByChildList(MerchantTypeDTO merchantTypeDTO);


    /**
     * 查询商户类别列表分页
     *
     * @param merchantTypeDTO 查询条件dto
     * @param pageBean       分页参数
     * @return List<MerchantTypeVO>
     * @author 熊克文
     * @时间 2017-5-12
     */
    Result getMerchantTypeByPage(PageBean pageBean, MerchantTypeDTO merchantTypeDTO);

    /**
     * 增加商户分类
     *
     * @param merchantTypeDTO dto
     * @return Result
     * @author 熊克文
     * @时间 2017-5-12
     */
    Result addMerchantType(MerchantTypeDTO merchantTypeDTO);

    /**
     * 查询商户详情接口
     *
     * @param merchantTypeDTO 查询Vo
     * @return Result
     * @author 熊克文
     * @时间 2017-5-12
     */
    Result getMerchantTypeDetail(MerchantTypeDTO merchantTypeDTO);

    /**
     * 修改商户分类
     *
     * @param merchantTypeDTO dto
     * @return Result
     * @author 熊克文
     * @时间 2017-5-12
     */
    Result updateMerchantType(MerchantTypeDTO merchantTypeDTO);

    /**
     * 删除商户分类  物理删除
     *
     * @param merchantTypeDTO dto
     * @return Result
     * @author 熊克文
     * @时间 2017-5-12
     */
    Result delete(MerchantTypeDTO merchantTypeDTO);

    /**
     * 树形列表接口
     *
     * @return Result
     * @author 熊克文
     * @时间 2017-5-12
     */
    List<MerchantTypeVO> getMerchantTypeTreeTableList(MerchantTypeDTO merchantTypeDTO);

    /**
     * 查询批发商分类
     * @param merchantTypeDTO
     * @return
     */
    List<MerchantTypeVO> getHunterTypeTreeTableList(MerchantTypeDTO merchantTypeDTO);
    /**
     * 查询商户类别app树形 默认根id为0
     *
     * @param id: id为0查询所有类别树
     * @return List<MerchantTypeVO>
     * @author 熊克文
     * @时间 2017-5-12
     */
    List<MerchantTypeAppVO> getMerchantTypeAppTree(Long id);
}
