package com.phshopping.api.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.system.service.IMerchantTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version 1.0.0
 * @项目：phshopping-parent
 * @描述： 商户类别app接口进入层
 * @作者： 熊克文
 * @创建时间： 2017/5/12
 * @Copyright by xkw
 */
@RestController
@RequestMapping("api/merchantType")
public class MerchantTypeController {

    // 创建日志
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /*商户接口*/
    @Reference(version = "1.0.0")
    private IMerchantTypeService iMerchantTypeService;

    /**
     * 查询商户类别树形 默认根id为0
     *
     * @param id: id为0查询所有类别树
     * @return List<MerchantTypeVO>
     * @author 熊克文
     * @时间 2017-7-25
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result getMerchantTypeTree(@PathVariable(name = "id") Long id) {
        return new Result(true, "200", "查询树形商户类别成功", iMerchantTypeService.getMerchantTypeAppTree(id));
    }
}
