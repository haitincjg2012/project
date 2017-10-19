package com.alqsoft.dao.producttype;

import java.util.List;
import java.util.Map;

import org.alqframework.orm.mybatis.MyBatisRepository;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import com.alqsoft.entity.producttype.ProductType;
import com.alqsoft.vo.ProcudtTypeVO;

/**
 * @author Xuejizheng
 * @date 2017-03-02 16:17
 * @see
 * @since 1.8
 */
@MyBatisRepository
public interface ProductTypeDao {

    List<ProcudtTypeVO> getProductTypeList(Map<String, Object> paramMap);
    
    ProductType  getProductTypeById(Long id);
    
    int selectProductTypeSon(Long id);
    /***
     * 获取一级分类，一级总的商品数量
     * @param id
     * @return
     */
    List<Map> getProductTypeFirst(Long id);
    
   List<String> getIp(Long id);
    /***
     * 根据类级名获取id
     * @param Content
     * @return
     */
    List<Long> getFirstIdByContent(String Content);
    /***
     * 获取二级分类，二级总的商品数量
     * @param id
     * @return
     */
    
    List<Map> getProductTypeSeconde(Long id);
    /***
     * 获取所有的种类
     */
    public Long getCount();
    /***
     * 通过类级的id获取sortNum
     * @param id
     * @return
     */
    public Integer getSortNumById(Long id); 
    /***
     * 大于当前sortNum的所有sortNum
     * @param sortNum
     * @return
     */
    public List<Map> getSortNumMax(Integer sortNum);
    /***
     * 小于所有sortNum的所有sortNum
     * @param sortNum
     * @return
     */
    public List<Map> getSortNumMin(Integer sortNum);
    /***
     * 获取分类
     */
    public List<Map> getAllClass(@Param("id")Long id,@Param("currentPage")Integer currentPage,@Param("numPage")Integer numPage);

    
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
     * 获取销售商品的中种类 ，规格
     */
    public List<Map> saleProductType(Long pId);
    /***
     * 销售商品的分类
     * @param pId 商品Id
     * @return
     */
    public List<Map> saleProductStandard(Long pId);
    /***
     * hId批发商Id,parentId一级类Id,二级类id
     * 获取ProductType
     * @return
     */
    public ProductType getRightOrError(@Param("hId") Long hId,@Param("parentId") Long parentId,@Param("Id")Long Id );
    /***
     *  id批发商id，一级类名firstType
     *  获取ProductType
     */
    public List<ProductType> findRightOrError(@Param("id") Long id,@Param("firstType") String firstType,@Param("pId") Long pId);
    /**
     * 通过cId类id，hId批发商id,sortNum是排序的标识getRightOrErrorAll
     */
    public ProductType getRightOrErrorAll(@Param("cId")Long cId,@Param("sortNum")String sortNum ,@Param("hId")Long hId);
    /***
     * cId类级的id
     * 通过类级的id获取类级的数据
     */
    public ProductType getFirstProductById(@Param("cId")Long cId,@Param("hId")Long hId);
    /***
     * 获取二级类名重复的个数
     * @param idFirst 第一个类名的id
     * @param hId 批发商的id
     * @param name 二级类名
     * @return
     */
    public int getSecondTypeName(@Param("Ma")Long idFirst,@Param("hId")Long hId,@Param("name")String name);

    List<ProcudtTypeVO> getProductTypeByHunterId(Long hunterId);
}
