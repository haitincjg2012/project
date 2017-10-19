package com.ph.shopping.facade.mapper;

import com.ph.shopping.facade.spm.entity.Agent;
import com.ph.shopping.facade.spm.entity.Merchant;
import com.ph.shopping.facade.spm.entity.Supplier;

/**
 * @项目：phshopping-service-permission
 *
 * @描述： 安全管理商户，代理商，供应商修改
 *
 * @作者： Mr.Shu
 *
 * @创建时间：2017-03-28
 *
 * @Copyright @2017 by Mr.Shu
 */
public interface SafeManageMapper {
    /**
     * @描述：修改代理商
     *
     * @param: agent
     *
     * @return:
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/4/25 17:25
     */
    void updateAgent(Agent agent);

    /**
     * @描述：修改商户
     *
     * @param: merchant
     *
     * @return:
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/4/25 17:26
     */
    void updateMerchant(Merchant merchant);

    /**
     * @描述：修改供应商
     *
     * @param: supplier
     *
     * @return:
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/4/25 17:26
     */
    void updateSupplier(Supplier supplier);
}
