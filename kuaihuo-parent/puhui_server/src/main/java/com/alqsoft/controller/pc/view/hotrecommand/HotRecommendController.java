package com.alqsoft.controller.pc.view.hotrecommand;

import com.alibaba.fastjson.JSON;
import com.alqsoft.entity.hotrecommend.HotRecommend;
import com.alqsoft.service.hotrecommend.HotRecommendService;
import com.alqsoft.service.product.ProductService;
import org.alqframework.easyui.EasyuiResult;
import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.alqframework.webmvc.servlet.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * 热门推荐
 * @author Xuejizheng
 * @date 2017-03-19 11:00
 */

@Controller
@RequestMapping("pc/view/hot")
public class HotRecommendController {

    @Autowired
    private HotRecommendService hotRecommendService;

    @Autowired
    private ProductService productService;

    /**
     * 跳转列表页面
     * @return
     */
    @RequestMapping("toList")
    public String toList(){
        return "hotrecommend/hot-list";
    }

    /**
     * 推荐列表
     * @param request
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "hotList",method = RequestMethod.POST)
    public @ResponseBody EasyuiResult getHotList(HttpServletRequest request, Integer page, Integer rows){
        Map<String,Object> map= ServletUtils.getParametersStartingWith(request,"");
        return hotRecommendService.getHotPage(map,page,rows);
    }

    /**
     * 获取推荐详情
     * @param id
     * @return
     */
    @RequestMapping(value = "detail",method = RequestMethod.POST)
    public @ResponseBody
    HotRecommend getHotRecommend(@RequestParam("id") Long id){
        return hotRecommendService.getHotById(id);
    }

    /**
     * 物理删除
     * @param id
     * @return
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    public @ResponseBody Result delete(@RequestParam("id")Long id){
        return hotRecommendService.deleteById(id);
    }

    /**
     * 获取商品列表
     * @return
     */
    @RequestMapping(value = "getProductList",method ={RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody
    String getProductList(){
        return JSON.toJSONString(productService.getAllProductList());
    }

    /**
     * 根据推荐ID获取商品ID
     * @param rid 热门推荐ID
     * @return
     */
    @RequestMapping(value = "getProductIds",method = RequestMethod.POST)
    public @ResponseBody String getProductIds(@RequestParam("rid")Long rid){
        return hotRecommendService.getProductIdsByRecommend(rid);
    }
    /**
     * 保存推荐商品信息
     * @return
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    public @ResponseBody Result save(@RequestParam("pids")String pids,
                                     @RequestParam("rid")Long rid,
                                     @RequestParam("name")String name){
        return hotRecommendService.save(pids,rid,name);
    }

    /**
     * 动态获取商品列表
     * @param name
     * @return
     */
    @RequestMapping(value = "getProductOptions",method = RequestMethod.POST)
    public @ResponseBody String getProductOptions(@RequestParam("name")String name){

        return hotRecommendService.getProductOptions(name);
    }

    /**
     * 动态获取商品列表
     * @param hid
     * @return
     */
    @RequestMapping(value = "getAllProductOptionByHotId",method = RequestMethod.POST)
    public @ResponseBody String getAllProductOptions(@RequestParam("hid")String hid){

        return hotRecommendService.getAllProductOptionByHotId(hid);
    }


    /**
     * 添加热门推荐信息
     * @param recommend
     * @return
     */
    @RequestMapping(value = "insertHot",method = RequestMethod.POST)
    public @ResponseBody Result insertHotRecomment(HotRecommend recommend){
        if (Objects.isNull(recommend)){
           return ResultUtils.returnError("参数不能为空");
        }

        recommend.setIsDel(0);
        try {
            hotRecommendService.saveAndModify(recommend);
            return ResultUtils.returnError("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.returnError("添加失败");
        }
    }


}
