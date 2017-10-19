package com.ph.order.api.service;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.ph.order.api.constants.PurchaseConstants;
import com.ph.order.api.controller.vo.PurchaseUserVO;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.spm.dto.AgentDTO;
import com.ph.shopping.facade.spm.dto.MerchantDTO;
import com.ph.shopping.facade.spm.dto.SupplierDTO;
import com.ph.shopping.facade.spm.service.*;
import com.ph.shopping.facade.spm.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * spm模块方法调用service
 *
 * @author 郑朋
 * @create 2017/5/25
 **/
@Service
public class SpmService {

    private static final Logger logger = LoggerFactory.getLogger(SpmService.class);

    @Reference(version = "1.0.0")
    ISupplierService supplierService;

    @Reference(version = "1.0.0")
    IAgentService agentService;

    @Reference(version = "1.0.0")
    IMerchantService merchantService;

    @Reference(version = "1.0.0")
    IWarehouseAddressService warehouseAddressService;

    @Reference(version = "1.0.0",retries = 0)
    IUserBalanceService userBalanceService;

    /**
     * @methodname getMerchant 的描述：通过userId查询商户信息
     * @param userId
     * @return java.util.List<com.ph.shopping.facade.spm.vo.MerchantVO>
     * @author 郑朋
     * @create 2017/5/25
     */
    public List<MerchantVO> getMerchant(Long userId) {
        List<MerchantVO> merchantVOList = new ArrayList<>();
        try {
            MerchantDTO merchantDTO = new MerchantDTO();
            merchantDTO.setUserId(userId);
            logger.info("查询商户接口入参：merchantDTO={}", JSON.toJSONString(merchantDTO));
            merchantVOList = merchantService.getMerchantList(merchantDTO);
            logger.info("查询商户接口返回值：result={}", JSON.toJSONString(merchantVOList));
        } catch (Exception e) {
            logger.error("查询商户接口异常：e={}", e);
        }
        return merchantVOList;
    }


    /**
     * @methodname getCityAgentVo 的描述：通过商户id查询市级代理
     * @param userId
     * @return com.ph.shopping.facade.spm.vo.AgentVO
     * @author 郑朋
     * @create 2017/6/7
     */
    public AgentVO getCityAgentVo(Long userId) {
        AgentDTO agentDTO = new AgentDTO();
        agentDTO.setUserId(userId);
        //TODO
        agentDTO.setAgentLevelId(1L);
        logger.info("通过商户id查询市级代理接口入参：merchantDTO={}", JSON.toJSONString(agentDTO));
        AgentVO vo = agentService.getAgentByMerchant(agentDTO);
        logger.info("通过商户id查询市级代理接口返回值：result={}", JSON.toJSONString(vo));
        return vo;
    }


    /**
     * @methodname getMerchantByAgent 的描述：通过代理id查询归属商户
     * @param agentId
     * @return java.util.List<com.ph.shopping.facade.spm.vo.MerchantVO>
     * @author 郑朋
     * @create 2017/5/26
     */
    public List<MerchantVO> getMerchantByAgent(Long agentId) {
        //调用spm通过市级代理id查询所有的归属商户信息
        List<MerchantVO> merchantVOList = new ArrayList<>();
        try {
            MerchantDTO merchantDTO = new MerchantDTO();
            merchantDTO.setAgentId(agentId);
            logger.info("代理id查询归属商户接口入参：merchantDTO={}", JSON.toJSONString(merchantDTO));
            merchantVOList = merchantService.getMerchantByAgentAllList(merchantDTO);
            logger.info("代理id查询归属商户接口返回值：result={}", JSON.toJSONString(merchantVOList));
        } catch (Exception e) {
            logger.error("代理id查询归属商户接口异常：e={}", e);
        }
        return merchantVOList;
    }

    /**
     * @methodname getAgent 的描述：通过userId查询代理商信息
     * @param userId
     * @return java.util.List<com.ph.shopping.facade.spm.vo.AgentVO>
     * @author 郑朋
     * @create 2017/5/25
     */
    public List<AgentVO> getAgentByCreateId(Long userId) {
        List<AgentVO> agentVOList = new ArrayList<>();
        try {
            AgentDTO agentDTO = new AgentDTO();
            agentDTO.setCreaterId(userId);
            logger.info("查询代理商接口入参：agentDTO={}", JSON.toJSONString(agentDTO));
            agentVOList = agentService.getAgentList(agentDTO);
            logger.info("查询代理商接口返回值：result={}", JSON.toJSONString(agentVOList));
        } catch (Exception e) {
            logger.error("查询代理商接口异常：e={}", e);
        }
        return agentVOList;
    }

    /**
     * @methodname getAgent 的描述：通过userId查询代理商信息
     * @param userId
     * @return java.util.List<com.ph.shopping.facade.spm.vo.AgentVO>
     * @author 郑朋
     * @create 2017/5/25
     */
    public List<AgentVO> getAgentByUser(Long userId) {
        List<AgentVO> agentVOList = new ArrayList<>();
        try {
            AgentDTO agentDTO = new AgentDTO();
            agentDTO.setUserId(userId);
            logger.info("查询代理商接口入参：agentDTO={}", JSON.toJSONString(agentDTO));
            agentVOList = agentService.getAgentList(agentDTO);
            logger.info("查询代理商接口返回值：result={}", JSON.toJSONString(agentVOList));
        } catch (Exception e) {
            logger.error("查询代理商接口异常：e={}", e);
        }
        return agentVOList;
    }


    /**
     * @methodname getCityAgent 的描述：查询所有的市级代理
     * @param level
     * @return java.util.List<com.ph.shopping.facade.spm.vo.AgentVO>
     * @author 郑朋
     * @create 2017/5/25
     */
    public List<AgentVO> getCityAgent(Long level) {
        List<AgentVO> agentVOList = new ArrayList<>();
        try {
            AgentDTO agentDTO = new AgentDTO();
            agentDTO.setAgentLevelId(level);
            logger.info("查询所有市级代理商接口入参：agentDTO={}", JSON.toJSONString(agentDTO));
            agentVOList = agentService.getAgentList(agentDTO);
            logger.info("查询所有市级代理商接口返回值：result={}", JSON.toJSONString(agentVOList));
        } catch (Exception e) {
            logger.error("查询所有市级代理商接口异常：e={}", e);
        }
        return agentVOList;
    }

    /**
     * @methodname getSupplierByUser 的描述：通过userId查询供应商信息
     * @param userId
     * @return java.util.List<com.ph.shopping.facade.spm.vo.SupplierVO>
     * @author 郑朋
     * @create 2017/5/25
     */
    public List<SupplierVO> getSupplierByUser(Long userId) {
        List<SupplierVO> list = new ArrayList<>();
        try {
            SupplierDTO supplierDTO = new SupplierDTO();
            supplierDTO.setUserId(userId);
            logger.info("查询供应商接口入参：agentDTO={}", JSON.toJSONString(supplierDTO));
            list = supplierService.getSupplierList(supplierDTO);
            logger.info("查询供应商接口返回值：result={}", JSON.toJSONString(list));
        } catch (Exception e) {
            logger.error("查询供应商接口异常：e={}", e);
        }
        return list;
    }

    /**
     * @methodname getSupplierByCreateId 的描述：通过createId查询供应商信息
     * @param userId
     * @return java.util.List<com.ph.shopping.facade.spm.vo.SupplierVO>
     * @author 郑朋
     * @create 2017/5/25
     */
    public List<SupplierVO> getSupplierByCreateId(Long userId) {
        List<SupplierVO> list = new ArrayList<>();
        try {
            SupplierDTO supplierDTO = new SupplierDTO();
            supplierDTO.setCreaterId(userId);
            logger.info("查询供应商接口入参：agentDTO={}", JSON.toJSONString(supplierDTO));
            list = supplierService.getSupplierList(supplierDTO);
            logger.info("查询供应商接口返回值：result={}", JSON.toJSONString(list));
        } catch (Exception e) {
            logger.error("查询供应商接口异常：e={}", e);
        }
        return list;
    }

    /**
     * @methodname getSupplierById 的描述：通过id查询供应商信息
     * @param supplierId
     * @return com.ph.shopping.facade.spm.vo.SupplierVO
     * @author 郑朋
     * @create 2017/5/25
     */
    public SupplierVO getSupplierById(Long supplierId) {
        SupplierVO supplierVO = new SupplierVO();
        try {
            SupplierDTO supplierDTO = new SupplierDTO();
            supplierDTO.setId(supplierId);
            logger.info("查询供应商接口入参：agentDTO={}", JSON.toJSONString(supplierDTO));
            supplierVO = supplierService.getSupplierListById(supplierDTO);
            logger.info("查询供应商接口返回值：result={}", JSON.toJSONString(supplierVO));
        } catch (Exception e) {
            logger.error("查询供应商接口异常：e={}", e);
        }
        return supplierVO;
    }


    /**
     * @methodname getSupplierByType 的描述：通过类型查询供应商信息
     * @param type
     * @return java.util.List<com.ph.shopping.facade.spm.vo.SupplierVO>
     * @author 郑朋
     * @create 2017/5/25
     */
    public List<SupplierVO> getSupplierByType(Byte type) {
        List<SupplierVO> list = new ArrayList<>();
        try {
            SupplierDTO supplierDTO = new SupplierDTO();
            supplierDTO.setSupplierType(type);
            logger.info("查询供应商接口入参：agentDTO={}", JSON.toJSONString(supplierDTO));
            list = supplierService.getSupplierList(supplierDTO);
            logger.info("查询供应商接口返回值：result={}", JSON.toJSONString(list));
        } catch (Exception e) {
            logger.error("查询供应商接口异常：e={}", e);
        }
        return list;
    }

    /**
     * @methodname getPurchaseUserVo 的描述：查询所有的商户和市级代理商
     * @param
     * @return java.util.List<com.ph.order.api.controller.vo.PurchaseUserVO>
     * @author 郑朋
     * @create 2017/5/25
     */
    public List<PurchaseUserVO> getPurchaseUserVo(){
        List<PurchaseUserVO> purchaseUserVoList = new ArrayList<>();
        //TODO 等待何文浪写枚举
        List<AgentVO> agentVOList = getCityAgent(1L);
        purchaseUserVoList = getPurchaseUserVo1(agentVOList);
        List<MerchantVO> merchantVOList = getMerchant(null);
        purchaseUserVoList.addAll(getPurchaseUserVo(merchantVOList));
       return purchaseUserVoList;
    }

    /**
     * @methodname getPurchaseUserVo 的描述：商户转换参数
     * @param merchantVOList
     * @return java.util.List<com.ph.order.api.controller.vo.PurchaseUserVO>
     * @author 郑朋
     * @create 2017/5/26
     */
    public static List<PurchaseUserVO> getPurchaseUserVo(List<MerchantVO> merchantVOList) {
        List<PurchaseUserVO> purchaseUserVoList = new ArrayList<>();
        PurchaseUserVO purchaseUserVo;
        if (CollectionUtils.isNotEmpty(merchantVOList)) {
            for (MerchantVO vo : merchantVOList) {
                purchaseUserVo = new PurchaseUserVO();
                purchaseUserVo.setId(vo.getUserId());
                purchaseUserVo.setName(vo.getMerchantName());
                purchaseUserVo.setType(PurchaseConstants.MERCHANT);
                purchaseUserVoList.add(purchaseUserVo);
            }

        }
        return purchaseUserVoList;
    }

    /**
     * @methodname getPurchaseUserVo1 的描述：代理商转换参数
     * @param agentVOList
     * @return java.util.List<com.ph.order.api.controller.vo.PurchaseUserVO>
     * @author 郑朋
     * @create 2017/5/26
     */
    public static List<PurchaseUserVO> getPurchaseUserVo1(List<AgentVO> agentVOList) {
        List<PurchaseUserVO> purchaseUserVoList = new ArrayList<>();
        PurchaseUserVO purchaseUserVo;
        if (CollectionUtils.isNotEmpty(agentVOList)) {
            for (AgentVO vo : agentVOList) {
                purchaseUserVo = new PurchaseUserVO();
                purchaseUserVo.setId(vo.getUserId());
                purchaseUserVo.setName(vo.getAgentName());
                purchaseUserVo.setType(PurchaseConstants.AGENT);
                purchaseUserVoList.add(purchaseUserVo);
            }
        }
        return purchaseUserVoList;
    }

    /**
     * @methodname getPurchaseName 的描述：通过类型查询进货人名称
     * @param type
     * @param userId
     * @return java.lang.String
     * @author 郑朋
     * @create 2017/5/26
     */
    public String getPurchaseName(Byte type,Long userId) {
        String purchaseName = null;
        if (type == 0) {
            //商户进货
            List<MerchantVO> list = getMerchant(userId);
            if (CollectionUtils.isNotEmpty(list)) {
                purchaseName = list.get(0).getMerchantName();
            }
        } else if (type == 1){
            //代理商进货
            List<AgentVO> list = getAgentByUser(userId);
            if (CollectionUtils.isNotEmpty(list)) {
                purchaseName = list.get(0).getAgentName();
            }
        }
        return purchaseName;
    }

    /**
     * @methodname getAddress 的描述：通过userId查询仓库地址
     * @param userId
     * @return java.util.List<com.ph.shopping.facade.spm.vo.WarehouseAddressVO>
     * @author 郑朋
     * @create 2017/5/26
     */
    public List<WarehouseAddressVO> getAddress(Long userId) {
        List<WarehouseAddressVO> warehouseAddressVOS = new ArrayList<>();
        try {
            logger.info("通过userId查询仓库地址接口入参: userId={}",userId);
            warehouseAddressVOS = warehouseAddressService.getWarehouseAddressVoList(userId);
            logger.info("通过userId查询仓库地址接口返回值: warehouseAddressVOS={}",JSON.toJSONString(warehouseAddressVOS));
        } catch (Exception e) {
            logger.error("通过userId查询仓库地址接口异常: e={}",e);
        }
        return warehouseAddressVOS;
    }

    /**
     * @methodname getUserBalance 的描述：通过userId查询用户余额
     * @param userId
     * @return com.ph.shopping.common.util.result.Result
     * @author 郑朋
     * @create 2017/6/2
     */
    public BalanceVO getUserBalance(Long userId) {
        logger.info("通过userId查询用户余额接口入参: userId={}",userId);
        Result result = userBalanceService.getUserBalance(userId);
        logger.info("通过userId查询用户余额接口返回值: result={}",JSON.toJSONString(result));

        BalanceVO balanceVO = new BalanceVO();
        if (result.isSuccess()) {
            balanceVO = (BalanceVO) result.getData();
        }
        return balanceVO;
    }

}
