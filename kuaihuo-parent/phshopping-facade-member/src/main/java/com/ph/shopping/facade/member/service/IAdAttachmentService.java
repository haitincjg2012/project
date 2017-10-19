package com.ph.shopping.facade.member.service;

import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.member.dto.AdAtachmentDTO;


/**
 * Created by wudi on 2017/9/1.
 */
public interface IAdAttachmentService {

    /**
     * 获取首页轮播图的列表
     * @param dto
     * @return
     */
    public Result getDataAtachmentList(AdAtachmentDTO dto);

    public Result addAdAttachment(String address,String name,String type,String detail_type);

    public Result deleteAttachment(Long id);

    public Result editAttachment(Long id,String name,String detail_content);
}
