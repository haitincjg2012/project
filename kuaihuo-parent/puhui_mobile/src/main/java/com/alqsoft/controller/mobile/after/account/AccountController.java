package com.alqsoft.controller.mobile.after.account;

import com.alqsoft.anno.MemberAnno;
import com.alqsoft.entity.member.Member;
import com.alqsoft.service.order.OrderService;
import org.alqframework.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 结算中心
 * @author xwolf
 * @since 1.8
 **/
@RestController
@RequestMapping("account")
public class AccountController {

    private static Logger log = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private OrderService orderService;

    /**
     * 生成订单
     * @param json
     * @param member
     * @return
     */
    @RequestMapping(value = "add",method = RequestMethod.POST)
    public Result createOrder(@RequestParam("json")String json, @MemberAnno Member member){
        log.info("member:{},json:{}",member,json);
        return orderService.createOrder(member,json);
    }

    /**
     * 获取订单列表
     * @param time
     * @param member
     * @return
     */
    @RequestMapping(value = "list",method = RequestMethod.POST)
    public Result list(@RequestParam(value = "time",required = false,defaultValue ="") String time, @MemberAnno Member member,
                       @RequestParam(value = "page",defaultValue = "1",required = false)int page,
                       @RequestParam(value = "size",defaultValue = "10",required = false)int size){
        log.info("member:{},time:{}",member,time);
        return orderService.getAccountOrderList(member,time,page,size);
    }

    /**
     * 修改期望时间
     * @param time
     * @param member
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    public Result update(String time,String orderNo, @MemberAnno Member member){

        return orderService.updateOrder(time,orderNo,member);
    }

    /**
     * 删除
     * @param member
     * @return
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    public Result delete(Long id, @MemberAnno Member member){

        return orderService.deleteOrder(id,member);
    }

    /**
     * 修改数量
     * @param member
     * @return
     */
    @RequestMapping(value = "updateNum",method = RequestMethod.POST)
    public Result updateNum(Long id,Long num, @MemberAnno Member member){

        return orderService.udpateOrderNum(id,num,member);
    }

    /**
     * 再来一单(返回修改) 数据回显
     * @param member
     * @return
     */
    @RequestMapping(value = "backShopCart",method = RequestMethod.POST)
    public Result backShopCart(String orderNo, @MemberAnno Member member){

        return orderService.backShopCart(orderNo,member);
    }

    /**
     * 返回修改
     * @param json
     * @param type
     * @param member
     * @return
     */
    @RequestMapping(value = "recur",method = RequestMethod.POST)
    public Result recur(@RequestParam(value = "type",defaultValue = "1") String type,
                        @RequestParam("json")String json, @MemberAnno Member member){

        return orderService.recurOrder(type,json,member);
    }


}
