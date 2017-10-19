package com.alqsoft.controller.order;

import com.alqsoft.service.order.OrderService;
import org.alqframework.easyui.EasyuiResult;
import org.alqframework.result.Result;
import org.alqframework.webmvc.servlet.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 订单列表
 * @author Xuejizheng
 * @date 2017-03-13 16:17
 */
@Controller(value = "serverOrderController")
@RequestMapping("order")
public class OrderController {

    private static Logger log = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private OrderService orderService;

    /**
     * 到订单结算列表页面
     * @return
     */
    @RequestMapping(value = "toAccountList",method = {RequestMethod.GET,RequestMethod.POST})
    public String toOrderAccountList(){
        return "order/order-account";
    }

    /**
     * 到订单列表页面
     * @return
     */
    @RequestMapping(value = "toList",method = {RequestMethod.GET,RequestMethod.POST})
    public String toOrderList(){
        return "order/order-list";
    }

    /**
     * 订单列表
     * @return
     */
    @RequestMapping(value = "order-list",method =RequestMethod.POST)
    public @ResponseBody EasyuiResult getOrderList(HttpServletRequest request,
                                                                  @RequestParam(value="page",defaultValue = "1",required = false)
                                                                int page,
                                                                  @RequestParam(value = "rows",defaultValue = "10",required = false)int rows){
        Map<String, Object> map = ServletUtils.getParametersStartingWith(
                request, "");
        log.info(" order-list : {} ", map);
        return orderService.getOrderList(map,page,rows);
    }


    /**
     * 结算列表
     * @return
     */
    @RequestMapping(value = "account-list",method =RequestMethod.POST)
    public @ResponseBody EasyuiResult getAccountList(HttpServletRequest request,
                                                   @RequestParam(value="page",defaultValue = "1",required = false)
                                                           int page,
                                                   @RequestParam(value = "rows",defaultValue = "10",required = false)int rows){
        Map<String, Object> map = ServletUtils.getParametersStartingWith(
                request, "");
        log.info(" order-list : {} ", map);
        return orderService.getAccountList(map,page,rows);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping(value = "deleteBatch",method = RequestMethod.POST)
    public @ResponseBody Result deleteBatch(@RequestParam("ids")String ids){
        log.info("删除的ID 为 ：{} ",ids);
        Result result=orderService.deleteBatch(ids);
        return result;
    }

    /**
     * 获取订单详情
     * @param id
     * @return
     */
    @RequestMapping(value = "detail",method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> getOrderDetail(@RequestParam("id")Long id){
        Map<String,Object> map = orderService.detail(id);
        return map;
    }
}
