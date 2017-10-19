package com.alqsoft.service.impl.collectionproduct;

import com.alqsoft.dao.collectionproduct.CollectionProductDao;
import com.alqsoft.entity.collectionproduct.CollectionProduct;
import com.alqsoft.entity.member.Member;
import com.alqsoft.entity.product.Product;
import com.alqsoft.rpc.mobile.RpcCollectionProductService;
import com.alqsoft.service.collectionproduct.CollectionProductService;
import com.alqsoft.service.member.MemberService;
import com.alqsoft.service.product.ProductService;
import com.alqsoft.utils.StatusCodeEnums;
import com.alqsoft.vo.CollectionProductVO;
import com.google.common.collect.Maps;
import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Xuejizheng
 * @date 2017-03-11 10:26
 */
@Service
@Transactional
public class CollectionProductServiceImpl implements CollectionProductService {
   
    private static Logger log = LoggerFactory.getLogger(CollectionProductServiceImpl.class);
    
    @Autowired
    private CollectionProductDao collectionProductDao;

    @Autowired
    private RpcCollectionProductService rpcCollectionProductService;
    @Autowired
    private ProductService  productService;
    @Autowired
    private MemberService  memberService;
    
    

    /**
     * 收藏、取消收藏
     * @param id
     * @param pid
     * @param type
     * @return
     */
    @Override
    public Result collect(Long id, Long pid, Integer type) {
        
    	Long hunterid=memberService.getHunteridByMemberid(id);
    	if(hunterid!=null){
    		return ResultUtils.returnError("只有商户能进行此操作，请登录商户");
    	}
    	if (Objects.isNull(id)|| Objects.isNull(pid) || Objects.isNull(type)){
            return ResultUtils.returnError(StatusCodeEnums.ERROR_PARAM.getMsg());
        }
        try {
            CollectionProductVO collectionProductVO = collectionProductDao.getCollectionProduct(id,pid);
            //收藏
            if (collectionProductVO==null && 0==type){
            	Product productdb =	productService.getProductById(pid);
            	if(productdb==null){
            		 return ResultUtils.returnError("不存在该商品");
            	}
            	Member memberdb = memberService.getMemberByHunterId(productdb.getHunter().getId());
            	if(id.longValue()==memberdb.getId().longValue()){
            		 return ResultUtils.returnError("不能收藏自己的商品");
            	}
                CollectionProduct collectionProduct = new CollectionProduct();
                Member member = new Member();
                member.setId(id);
                Product hunter= new Product();
                hunter.setId(pid);
                collectionProduct.setMember(member);
                collectionProduct.setType(type);
                collectionProduct.setProduct(hunter);
                rpcCollectionProductService.saveAndModify(collectionProduct);
                return ResultUtils.returnSuccess("收藏成功");
            }
            //取消收藏
            if (collectionProductVO != null){
                Integer collectionType =  collectionProductVO.getType();
                if (String.valueOf(type).equals(String.valueOf(collectionType))){
                    if ("1".equals(String.valueOf(collectionType))){
                        return ResultUtils.returnError("不能重复取消收藏");
                    }
                    if ("0".equals(String.valueOf(collectionType))){
                        return ResultUtils.returnError("不能重复收藏");
                    }
                }
                if("0".equals(String.valueOf(type))){
                	Product productdb =	productService.getProductById(pid);
                	if(productdb==null){
                		 return ResultUtils.returnError("不存在该商品");
                	}
                	Member memberdb = memberService.getMemberByHunterId(productdb.getHunter().getId());
                	if(id.longValue()==memberdb.getId().longValue()){
                		 return ResultUtils.returnError("不能收藏自己的商品");
                	}
                }
                CollectionProduct collectionProduct = new CollectionProduct();
                collectionProduct.setId(collectionProductVO.getId());
                Member member = new Member();
                member.setId(id);
                Product hunter= new Product();
                hunter.setId(pid);
                collectionProduct.setMember(member);
                collectionProduct.setType(type);
                collectionProduct.setProduct(hunter);
                rpcCollectionProductService.saveAndModify(collectionProduct);

                if ("1".equals(String.valueOf(type))){
                    return ResultUtils.returnSuccess("取消收藏");
                }
                if ("0".equals(String.valueOf(type))){
                    return ResultUtils.returnSuccess("收藏成功");
                }

            }
            return ResultUtils.returnError(StatusCodeEnums.ERROR_PARAM.getMsg());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
           // TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultUtils.returnError(StatusCodeEnums.ERROR.getMsg());
        }
    }

    /**
     * 用户收藏商品列表
     * @param id
     * @param page
     * @param rows
     * @return
     */
    @Override
    public Result list(Long id, int page, int rows) {
        if (Objects.isNull(id)){
           return ResultUtils.returnError(StatusCodeEnums.ERROR_PARAM.getMsg());
        }
        Map<String,Object> map = Maps.newHashMap();
        map.put("uid",id);
        map.put("page",(page-1)*rows);
        map.put("rows",rows);
        try {
            List<Map<String,Object>> lists=collectionProductDao.getCollectionProductList(map);
            return ResultUtils.returnSuccess(StatusCodeEnums.SUCCESS.getMsg(),lists);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return ResultUtils.returnError(StatusCodeEnums.ERROR.getMsg());
        }
    }
}
