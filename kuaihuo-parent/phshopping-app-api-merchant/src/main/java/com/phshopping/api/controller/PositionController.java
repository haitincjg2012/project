package com.phshopping.api.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.shopping.common.core.cache.redis.ICacheService;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.spm.dto.PositionDTO;
import com.ph.shopping.facade.spm.service.IPositionService;
import com.ph.shopping.facade.spm.vo.PositionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version 2.1
 * @项目：phshopping-parent
 * @描述： 区域控制器
 * @作者： 熊克文
 * @创建时间： 2017/7/24
 * @Copyright by xkw
 */
@RestController
@RequestMapping(value = "/position")
public class PositionController {

    @Reference(version = "1.0.0")
    private IPositionService iPositionService;

    @Autowired
    private ICacheService iCacheService;

    private final static String MERCHANT_APP_COUNTY_KEY = "MERCHANT_APP_COUNTY_KEY_1.0.0";

    private static ThreadLocal<Long> lastCountyCacheTimestamp = new ThreadLocal<>();

    //默认三级区域有效时间1个小时
    private final static Long CACHE_TIMES = 60 * 60 * 1000L;

    /**
     * @title app 区域前三级区域查询
     * @author 熊克文
     */
    @RequestMapping(value = "/getMerchantAppCounty", method = {RequestMethod.GET})
    public Object getMerchantAppCounty() {
        //初始化的时候和缓存时间到了都更新这个
        if (lastCountyCacheTimestamp.get() == null || (lastCountyCacheTimestamp.get() - System.currentTimeMillis() >= CACHE_TIMES)) {
            iCacheService.remove(MERCHANT_APP_COUNTY_KEY);
            lastCountyCacheTimestamp.set(System.currentTimeMillis());
        }

        if (!iCacheService.exists(MERCHANT_APP_COUNTY_KEY)) {
            iCacheService.set(MERCHANT_APP_COUNTY_KEY, iPositionService.getMerchantAppCounty());
        }
        return iCacheService.get(MERCHANT_APP_COUNTY_KEY);
    }

    /**
     * @title app 根据区coe 查询下级town
     * @author 熊克文
     */
    @RequestMapping(value = "/getTownsByCountyCode/{countyCode}", method = {RequestMethod.GET})
    public Object getTownsByCountyCode(@PathVariable Long countyCode) {
        PositionDTO positionDTO = new PositionDTO();
        positionDTO.setCountyId(countyCode);
        List<Map<String, Object>> townMap = new ArrayList<>(32);
        for (PositionVO positionVO : iPositionService.getPositionChildList(positionDTO)) {
            Map<String, Object> town = new HashMap<>();
            town.put("townName", positionVO.getTownName());
            town.put("id", positionVO.getId());
            town.put("townId", positionVO.getTownId());
            townMap.add(town);
        }
        return new Result(true, "200", "查询社区成功", townMap);
    }
}
