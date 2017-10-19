package com.alqsoft.service.hotrecommend;

import com.alqsoft.entity.hotrecommend.HotRecommend;
import org.alqframework.easyui.EasyuiResult;
import org.alqframework.orm.BaseService;
import org.alqframework.result.Result;

import java.util.List;
import java.util.Map;

/**
 * @author Xuejizheng
 * @date 2017-03-19 11:21
 */
public interface HotRecommendService extends BaseService<HotRecommend> {


    EasyuiResult<List<HotRecommend>> getHotPage(Map<String, Object> map, Integer page, Integer rows);

    HotRecommend getHotById(Long id);

    Result deleteById(Long id);

    Result save(String pids, Long rid, String name);

    String getProductIdsByRecommend(Long rid);

    String getProductOptions(String name);

    String getAllProductOptionByHotId(String hid);
}
