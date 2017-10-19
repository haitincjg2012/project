package com.ph.permission.api.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.ph.shopping.common.core.constant.PageConstant;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.member.dto.IndustrysDTO;
import com.ph.shopping.facade.member.service.IIndustryService;
import com.ph.shopping.facade.permission.vo.AdAttachmentVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**批发
 * 行业分类的列表
 * Created by wudi on 2017/9/1.
 */
@Controller
@RequestMapping(value = "web/industry")
public class IndustryController {

    //日志
    private static final Logger log = LoggerFactory.getLogger(IndustryController.class);

    @Reference(version = "1.0.0")
    private IIndustryService iIndustryService;

    /**
     * 跳转页面
     * @return
     */
    @RequestMapping("/inustryurl")
    public String getIndustryUrl(){
        return  "industrys/industryslist";
    }

    /**
     * 跳转到编辑页面
     * @return
     */
    @RequestMapping("/editindustryurl")
    public String getEditIndustryUrl(){
        return "industrys/industrysAddOrUpdate";
    }

    /**
     * 行业分类展示数据列表
     * @param dto
     * @return
     */
    @RequestMapping("/industrydatalist")
    @ResponseBody
   public Result getIndustryDataList(@ModelAttribute PageBean page,IndustrysDTO dto){
       log.info("加载会员列表参数 MemberPageDto = {} ", JSON.toJSONString(dto));
       if (dto.getPageNum() == null) {
           dto.setPageNum(PageConstant.pageNum);
       }
       if (dto.getPageSize() == null) {
           dto.setPageSize(PageConstant.pageSize);
       }
       return  iIndustryService.getIndustryDataList(page,dto);
    }
    /**
     * 保存数据
     * @param ato
     * @return
     */
    @RequestMapping(value="/addIndustry",method = RequestMethod.POST)
    @ResponseBody
    public Result addIndustry(@ModelAttribute AdAttachmentVo ato){
        if (ato ==null){
            return  new Result(false, "1", "传递参数");
        }
        System.out.print(ato.toString());
        if (ato.getAddress()==null){
            return new Result(false, "1", "请先上传图片");
        }
        if (ato.getName()==null){
            return new Result(false, "1", "请填写行业分类的名");
        }

        return iIndustryService.addIndustry(ato.getAddress(),ato.getName(),ato.getIsTop());
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
    @RequestMapping(value="/deleteIndustry",method=RequestMethod.POST)
    @ResponseBody
    public Result deleteIndustry(@RequestParam("id") Long id){
        if (id==null){
            return new Result(false, "1", "选中删除的数据");
        }
        return iIndustryService.deleteIndustry(id);
    }

    /**
     * 编辑行业分类
     * @param
     * @param
     * @return
     */
    @RequestMapping(value="/editIndustry",method=RequestMethod.POST)
    @ResponseBody
    public Result editIndustry(@ModelAttribute AdAttachmentVo ato){
        if (ato.getId()==null){
            return new Result(false, "1", "选中修改的数据");
        }
        if (ato.getName().trim().equals("")){
            return new Result(false,"1","请输入修改的名字");
        }
        return iIndustryService.editIndustry(ato.getId(),ato.getName(),ato.getIsTop());
    }

    /**
     * 查询一级行业分类
     * @param
     * @param
     * @return
     */
    @RequestMapping(value="/findFirstIndustry",method=RequestMethod.POST)
    @ResponseBody
    public Result findFirstIndustry(Model model){
        Result result = new Result();
        List<Map> industry = iIndustryService.findFirstIndustry();
        result.setCode("1");
        result.setData(industry);
       // model.addAttribute("industry",industry);

        return result;
    }
    /**
     * 保存二级行业分类
     * @param
     * @param
     * @return
     */
    @RequestMapping(value="/addSecondIndstry",method=RequestMethod.POST)
    @ResponseBody
    public Result addSecondIndstry(@ModelAttribute IndustrysDTO dato){
         if(dato.getSecondName() == null){
             return new Result(false, "1", "请输入二级的行业名");
         }
         if(dato==null){
             return new Result(false, "1", "请选择二级行业分类");
         }
     return    iIndustryService.addSecondIndstry(dato.getSecondName(),dato.getType());
    }
}
