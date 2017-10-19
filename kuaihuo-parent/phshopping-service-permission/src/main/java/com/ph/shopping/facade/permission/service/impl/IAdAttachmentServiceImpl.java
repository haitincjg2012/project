package com.ph.shopping.facade.permission.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.mapper.AdAttachmentsMapper;
import com.ph.shopping.facade.member.dto.AdAtachmentDTO;
import com.ph.shopping.facade.member.service.IAdAttachmentService;
import com.ph.shopping.facade.permission.exception.PermissionEnum;
import com.ph.shopping.facade.permission.vo.AdAttachmentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wudi on 2017/9/1.
 */
@Component
@Service(version = "1.0.0")
public class IAdAttachmentServiceImpl implements IAdAttachmentService {

    @Autowired
    private AdAttachmentsMapper adAtachmentsMapper;

    @Override
    public Result getDataAtachmentList(AdAtachmentDTO dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        List<AdAttachmentVo> list = adAtachmentsMapper.getDataAtachmentList(dto);
        PageInfo<AdAttachmentVo> pageInfo = new PageInfo<AdAttachmentVo>(list);
        return ResultUtil.getResult(PermissionEnum.Code.SUCCESS,pageInfo.getList(),pageInfo.getTotal());

    }

    public Result addAdAttachment(String address,String name,String type,String detail_type){
        Result result = new Result();
        try{
            adAtachmentsMapper.addAdAttachment(address, name,type,detail_type);
            result.setCode("200");
            result.setMessage("添加成功");
            result.setSuccess(true);
           return result;
        }catch(Exception e){
            result.setCode("100");
            result.setMessage("添加失败");
            result.setSuccess(false);
            return result;
        }


    }

    @Override
    public Result deleteAttachment(Long id) {
        Result result = new Result();
        try{
            adAtachmentsMapper.deleteAttachment(id);

            result.setCode("200");
            result.setMessage("删除成功");
            result.setSuccess(true);
            return result;
        }catch(Exception e){
            result.setCode("100");
            result.setMessage("删除失败");
            result.setSuccess(false);
            return result;
        }
    }

    @Override
    public Result editAttachment(Long id, String name,String detail_content) {
        Result result = new Result();
        try{
            adAtachmentsMapper.editAttachment(id, name,detail_content);

            result.setCode("200");
            result.setMessage("编辑成功");
            result.setSuccess(true);
            return result;
        }catch(Exception e){
            result.setCode("100");
            result.setMessage("编辑失败");
            result.setSuccess(false);
            return result;
        }
    }
}
