package com.alqsoft.controller.mobile.after.producttype;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alqsoft.anno.Explosionproof;
import com.alqsoft.anno.MemberAnno;
import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.member.Member;
import com.alqsoft.service.producttype.ProductTypeService;

/**
 * 商品分类控制器
 * @author Xuejizheng
 * @date 2017-03-02 15:52
 * @since 1.8
 */
@RestController
@RequestMapping("mobile/after/producttype")
public class ProductTypeController {

    private static Logger log = LoggerFactory.getLogger(ProductTypeController.class);

    @Autowired
    private ProductTypeService productTypeService;

    /***
     * 获取商品分类(一级分类和二级分类)
     * @return
     * @author wudi
     */
    @RequestMapping(value = "first",method = RequestMethod.POST)
    public Result getProductTypeFirst(@MemberAnno Member member,@RequestParam(value="currentPage",defaultValue="1") Integer currentPage,@RequestParam(value="numPage",defaultValue="10") Integer numPage){
    	Long id=null;
    	if(member!=null){
    	    id=member.getHunter().getId();
    	}
    	
    	return productTypeService.getProductTypeFirst(id,currentPage,numPage);
    }
    /***
     * 创建商品一二级分类
     * @param member
     * @param firstType  一级类名
     * @param secondeType 二级类名
     * @param 默认首页为1
     * @param 每页含有的数据为10
     * @return result
     * @author wudi
     */
    @Explosionproof//接口防爆注解
    @RequestMapping(value = "made",method = RequestMethod.POST)
    public Result madeProductType(@MemberAnno Member member,@RequestParam(value="firstType") 
    String firstType,@RequestParam(value="secondeType" ,defaultValue="-1") String secondeType){
    	Long id=null;
    	if(member.getHunter()==null){
    	   return ResultUtils.returnError("你已经越权");
    	}
    	return productTypeService.madeProductType(member.getHunter().getId(), firstType, secondeType);
   }
    /***
     * 编辑分类,修改类级的名称,增加
     * @param pId
     * @return
     * @author wudi
     */
    @Explosionproof//接口防爆注解
    @RequestMapping(value = "compile",method = RequestMethod.POST)
    public Result compileProductType(@MemberAnno Member member,String listMap){
    	 Hunter hunter = member.getHunter();
         if (hunter==null){
             return ResultUtils.returnError("批发商信息不存在");
          }
    	 Long hId=hunter.getId();
    	
    	//return productTypeService.compileProductType(hId,listMap);
    	 return productTypeService.compileAndMadeProductType(hId,listMap);
    }
    /***
     * 删除类级 
     * @param cId 类级的id
     * @return
     */
    @Explosionproof//接口防爆注解
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    public Result deleteProdcutType(@MemberAnno Member member,Long cId){
    	Hunter hunter = member.getHunter();
        if (hunter==null){
            return ResultUtils.returnError("你已经越权");
         }
    	
    	return productTypeService.deleteProdcutType(cId,hunter.getId());
    }
    /***
     * @param sortNums页面穿过来的排序参数使用逗号分隔
     * @return result
     */
    @Explosionproof//接口防爆注解
    @RequestMapping(value = "sort",method = RequestMethod.POST)
    public Result getSortProductType(String cIds ,String sortNums,@MemberAnno Member member){
    	Hunter hunter = member.getHunter();
        if (hunter==null){
            return ResultUtils.returnError("批发商信息不存在");
         }
    	return productTypeService.getSortProductType(cIds,sortNums,hunter.getId());
    }
    /***
     * 销售商品的分类,商品的规格
     * @param member
     * @param pId
     * @return
     */
    @RequestMapping(value = "saleproducttype",method = RequestMethod.POST)
    public Result saleProductType(@MemberAnno Member member,Long pId){
    	
    	return productTypeService.saleProductType(member,pId);
    }
    
    /**
     * 商品管理下的添加分类，获取一二级分类
     * @return
     */
    @RequestMapping(value = "managerproducttype",method = RequestMethod.POST)
    @ResponseBody
    public Result getAllProductType(@MemberAnno Member member){
    	if(member.getHunter()==null||member.getHunter().getId()==null){
			return ResultUtils.returnError("您不是批发商身份");
		}
    	return productTypeService.getAllProductType(member.getHunter().getId());
    }
    
    /***
     * 
     * 通过一级分类的id获取二级分类
     * cId 二级分类
     * 
     */
    @RequestMapping(value = "seconde",method = RequestMethod.POST)
    public Result getSecondeClassify(@MemberAnno Member member,Long cId){
    	if(member.getHunter()==null||member.getHunter().getId()==null){
			return ResultUtils.returnError("您不是批发商身份");
		}
    	return productTypeService.getSecondProductTypeList(cId,member.getHunter().getId());
    }
    /***
     * 获取一个一级和一级对应的二级分类
     * cId 一级类名
     * hId 二级类名
     * 
     */
    @RequestMapping(value = "firstandseconde",method = RequestMethod.POST)
    public  Result getFirstAndSecondeProductType(@MemberAnno Member member,Long cId){
    	if(member.getHunter()==null||member.getHunter().getId()==null){
			return ResultUtils.returnError("您不是批发商身份");
		}
    	return productTypeService.getFirstAndSecondeProductType(cId,member.getHunter().getId());
    }
    

}
    


