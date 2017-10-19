package com.alqsoft.controller.mobile.after.collectionproduct;

import com.alqsoft.anno.MemberAnno;
import com.alqsoft.entity.member.Member;
import com.alqsoft.service.collectionproduct.CollectionProductService;
import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品收藏
 * @author Xuejizheng
 * @date 2017-03-11 10:07
 */
@RestController
@RequestMapping("mobile/after/collectionproduct")
public class CollectionProductController {

    @Autowired
    private CollectionProductService collectionProductService;

    /**
     * 收藏/取消收藏
     * @param member 会员
     * @param type 操作类型
     * @param hid 商品ID
     * @return
     */
    @RequestMapping(value = "collect",method = RequestMethod.POST)
    public Result collection(@MemberAnno Member member, @RequestParam("type")Integer type,
                             @RequestParam("productId")Long hid){

        return  collectionProductService.collect(member.getId(),hid,type);
    }

    /**
     * 收藏商品列表
     * @param member
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "list",method = RequestMethod.POST)
    public Result list(@MemberAnno Member member,
                       @RequestParam(value = "page",defaultValue = "1",required = false)int page,
                      @RequestParam(value = "rows",defaultValue = "10",required = false)int rows){

        return collectionProductService.list(member.getId(),page,rows);
    }
}
