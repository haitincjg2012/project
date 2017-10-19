package com.alqsoft.controller.mobile.after.shopcart;

import com.alqsoft.anno.Explosionproof;
import com.alqsoft.anno.MemberAnno;
import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.member.Member;
import com.alqsoft.service.shopcart.ShopCartService;
import com.alqsoft.utils.StatusCodeEnums;
import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 购物车控制器
 * @author Xuejizheng
 * @date 2017-02-28 14:26
 * @since 1.8
 * @see
 */
@RestController
@RequestMapping("mobile/after/shopcart")
public class ShopCartController {

    private static Logger log = LoggerFactory.getLogger(ShopCartController.class);

    @Autowired
    private ShopCartService shopCartService;

    /**
     * 获取购物车列表
     * @param member
     * @return
     */
    @RequestMapping(value = "cart-list",method = RequestMethod.POST)
    public  Result getShopCartList(@MemberAnno Member member){
       Map<String,Object> map = new HashMap<>();
       map.put("uid",member.getId());
       Result result= shopCartService.getShopCartList(map);
       return result;
    }

    /**
     * 获取购物车列表
     * @param member
     * @return
     */
    @RequestMapping(value = "cartList",method = RequestMethod.POST)
    public  Result getHunterShopCartList(@MemberAnno Member member,
                                         @RequestParam(value = "hid") Long hid){
        Map<String,Object> map = new HashMap<>();
        map.put("uid",member.getId());
        map.put("hid",hid);
        Result result= shopCartService.getHunterShopCartList(map);
        return result;
    }

    /**
     * 修改
     * @param member  用户
     * @param cid  购物车ID
     * @param num  商品数量
     * @return
     */
    @Explosionproof
    @RequestMapping(value = "update",method = RequestMethod.POST)
    public Result saveAndModify(@MemberAnno Member member,@RequestParam("cid")Long cid,
                                @RequestParam(value = "num",required = false) Long num,
                                @RequestParam("type")Long type){

        log.info(" shopcart save: uid:{},cid:{},num:{}",member.getId(),cid,num);
        //修改
        if ( type==0){
            return shopCartService.update(member.getId(),cid,num);
        }
        //删除
        if ( type == 1) {
            return  shopCartService.delete(member.getId(),cid);
        }
        return ResultUtils.returnError(StatusCodeEnums.ERROR_PARAM.getMsg());
    }

    /**
     * 删除
     * @param member 会员ID
     * @param cid  购物车ID
     * @return
     */
    @Explosionproof
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    public Result delete(@MemberAnno Member member,@RequestParam("cid")Long cid){

       log.info(" shopcart delete: uid:{},cid:{}",member.getId(),cid);

       return  shopCartService.delete(member.getId(),cid);
    }

    /**
     * 添加入购物车
     * @param member  用户ID
     * @param spid 商品规格ID
     * @param num  购买数量
     * @param stid 商品类型ID
     * @return
     */
    //@Explosionproof
    @RequestMapping(value = "add",method =RequestMethod.POST)
    public Result add(@MemberAnno Member member,@RequestParam("spid")Long spid
            ,@RequestParam("num")Long num,@RequestParam("stid")Long stid){
        log.info("shopcart add param:  uid :{} , spid :{} , num:{} , stid :{} ",member.getId(),spid,num,stid);
        return shopCartService.add(member,spid,num,stid);
    }

    /**
     * 修改
     * @param member  用户
     * @param cids  购物车ID
     * @param nums  商品数量
     * @return
     */
    @Explosionproof
    @RequestMapping(value = "batchUpdate",method = RequestMethod.POST)
    public Result batchUpdate(@MemberAnno Member member,@RequestParam("cids")String cids,
                                @RequestParam(value = "nums") String nums){

        log.info("  batchUpdate: uid:{},cid:{},num:{}",member.getId(),cids,nums);
        return shopCartService.batchUpdate(member.getId(),cids,nums);
    }

    /**
     * 清空购物车
     * @param member  用户
     * @return
     */
    //@Explosionproof
    @RequestMapping(value = "batchDelete",method = RequestMethod.POST)
    public Result batchUpdate(@MemberAnno Member member,@RequestParam("hid")Long hid){
        return shopCartService.batchDelete(member,hid);
    }

    /**
     * 批量添加入购物车
     * @param member  用户ID
     * @param spid 商品规格ID
     * @param num  购买数量
     * @param stid 商品类型ID
     * @return
     */
    @Explosionproof
    @RequestMapping(value = "batchAdd",method =RequestMethod.POST)
    public Result batchAdd(@MemberAnno Member member,@RequestParam("spid")String spid
            ,@RequestParam("num")String num,@RequestParam("stid")String stid){
        log.info(" batchAdd param:  uid :{} , spid :{} , num:{} , stid :{} ",member.getId(),spid,num,stid);
        return shopCartService.batchAdd(member,spid,num,stid);
    }

    /**
     * 获取当前用户的购物车数量
     * @param member
     * @return
     */
    @RequestMapping(value = "count",method = RequestMethod.POST)
    public Result getShopcartCount(@MemberAnno Member member){
         log.info("member:{}",member);
         return shopCartService.getShopCartCount(member.getId());
    }

}
