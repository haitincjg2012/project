package com.alqsoft.controller.mobile.view.huntersalelist;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alqsoft.anno.MemberAnno;
import com.alqsoft.entity.member.Member;
import com.alqsoft.service.huntersalelist.HunterSaleListService;

/**
 * 
 * @Description: TODO
 * @author Wudi
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年3月1日 上午11:32:01
 * 获取批发商销售展品列表,按照销售，跟新，价格,
 * id是选择的销售0，更新1，价格2
 * currentPage 当前页，默认为1
 * numPage 每页数据，默认为10
 * hId 批发商Id
 * 销售和时间只有降序，价格有升序和降序
 */
    @RequestMapping("mobile/view/hunter")
    @Controller
    public class HunterSaleListController {
	
    	private static Logger logger=LoggerFactory.getLogger(HunterSaleListController.class);
    	
    @Autowired	
	private HunterSaleListService huntersaleListService;
    @ResponseBody	
    @RequestMapping(value="list",method=RequestMethod.POST)	
    public Result findAllHunterProductSaleList(@RequestParam(value="id",defaultValue="0") Integer id ,@RequestParam(value="flat",defaultValue="false") boolean flat ,@RequestParam(value="currentPage",defaultValue="1") Integer currentPage,@RequestParam(value="numPage",defaultValue="10") Integer numPage,Long hId){
    
    	
    	
    	Result reuslt=huntersaleListService.findAllProductSale(id,flat,hId,currentPage,numPage);
    	
	    return reuslt;
    }

  
  }
