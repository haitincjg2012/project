package com.ph.shopping.facade.spm.service;

import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.spm.entity.Agent;
import com.ph.shopping.facade.spm.entity.Merchant;
import com.ph.shopping.facade.spm.entity.Supplier;

/**
 * @项目：phshopping-facade-permission
 * @描述：修改商户代理商供应商接口
 * @作者： Mr.Shu
 * @创建时间：2017-03-15
 * @Copyright @2017 by Mr.Shu
 */
public interface ISafeManageService {
    /**
     * @描述：修改代理商
     * @param: agent
     * @return:
     * @作者： Mr.Shu
     * @创建时间：2017/4/25 17:54
     */
    Result updateAgent(Agent agent);

    /**
     * @描述：修改商户
     *
     * @param: merchant
     *
     * @return:
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/4/25 17:54
     */
    Result updateMerchant(Merchant merchant);

    /**
     * @描述：修改供应商
     *
     * @param: supplier
     *
     * @return:
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/4/25 17:54
     */
    Result updateSupplier(Supplier supplier);
}
