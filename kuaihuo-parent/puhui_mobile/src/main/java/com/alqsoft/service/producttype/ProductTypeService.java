package com.alqsoft.service.producttype;

import java.util.List;
import java.util.Map;

import org.alqframework.result.Result;

import com.alqsoft.entity.member.Member;
import com.alqsoft.entity.producttype.ProductType;

/**
 * @author Xuejizheng
 * @date 2017-03-02 16:02
 * @since 1.8
 */
public interface ProductTypeService {

    Result getProductTypeList(Long hid,Long pid,Long type);
    
    /**
     * 根据id查询分类
     * @param id
     * @return
     */
    ProductType  getProductTypeById(Long id);
    
    /**
     * 查询一级分类下二级分类的数量
     * @param id
     * @return
     */
    int selectProductTypeSon(Long id);
    /**
     * 查询一级分类以及分类的数量
     * @param 当前页 currentPage
     * @param 当前页的数据  numPage
     */
    public Result getProductTypeFirst(Long id,Integer currentPage,Integer numPage);
    /***
     * 创建一级和二级分类
     * @param id
     * @param firstType 一级类名
     * @param secondeType 二级类名
     * 
     * @return result
     */
    public Result madeProductType(Long id,String firstType,String secondeType);
    /***
     * 编辑商品分类
     * @param pId
     * @return
     */
    public Result compileProductType(Long pId,String listMap);
   
    /***
     *删除类级别
     * @param cId  类级的id
     * @param hId 批发商id
     * @return
     */
    public Result deleteProdcutType(Long cId,Long hId);
    /***
     * 
     * @param pId 类级的id
     * @param flat 上移是true，下移是false
     * @return
     */
    
    public Result getSortProductType(String cIds,String sortNums,Long hId);

    
    /**
     * 商品管理下的添加分类，获取一二级分类
     * @param hunterid
     * @return
     */
    public Result getAllProductType(Long hunterid);
    
    
    /**
     * 获取该批发商商品分类的一级分类
     * @param hunterid
     * @return
     */
    public List<Map<String,Object>> getFirstProductType(Long hunterid);
    
    /**
     * 获取该批发商商品分类的二级分类
     * @param hunterid
     * @return
     */
    public List<Map<String,Object>> getSecondProductType(Long firstid);

    /***
     * 销售商品的分类
     * @param member
     * @param pId
     * @return
     */
    public Result saleProductType(Member member,Long pId);
    /***
     * 销售商品的规格
     * @param member
     * @param pId
     * @return
     */
    public Result saleProductStandard(Member member,Long pId);
    /***
     * 通过一级id获取对应的二级分类。
     * @param cId
     * @param hId
     * @return
     */
    public Result getSecondProductTypeList(Long cId,Long hId);
    /***
     * 获取一级一级一级对应的二级的类
     * @param cId
     * @param hId
     * @return
     */
    public Result getFirstAndSecondeProductType(Long cId,Long hId);
    /***
     * 编辑一二级或者添加二级
     * @return
     */
    public Result compileAndMadeProductType(Long hId,String listMap);

}
