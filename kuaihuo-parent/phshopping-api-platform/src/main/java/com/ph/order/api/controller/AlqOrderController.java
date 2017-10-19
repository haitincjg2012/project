package com.ph.order.api.controller;

import cm.ph.shopping.facade.order.vo.AlqOrderVO;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.ph.base.BaseController;
import com.ph.order.api.service.SpmService;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.permission.vo.SessionUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cm.ph.shopping.facade.order.service.AlqOrderService;

/**
 * 批发订单controller
 *
 * @author 郑朋
 * @create 2017/5/25
 **/
@Controller
@RequestMapping("web/order/alq/")
public class AlqOrderController extends BaseController {

    @Reference(version = "1.0.0")
    AlqOrderService alqOrderService;

    @Autowired
    SpmService spmService;


    /**
     * @methodname toAlqListPage 的描述：进入批发订单列表
     * @param model
     * @return
     * @author lzh
     * @create 2017/9/25
     */
    @RequestMapping(value = "toAlqListPage", method = {RequestMethod.GET})
    public String listPage(Model model) {
        return "order/alq/alqlist";
    }

    /** 进入批发代理订单
     * gaoge
     * @param model
     * @return
     */
    @RequestMapping(value = "toAlqPfListPage", method = {RequestMethod.GET})
    public String PflistPage(Model model) {
        return "order/alq/alqlist_pf";
    }

    /*====================================================================================================================================*/



    /**
     * @methodname alqList 的描述：批发订单列表
     * @param
     * @return com.ph.shopping.common.util.result.Result
     * @author lzh
     * @create 2017/9/25
     */
    @RequestMapping("/alqList")
    @ResponseBody
    public Result getAlqList(PageBean pageBean, AlqOrderVO alqOrderVO) {
        logger.info("================后台查询批发订单开始================");
        Result result=alqOrderService.getAlqList(pageBean,alqOrderVO);
        logger.info("================后台查询批发订单结束================"+ JSON.toJSON(result));
        return  result;
    }

    /**
     * @methodname alqList 的描述：批发代理订单列表
     * @param
     * @return com.ph.shopping.common.util.result.Result
     * @author 高歌
     * @create 2017/9/25
     */
    @RequestMapping("/alqListPf")
    @ResponseBody
    public Result getAlqPfList(PageBean pageBean, AlqOrderVO alqOrderVO) {
        SessionUserVO userBean = getLoginUser();
        logger.info("================后台查询批发订单开始================");
        alqOrderVO.setArgentId(userBean.getId());
        Result result=alqOrderService.getAlqPfList(pageBean,alqOrderVO);
        logger.info("================后台查询批发订单结束================"+ JSON.toJSON(result));
        return  result;
    }

    /**
     * @author: 张霞
     * @description：批发订单详情
     * @createDate: 17:19 2017/6/17
     * @param model
     * @param
     * @return: java.lang.String
     * @version: 2.1
     */
    @RequestMapping("/detail")
    public String detail(Model model,@RequestParam Long subOrderId){
        Result result=null;
        try {
            result = alqOrderService.getAlqOrderDetailVO(subOrderId);
            logger.info( "批发订单详情结果：{}",JSON.toJSON( result ) );
        }catch (Exception e){
            logger.info( "批发订单获取详情异常，result={}",JSON.toJSON( result ) );
            result = ResultUtil.getResult( RespCode.Code.FAIL );
        }
        model.addAttribute( "alqOrder",result.getData());
        return "order/alq/alqdetail";
    }
}
