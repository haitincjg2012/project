/**
 *
 */
package com.ph.shopping.facade.spm.service;


import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.spm.entity.AgentPurchaseOrder;

import java.util.List;

/**
 * @项目：phshopping-AgentPurchaseOrder-agent
 * @描述：代理商进货订单接口
 * @作者： Mr.Shu
 * @创建时间：2017年3月10日
 * @Copyright @2017 by Mr.Shu
 */
public interface IAgentPurchaseOrderService {


    /**
     * 分页获取代理商进货订单列表
     *
     * @return
     */
    public Result getAgentPurchaseOrderListByPage(Integer pageIndex, Integer pageSize);

    /**
     * 获取代理商进货订单列表
     *
     * @return
     */
    public List<AgentPurchaseOrder> getAgentPurchaseOrderList();

    /**
     * 新增代理商进货订单
     *
     * @return
     */
    public Result addAgentPurchaseOrder(AgentPurchaseOrder agentPurchaseOrder);

    /**
     * 修改代理商进货订单
     *
     * @return
     */
    public Result updateAgentPurchaseOrder(AgentPurchaseOrder agentPurchaseOrder);

    /**
     * 删除代理商进货订单
     *
     * @return
     */
    public Result deleteAgentPurchaseOrder(AgentPurchaseOrder agentPurchaseOrder);

}
