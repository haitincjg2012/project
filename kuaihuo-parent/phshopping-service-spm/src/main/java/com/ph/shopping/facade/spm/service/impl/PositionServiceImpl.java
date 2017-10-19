package com.ph.shopping.facade.spm.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.mapper.PositionMapper;
import com.ph.shopping.facade.spm.dto.PositionDTO;
import com.ph.shopping.facade.spm.exception.MerchantExceptionEnum;
import com.ph.shopping.facade.spm.service.IPositionService;
import com.ph.shopping.facade.spm.vo.PositionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author 何文浪
 * @version 2.1
 * @项目 phshopping-service-spm
 * @描述 区域业务层
 * @时间 2017-5-22
 */
@Component
@Service(version = "1.0.0")
public class PositionServiceImpl implements IPositionService {
    //区域持久层
    @Autowired
    private PositionMapper positionMapper;

    @Override
    public List<PositionVO> getPositionList(PositionDTO positionDTO) {
        return positionMapper.getPositionList(positionDTO);
    }

    @Override
    public List<PositionVO> getPositionChildList(PositionDTO positionDTO) {
        return positionMapper.getPositionChildList(positionDTO);
    }

    @Override
    public Result getPositionList(PageBean pageBean, PositionDTO positionDTO) {
        //分页
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        List<PositionVO> list = positionMapper.getPositionList(positionDTO);
        PageInfo<PositionVO> pageInfo = new PageInfo<PositionVO>(list);
        return new Result(true, MerchantExceptionEnum.SELECT_MRRCHANT_EXCEPTION.getCode(), "", pageInfo.getList(),
                pageInfo.getTotal());
    }

    @Override
    public PositionVO getPositionVo(PositionDTO positionDTO) {
        List<PositionVO> list = positionMapper.getPositionList(positionDTO);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public PositionVO getPositionVoById(Long id) {
        return positionMapper.getPositionById(id);
    }

    @Override
    public List<PositionVO> getPositionVoByTownIds(List<Long> ids) {
        List<PositionVO> list = new LinkedList<PositionVO>();
        if (null != ids && !ids.isEmpty()) {
            Map<Object, List<Long>> map = getTownIds(ids);
            for (Map.Entry<Object, List<Long>> id : map.entrySet()) {
                List<PositionVO> ps = positionMapper.getPositionVoByTownIds(id.getValue());
                if (null != ps) {
                    list.addAll(ps);
                }
            }
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Result getMerchantAppCounty() {
        Result result = new Result(true);
        List<PositionVO> countyList = this.positionMapper.getPositionVoByGroup("countyId");
        //此处31是因为数据库的province有31条
        List<Map<String, Object>> all = new ArrayList<>(31);
        //数据库的所有city有344条,province有31条 用于判断是否已经存在
        Set<Long> codeSet = new HashSet<>(344 + 31);
        for (PositionVO position : countyList) {
            if (!codeSet.contains(position.getProvinceId())) {
                codeSet.add(position.getProvinceId());
                all.add(CollectionUtils.toMap("areaId", position.getProvinceId(), "areaName", position.getProvinceName()));
                List<Map<String, Object>> cityMap = new ArrayList<>();
                List<Map<String, Object>> countyMap = new ArrayList<>();
                cityMap.add(CollectionUtils.toMap("areaId", position.getCityId(), "areaName", position.getCityName()));
                codeSet.add(position.getCityId());
                cityMap.get(0).put("counties", countyMap);
                countyMap.add(CollectionUtils.toMap("areaId", position.getCountyId(), "areaName", position.getCountyName()));
                //因为新加入的province是新添加的,所以直接在最后一个province加入子集cities
                all.get(all.size() - 1).put("cities", cityMap);
            } else {
                if (!codeSet.contains(position.getCityId())) {
                    //加入子集的city
                    for (Map<String, Object> province : all) {
                        if (province.get("areaId").equals(position.getProvinceId())) {
                            Map<String, Object> cityMap = CollectionUtils.toMap("areaId", position.getCityId(), "areaName", position.getCityName());
                            ((List<Map<String, Object>>) province.get("cities")).add(
                                    cityMap);
                            codeSet.add(position.getCityId());
                            List<Map<String, Object>> countyMap = new ArrayList<>();
                            cityMap.put("counties", countyMap);
                            countyMap.add(CollectionUtils.toMap("areaId", position.getCountyId(), "areaName", position.getCountyName()));
                            break;
                        }
                    }
                } else {
                    //加入子集的county
                    for (Map<String, Object> province : all) {
                        if (province.get("areaId").equals(position.getProvinceId())) {
                            for (Map<String, Object> city : ((List<Map<String, Object>>) province.get("cities"))) {
                                if (city.get("areaId").equals(position.getCityId())) {
                                    ((List<Map<String, Object>>) city.get("counties")).add(
                                            CollectionUtils.toMap("areaId", position.getCountyId(), "areaName", position.getCountyName()));
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        result.setMessage("查询区域三级成功");
        result.setData(all);
        result.setCount(all.size());
        result.setCode("200");
        return result;
    }

    private Map<Object, List<Long>> getTownIds(List<Long> list) {
        Map<Object, List<Long>> map = new HashMap<Object, List<Long>>();
        int num = 0;
        int key = 1;
        List<Long> ids = new LinkedList<Long>();
        for (Long id : list) {
            if (num < 1000) {
                ids.add(id);
            } else {
                map.put(key++, ids);
                ids = new LinkedList<Long>();
                ids.add(id);
                num = 0;
                continue;
            }
            num++;
        }
        if (!ids.isEmpty()) {
            map.put(key, ids);
        }
        return map;
    }
}
