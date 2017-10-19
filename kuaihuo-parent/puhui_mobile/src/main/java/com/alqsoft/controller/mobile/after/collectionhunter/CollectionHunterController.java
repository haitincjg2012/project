package com.alqsoft.controller.mobile.after.collectionhunter;

import com.alqsoft.anno.MemberAnno;
import com.alqsoft.entity.member.Member;
import com.alqsoft.service.collectionhunter.CollectionHunterService;
import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 批发商收藏
 * @author Xuejizheng
 * @date 2017-03-11 10:04
 */
@RestController
@RequestMapping("mobile/after/collectionhunter")
public class CollectionHunterController  {

     @Autowired
     private CollectionHunterService collectionHunterService;

     /**
      * 收藏、取消收藏批发商
      * @param member
      * @param type
      * @param hid
      * @return
      */
     @RequestMapping("collect")
     public Result  collection(@MemberAnno Member member, @RequestParam("type")Integer type,
                               @RequestParam("hunterId")Long hid){

        return  collectionHunterService.collect(member.getId(),hid,type);
     }

     /**
      * 收藏批发商列表
      * @param member
      * @param page
      * @param rows
      * @return
      */
     @RequestMapping(value = "list",method = RequestMethod.POST)
     public Result list(@MemberAnno Member member,
                        @RequestParam(value = "page",defaultValue = "1",required = false)int page,
                        @RequestParam(value = "rows",defaultValue = "10",required = false)int rows){

          return collectionHunterService.list(member.getId(),page,rows);
     }
}
