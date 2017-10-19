package com.ph.shopping.facade.spm.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.mapper.StoreManagerMapper;
import com.ph.shopping.facade.spm.service.IStoreManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by jx on 2017/9/26.
 */
@Component
@Service(version = "1.0.0")
public class IStoreManagerServiceImpl implements IStoreManagerService {
    @Autowired
    private StoreManagerMapper storeManagerMapper;

    @Override
    public Result getStoreMangerByUserId(Long merchantId) {
        Map map = storeManagerMapper.getStoreManagerById(merchantId);
        return ResultUtil.getResult(RespCode.Code.SUCCESS, map);
    }

    @Override
    public void updateStoreManager(Long id) {
        storeManagerMapper.updateStoreManager(id);
    }
}
