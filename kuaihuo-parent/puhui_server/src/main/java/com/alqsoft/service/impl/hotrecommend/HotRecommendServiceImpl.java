package com.alqsoft.service.impl.hotrecommend;

import com.alqsoft.dao.hotrecommend.HotRecommendDao;
import com.alqsoft.dao.product.ProductDao;
import com.alqsoft.entity.hotrecommend.HotRecommend;
import com.alqsoft.entity.product.Product;
import com.alqsoft.service.hotrecommend.HotRecommendService;
import com.alqsoft.service.product.ProductService;
import org.alqframework.easyui.EasyUtils;
import org.alqframework.easyui.EasyuiResult;
import org.alqframework.orm.filter.DynamicSpecifications;
import org.alqframework.result.Result;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Xuejizheng
 * @date 2017-03-19 11:22
 */
@Service
@Transactional(readOnly = true)
public class HotRecommendServiceImpl implements HotRecommendService {

    private static Logger log = LoggerFactory.getLogger(HotRecommendServiceImpl.class);
    @Autowired
    private HotRecommendDao hotRecommendDao;

    @Autowired
    @Qualifier("mybatisHotRecommend")
    private com.alqsoft.mybatis.dao.hotRecommend.HotRecommendDao mybatisHotRecommendDao;

    @Autowired
    private ProductService productService;

    @Override
    @Transactional
    public HotRecommend saveAndModify(HotRecommend hotRecommend) {
        return hotRecommendDao.save(hotRecommend);
    }

    @Override
    @Transactional
    public boolean delete(Long aLong) {
        try {
            hotRecommendDao.delete(aLong);
            return true;
        } catch (Exception e) {
           // e.printStackTrace();
            log.info(e.getMessage(),e);
            return false;
        }

    }

    @Override
    public HotRecommend get(Long aLong) {
        return hotRecommendDao.findOne(aLong);
    }

    @Override
    public EasyuiResult<List<HotRecommend>> getHotPage(Map<String, Object> map, Integer page, Integer rows) {
        //Map<String, SearchFilter> filter = SearchFilter.parse(map);
        Specification<HotRecommend> specification = DynamicSpecifications.bySearchFilter(null,
                HotRecommend.class);
        Page<HotRecommend> Page = hotRecommendDao.findAll(specification, new PageRequest(page - 1, rows,
                new Sort(Sort.Direction.DESC, new String[] { "createdTime" })));
        return EasyUtils.returnPage(HotRecommend.class, Page);
    }

    @Override
    public HotRecommend getHotById(Long id) {
        return hotRecommendDao.findOne(id);
    }

    @Override
    @Transactional
    public Result deleteById(Long id) {
        Result result= new Result();
        try {
            int flag =mybatisHotRecommendDao.delete(id);
            if(flag>0){
                result.setCode(1);
                result.setMsg("删除成功");
            }else{
                result.setCode(0);
                result.setMsg("删除失败");
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            result.setCode(0);
            result.setMsg("有关联的商品信息无法删除");
        }finally {
            return result;
        }
    }

    /**
     * 保存推荐信息
     * @param pids
     * @param rid
     * @param name
     * @return
     */
    @Override
    @Transactional
    public Result save(String pids, Long rid, String name) {
        log.info("pids:{} , rid : {} ,name: {}",pids,rid,name);
        Result result = new Result();
        if (Objects.isNull(rid)||
                StringUtils.isBlank(name)){
            result.setCode(0);
            result.setMsg("参数异常.");
            return result;
        }
        //清空所有推荐信息
        if(StringUtils.isBlank(pids)){
            //删除所有热门推荐商品
            int delCount = productService.setHotRecommendNull(rid);
            log.info("delCount : {} ",delCount);
            result.setCode(1);
            result.setMsg("修改成功");
            return result;
        }
        String[] pidAry = pids.split(",");
        try {
            HotRecommend recommend=hotRecommendDao.findOne(Long.valueOf(rid));
            recommend.setName(name);
            HotRecommend hotRecommend=hotRecommendDao.save(recommend);
            //删除所有热门推荐商品
           int delCount = productService.setHotRecommendNull(rid);
           log.info("delCount : {} ",delCount);
            for (String pid:pidAry){
              if (StringUtils.isBlank(pid)){
                  continue;
               }
              Product product= productService.get(Long.valueOf(pid));
              product.setHoRecommendId(hotRecommend);
              productService.saveAndModify(product);
            }
            result.setCode(1);
            result.setMsg("修改成功");
            return result;
        }catch (Exception e){
            log.error(e.getMessage(),e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.setCode(0);
            result.setMsg("修改失败");
            return result;
        }
    }

    @Override
    public String getProductIdsByRecommend(Long rid) {
      HotRecommend recommend = new HotRecommend();
      recommend.setId(rid);
      List<Product> products=  productService.getProductIdsByRecommendId(recommend);
      log.info("product:{}",products);
      StringBuilder builder = new StringBuilder();
      for (Product product:products){
          builder.append(product.getId()).append(",");
      }
      String ids = builder.toString();
      log.info("ids:{}",ids);
      return ids;
    }

    @Override
    public String getProductOptions(String name) {
        return productService.getProductOptionsByName(name);
    }

    @Override
    public String getAllProductOptionByHotId(String hid) {
        return productService.getProductOptionsByHotId(hid);
    }
}
