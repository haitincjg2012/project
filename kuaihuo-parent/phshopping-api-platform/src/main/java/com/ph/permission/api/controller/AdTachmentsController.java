package com.ph.permission.api.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.ph.shopping.common.core.constant.PageConstant;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.member.dto.AdAtachmentDTO;
import com.ph.shopping.facade.member.service.IAdAttachmentService;
import com.ph.shopping.facade.permission.vo.AdAttachmentVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**轮播图展示页面
 * Created by wudi on 2017/8/31.
 */
@Controller
@RequestMapping("web/attachments")
public class AdTachmentsController {
    //日志
    private static final Logger log = LoggerFactory.getLogger(AdTachmentsController.class);

    //会员接口
    @Reference(version = "1.0.0")
    private IAdAttachmentService iAdAttachmentService;

    /**
     * 展示页面数据
     * @return
     */
    @RequestMapping("/attachmenturl")
    public  String getAttachmentUrl(){
        return "addAtachments/addattachments";
    }

    /**
     * 跳转到页面上传的页面
     * @return
     */
    @RequestMapping("/uploadurl")
     public String uploadImge(){
         return "addAtachments/addtachmentadd";
     }
    /**
     * 获取轮播博图数据列表
     * @param dto
     * @return
     */
    @RequestMapping("/getdataAtattachmentlist")
    @ResponseBody
    public Result getDataAtachmentList(AdAtachmentDTO dto){
        log.info("加载会员列表参数 MemberPageDto = {} ", JSON.toJSONString(dto));
        if (dto.getPageNum() == null) {
            dto.setPageNum(PageConstant.pageNum);
        }
        if (dto.getPageSize() == null) {
            dto.setPageSize(PageConstant.pageSize);
        }
        return  iAdAttachmentService.getDataAtachmentList(dto);
    }

    /**
     * 保存数据
     * @param ato
     * @return
     */
    @RequestMapping(value="/addAdAttachment",method = RequestMethod.POST)
    @ResponseBody
    public Result addAdAttachment(@ModelAttribute AdAttachmentVo ato){
        if (ato ==null){
            return  new Result(false, "1", "传递参数");
        }
        if (ato.getAddress()==null){
            return new Result(false, "1", "请先上传图片");
        }
        if(ato.getName() == null){
            return new Result(false, "1", "请填写图片name");
        }
        if (ato.getType()==null){
            return new Result(false, "1", "请填写轮播图的类型");
        }

        if (ato.getDetail_content()==null){
            return new Result(false, "1", "请先上传详情图片");
        }


        return iAdAttachmentService.addAdAttachment(ato.getAddress(),ato.getName(),ato.getType(),ato.getDetail_content());
    }
    /**
     * @描述：删除
     *
     * @param: role
     *
     * @return:
     *
     * @作者： Mr.Shu
     *
     * @创建时间：2017/5/17 17:03
     */
    @RequestMapping(value="/deleteAttachment",method=RequestMethod.POST)
    @ResponseBody
    public Result deleteIndustry(@RequestParam("id") Long id){
        if (id==null){
            return new Result(false, "1", "选中删除的数据");
        }
        return iAdAttachmentService.deleteAttachment(id);
    }

    /**
     * 编辑行业分类
     * @param
     * @param
     * @return
     */
    @RequestMapping(value="/editAttachment",method=RequestMethod.POST)
    @ResponseBody
    public Result editIndustry(@ModelAttribute AdAttachmentVo ato){
        if (ato.getId()==null){
            return new Result(false, "1", "选中修改的数据");
        }
        if (ato.getName().trim().equals("")){
            return new Result(false,"1","请输入修改的名字");
        }

        return iAdAttachmentService.editAttachment(ato.getId(),ato.getName(),ato.getDetail_content());
    }
}
