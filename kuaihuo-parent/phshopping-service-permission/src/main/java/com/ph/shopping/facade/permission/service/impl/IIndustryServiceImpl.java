package com.ph.shopping.facade.permission.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.mapper.IndustrysMapper;
import com.ph.shopping.facade.member.dto.IndustrysDTO;
import com.ph.shopping.facade.member.entity.Attachment;
import com.ph.shopping.facade.member.service.IIndustryService;
import com.ph.shopping.facade.member.vo.IndustryVO;
import com.ph.shopping.facade.permission.exception.PermissionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by wudi on 2017/9/2.
 */
@Component
@Service(version = "1.0.0")
public class IIndustryServiceImpl implements IIndustryService {

    @Autowired
    private IndustrysMapper industrysMapper;

    public IIndustryServiceImpl() {
    }

    @Override
    public Result getIndustryDataList(PageBean page,IndustrysDTO dto) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<IndustryVO> vo = industrysMapper.getIndustryDataList();
        PageInfo<IndustryVO> pageInfo = new PageInfo<IndustryVO>(vo);
        return ResultUtil.getResult(PermissionEnum.Code.SUCCESS, pageInfo.getList(), pageInfo.getTotal());

    }

    @Override
    public Result addIndustry(String address, String name, Integer isTop) {
        try {
            //保存图片到attachment表中
            industrysMapper.addPicture(address, name);
            //查询attachment
            Attachment attachment = industrysMapper.selectPicture(address, name);
            //保存alq_industry表
            industrysMapper.addIndustry(attachment.getId(), name, isTop);
            return new Result(true, "保存成功", 0);
        } catch (Exception e) {
            return new Result(false, "保存错误", 1);
        }
    }

    public Result deleteIndustry(Long id) {
        Result result = new Result();

        if (id == null) {
            result.setCode("100");
            result.setMessage("请选择id");
            result.setSuccess(false);
            return result;}
        try {
            industrysMapper.deleteIndustry(id);
            result.setCode("200");
            result.setMessage("删除成功");
            result.setSuccess(true);
            return result;
        } catch (Exception e) {
            result.setCode("100");
            result.setMessage("删除失败");
            result.setSuccess(true);
            return result;
        }
    }

    @Override
    public Result editIndustry(Long id, String name, Integer isTop) {
        Result result = new Result();
        try {
            industrysMapper.editIndustry(id, name, isTop);
            result.setCode("200");
            result.setMessage("编辑成功");
            result.setSuccess(true);
            return result;
        } catch (Exception e) {
            result.setCode("100");
            result.setMessage("编辑失败");
            result.setSuccess(true);
            return result;
        }


    }

    @Override
    public List<Map> findFirstIndustry() {
        return industrysMapper.findFirstIndustry();
    }

    @Override
    public Result addSecondIndstry(String name, Long id) {
        try {
            //保存二级行业分类,关联一级行业分类
            industrysMapper.addSecondIndstry(name, id);
            return new Result(true, "保存成功", 1);
        } catch (Exception e) {
            return new Result(false, "保存错误", 0);
        }

    }


}
