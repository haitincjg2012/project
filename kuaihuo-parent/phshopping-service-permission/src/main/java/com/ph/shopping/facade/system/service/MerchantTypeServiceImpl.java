package com.ph.shopping.facade.system.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ph.shopping.common.core.cache.redis.ICacheService;
import com.ph.shopping.common.util.bean.VoDtoEntityUtil;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.mapper.MerchantTypeMapper;
import com.ph.shopping.facade.system.dto.MerchantTypeDTO;
import com.ph.shopping.facade.system.entity.MerchantType;
import com.ph.shopping.facade.system.vo.MerchantTypeAppVO;
import com.ph.shopping.facade.system.vo.MerchantTypeVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @version 1.0.0
 * @项目：phshopping-parent
 * @描述： 商户类别接口实现类
 * @作者： 熊克文
 * @创建时间： 2017/5/12
 * @Copyright by xkw
 */
@Component
@Service(version = "1.0.0")
public class MerchantTypeServiceImpl implements IMerchantTypeService {
    //树形缓存的key
    private final static String REDIS_MERCHANT_TYPE_TREE_KEY = "redisMerchantTypeTreeKey_2.1";
    // 日志
    private final static Logger logger = LoggerFactory.getLogger(MerchantTypeServiceImpl.class);
    //商户
    @Autowired
    private MerchantTypeMapper merchantTypeMapper;
    //redis
    @Autowired
    private ICacheService iCacheService;

    /**
     * 递归树形分类
     */
    private MerchantTypeVO recursiveMerchantTypeVO(MerchantTypeVO root, Map<Long, MerchantTypeVO> allMap) {
        allMap.keySet().stream().filter(key -> allMap.get(key).getParentId().equals(root.getId())).
                forEach(key -> root.getChildren().add(this.recursiveMerchantTypeVO(allMap.get(key), allMap)));
        return root;
    }

    private MerchantTypeVO recursiveMerchantTypeVO(MerchantTypeVO root, List<MerchantTypeVO> all) {
        Map<Long, MerchantTypeVO> allMap = new HashMap<>();
        //key 为id
        all.forEach(merchantTypeVO -> allMap.put(merchantTypeVO.getId(), merchantTypeVO));
        BeanUtils.copyProperties(allMap.getOrDefault(root.getId(), root), root);
        return this.recursiveMerchantTypeVO(root, allMap);

    }

    @Override
    public List<MerchantTypeAppVO> getMerchantTypeAppTree(Long id) {
        MerchantTypeDTO merchantTypeDTO = new MerchantTypeDTO();
        merchantTypeDTO.setId(id);
        List<MerchantTypeAppVO> appList = JSON.parseArray(JSON.toJSONString(this.getMerchantTypeTree(merchantTypeDTO)), MerchantTypeAppVO.class);
        recursiveAppSort(appList);
        return appList;
    }

    //递归app排序
    private static void recursiveAppSort(List<MerchantTypeAppVO> appList) {
        if (!org.springframework.util.CollectionUtils.isEmpty(appList)) {
            appList.sort((a, b) -> (int) (a.getSort() - b.getSort()));
            appList.stream().map(MerchantTypeAppVO::getChildren).forEach(MerchantTypeServiceImpl::recursiveAppSort);
        }
    }

    @Override
    public List<MerchantTypeVO> getMerchantTypeTree(MerchantTypeDTO merchantTypeDTO) {
        try {
            List<MerchantTypeVO> cacheTree;
            if (iCacheService.get(REDIS_MERCHANT_TYPE_TREE_KEY) == null) {
                MerchantTypeDTO selectAll = new MerchantTypeDTO();
                selectAll.setIsDelete((byte) 0);
                cacheTree = this.merchantTypeMapper.getMerchantTypeList(selectAll);
                //加入缓存
                iCacheService.set(REDIS_MERCHANT_TYPE_TREE_KEY, JSON.toJSONString(cacheTree));
            } else {
                cacheTree = JSON.parseArray(iCacheService.get(REDIS_MERCHANT_TYPE_TREE_KEY).toString(), MerchantTypeVO.class);
            }
            List<MerchantTypeAppVO> all = VoDtoEntityUtil.convertList(cacheTree, MerchantTypeAppVO.class);
            if (all == null || all.isEmpty()) {
                return Collections.emptyList();
            }
            //默认查询rootId为0的跟目录
            MerchantTypeVO root = new MerchantTypeVO();
            root.setId(merchantTypeDTO.getId() == null ? 0 : merchantTypeDTO.getId());
            return this.recursiveMerchantTypeVO(root, cacheTree).getChildren();
        } catch (Exception e) {
            logger.error("生成类别树形菜单异常");
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<MerchantTypeVO> getMerchantTypeByChildList(MerchantTypeDTO merchantTypeDTO) {
        return this.merchantTypeMapper.getMerchantTypeByChildList(merchantTypeDTO);
    }

    @Override
    public Result getMerchantTypeByPage(PageBean pageBean, MerchantTypeDTO merchantTypeDTO) {
        //分页
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        List<MerchantType> list = this.merchantTypeMapper.select(VoDtoEntityUtil.convert(merchantTypeDTO, MerchantType.class));
        PageInfo<MerchantType> pageInfo = new PageInfo<>(list);
        return new Result(true, pageInfo.getList(),
                pageInfo.getTotal());
    }

    @Override
    public Result addMerchantType(MerchantTypeDTO merchantTypeDTO) {
        String validateMessage = merchantTypeDTO.validateForm();
        if (StringUtils.isNotBlank(validateMessage)) {
            return new Result(false, "", validateMessage);
        }
        //暂时只开放2级分类
        if (Objects.nonNull(merchantTypeDTO.getParentId()) && merchantTypeDTO.getParentId() != 0L) {
            MerchantType prev = merchantTypeMapper.selectByPrimaryKey(merchantTypeDTO.getParentId());
            if (Objects.isNull(prev)) {
                return new Result(false, "", "不存在的上级分类");
            } else {
                if (prev.getParentId() != 0L) {
                    return new Result(false, "", "暂未开放三级以上分类");
                }
            }
        } else {
            merchantTypeDTO.setParentId(0L);
        }
        merchantTypeDTO.setParentId(merchantTypeDTO.getParentId() == null ? 0L : merchantTypeDTO.getParentId());
        MerchantType add = VoDtoEntityUtil.convert(merchantTypeDTO, MerchantType.class);
        add.basic(merchantTypeDTO.getCreaterId());
        String code = this.merchantTypeMapper.insert(add) > 0 ? "200" : "500";
        iCacheService.remove(REDIS_MERCHANT_TYPE_TREE_KEY);
        return new Result(true, code, "添加商户类别成功");
    }

    @Override
    public Result getMerchantTypeDetail(MerchantTypeDTO merchantTypeDTO) {
        return new Result(true, this.merchantTypeMapper.selectByPrimaryKey(merchantTypeDTO), 0);
    }

    @Override
    public Result updateMerchantType(MerchantTypeDTO merchantTypeDTO) {
        MerchantType prev = merchantTypeMapper.selectByPrimaryKey(merchantTypeDTO);
        if (Objects.isNull(prev)) {
            return new Result(false, "", "不存在的类别");
        }
        MerchantType update = VoDtoEntityUtil.convert(merchantTypeDTO, MerchantType.class);
        //商户修改手机号，
        VoDtoEntityUtil.copyPropertiesNotNull(update, prev);
        prev.basic(merchantTypeDTO.getUpdaterId());
        String code = this.merchantTypeMapper.updateByPrimaryKey(prev) > 0 ? "200" : "500";
        iCacheService.remove(REDIS_MERCHANT_TYPE_TREE_KEY);
        return new Result(true, code, "修改商户类别成功");
    }

    @Transactional
    @Override
    public Result delete(MerchantTypeDTO merchantTypeDTO) {
        if (merchantTypeDTO == null || merchantTypeDTO.getId() == null) {
            return new Result(false, "", "删除id不能为空");
        }
        MerchantType merchantType = this.merchantTypeMapper.selectByPrimaryKey(merchantTypeDTO.getId());
        if (merchantType.getMerchanTypeLevel() == 1) {
            MerchantTypeDTO _merchantTypeDTO = new MerchantTypeDTO();
            _merchantTypeDTO.setParentId(merchantTypeDTO.getId());
            List<MerchantTypeVO> secondList = this.getMerchantTypeByChildList(_merchantTypeDTO);
            secondList.stream().map(MerchantTypeVO::getId).forEach(merchantTypeMapper::deleteByPrimaryKey);
        }
        iCacheService.remove(REDIS_MERCHANT_TYPE_TREE_KEY);
        String code = this.merchantTypeMapper.deleteByPrimaryKey(merchantTypeDTO) > 0 ? "200" : "500";

        return new Result(true, code, "删除商户类别成功");
    }

    @Override
    public List<MerchantTypeVO> getMerchantTypeTreeTableList(MerchantTypeDTO merchantTypeDTO) {
        Map<Long, MerchantTypeVO> all = new HashMap<>();
        List<MerchantTypeVO> merchantTypeVOList = this.merchantTypeMapper.getMerchantTypeList(merchantTypeDTO);
        merchantTypeVOList.forEach(key -> all.put(key.getId(), key));
        List<MerchantTypeVO> returnList = new ArrayList<>(merchantTypeVOList.size());
        merchantTypeVOList.stream().map(
                vo -> new MerchantTypeVO(
                        vo.getId(),
                        vo.getMerchantCount(),
                        vo.getMerchantTypeName(),
                        vo.getDesc(),
                        vo.getIcon(),
                        vo.getParentId(),
                        vo.getCreaterId(),
                        vo.getCreateTime(),
                        vo.getUpdaterId(),
                        vo.getUpdateTime(),
                        vo.getSort(),
                        vo.getIsDelete(),
                        vo.getMerchanTypeLevel(),
                        this.generateProductClassifySortList(vo.getId(), all)
                )).forEach(returnList::add);

        Collections.sort(returnList);

        return returnList;
    }

    @Override
    public List<MerchantTypeVO> getHunterTypeTreeTableList(MerchantTypeDTO merchantTypeDTO) {
        Map<Long, MerchantTypeVO> all = new HashMap<>();
        List<MerchantTypeVO> merchantTypeVOList = this.merchantTypeMapper.getHunterTypeList(merchantTypeDTO);
        System.out.println(merchantTypeVOList.size());
        for (MerchantTypeVO m:merchantTypeVOList) {
            System.out.println(m.getMerchantTypeName()+m.getChildren().get(0).getMerchantTypeName());
        }
        merchantTypeVOList.forEach(key -> all.put(key.getId(), key));
        List<MerchantTypeVO> returnList = new ArrayList<>(merchantTypeVOList.size());
        /*merchantTypeVOList.stream().map(
                vo -> new MerchantTypeVO(
                        vo.getId(),
                        vo.getMerchantCount(),
                        vo.getMerchantTypeName(),
                        vo.getDesc(),
                        vo.getIcon(),
                        vo.getParentId(),
                        vo.getCreaterId(),
                        vo.getCreateTime(),
                        vo.getUpdaterId(),
                        vo.getUpdateTime(),
                        vo.getSort(),
                        vo.getIsDelete(),
                        vo.getMerchanTypeLevel(),
                        this.generateProductClassifySortList(vo.getId(), all)
                )).forEach(returnList::add);*/

        Collections.sort(returnList);

        return returnList;
    }

    private ArrayList<Long> generateProductClassifySortList(Long id, Map<Long, MerchantTypeVO> all) {
        return this.generateProductClassifySortList(id, all, null);
    }

    private ArrayList<Long> generateProductClassifySortList(Long id, Map<Long, MerchantTypeVO> all, ArrayList<Long> sortList) {
        if (sortList == null) {
            sortList = new ArrayList<>();
        }

        MerchantTypeVO current = all.get(id);

        if (current == null) {
            Collections.reverse(sortList);
            return sortList;
        }

        sortList.add(current.getSort());

        if (all.containsKey(id)) {
            return this.generateProductClassifySortList(current.getParentId(), all, sortList);
        } else {
            //因为加入排序的时候是正序 反转返回
            Collections.reverse(sortList);
            return sortList;
        }
    }
}
