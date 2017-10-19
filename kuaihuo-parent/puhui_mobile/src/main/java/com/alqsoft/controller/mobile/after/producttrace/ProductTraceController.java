package com.alqsoft.controller.mobile.after.producttrace;

import com.alqsoft.anno.MemberAnno;
import com.alqsoft.entity.member.Member;
import com.alqsoft.service.producttrace.ProductTraceService;
import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 订单跟踪信息
 * @author Xuejizheng
 * @date 2017-03-07 13:52
 */
@RestController
@RequestMapping("mobile/after/producttrace")
public class ProductTraceController {

    @Autowired
    private ProductTraceService productTraceService;
    /**
     * 添加跟踪信息
     * @param member  会员
     * @param content 追踪内容
     * @param orderNo  子订单编号
     * @param firstFile 第一个附件
     * @param secondFile 第二个附件
     * @return
     */
    @RequestMapping(value = "add",method = RequestMethod.POST)
    public Result add (@MemberAnno Member member,
                       @RequestParam("orderNo") String orderNo,
                       @RequestParam("content")String content,
                       @RequestParam(value = "furl",required = false)MultipartFile firstFile,
                       @RequestParam(value = "surl",required = false)MultipartFile secondFile,
                       @RequestParam(value = "turl",required = false)MultipartFile thirdFile) {

          Result result = productTraceService.add(member.getId(),orderNo,content,firstFile,secondFile,thirdFile);

          return result;
    }

    /**
     * 添加跟踪信息
     * @param member  会员
     * @param content 追踪内容
     * @param orderNo  子订单编号
     * @param ids 附件ID逗號拼接的字符串
     * @return
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    public Result addProductTrace (@MemberAnno Member member,
                       @RequestParam("orderNo") String orderNo,
                       @RequestParam("content")String content,
                        @RequestParam(value="aids",required = false)String ids) {

        Result result = productTraceService.addProductTrace(member.getId(),orderNo,content,ids);
        return result;
    }



}
